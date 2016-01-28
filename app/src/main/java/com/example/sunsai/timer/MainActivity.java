package com.example.sunsai.timer;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sunsai.timer.view.CircleView;
import com.example.sunsai.timer.view.TimeView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 结构混乱，需要进一步重构
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CircleView mCircleView;
    private TimeView mTimeView;
    private Button mButton;
    private Button mButton2;
    private MyHandler mHandler;
    private boolean mRunning;
    private MyRecyclerViewAdapter adapter;

    private TextView topTextView;

    private RecyclerView mRecyclerView;

    private List<String> list = new ArrayList<>();

    private int index = 0;

    private long time = 0;//毫秒
    private long sumTime = 0;
    private long sysBeginTime = 0;
    private long subTime = 0;

    public boolean isRunning() {
        return mRunning;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircleView = (CircleView) findViewById(R.id.circle_view);
        mTimeView = (TimeView) findViewById(R.id.time_view);
        mButton = (Button) findViewById(R.id.start);
        mButton2 = (Button) findViewById(R.id.stop);
        mHandler = new MyHandler(MainActivity.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new MyRecyclerViewAdapter(MainActivity.this, list);
        mRecyclerView.setAdapter(adapter);
        topTextView = (TextView) findViewById(R.id.top_text_view);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning()) {
                    setRunning(true);
                    Message message = mHandler.obtainMessage();
                    mHandler.sendMessageDelayed(message, 10);
                    mButton.setText("暂停");
                    mButton2.setText("记圈");
                } else {
                    setRunning(false);
                    mButton.setText("继续");
                    mButton2.setText("重置");
                }
            }
        });


        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning()) {
                    jiquan();
                    updateTime();
//                    sysBeginTime = new Date().getTime();
                } else {
                    initTime();
                    mButton.setText("开始");
                }
            }
        });

    }

    public void jiquan() {
        String str = "#" + index + "    " + getStrTime(subTime + time) + "    " + getStrTime(sumTime + time);
        list.add(str);
        adapter.notifyDataSetChanged();
        index ++;
        subTime = 0;
        sumTime += time;
        time = 0;
        sysBeginTime = new Date().getTime();
    }


    public String getStrTime(long time) {
        int mm = (int) time / 1000 / 60;
        int ss = (int) time / 1000 % 60;
        int ms = (int) time % 1000 / 10;


        String a = String.valueOf(mm);

        String b = String.valueOf(ss);
        if (ss < 10) {
            b = "0" + b;
        }

        String c = String.valueOf(ms);
        if (ms < 10) {
            c = "0" + c;
        }

        return "" + a +":" + b +"." + c;
    }


    public void setRunning(boolean mRunning) {
        this.mRunning = mRunning;
        if (mRunning) {
            sysBeginTime = new Date().getTime();
        } else {
            Logger.d("before sumTime = " + sumTime);
            Logger.d("before time = " + time);
            sumTime += time;
            subTime += time;
            Logger.d("sumTime = " + sumTime);
        }

    }


    public void updateTime() {
        if (isRunning()) {
            long now = new Date().getTime();
            time = now - sysBeginTime;
            topTextView.setText("#" + index + "    " + getStrTime(subTime + time) +"    " + getStrTime(sumTime + time));
            mCircleView.changeProgress((float)(subTime + time) / 600f);
            mTimeView.setTime(sumTime + time);
        }
    }

    public void initTime() {
        time = 0;
        sumTime = 0;
        subTime = 0;
        index = 0;
        mCircleView.changeProgress(0);
        mTimeView.setTime(sumTime + time);
        list.clear();
        topTextView.setText("");
        adapter.notifyDataSetChanged();
    }

    public static class MyHandler extends Handler {

        private MainActivity activity;

        public MyHandler(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            activity.updateTime();
            if (activity.isRunning()) {
                Message message = obtainMessage();
                sendMessageDelayed(message, 10);
            }
        }
    }

}

