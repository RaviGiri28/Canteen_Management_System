package com.example.canteenmanagementsystem;

import android.widget.EditText;

public class ReadWriteUserDetails {
    public String Fullnme,usernme;

    public ReadWriteUserDetails(String Fullname, String username){
        this.Fullnme=Fullname;
        this.usernme=username;
    }

   public ReadWriteUserDetails(EditText fullname, EditText username) {

   }
}
