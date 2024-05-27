package com.sunnyweather.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.sunnyweather.android.databinding.ActivityMainBinding;
import com.sunnyweather.android.databinding.ActivityRxjavaBinding;

import java.util.Deque;
import java.util.LinkedList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {

    public static final String TAG = RxjavaActivity.class.getSimpleName();

    private Observable<Integer> observable;
    private Observer<Integer> observer;

    private ActivityRxjavaBinding binging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binging = ActivityRxjavaBinding.inflate(getLayoutInflater());
        setContentView(binging.getRoot());
        //被观察者订阅观察者
        observable1.subscribe(observer1);
        initView();
    }

    // 被观察者
    Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
            // 产生事件
            emitter.onNext("Hello");
            emitter.onNext("World!");
            emitter.onComplete();
        }
    });

    // 观察者
    Observer<String> observer1 = new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            // 接收事件前执行
        }

        @Override
        public void onNext(@NonNull String s) {
            // 接收到next事件就执行
            binging.textRxjava.setText(s);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            // 接收到Error事件就执行
        }

        @Override
        public void onComplete() {
            // 接收到Complete事件就执行
        }
    };

    private void initView(){
        binging.progressbar.setMax((int) Math.pow(10, 3));
        binging.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 开始下载
                startDownload();
            }
        });
    }

    //模拟下载操作
    private void startDownload(){
        observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            //处理耗时操作
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < Math.pow(10, 3); i++){
                    Thread.sleep(30);
                    emitter.onNext(i);
                }
            }
        });

        observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                binging.progressbar.setProgress(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //订阅
        observable.subscribeOn(Schedulers.io()) //订阅在子线程
                .observeOn(AndroidSchedulers.mainThread()) //观察在应用程序
                .subscribe(observer);
    }

}

