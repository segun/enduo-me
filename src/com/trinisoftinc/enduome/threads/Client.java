/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoftinc.enduome.threads;

import com.trinisoft.baselib.io.BufferedReader;
import com.trinisoft.baselib.io.BufferedWriter;
import com.trinisoft.baselib.util.Echo;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.enduome.models.Message;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author trinisoftinc
 */
public class Client implements Runnable {

    private String url;
    public Vector onlineList, messageList, chattersList;
    private Message lastMessage;
    public boolean isRunning = false;
    EnduoMe parent;

    public Client(String url, EnduoMe parent) {
        this.url = url;
        this.parent = parent;
        onlineList = new Vector();
        messageList = new Vector();
        chattersList = new Vector();
    }

    public void start() {
        if (!isRunning) {
            Thread t = new Thread(this);
            t.start();
            isRunning = true;
        }
    }

    public void updateClientsList(Vector newList) {
        this.onlineList = newList;
        parent.homeForm.updateClientsList();
    }

    public void updateChatList(String chatUser) {
        if(!chattersList.contains(chatUser)) {
            chattersList.addElement(chatUser);
        }
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
        messageList.addElement(lastMessage);
        updateChatList(lastMessage.getFrom());
        parent.homeForm.doNewMessage(lastMessage);
    }

    public void run() {
        try {
            Echo.outln("In RUN: tryeing to connect");
            SocketConnection connection = (SocketConnection) Connector.open(url);
            InputStream is = connection.openInputStream();
            OutputStream os = connection.openOutputStream();
            Echo.outln("In RUN: cnnected");
            BufferedWriter writer = new BufferedWriter(os);

            String clientDetails = "details:port=" + connection.getLocalPort() + ":sname=" + "Mobility" + ":saddress=" + connection.getLocalAddress();
            writer.write(clientDetails, true);
            writer.flush();

            BufferedReader reader = new BufferedReader(is);
            new ProtocolHandler(this, reader, writer).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
