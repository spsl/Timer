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

import java.util.List;


/**
 * 结构混乱，需要进一步重构
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private CircleView mCircleView;
    private TimeView mTimeView;
    private Button mButton;
    private Button mButton2;
    private MyHandler mHandler;
    private MyRecyclerViewAdapter adapter;

    private TextView topTextView;

    private RecyclerView mRecyclerView;

    private List<String> list;

    private Timer timer;


    public boolean isRunning() {
        return timer.isRunning();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = new Timer();
        list = timer.getList();
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
        mButton.setOnClickListener(this);
        mButton2.setOnClickListener(this);
    }

    public void updateTime() {
        timer.update();
        topTextView.setText(timer.getRoundStrTime());
        mCircleView.changeProgress((float) timer.getSubTime() / 600f);
        mTimeView.setTime(timer.getSumTime());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start :
                if (!isRunning()) {
                    timer.start();
                    Message message = mHandler.obtainMessage();
                    mHandler.sendMessageDelayed(message, 10);
                    mButton.setText("暂停");
                    mButton2.setText("记圈");
                } else {
                    mButton.setText("继续");
                    mButton2.setText("重置");
                    timer.pause();
                }
                break;

            case R.id.stop:
                if (isRunning()) {
                    timer.round();
                    adapter.notifyDataSetChanged();
                    updateTime();
                } else {
                    timer.init();
                    topTextView.setText("");
                    mButton.setText("开始");
                    updateTime();
                }
                break;
        }
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

