package com.example.myapplication.manager.bd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.bean.ArticleBean;
import com.example.myapplication.bean.UserBean;

@Database(entities = {UserBean.class, ArticleBean.class}, version = 1,exportSchema = false)
abstract public class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ArticleDao articleDao();

    private static final String DB_NAME = "repoDatabase.db";
    private static volatile UserDatabase instance;

    public static UserDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (UserDatabase.class) {
                if (instance == null) {
                    instance = create(context);
                }
            }

        }
        return instance;
    }

    private static UserDatabase create(Context context) {
        return Room.databaseBuilder(
                context,
                UserDatabase.class,
                DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

}
