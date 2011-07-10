/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.FlowLayout;
import com.trinisoft.baselib.util.Echo;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.mlib.views.BaseForm;
import java.util.Vector;

/**
 *
 * @author trinisoftinc
 */
public class HomeForm extends BaseForm {

    private EnduoMe parent;
    private OnlineListForm onlineListForm;

    public HomeForm(EnduoMe parent) {
        super(parent);
        this.parent = parent;
        init();
    }

    private void init() {
        setTitle("Enduo-Me");
        setLayout(new BorderLayout());
        Command c = new Command("Online");
        Container menu = new Container(new FlowLayout());
        menu.addComponent(new Button(c));
        addCommand(c);
        addCommandListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                replace(onlineListForm, (onlineListForm = new OnlineListForm(parent)), in);
            }
        });

        addComponent(BorderLayout.NORTH, menu);
        addComponent(BorderLayout.CENTER, (onlineListForm = new OnlineListForm(parent)));
    }
}
