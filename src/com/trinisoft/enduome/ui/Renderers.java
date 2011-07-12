/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Border;
import com.trinisoft.baselib.util.Echo;
import com.trinisoft.enduome.models.Message;

/**
 *
 * @author trinisoftinc
 */
public final class Renderers {

    public static class SingleChatRenderer implements ListCellRenderer {

        String owner;

        public SingleChatRenderer(String owner) {
            this.owner = owner;
        }

        public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
            Message message = (Message) value;

            Container container = new Container(new BorderLayout());

            Label nameLabel = new Label(message.getFrom());
            nameLabel.getStyle().setBgColor(0x4AA02C);
            nameLabel.animate();
            nameLabel.setScrollAnimationSpeed(500);

            TextArea area = new TextArea(message.getMsg());
            area.setRows(2);
            area.setGrowByContent(true);
            area.setEditable(false);
            area.setIsScrollVisible(true);

            container.addComponent(BorderLayout.NORTH, nameLabel);
            container.addComponent(BorderLayout.CENTER, area);

            if (isSelected) {
                area.setFocus(true);
                area.setEditable(true);
                area.getStyle().setBorder(Border.createBevelRaised());
            }
            Echo.outln(area.getActualRows() + "");
            return container;

        }

        public Component getListFocusComponent(List list) {
            return null;
        }
    }

    public static class ButtonRenderer implements ListCellRenderer {

        public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
            Command c = new Command(String.valueOf(value), index);
            Button b = new Button(c);
            if (isSelected) {
                b.setFocus(true);
                b.getStyle().setBgColor(0x6d869e);
                //b.setUIID("ComboBox");
                return b;
            }
            return b;
        }

        public Component getListFocusComponent(List list) {
            return null;
        }
    }
}
