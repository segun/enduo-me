/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.entities;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author trinisoftinc
 */
public class EntityConstants {
    public static RecordStore USER_STORE;

    static {
        try {
            USER_STORE = RecordStore.openRecordStore("USERS_STORE", true);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }
}
