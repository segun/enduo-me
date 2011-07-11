/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Container;
import com.sun.lwuit.List;
import com.sun.lwuit.layouts.BorderLayout;
import com.trinisoft.enduome.EnduoMe;
import java.util.Vector;

/**
 *
 * @author trinisoftinc
 */
public class ChatListForm extends Container {
    EnduoMe parent;
    Vector chatList;
    List chatters;

    public ChatListForm(EnduoMe parent) {
        this.parent = parent;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        chatList = parent.client.chatList;

        chatters = new List(chatList);
        chatters.setListCellRenderer(new Renderers.ButtonRenderer());
        addComponent(BorderLayout.CENTER, chatters);
    }
}
