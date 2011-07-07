/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoftinc.enduome.threads;

import com.trinisoft.baselib.io.BufferedReader;
import com.trinisoft.baselib.io.BufferedWriter;
import com.trinisoft.baselib.util.Echo;
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
    private Vector clientsList, messageList;
    private Message lastMessage;
    public boolean isRunning = false;

    public Client(String url) {
        this.url = url;
        clientsList = new Vector();
        messageList = new Vector();
    }

    public void start() {
        if (!isRunning) {
            Thread t = new Thread(this);
            t.start();
            isRunning = true;
        }
    }

    public void updateClientsList(Vector newList) {
        this.clientsList = newList;
        Enumeration clientsEnumeration = clientsList.elements();
        System.out.print("CLIENTS: ");
        while (clientsEnumeration.hasMoreElements()) {
            System.out.print(clientsEnumeration.nextElement().toString());
        }
        System.out.println();
        //do some ui things here too
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
        messageList.addElement(lastMessage);
        showMessages();
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
            System.out.println(clientDetails);
            writer.write(clientDetails, true);
            writer.flush();

            Echo.outln("flushed");
            BufferedReader reader = new BufferedReader(is);
            new ProtocolHandler(this, reader, writer).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMessages() {
        Enumeration msgEnumeration = messageList.elements();
        while (msgEnumeration.hasMoreElements()) {
            Message m = (Message) msgEnumeration.nextElement();
            System.out.println(m.toNamedString());
        }
    }
}