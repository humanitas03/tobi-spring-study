/**
 * ===============================================================
 * File name : User.java
 * Created by injeahwang on 2021-05-24
 * ===============================================================
 */
package com.example.tobispring.chapter01;


import com.example.tobispring.chapter01.enums.Level;

public class User {

    private String id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommend;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(){
        //기존 코드와 하위 호환성을 위해 no argument 생성자
    }

    public User(String id, String name, String password, Level level, int login, int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public User(String id, String name, String password, String email, Level level, int login, int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
        this.email = email;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public Level getLevel(){
        return level;
    }

    public void setLevel(Level level){
        this.level = level;
    }
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


    public void upgradeLevel(){
        Level nextLevel = this.level.nextLevel();
        if(nextLevel==null){
            throw new IllegalStateException(this.level + "은 업그레이드가 불가능 합니다.");
        }
        else{
            this.level = nextLevel;
        }
    }

    @Override
    public String toString(){
        return "USER(id = "+this.id+", name : "+this.name+", " + "password : "
            +this.password+ "level : "+this.level + " login : "+this.login+" recommend : "+this.recommend+")";
    }



    public static void userSample(){
        User user = new User();
        user.setId("1");
        user.setName("jay");
        user.setPassword("123");
        user.setLevel(Level.BASIC);
        System.out.println("USER SAMPLE : " + user.toString());
    }

}
