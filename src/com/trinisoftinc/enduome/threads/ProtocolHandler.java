/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoftinc.enduome.threads;

import com.trinisoft.baselib.io.BufferedReader;
import com.trinisoft.baselib.io.BufferedWriter;
import com.trinisoft.baselib.util.Date;
import com.trinisoft.baselib.util.Echo;
import com.trinisoft.baselib.util.MStrings;
import com.trinisoft.enduome.models.Message;
import java.io.IOException;
import java.util.Enumeration;

/**
 *
 * @author trinisoftinc
 */
public class ProtocolHandler implements Runnable {

    private BufferedReader reader;
    private BufferedWriter writer;
    private Client client;
    private boolean isRunning = false;

    public ProtocolHandler(Client client, BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
        this.client = client;
    }

    public void start() {
        Echo.outln("In Start: " + isRunning);
        if(!isRunning) {
            Thread t = new Thread(this);
            t.start();
            isRunning = true;
        }
    }
    public void run() {
        Echo.outln("In RUN");
        while (isRunning) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    if (line.startsWith("clients:")) {
                        line = MStrings.replace(line, "clients:", "");
                        client.updateClientsList(MStrings.splitString(line, ","));
                    } else if (line.startsWith("message:")) {
                        line = MStrings.replace(line, "message:", "");
                        Message message = parseMessage(line);
                        client.setLastMessage(message);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    private Message parseMessage(String clientMessage) {
        Message message = new Message();
        Enumeration splitted = MStrings.splitString(clientMessage, ":s").elements();
        while (splitted.hasMoreElements()) {
            String token = splitted.nextElement().toString();
            if (MStrings.contains(token, "from=")) {
                message.setFrom(token.substring((token.indexOf("=") + 1), token.length()));
            } else if (MStrings.contains(token, "to=")) {
                message.setTo(token.substring((token.indexOf("=") + 1), token.length()));
            } else if (MStrings.contains(token, "date=")) {
                long dateInMillis = Long.parseLong(token.substring((token.indexOf("=") + 1), token.length()));
                Date date = new Date(dateInMillis);
                message.setTime(date);
            } else if(MStrings.contains(token, "msg=")) {
                message.setMsg(token.substring((token.indexOf("=") + 1), token.length()));
            }
        }
        return message;
    }
}
