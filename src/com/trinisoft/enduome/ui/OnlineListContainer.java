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
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ContainerList;
import com.sun.lwuit.list.DefaultListModel;
import com.trinisoft.baselib.util.Echo;
import com.trinisoft.enduome.EnduoMe;
import java.util.Vector;

/**
 *
 * @author trinisoftinc
 */
public class OnlineListContainer extends ContainerList {

    EnduoMe parent;
    Vector onlineVector;
    //List online = null;

    public OnlineListContainer(EnduoMe parent) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.parent = parent;
        init();                
    }

    public void refresh() {
        onlineVector = parent.client.onlineList;
        
        DefaultListModel model = new DefaultListModel(onlineVector);        
        setModel(model);    
        setRenderer(new Renderers.ButtonRenderer());
        
        System.out.println(getModel().getSize());
        System.out.println(getModel().getItemAt(0));        
    }
    
    private void init() {        
        onlineVector = parent.client.onlineList;
        
        DefaultListModel model = new DefaultListModel(onlineVector);        
        setModel(model);
        
        addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                String selectedItem = getSelectedItem().toString();
                System.out.println("Here in OLC");
                parent.client.updateChatList(selectedItem);

                /**
                if (parent.homeForm.chattersListContainer == null) {
                    parent.homeForm.chattersListContainer = new ChattersListContainer(parent, new ChattersListCommander(parent));
                } else {
                    parent.homeForm.chattersListContainer.chatters.addItem(selectedItem);
                }
                parent.homeForm.chattersListContainer.setSelectedItem(selectedItem);
                
                parent.homeForm.showForm(HomeForm.CHATS_FORM_SHOW_STRING);
                * **/
            }
        });
        
        setRenderer(new Renderers.ButtonRenderer());
        System.out.println(getModel().getSize());
        System.out.println(getModel().getItemAt(0));        
        //online.setListCellRenderer(new Renderers.ButtonRenderer());

        //addComponent(BorderLayout.CENTER, online);
    }
}
