/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinisoft.enduome.entities;

import com.trinisoft.baselib.db.BaseStore;
import com.trinisoft.baselib.db.BaseStoreImpl;
import com.trinisoft.baselib.db.StorableList;
import com.trinisoft.baselib.util.Echo;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import trinisoftinc.json.me.MyJSONArray;
import trinisoftinc.json.me.MyJSONObject;

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
    private StorableList friends = new StorableList();

    public boolean delete(RecordStore rs, int recordID) throws RecordStoreException {
        return super.delete(rs, recordID);
    }

    public BaseStore find(RecordStore rs, int id) {
        return super.find(rs, id);
    }

    public void fromJSONString(String json) throws Exception {
        MyJSONObject object = new MyJSONObject(json);
        id = object.getInt("id");
        username = object.getString("username");
        email = object.getString("email");
        age = object.getInt("age");
        address = object.getString("address");
        friends.fromJSONString(object.getString("friends"));        
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
        MyJSONObject object = new MyJSONObject();
        object.put("id", id);
        object.put("username", username);
        object.put("email", email.length() <= 0 ? "Not Yet Provided" : email);
        object.put("age", age);
        object.put("address", address.length() <= 0 ? "Not Yet Provided" : address);
        object.put("friends", friends.toJSONString());

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

    public boolean isFriend(String username) throws Exception {
        MyJSONArray array = new MyJSONArray(friends.toJSONString());
        
        int size = array.length();
        for(int i = 0; i < size; i++) {
            if(array.getString(i).equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    public StorableList getFriends() {
        return friends;
    }

    public void setFriends(StorableList friends) {
        this.friends = friends;
    }
    
}
