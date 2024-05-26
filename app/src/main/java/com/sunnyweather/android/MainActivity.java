package com.sunnyweather.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sunnyweather.android.adapter.UserAdapter;
import com.sunnyweather.android.databinding.ActivityMainBinding;
import com.sunnyweather.android.db.MyDatabase;
import com.sunnyweather.android.db.bean.User;
import com.sunnyweather.android.db.dbEngine.UserEngine;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = MainActivity.class.getSimpleName();
    private MyDatabase db;
    private List<User> userList = new ArrayList<>();
    private UserAdapter userAdapter;
    private UserEngine userEngine;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        binding.btnAdd.setOnClickListener(this);
        binding.btnDelete.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
        binding.btnQuery.setOnClickListener(this);
        //列表
        RecyclerView rv = binding.rv;
        userAdapter = new UserAdapter(R.layout.item_rv, userList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(userAdapter);
        //初始化数据库
        db = MyDatabase.getInstance(this);
        userEngine = new UserEngine(this);
        //rxjava
        binding.btnRxjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RxjavaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                addUser();
                break;
            case R.id.btn_delete:
                deleteUser();
                break;
            case R.id.btn_update:
                updateUser();
                break;
            case R.id.btn_query:
                queryAll();
                break;
            default:break;
        }
    }


    /**
     * 增加用户
     */
    private void addUser() {
        userEngine.insertUser(new User("张三", 20, "张大炮", "北京八宝山4号墓地"),
                new User("李四", 60, "尼古拉斯.凯奇", "美国佛罗里达州"),
                new User("王五", 70, "爱新觉罗.爱国", "北京故宫乾清宫西北方向角落"),
                new User("赵六", 30, "叶赫那拉.啦啦啦", "北京前门外前门大街皮条胡同"));
        Toast.makeText(MainActivity.this,"增加成功",Toast.LENGTH_SHORT).show();
    }

    /**
     * 删除用户
     */
    private void deleteUser() {
        runOnUiThread(() -> {
            User user = db.userDao().findByName("张三");
            if (user == null) return;
            userEngine.deleteUser(user);
            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
        });
    }


    /**
     * 修改用户
     */
    private void updateUser() {
        runOnUiThread(() -> {
            User user = db.userDao().findByName("李四");
            if (user == null) return;
            user.setUserName("赵四");
            user.setUserAge(10);
            user.setNickName("xxxxx");
            user.setAddress("中国杭州");
            db.userDao().update(user);
            userAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * 查询所有用户
     */
    private void queryAll() {
        runOnUiThread(() -> {
            userList.clear();
            userList.addAll(db.userDao().queryAll());
            userAdapter.notifyDataSetChanged();
        });
    }



}