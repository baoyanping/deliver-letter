package com.example.myapplication.bean;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "subscriber", indices = {@Index(value = "account", unique = true)})

public class UserBean implements Serializable {
    @PrimaryKey(autoGenerate = true)//主键注解，autoGenerate为自增长
    private int id;
    private String username;//姓名
    private String account; //账户
    private String password;//密码
    private int identity;   //身份    1管理员  2专家 3作家
    private int approval;   //是否审批  0未审批 1已通过 2未通过


    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", identity=" + identity +
                ", approval=" + approval +
                '}';
    }

    public UserBean(String username, String account, String password, int identity, int approval) {
        this.username = username;
        this.account = account;
        this.password = password;
        this.identity = identity;
        this.approval = approval;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public int getApproval() {
        return approval;
    }

    public void setApproval(int approval) {
        this.approval = approval;
    }

    public String getStringIdentity() {
        switch (identity) {
            case 2:
                return "专家";
            case 3:
                return "作家";
            default:
                return "管理员";
        }
    }

    public String getStringApproval() {
        switch (approval) {
            case 0:
                return "未审批";
            case 1:
                return "已通过";
            default:
                return "未通过";
        }
    }
}
