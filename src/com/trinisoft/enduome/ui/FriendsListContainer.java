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
import com.trinisoft.enduome.EnduoMe;
import java.util.Vector;
import trinisoftinc.json.me.MyJSONArray;

/**
 *
 * @author trinisoftinc
 */
public class FriendsListContainer extends ContainerList {

    EnduoMe parent;

    public FriendsListContainer(EnduoMe parent) {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.parent = parent;
        init();
    }

    public void refresh() throws Exception {
        System.out.println("Calling refresh");
        StorableList friends = EnduoMe.loggedInUser.getFriends();
        Vector friendsList = new Vector();
        MyJSONArray friendsArray = new MyJSONArray(friends.toJSONString());

        int size = friendsArray.length();

        for (int i = 0; i < size; i++) {
            String user = friendsArray.getString(i);
            if (HomeForm.showOffline) {
                friendsList.addElement(user);
            } else {
                if (parent.homeForm.isOnline(user)) {
                    friendsList.addElement(user);
                }
            }
        }

        DefaultListModel model = new DefaultListModel(friendsList);
        setModel(model);
        setRenderer(new Renderers.FriendsListButtonsRenderer(parent));
    }

    private void init() {
        try {
            refresh();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                final String selectedItem = getSelectedItem().toString();
                parent.homeForm.currentTo = selectedItem;
                parent.homeForm.startChat(selectedItem);
            }
        });
    }
}
