/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.List;
import com.sun.lwuit.list.ListCellRenderer;

/**
 *
 * @author trinisoftinc
 */
public final class Renderers {

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
