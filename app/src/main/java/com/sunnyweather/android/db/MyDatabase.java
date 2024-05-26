package com.sunnyweather.android.db;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sunnyweather.android.MainActivity;
import com.sunnyweather.android.db.bean.User;
import com.sunnyweather.android.db.dao.UserDao;


@Database(entities = {User.class}, version = 1, exportSchema = false) //关联数据库
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    //单例模式
    private static volatile MyDatabase myDatabase;
    public static MyDatabase getInstance(Context context){
        if (myDatabase == null){
            synchronized (MyDatabase.class){
                if (myDatabase == null){
                     myDatabase = Room.databaseBuilder(context, MyDatabase.class, "DemoDB")
                            //是否允许在主线程上操作数据库,这里是允许。默认是不允许的
                            .allowMainThreadQueries()
                             //数据库升级后自动销毁之前的，数据也不保留（只限于开发中）
                             .fallbackToDestructiveMigration()
                            //数据库创建和打开的事件会回调到这里，可以再次操作数据库
                            .addCallback(new CallBack())
                            .build();
                }
            }
        }
        return myDatabase;
    }

    static class CallBack extends RoomDatabase.Callback {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "db create");
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "db open");
        }
    }
}


