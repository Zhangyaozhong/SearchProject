package com.bwie.android.exercise1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bwie.android.exercise1.adapter.GridAdapter;
import com.bwie.android.exercise1.adapter.SearchAdapter;
import com.bwie.android.exercise1.bean.SearchBean;
import com.bwie.android.exercise1.contract.SearchContract;
import com.bwie.android.exercise1.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchContract.ISearchView, CustomSearchView.SearchCallback {

    private SharedPreferences sp;
    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.csv)
    CustomSearchView customSearchView;
    private SearchPresenter searchPresenter;
    private String context = "板鞋";
    private List<SearchBean.ResultBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        customSearchView.setSearchCallback(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        initData();
        mRecycleView.setAdapter(new SearchAdapter(MainActivity.this, dataList));
        sp = getSharedPreferences("isTruc", MODE_PRIVATE);
    }

    private void initData() {
        searchPresenter = new SearchPresenter(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("keyword", context);
        params.put("page", 1 + "");
        params.put("count", 5 + "");
        searchPresenter.getData(params);
    }

    @Override
    public void judgeSearchContent() {
        Toast.makeText(this, "关键词不能为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void backClick() {
        this.finish();
    }

    @Override
    public void searchclearFocus(String keywords) {
        context = keywords;
        initData();
        Toast.makeText(this, keywords, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void classifyClick() {
        boolean flag = sp.getBoolean("flag", true);
        if (flag) {
            mRecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            initData();
            mRecycleView.setAdapter(new SearchAdapter(MainActivity.this, dataList));
            sp.edit().putBoolean("flag", false).commit();
        } else {
            mRecycleView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            initData();
            mRecycleView.setAdapter(new GridAdapter(MainActivity.this, dataList));
            sp.edit().putBoolean("flag", true).commit();
        }

    }

    @Override
    public void success(List<SearchBean.ResultBean> resultBeans) {
        if (resultBeans!=null){
            dataList=resultBeans;
            mRecycleView.setAdapter(new SearchAdapter(MainActivity.this, dataList));
        }
    }

    @Override
    public void error(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
