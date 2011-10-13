/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ContainerList;
import com.sun.lwuit.list.DefaultListModel;
import com.trinisoft.baselib.db.StorableList;
import com.trinisoft.baselib.db.decorator.StoreDecorator;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.enduome.entities.EntityConstants;

/**
 *
 * @author trinisoftinc
 */
public class OnlineListContainer extends ContainerList {

    EnduoMe parent;

    public OnlineListContainer(EnduoMe parent) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.parent = parent;
        init();
    }

    public void refresh() {
        DefaultListModel model = new DefaultListModel(parent.client.onlineList);
        removeAll();
        setModel(model);
        setRenderer(new Renderers.ButtonRenderer());

    }

    private void init() {
        refresh();

        addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                final String selectedItem = getSelectedItem().toString();

                parent.client.updateChatList(selectedItem);
                parent.homeForm.chattersList.refresh();


                try {
                    if (!EnduoMe.loggedInUser.isFriend(selectedItem)) {
                        if (parent.homeForm.showConfirmDialog(selectedItem + " is not on your friend's list. Do you want to add before starting chat?")) {
                            StorableList userFriends = EnduoMe.loggedInUser.getFriends();
                            if (userFriends == null) {
                                userFriends = new StorableList();
                            } else {
                                userFriends.addElement(selectedItem);
                                EnduoMe.loggedInUser.setFriends(userFriends);
                                new StoreDecorator(EnduoMe.loggedInUser).update(EntityConstants.USER_STORE, EnduoMe.loggedInUser.toJSONString().getBytes(), EnduoMe.loggedInUser.getId());
                            }
                        } else {
                            //do nothing
                        }
                    } else {
                        //do nothing
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                parent.homeForm.currentTo = selectedItem;                        
                parent.homeForm.startChat(selectedItem);
            }
        });
    }
}
