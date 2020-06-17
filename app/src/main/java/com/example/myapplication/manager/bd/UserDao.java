package com.example.myapplication.manager.bd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.bean.UserBean;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertAll(UserBean userBean);

    @Delete
    int delete(UserBean userBean) ;//可以返回一个int值，表示从数据库中删除的行数

    @Query("SELECT * FROM subscriber")
    List<UserBean> selectAll();

    @Query("SELECT id FROM subscriber where account=:account")
    int judgeUserAccount(String account);

    @Query("SELECT * FROM subscriber Where account= :account AND password =:password")
    UserBean judgeUserExistence(String account,String password);

    @Update
    int updateApproval(UserBean bean);

    @Query("select username from subscriber where id=:id")
    String queryName(int id);

}