/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.GridLayout;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Log;
import com.sun.lwuit.util.Resources;
import com.trinisoft.baselib.io.HttpPull;
import com.trinisoft.baselib.util.Echo;
import com.trinisoft.enduome.entities.EntityConstants;
import com.trinisoft.enduome.entities.User;
import com.trinisoft.enduome.ui.HomeForm;
import com.trinisoft.enduome.ui.LoginForm;
import com.trinisoft.mlib.util.URLConstants;
import com.trinisoftinc.enduome.threads.Client;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import org.json.me.JSONObject;

/**
 * @author trinisoftinc
 */
public class EnduoMe extends MIDlet {

    Resources resources;
    Button advertButton;
    Label imageLabel;
    Image advertImage;
    Dialog advertDialog;
    public HomeForm homeForm;
    public Client client;
    public static Container current;
    public static User loggedInUser;
    String newVersion, thisVersion = "1.0";
    CommonTransitions in = CommonTransitions.createFade(500);
    public static final String server = "socket://unotifier.com:1981";

    public void startApp() {
        Display.init(this);
        try {
            resources = Resources.open("/enduome_base.res");
            UIManager.getInstance().setThemeProps(resources.getTheme(resources.getThemeResourceNames()[0]));
            advertButton = new Button("Place your ads here. contact@statusforsale.net or 08089370313");
            imageLabel = new Label("");
            //readAdverts();
        } catch (java.io.IOException e) {
            Log.p("IO Exception Occured While Starting. Will Quit. " + e.getMessage());
            Log.showLog();
            notifyDestroyed();
        }

        client = new Client(server, this);

        Display.getInstance().callSerially(new Runnable() {

            public void run() {
                try {
                    initForms();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        new Thread() {

            public void run() {
                //doNewVersion();
            }
        }.start();
    }

    private void doNewVersion() {
        if (isNewVersionAvailable()) {
            final Dialog d = new Dialog("New Version Available.");
            d.setLayout(new GridLayout(2, 1));
            Command yes = new Command("Update", 0);
            Command no = new Command("Skip", 1);

            //Label l = new Label("Make sure you delete the old version after downloading the new version");
            Button yesButton = new Button(yes);
            yesButton.setIcon(resources.getImage("update"));
            yesButton.setAlignment(Button.CENTER);
            yesButton.setTextPosition(Button.BOTTOM);

            Button noButton = new Button(no);
            noButton.setIcon(resources.getImage("skip"));
            noButton.setAlignment(Button.CENTER);
            noButton.setTextPosition(Button.BOTTOM);

            d.addComponent(yesButton);
            d.addComponent(noButton);
            d.addCommandListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    Command c = evt.getCommand();
                    if (c.getId() == 0) {
                        try {
                            RecordStore store = RecordStore.openRecordStore("VT", true);
                            RecordEnumeration enu = store.enumerateRecords(null, null, true);

                            byte[] b = newVersion.getBytes();
                            int id = enu.nextRecordId();
                            store.setRecord(id, b, 0, b.length);
                            HttpPull pull = new HttpPull();
                            String vURL = pull.get(URLConstants.BASE_NEW_VERSION_URL, "");
                            platformRequest(vURL);
                        } catch (ConnectionNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                        }
                    }
                    d.dispose();
                }
            });
            d.showDialog();
        }
    }

    private boolean isNewVersionAvailable() {
        try {
            RecordStore store = RecordStore.openRecordStore("VT", true);
            RecordEnumeration enu = store.enumerateRecords(null, null, true);
            byte[] data = null;
            if (enu.hasNextElement()) {
                data = enu.nextRecord();
            } else {
                data = thisVersion.getBytes();
                store.addRecord(data, 0, data.length);
            }
            String localVersion = new String(data);

            HttpPull pull = new HttpPull();
            newVersion = pull.get(URLConstants.BASE_CHECK_VERSION_URL, "");
            Echo.outln("NV: " + newVersion + " LV: " + localVersion);
            if (newVersion.equals(localVersion)) {
                //there's no update
                return false;
            } else {
                //there is an update
                return true;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void initForms() throws Exception {
        homeForm = new HomeForm(this);
        LoginForm loginForm = new LoginForm(this);

        if (EntityConstants.USER_STORE.getNumRecords() <= 0) {
            current = loginForm;
            loginForm.show();
        } else {
            User u = new User();

            RecordEnumeration re = EntityConstants.USER_STORE.enumerateRecords(null, null, true);
            if (re.hasNextElement()) {
                u.fromJSONString(new String(re.nextRecord()));
            }
            loggedInUser = u;
            current = homeForm;
            homeForm.show();

            Display.getInstance().callSerially(new Runnable() {

                public void run() {
                    startChat();
                }
            });
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void startChat() {
        client.start();
    }

    private void readAdverts() {
        final MIDlet me = this;
        new Thread() {

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(120000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        HttpPull pull = new HttpPull();
                        String result = pull.get(URLConstants.ADS_URL, "");
                        JSONObject object = new JSONObject(result);
                        advertButton.setText(object.getString("adtext"));
                        try {
                            HttpConnection connection = (HttpConnection) Connector.open(object.getString("adurl"));
                            InputStream is = connection.openInputStream();
                            advertImage = Image.createImage(is);
                            imageLabel.setIcon(advertImage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (advertDialog == null) {
                            advertDialog = new Dialog("Message From Our Sponsors");
                            advertDialog.setLayout(new GridLayout(2, 1));
                            advertDialog.setTransitionInAnimator(in);
                            advertDialog.addCommand(new Command("Close"));
                            advertDialog.addCommandListener(new ActionListener() {

                                public void actionPerformed(ActionEvent ae) {
                                    advertDialog.dispose();
                                    advertDialog.removeAll();
                                    advertDialog.removeAllCommands();
                                    advertDialog = null;
                                }
                            });
                        }
                        if (!advertDialog.contains(advertButton)) {
                            advertDialog.addComponent(advertButton);
                            advertDialog.addComponent(imageLabel);
                        }
                        advertDialog.showDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
