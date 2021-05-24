/**
 * ===============================================================
 * File name : User.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01;


public class User {
    private String id;
    private String name;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return "USER(id = "+this.id+", name : "+this.name+", " + "password : "+this.password+")";
    }

    public static void userSample(){
        User user = new User();
        user.setId("1");
        user.setName("jay");
        user.setPassword("123");
        System.out.println("USER SAMPLE : " + user.toString());
    }

}
