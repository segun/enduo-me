/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.entities;

import com.trinisoft.baselib.db.BaseStore;
import com.trinisoft.baselib.db.BaseStoreImpl;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import org.json.me.JSONObject;

/**
 *
 * @author trinisoftinc
 */
public class User extends BaseStoreImpl {

    private int id;
    private String email;
    private String username;
    private int age;
    private String address;

    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException {
        return super.delete(rs, recordID);
    }

    public BaseStore find(RecordStore rs, int id) {
        return super.find(rs, id);
    }

    public void fromJSONString(String json) throws Exception {
        JSONObject object = new JSONObject(json);
        id = object.getInt("id");
        username = object.getString("username");
        email = object.getString("email");
        age = object.getInt("age");
        address = object.getString("address");
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
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("username", username);
        object.put("email", email.length() <= 0 ? "Not Yet Provided" : email);
        object.put("age", age);
        object.put("address", address.length() <= 0 ? "Not Yet Provided" : address);

        return object.toString();
    }

    public boolean update(RecordStore rs, byte[] newData, int recordID) throws RecordStoreException {
        return super.update(rs, newData, recordID);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
