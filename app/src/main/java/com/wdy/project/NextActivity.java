package com.wdy.project;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.base.library.activity.WDYBaseActivity;
import com.base.library.animation.LayoutAnimationHelper;
import com.base.library.application.BaseApplication;
import com.base.library.listen.OnRecyclerClickListen;
import com.base.library.util.CodeUtil;
import com.base.library.view.refresh.OnAutoRefreshListener;
import com.base.library.view.refresh.OnLoadListener;
import com.base.library.view.refresh.RefreshLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * 作者：王东一
 * 创建时间：2017/7/7.
 */

public class NextActivity extends WDYBaseActivity {
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
        setStatusBar(true);
        initView();
        messageAdapter = new MessageAdapter(this, list);
        messageAdapter.setOnRecyclerClickListen(new OnRecyclerClickListen() {
            @Override
            public void onClick(int position) {
                Window.getInstance().with(NextActivity.this, new Window.onClickSure() {
                    @Override
                    public void onClick(String password) {

                    }
                });
            }
        });
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

    @Override
    protected void onAnimationComplete() {
        super.onAnimationComplete();
        LayoutAnimationController controller = new LayoutAnimationController(LayoutAnimationHelper.getAnimationSetFromBottom());
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        load = (LinearLayout) findViewById(R.id.load);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        edit1 = (EditText) findViewById(R.id.edit1);
        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (CodeUtil.isMobileNO(edit1.getText().toString())) {
                    BaseApplication.getToastUtil().showMiddleToast("对了");
                }
            }
        });
    }

    public void getData() {
        Random r = new Random();
        final int l = r.nextInt(3);
        for (int i = 0; i < 2; i++) {
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
