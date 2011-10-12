/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Component;
import com.sun.lwuit.Tabs;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ContainerList;
import com.sun.lwuit.list.DefaultListModel;
import com.trinisoft.enduome.EnduoMe;
import com.trinisoft.mlib.views.BaseForm;

/**
 *
 * @author trinisoftinc
 */
public class HomeForm extends BaseForm {

    EnduoMe parent;

    public HomeForm(EnduoMe parent) {
        super(parent);
        this.parent = parent;
        init();
    }

    private void init() {
        final Tabs tabs = new Tabs(Tabs.TOP);
        final OnlineListContainer onlineListContainer = new OnlineListContainer(parent);
        ContainerList containerList2 = new ContainerList(new BoxLayout(BoxLayout.Y_AXIS), new DefaultListModel());
        tabs.addTab("Online Now", onlineListContainer);
        tabs.addTab("Friends List", containerList2);
        tabs.setSelectedIndex(0);

        tabs.addTabsFocusListener(new FocusListener() {

            public void focusGained(Component cmp) {
                onlineListContainer.refresh();
            }

            public void focusLost(Component cmp) {
            }
        });
        addComponent(tabs);
    }
}
