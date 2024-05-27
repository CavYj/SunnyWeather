package com.sunnyweather.android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sunnyweather.android.databinding.ActivityNetBinding;
import com.sunnyweather.android.network.api.ApiService;
import com.sunnyweather.android.network.bean.WallPaperResponse;
import com.sunnyweather.network.NetworkApi;
import com.sunnyweather.network.observer.BaseObserver;
import com.sunnyweather.network.util.KLog;

import java.util.List;

public class NetActivity extends AppCompatActivity {

    public static final String TAG = NetActivity.class.getSimpleName();
    private ActivityNetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestNetwork();
    }


    @SuppressLint("CheckResult")
    private void requestNetwork() {
        NetworkApi.createService(ApiService.class)
                .getWallPaper()
                .compose(NetworkApi.applySchedulers(new BaseObserver<WallPaperResponse>() {
                    @Override
                    public void onSuccess(WallPaperResponse wallPaperResponse) {
                        List<WallPaperResponse.ResBean.VerticalBean> vertical = wallPaperResponse.getRes().getVertical();
                        if (vertical != null && vertical.size() > 0) {
                            String imgUrl = vertical.get(0).getImg();
                            Glide.with(NetActivity.this).load(imgUrl).into(binding.imageView);
                        } else {
                            Toast.makeText(NetActivity.this, "数据为空", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        KLog.e("MainActivity", e.toString());
                        Toast.makeText(NetActivity.this, "访问失败", Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}