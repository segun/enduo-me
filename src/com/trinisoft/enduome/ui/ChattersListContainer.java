/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ContainerList;
import com.sun.lwuit.list.DefaultListModel;
import com.trinisoft.enduome.EnduoMe;

/**
 *
 * @author trinisoftinc
 */
public class ChattersListContainer extends ContainerList {

    EnduoMe parent;
    //Vector chatList;
    //List chatters;
    ActionListener chattersListCommander;

    public ChattersListContainer(EnduoMe parent, ActionListener chattersListCommander) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.parent = parent;
        this.chattersListCommander = chattersListCommander;
        init();
    }

    public void refresh() {
        DefaultListModel model = new DefaultListModel(parent.client.chattersList);
        setModel(model);
        setRenderer(new Renderers.ButtonRenderer());

    }

    private void init() {
        refresh();
    }
}
