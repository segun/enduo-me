/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.models;

import com.trinisoft.baselib.db.BaseStoreImpl;
import com.trinisoft.baselib.db.StorableList;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author trinisoftinc
 */
public class History extends BaseStoreImpl {
    private StorableList messages;
    private String from;
    int id;

    public History() {
    }

    public void fromJSONString(String json) throws Exception {
        super.fromJSONString(json);
    }

    public int getId() {
        return id;
    }

    public int save(RecordStore rs) throws RecordStoreException, IOException {
        return super.save(rs);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toJSONString() throws Exception {
        return super.toJSONString();
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public StorableList getMessages() {
        return messages;
    }

    public void setMessages(StorableList messages) {
        this.messages = messages;
    }
}
