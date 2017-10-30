package com.wdy.project;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.base.library.activity.WDYBaseActivity;
import com.base.library.view.recyclerView.CustomRecyclerView;

import java.util.ArrayList;

/**
 * 作者：王东一
 * 创建时间：2017/10/18.
 */

public class RecycleTestActivity extends WDYBaseActivity {
    private CustomRecyclerView recyclerView;
    private ArrayList<MessageBean> list = new ArrayList<>();
    private MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_test);
        initView();
        for (int i = 0; i < 12; i++) {
            MessageBean m = new MessageBean();
            m.setContent("测试内容");
            m.setTitle("测试标题" + i);
            m.setTime("2017-070-20");
            list.add(m);
        }
    }

    private void initView() {
        recyclerView = (CustomRecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void onAnimationComplete() {
        super.onAnimationComplete();

    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        messageAdapter = new MessageAdapter(this, list);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.scheduleLayoutAnimation();
    }
}
