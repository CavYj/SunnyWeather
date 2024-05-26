package com.sunnyweather.android.db.dbEngine;

import android.content.Context;
import android.os.AsyncTask;

import com.sunnyweather.android.db.MyDatabase;
import com.sunnyweather.android.db.bean.User;
import com.sunnyweather.android.db.dao.UserDao;

import java.util.List;

public class UserEngine {

    private static final String TAG = "UserEngine";

    private UserDao userDao;
    private Context context;

    public UserEngine(Context context){
        this.context = context;
        MyDatabase db = MyDatabase.getInstance(context);
        userDao = db.userDao();
    }

    /**
     *插入用户
     * @param users
     */
    public void insertUser(User... users){
        new InsertAsync(userDao).execute(users);
    }

    public void deleteUser(User user){
        new DeleteAsync(userDao).execute(user);
    }

    //怎么获取AsyncTask的返回值
    public void findUserByName(String userName){
        new FindUserAsync(userDao).execute(userName);
    }

    public void updateUser(User user){
        new UpdateAsync(userDao).execute(user);
    }

    public void queryAllUser(){
        new QueryUserAsync(userDao,context).execute();
    }

    //引入异步
    //插入
    static class InsertAsync extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        public InsertAsync(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users);
            return null;
        }
    }

    //删除
    static class DeleteAsync extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        public DeleteAsync(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    //更新
    static class UpdateAsync extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        public UpdateAsync(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    //根据名字查用户
    static class FindUserAsync extends AsyncTask<String, Void, User>{
        private UserDao userDao;
        public FindUserAsync(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected User doInBackground(String... strings) {
            User user = userDao.findByName(strings[0]);
            return user;
        }
    }

    static class QueryUserAsync extends AsyncTask<Void, Void, List<User>>{
        private UserDao userDao;
        private Context context;
        public QueryUserAsync(UserDao userDao, Context context){
            this.userDao = userDao;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            List<User> users = userDao.queryAll();
            return users;
        }

        protected void onPostExecute(List<User> users) {

        }
    }
}
