/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.List;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.trinisoft.baselib.util.Echo;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.enduome.models.Message;
import java.util.Vector;

/**
 *
 * @author trinisoftinc
 */
public class ChatsContainer extends Container {
    EnduoMe parent;
    String from;

    public ChatsContainer(EnduoMe parent, String from) {
        this.parent = parent;
        this.from = from;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    }

    public void addChat(Message message) {
        if(message.getFrom().equals(from)) {
            Container chatContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            TextArea area = new TextArea(message.getMsg());
            area.setRows(2);
            area.setGrowByContent(true);

            chatContainer.addComponent(area);

            this.addComponent(0, chatContainer);
            area.setFocus(true);
            this.repaint();
        }
    }
}
