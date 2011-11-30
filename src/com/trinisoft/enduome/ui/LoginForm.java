/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Display;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.trinisoft.baselib.db.decorator.StoreDecorator;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.enduome.entities.EntityConstants;
import com.trinisoft.enduome.entities.User;
import com.trinisoft.mlib.views.BaseForm;
import com.trinisoft.mlib.views.XTextField;
import javax.microedition.rms.RecordEnumeration;

/**
 *
 * @author trinisoftinc
 */
public class LoginForm extends BaseForm {

    XTextField nameField, ageField, addressField, emailField;
    public static final int LOGIN_ACTION = 1;
    public static final int QUIT_ACTION = 2;
    EnduoMe parent;
       
    public LoginForm(EnduoMe parent) {
        super(parent);
        this.parent = parent;
        init();
    }

    private void addWithLabel(String label, XTextField field) {
        addLabel(label);
        addComponent(field);
    }
    
    private void init() {
        setTitle("Enter your name");
        nameField = new XTextField();
        addWithLabel("Enter your username (compulsory)", nameField);

        ageField = new XTextField();
        ageField.setInputMode("123");
        ageField.setConstraint(TextField.NUMERIC);
        addWithLabel("Age: ", ageField);

        addressField = new XTextField();
        addWithLabel("Address: ", addressField);

        emailField = new XTextField();
        addWithLabel("Enter your email or phone number", emailField);

        Command loginCommand = new Command("Login", LOGIN_ACTION);
        Command quitCommand = new Command("Quit", QUIT_ACTION);

        Button q = new Button(quitCommand);
        Button l = new Button(loginCommand);

        addCommand(loginCommand);
        addCommand(quitCommand);

        addComponent(l);
        addComponent(q);

        addCommandListener(new LoginCommander(this));
    }

    private class LoginCommander implements ActionListener {

        LoginForm loginForm;
        public LoginCommander(LoginForm loginForm) {
            this.loginForm = loginForm;
        }

        public void actionPerformed(ActionEvent ae) {
            int cid = ae.getCommand().getId();

            switch (cid) {
                case LOGIN_ACTION:
                    if (nameField.getText().length() > 0) {
                        try {
                            User u = new User();
                            u.setUsername(nameField.getText());
                            u.setAddress(addressField.getText());
                            try {
                                u.setAge(Integer.parseInt(ageField.getText()));
                            } catch (NumberFormatException nfe) {
                                if (ageField.getText().length() > 0) {
                                    showErrorDialog("Please enter a valid age", loginForm);
                                } else {
                                    u.setAge(0);
                                }
                            }
                            try {
                                RecordEnumeration re = EntityConstants.USER_STORE.enumerateRecords(null, null, true);
                                while(re.hasNextElement()) {
                                    EntityConstants.USER_STORE.deleteRecord(re.nextRecordId());
                                }
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                            u.setEmail(emailField.getText());
                            new StoreDecorator(u).save(EntityConstants.USER_STORE);
                            //showMessageDialog("Success", "User Registration Successfull", true);

                            EnduoMe.loggedInUser = u;
                            Display.getInstance().callSerially(new Runnable() {

                                public void run() {
                                    parent.startChat();
                                }
                            });    
                            parent.homeForm.setTitle("Enduo-Me : Welcome " + u.getUsername());
                            parent.homeForm.show();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        showErrorDialog("Please enter a username", loginForm);
                    }
                    break;
                case QUIT_ACTION:
                    parent.notifyDestroyed();
                    break;
            }
        }
    }
}
