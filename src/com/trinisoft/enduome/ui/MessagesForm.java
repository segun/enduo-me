/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.mlib.views.BaseForm;
import com.trinisoft.mlib.views.XTextField;

/**
 *
 * @author trinisoftinc
 */
public class MessagesForm extends BaseForm {
    EnduoMe parent;
    ChatsContainer chatsContainer;
    String from;
    
    public XTextField myChatsField;
    private Button sendButton;  
    
    Container sendContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

    public MessagesForm(EnduoMe midlet, String from, ChatsContainer container) {
        super(midlet);
        this.parent = midlet;
        this.chatsContainer = container;
        this.from = from;
        init();
    }

    public void setFrom(String from) {
        this.from = from;
        setTitle("Enduo-Me : Chat with " + from);
    }
    
    
    private void init() {
        setTitle("Enduo-Me : Chat with " + from);
        setScrollableY(false);
        setLayout(new BorderLayout());
        
        addComponent(BorderLayout.CENTER, chatsContainer);
        
        Command back = new Command("Home");
        Command up = new Command("Reply");
        
        addCommand(back);
        addCommand(up);
        
        myChatsField = new XTextField("");
        sendButton = new Button(new Command("Send"));

        sendContainer.addComponent(myChatsField);
        sendContainer.addComponent(sendButton);
        
        addComponent(BorderLayout.NORTH, sendContainer);
        
        myChatsField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                try {
                    parent.homeForm.sendMessage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        sendButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                try {
                    parent.homeForm.sendMessage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        addCommandListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(evt.getCommand().getCommandName().equals("Home")) {
                    parent.homeForm.showBack();
                } else if(evt.getCommand().getCommandName().equals("Reply")) {
                    myChatsField.requestFocus();
                    myChatsField.setFocus(true);
                }
            }
        });
    }
    
    public void showChatContainer(ChatsContainer container) {
        if(!contains(container)) {            
            addComponent(BorderLayout.CENTER, container);
        }
    }
}
