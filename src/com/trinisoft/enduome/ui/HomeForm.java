/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Tabs;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.GridLayout;
import com.trinisoft.baselib.util.Date;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.enduome.models.Message;
import com.trinisoft.mlib.views.BaseForm;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;

/**
 *
 * @author trinisoftinc
 */
public class HomeForm extends BaseForm {

    EnduoMe parent;
    OnlineListContainer onlineNow;
    ChattersListContainer chattersList;
    public FriendsListContainer friendsList;
    Tabs homeTabs = new Tabs(Tabs.TOP);
    private static Hashtable chats = new Hashtable();
    public String currentTo;
    public MessagesForm messagesForm = null;
    public static final int ONLINE_TAB_INDEX = 0;
    public static final int CHATTERS_TAB_INDEX = 1;
    public static final int FRIENDS_TAB_INDEX = 2;
    public static final int QUIT_ACTION = 102;
    public static final int SHOW_OFFLINE_ACTION = 103;
    public static final int HIDE_OFFLINE_ACTION = 104;
    public static boolean showOffline = true;
    Command showOfflineCommand, hideOfflineCommand;

    public HomeForm(EnduoMe parent) {
        super(parent);
        this.parent = parent;
        init();
    }

    private void init() {
        setTitle("Enduo-Me : Welcome ");

        onlineNow = new OnlineListContainer(parent);
        chattersList = new ChattersListContainer(parent);
        friendsList = new FriendsListContainer(parent);

        homeTabs.getTabsContainer().setLayout(new GridLayout(1, 3));

        homeTabs.addTab("Online", onlineNow);
        homeTabs.addTab("Chats", chattersList);
        homeTabs.addTab("Friends", friendsList);

        Command quitCommmand = new Command("Logout", QUIT_ACTION);
        addCommand(quitCommmand);

        homeTabs.setSelectedIndex(0);

        showOfflineCommand = new Command("Show Offline", SHOW_OFFLINE_ACTION);
        hideOfflineCommand = new Command("Hide Offline", HIDE_OFFLINE_ACTION);

        addCommand(showOfflineCommand);
        addCommand(hideOfflineCommand);

        Button friendsButton = (Button) homeTabs.getTabsContainer().getComponentAt(2);
        Button chatsButton = (Button) homeTabs.getTabsContainer().getComponentAt(1);
        Button onlineButton = (Button) homeTabs.getTabsContainer().getComponentAt(0);

        chatsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                removeCommand(showOfflineCommand);
                removeCommand(hideOfflineCommand);
            }
        });

        onlineButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                removeCommand(showOfflineCommand);
                removeCommand(hideOfflineCommand);
            }
        });

        friendsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                try {
                    friendsList.refresh();
                    addCommand(showOfflineCommand);
                    addCommand(hideOfflineCommand);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //chatsBaseContainer.addComponent(BorderLayout.NORTH, sendContainer);



        addCommandListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                int cid = evt.getCommand().getId();

                switch (cid) {
                    case QUIT_ACTION:
                        parent.client.stop();
                        parent.loginForm.show();
                        break;
                    case SHOW_OFFLINE_ACTION:
                        showOffline = true;
                        try {
                            friendsList.refresh();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case HIDE_OFFLINE_ACTION:
                        showOffline = false;
                        try {
                            friendsList.refresh();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                }
            }
        });

        addComponent(homeTabs);
    }

    public void updateClientsList() {
        onlineNow.refresh();
    }

    public void doNewMessage(Message message) {
        String from = message.getFrom();
        String you = EnduoMe.loggedInUser.getUsername();

        if (from.equals(you)) {
            ChatsContainer meChat = (ChatsContainer) chats.get(message.getTo());
            meChat.addChat(message);
            messagesForm.myChatsField.setText("");
            return;
        }

        currentTo = from;
        if (chats.containsKey(from)) {
            ChatsContainer container = (ChatsContainer) chats.get(from);
            container.addChat(message);
        } else {
            ChatsContainer container = new ChatsContainer(parent, from);
            container.addChat(message);
            chats.put(from, container);
        }

        if (messagesForm != null) {
            if (messagesForm.isVisible()) {
                if (messagesForm.from.equals(from)) {
                    startChat(from);
                    try {
                        Manager.playTone(69, 3000, 100);
                    } catch (MediaException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    showAlert(message);
                }
            } else {
                showAlert(message);
            }
        } else {
            showAlert(message);
        }
    }

    private void showAlert(Message message) {
        String from = message.getFrom();
        boolean show = Dialog.show("New Message from " + from, message.getMsg(), "View Message", "Cancel");
        if (show) {
            startChat(from);
        }
    }

    public void sendMessage() throws Exception {
        if (messagesForm.myChatsField.getText().length() <= 0) {
            Dialog.show("Error", "Please type a message.", "OK", "");
        } else {
            Message message = new Message();
            message.setFrom(EnduoMe.loggedInUser.getUsername());
            message.setTo(currentTo);
            message.setTime(new Date());
            message.setMsg(messagesForm.myChatsField.getText());
            parent.client.sendMessage(message);
        }
    }

    public void startChat(String from) {
        ChatsContainer chatsContainer = null;
        if (chats.get(from) == null) {
            chatsContainer = new ChatsContainer(parent, from);
            chats.put(from, chatsContainer);
        } else {
            chatsContainer = (ChatsContainer) chats.get(from);
        }

        chatsContainer.setScrollableY(true);

        if (messagesForm == null) {
            messagesForm = new MessagesForm(parent, from, chatsContainer);
        } else {
            messagesForm.showChatContainer(chatsContainer);
        }

        messagesForm.setFrom(from);
        messagesForm.show();
    }

    public boolean isOnline(String user) {
        Vector onlineList = parent.client.onlineList;
        return onlineList.contains(user);
    }
}
