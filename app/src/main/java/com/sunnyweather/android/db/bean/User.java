package com.sunnyweather.android.db.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity//Entity表示数据库中的表
public class User {
    @PrimaryKey(autoGenerate = true)//主键，自增
    @NonNull//表示不为空
    public int id;

    //可以不写，写是为了设置列名，不写的话使用变量名作为列名
    @ColumnInfo(name = "user_name", defaultValue = "")
    public String userName;

    @ColumnInfo(name = "user_age")
    public int userAge;

    @ColumnInfo(name = "nick_name")
    public String nickName;

    @ColumnInfo(name = "address")
    public String address;

    public User(String userName, int userAge, String nickName, String address){
        this.userName = userName;
        this.userAge = userAge;
        this.nickName = nickName;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
