/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.FlowLayout;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.enduome.models.Message;
import com.trinisoft.mlib.views.BaseForm;
import java.util.Hashtable;

/**
 *
 * @author trinisoftinc
 */
public class HomeForm extends BaseForm {

    private EnduoMe parent;
    private OnlineListContainer onlineListForm;
    private ChattersListContainer chattersListContainer;
    private ChatsContainer aChat;
    private static final String ONLINE_LIST_SHOW_STRING = "online_list_form";
    private static final String CHATTERS_LIST_SHOW_STRING = "chatters_list_form";
    private static final String CHATS_FORM_SHOW_STRING = "chats_form";
    private static Hashtable chats = new Hashtable();
    public static final int SHOW_ONLINE_LIST_FORM = 0;
    public static final int SHOW_CHAT_LIST_FORM = 1;

    public HomeForm(EnduoMe parent) {
        super(parent);
        this.parent = parent;
        init();
    }

    public void doNewMessage(Message message) {
        String from = message.getFrom();
        if (chats.containsKey(from)) {
            ChatsContainer container = (ChatsContainer) chats.get(from);
            container.addChat(message);
        } else {
            ChatsContainer container = new ChatsContainer(parent, from);
            container.addChat(message);
            chats.put(from, container);
        }

        tryShowAlert(message);
    }

    public void updateClientsList() {
        if (EnduoMe.current instanceof OnlineListContainer || EnduoMe.current instanceof HomeForm) {
            showForm(ONLINE_LIST_SHOW_STRING);
        }
    }

    private void tryShowAlert(Message message) {
        if (EnduoMe.current instanceof OnlineListContainer) {
            showAlert(message);
        } else if (EnduoMe.current instanceof ChattersListContainer) {
            showForm(CHATTERS_LIST_SHOW_STRING);
            showAlert(message);
        } else if (EnduoMe.current instanceof ChatsContainer) {
            ChatsContainer oneChat = (ChatsContainer) EnduoMe.current;
            String from = message.getFrom();
            if (oneChat.from.equals(from)) {
                //do nothing
            } else {
                showAlert(message);
            }
        }
    }

    private void showAlert(Message message) {
        String from = message.getFrom();
        boolean show = Dialog.show("New Message from " + from, message.getMsg(), "View Message", "Cancel");
        if (show) {
            chattersListContainer.setSelectedItem(from);
            showForm(CHATS_FORM_SHOW_STRING);
        }
    }

    private void init() {
        setTitle("Enduo-Me");
        setLayout(new BorderLayout());
        Command c = new Command("Online", SHOW_ONLINE_LIST_FORM);
        Command d = new Command("Chat List", SHOW_CHAT_LIST_FORM);
        Container menu = new Container(new FlowLayout());
        menu.addComponent(new Button(c));
        menu.addComponent(new Button(d));
        addCommand(c);
        addCommandListener(new HomeFormCommander());

        addComponent(BorderLayout.NORTH, menu);
        addComponent(BorderLayout.CENTER, (onlineListForm = new OnlineListContainer(parent)));
    }

    public void showForm(String whichForm) {
        ChattersListCommander chattersListCommander = new ChattersListCommander();
        if (whichForm.equals(ONLINE_LIST_SHOW_STRING)) {
            if (contains(onlineListForm)) {
                replace(onlineListForm, (onlineListForm = new OnlineListContainer(parent)), in);
            } else if (contains(chattersListContainer)) {
                replace(chattersListContainer, (onlineListForm = new OnlineListContainer(parent)), in);
            } else if (contains(aChat)) {
                replace(aChat, (onlineListForm = new OnlineListContainer(parent)), in);
            }
            EnduoMe.current = onlineListForm;
        } else if (whichForm.equals(CHATTERS_LIST_SHOW_STRING)) {
            if (contains(chattersListContainer)) {
                replace(chattersListContainer, (chattersListContainer = new ChattersListContainer(parent, chattersListCommander)), in);
            } else if (contains(onlineListForm)) {
                replace(onlineListForm, (chattersListContainer = new ChattersListContainer(parent, chattersListCommander)), in);
            } else if (contains(aChat)) {
                replace(aChat, (chattersListContainer = new ChattersListContainer(parent, chattersListCommander)), in);
            }
            EnduoMe.current = chattersListContainer;
        } else if (whichForm.equals(CHATS_FORM_SHOW_STRING)) {
            String from = chattersListContainer.getSelectedItem();
            ChatsContainer container = (ChatsContainer) chats.get(from);
            System.out.println(from + " : " + container + ", " + chats.size() + " : " + chats);
            if (contains(chattersListContainer)) {
                aChat = container;
                replace(chattersListContainer, aChat, in);
            } else if (contains(onlineListForm)) {
                aChat = container;
                replace(onlineListForm, aChat, in);
            } else if (contains(aChat)) {
                replace(aChat, (aChat = container), in);
            }
            EnduoMe.current = aChat;
        }
    }

    private class HomeFormCommander implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            int cid = ae.getCommand().getId();
            switch (cid) {
                case SHOW_ONLINE_LIST_FORM:
                    showForm(ONLINE_LIST_SHOW_STRING);
                    break;
                case SHOW_CHAT_LIST_FORM:
                    showForm(CHATTERS_LIST_SHOW_STRING);
                    break;
            }
        }
    }

    private class ChattersListCommander implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            showForm(CHATS_FORM_SHOW_STRING);
        }
    }
}
