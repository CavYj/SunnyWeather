package com.sunnyweather.android.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.sunnyweather.android.R;
import com.sunnyweather.android.db.bean.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    public UserAdapter(int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, User user) {
        holder.setText(R.id.tv_id, user.id + "")
                .setText(R.id.tv_name, user.userName)
                .setText(R.id.tv_age, user.userAge + "")
                .setText(R.id.tv_nickname, user.nickName)
                .setText(R.id.tv_address, user.address);
    }
}


