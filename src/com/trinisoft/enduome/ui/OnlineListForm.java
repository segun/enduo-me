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
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.mlib.views.BaseForm;
import java.util.Vector;

/**
 *
 * @author trinisoftinc
 */
public class OnlineListForm extends Container {
    EnduoMe parent;
    Vector onlineVector;
    List online = null;

    public OnlineListForm(EnduoMe parent) {
        setLayout(new BorderLayout());
        this.parent = parent;
        init();
    }

    private void init() {
        if(online == null) {
            onlineVector = parent.client.onlineList;
            online = new List(onlineVector);
            online.setListCellRenderer(new ButtonRenderer());
        }
        addComponent(BorderLayout.NORTH, new Label("Online Now!!!"));
        addComponent(BorderLayout.CENTER, online);
    }

    class ButtonRenderer implements ListCellRenderer {

        public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
            Command c = new Command(String.valueOf(value));
            if(isSelected) {
                Button b = new Button(c);
                b.setFocus(true);
                b.getStyle().setBgColor(0x6d869e);
                b.setUIID("ComboBox");
                return b;
            }
            return new Button(c);
        }


        public Component getListFocusComponent(List list) {
            return null;
        }

    }
}
