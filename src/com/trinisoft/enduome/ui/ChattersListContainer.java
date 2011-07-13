/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Container;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.trinisoft.baselib.util.Echo;
import com.trinisoft.enduome.EnduoMe;
import java.util.Vector;

/**
 *
 * @author trinisoftinc
 */
public class ChattersListContainer extends Container {
    EnduoMe parent;
    Vector chatList;
    List chatters;

    ActionListener chattersListCommander;

    public ChattersListContainer(EnduoMe parent, ActionListener chattersListCommander) {
        this.parent = parent;
        this.chattersListCommander = chattersListCommander;
        init();
    }

    public String getSelectedItem() {
        return chatters.getSelectedItem().toString();
    }

    public void setSelectedItem(String item) {
        chatters.setSelectedItem(item);
    }

    public void addItem(String item) {
        chatters.addItem(item);
    }

    private void init() {
        setLayout(new BorderLayout());
        chatList = parent.client.chattersList;
        chatters = new List(chatList);

        chatters.addActionListener(chattersListCommander);

        chatters.setListCellRenderer(new Renderers.ButtonRenderer());
        addComponent(BorderLayout.CENTER, chatters);
    }
}
