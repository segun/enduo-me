/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.trinisoft.baselib.db.decorator.StoreDecorator;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.enduome.entities.EntityConstants;
import com.trinisoft.enduome.entities.User;
import com.trinisoft.mlib.views.BaseForm;
import org.w3c.dom.Entity;

/**
 *
 * @author trinisoftinc
 */
public class LoginForm extends BaseForm {

    TextField nameField, ageField, addressField, emailField;
    public static final int LOGIN_ACTION = 1;
    public static final int QUIT_ACTION = 2;
    EnduoMe parent;

    public LoginForm(EnduoMe parent) {
        super(parent);
        this.parent = parent;
        init();
    }

    private void init() {
        setTitle("Enter your name");
        nameField = new TextField();
        addWithLabel("Enter you name (compulsory)", nameField);

        ageField = new TextField();
        ageField.setInputMode("123");
        ageField.setConstraint(TextField.NUMERIC);
        addWithLabel("Age: ", ageField);

        addressField = new TextField();
        addWithLabel("Address: ", addressField);

        emailField = new TextField();
        addWithLabel("Enter your email or phone number", emailField);

        Command loginCommand = new Command("Login", LOGIN_ACTION);
        Command quitCommand = new Command("Quit", QUIT_ACTION);

        Button q = new Button(quitCommand);
        Button l = new Button(loginCommand);

        addCommand(loginCommand);
        addCommand(quitCommand);

        addComponent(l);
        addComponent(q);

        addCommandListener(new LoginCommander());
    }

    private class LoginCommander implements ActionListener {

        public LoginCommander() {
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
                            } catch(NumberFormatException nfe) {
                                if(ageField.getText().length() > 0) {
                                    showMessageDialog("Error", "Please enter a valid age", true);
                                } else {
                                    u.setAge(0);
                                }
                            }
                            u.setEmail(emailField.getText());
                            new StoreDecorator(u).save(EntityConstants.USER_STORE);
                            //showMessageDialog("Success", "User Registration Successfull", true);
                            new HomeForm(parent).show();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        showMessageDialog("Error", "Please enter a username", true);
                    }
                    break;
                case QUIT_ACTION:
                    parent.notifyDestroyed();
                    break;
            }
        }
    }
}
