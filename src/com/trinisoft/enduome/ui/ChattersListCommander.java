/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.trinisoft.enduome.EnduoMe;

/**
 *
 * @author trinisoftinc
 */
public class ChattersListCommander implements ActionListener {

    EnduoMe parent;

    public ChattersListCommander(EnduoMe parent) {
        this.parent = parent;
    }


    public void actionPerformed(ActionEvent ae) {
        parent.homeForm.showForm(HomeForm.CHATS_FORM_SHOW_STRING);
    }
}