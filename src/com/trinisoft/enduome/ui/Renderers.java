/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.list.CellRenderer;
import com.trinisoft.enduome.EnduoMe;

/**
 *
 * @author trinisoftinc
 */
public final class Renderers {

    public static class ButtonRenderer implements CellRenderer {

        public Component getCellRendererComponent(Component list, Object model, Object value, int index, boolean isSelected) {
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

        public Component getFocusComponent(Component list) {
            return null;
        }
    }

    public static class FriendsListButtonsRenderer implements CellRenderer {

        EnduoMe parent;

        public FriendsListButtonsRenderer(EnduoMe parent) {
            this.parent = parent;
        }
        
        
        public Component getCellRendererComponent(Component list, Object model, Object value, int index, boolean isSelected) {
            Command c = new Command(String.valueOf(value), index);
            Button b = new Button(c);
            if (isSelected) {
                b.setFocus(true);
                b.getStyle().setBgColor(0x6d869e);
                return b;
            }
            if(!parent.homeForm.isOnline(String.valueOf(value))) {
                b.setUIID("Offline");
            }
            return b;
        }

        public Component getFocusComponent(Component list) {
            return null;
        }
    }
}
