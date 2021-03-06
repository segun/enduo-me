/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.enduome.models.Message;

/**
 *
 * @author trinisoftinc
 */
public class ChatsContainer extends Container {

    EnduoMe parent;
    String from;

    public ChatsContainer(EnduoMe parent, String from) {
        this.parent = parent;
        this.from = from;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    }

    public void addChat(Message message) {

        String you = parent.loggedInUser.getUsername();
        String messageFrom = message.getFrom();
        if (message.getFrom().equals(from) || message.getTo().equals(from)) {
            Container chatContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container oneMessageContainer = new Container(new BorderLayout());

            TextArea area = new TextArea(message.getMsg());
            area.setRows(3);

            oneMessageContainer.addComponent(BorderLayout.CENTER, area);
            Label nameLabel = new Label("");
            if (messageFrom.equals(you)) {
                nameLabel.setText("YOU");
                nameLabel.getStyle().setBgColor(0x657300);

                area.getSelectedStyle().setBorder(Border.createBevelRaised());
                area.getSelectedStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM), true);
                area.getSelectedStyle().setBgColor(0x657300);
                area.getSelectedStyle().setFgColor(0xffffff);
                
                area.getUnselectedStyle().setBorder(Border.createLineBorder(5));
                area.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM), true);
                area.getUnselectedStyle().setBgColor(0x657300);
                area.getUnselectedStyle().setFgColor(0xffffff);
            } else {
                nameLabel.setText(from);
                nameLabel.getStyle().setBgColor(0x657383);

                area.getSelectedStyle().setBorder(Border.createBevelRaised());
                area.getSelectedStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM), true);
                area.getSelectedStyle().setBgColor(0x657383);
                area.getSelectedStyle().setFgColor(0xffffff);
                
                area.getUnselectedStyle().setBorder(Border.createLineBorder(5));
                area.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM), true);
                area.getUnselectedStyle().setBgColor(0x657383);
                area.getUnselectedStyle().setFgColor(0xffffff);
            }

            oneMessageContainer.addComponent(BorderLayout.NORTH, nameLabel);
            nameLabel.setText(nameLabel.getText() + " | " + message.getTime().toString());
            nameLabel.getStyle().setBorder(Border.createRoundBorder(10, 10, 0x0ff12f, true));
            nameLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_SMALL), true);
            nameLabel.getStyle().setFgColor(0xffffff);            

            chatContainer.addComponent(oneMessageContainer);

            this.addComponent(0, chatContainer);
            //area.requestFocus();
            this.repaint();
        }
    }
}
