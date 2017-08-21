package com.wdy.project;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.base.library.activity.BaseActivity;
import com.base.library.view.refresh.OnAutoRefreshListener;
import com.base.library.view.refresh.OnLoadListener;
import com.base.library.view.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * 作者：王东一
 * 创建时间：2017/7/7.
 */

public class NextActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private LinearLayout load;
    private RefreshLayout refresh;
    private int page = 0;
    private ArrayList<MessageBean> list = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private EditText edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        initView();
        setTitleName("消息盒子");
        messageAdapter = new MessageAdapter(this, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(messageAdapter);
        refresh.setColorSchemeResources(R.color.text_black);
        refresh.setAutoRefreshListener(new OnAutoRefreshListener() {
            @Override
            public void refresh() {
                //下拉刷新
                page = 0;
                list.clear();
                getData();
            }
        });
        refresh.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                //加载更多
                page++;
                getData();
            }
        });
        refresh.setCanLoadMore(true);
        refresh.AutoRefresh();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        load = (LinearLayout) findViewById(R.id.load);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        edit1 = (EditText) findViewById(R.id.edit1);
    }

    public void getData() {
        Random r = new Random();
        final int l = r.nextInt(15);
        for (int i = 0; i < 11; i++) {
            MessageBean m = new MessageBean();
            m.setContent("测试内容");
            m.setTitle("测试标题" + i);
            m.setTime("2017-070-20");
            list.add(m);
        }
        messageAdapter.notifyDataSetChanged();
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (refresh != null) {
                    refresh.setCanLoadMore(true);
                    refresh.RefreshComplete();
                    refresh.LoadMoreComplete();
                }
            }
        }, 1000);
    }

}
