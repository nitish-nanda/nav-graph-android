package com.example.navigationandroid.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel implements Serializable {

    public Long id;
    public String name;
    public String email;
    public String phone;
    public String dob;
    public String gender;
    public String password;
    public String imgUrl;

    public String verificationCode;
}
