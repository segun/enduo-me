/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.FlowLayout;
import com.trinisoft.baselib.util.Echo;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.enduome.models.Message;
import com.trinisoft.mlib.views.BaseForm;
import java.util.Vector;

/**
 *
 * @author trinisoftinc
 */
public class HomeForm extends BaseForm {

    private EnduoMe parent;
    private OnlineListForm onlineListForm;
    private ChatListForm chatListForm;
    public static final int SHOW_ONLINE_LIST_FORM = 0;
    public static final int SHOW_CHAT_LIST_FORM = 1;

    public HomeForm(EnduoMe parent) {
        super(parent);
        this.parent = parent;
        init();
    }

    public void alertNewMessage(Message message) {
        Dialog.show("New Message", "You got a new message from " + message.getFrom() + ".\n Message: " + message.getMsg(), "View Message", "Cancel");
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
        addComponent(BorderLayout.CENTER, (onlineListForm = new OnlineListForm(parent)));
    }

    private class HomeFormCommander implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            int cid = ae.getCommand().getId();
            switch (cid) {
                case SHOW_ONLINE_LIST_FORM:
                    if(contains(onlineListForm)) {
                        replace(onlineListForm, (onlineListForm = new OnlineListForm(parent)), in);
                    } else if(contains(chatListForm)) {
                        replace(chatListForm, (onlineListForm = new OnlineListForm(parent)), in);
                    }
                    break;
                case SHOW_CHAT_LIST_FORM:
                    if(contains(chatListForm)) {
                        replace(chatListForm, (chatListForm = new ChatListForm(parent)), in);
                    } else if(contains(onlineListForm)) {
                        replace(onlineListForm, (chatListForm = new ChatListForm(parent)), in);
                    }
                    break;
            }
        }
    }
}
