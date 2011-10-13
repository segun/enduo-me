/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.*;
import com.sun.lwuit.list.ContainerList;
import com.trinisoft.baselib.util.Date;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.enduome.models.Message;
import com.trinisoft.mlib.views.BaseForm;
import java.util.Hashtable;
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
    Tabs homeTabs = new Tabs(Tabs.TOP);
    private static Hashtable chats = new Hashtable();
    public String currentTo;
    //Container chatsContainer = new Container(new BorderLayout());    
    public MessagesForm messagesForm = null;
    public static final int ONLINE_TAB_INDEX = 0;
    public static final int CHATTERS_TAB_INDEX = 1;
    public static final int FRIENDS_TAB_INDEX = 2;

    public HomeForm(EnduoMe parent) {
        super(parent);
        this.parent = parent;
        init();
    }

    private void init() {
        setTitle("Enduo-Me : Welcome ");

        onlineNow = new OnlineListContainer(parent);
        chattersList = new ChattersListContainer(parent, null);

        homeTabs.addTab("Online", onlineNow);
        homeTabs.addTab("Chats", chattersList);
        homeTabs.addTab("Friends", new ContainerList());

        homeTabs.setSelectedIndex(0);

        //chatsBaseContainer.addComponent(BorderLayout.NORTH, sendContainer);

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
}
