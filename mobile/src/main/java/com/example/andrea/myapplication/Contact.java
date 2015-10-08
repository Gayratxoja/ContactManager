package com.example.andrea.myapplication;

import android.net.Uri;

/**
 * Created by Andrea on 10.09.2015.
 */
public class Contact {



    private String _name;
    private String _phone;
    private String _email;
    private String _address;
    private Uri _image;
    private int _id;

    public Contact(int id,String name, String phone, String email, String address, Uri image) {
        _id = id;
        _name = name;
        _phone = phone;
        _email = email;
        _address = address;
        _image = image;
    }
    public String get_name() {
        return _name;
    }


    public String get_phone() {
        return _phone;
    }



    public String get_email() {
        return _email;
    }


    public String get_address() {
        return _address;
    }

    public Uri get_image(){ return _image;}

    public int get_id() {
        return _id;
    }

}
