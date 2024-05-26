package com.sunnyweather.android.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sunnyweather.android.db.bean.User;

import java.util.List;

@Dao
public interface UserDao {

    /**
     * 增加
     * @param users 用户
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User... users);

    /**
     *查询用户列表
     * @return 用户列表
     */
    @Query("SELECT * FROM user")
    List<User> queryAll();

    /**
     * 按用户名查询
     * @param userName 用户名
     * @return 用户
     */
    @Query("SELECT * FROM user WHERE user_name LIKE:userName LIMIT 1")
    User findByName(String userName);

    /**
     * 修改
     * @param user 用户
     */
    @Update
    void update(User user);

    /**
     * 删除
     * @param users 根据用户进行删除
     */
    @Delete
    void delete(User users);
}
