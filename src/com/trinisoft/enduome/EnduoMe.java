/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome;

import com.trinisoftinc.enduome.threads.Client;
import javax.microedition.midlet.*;

/**
 * @author trinisoftinc
 */
public class EnduoMe extends MIDlet {

    public void startApp() {
        new Thread() {

            public void run() {
                new Client("socket://localhost:1981").start();
            }

        }.start();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
