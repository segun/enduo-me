/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.ui;

import com.sun.lwuit.Container;
import com.sun.lwuit.List;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.SelectionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.trinisoft.baselib.util.Echo;
import com.trinisoft.enduome.EnduoMe;
import java.util.Vector;

/**
 *
 * @author trinisoftinc
 */
public class OnlineListContainer extends Container {

    EnduoMe parent;
    Vector onlineVector;
    List online = null;

    public OnlineListContainer(EnduoMe parent) {
        setLayout(new BorderLayout());
        this.parent = parent;
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        onlineVector = parent.client.onlineList;
        online = new List(onlineVector);
        online.setListCellRenderer(new Renderers.ButtonRenderer());

        addComponent(BorderLayout.CENTER, online);
    }
}
