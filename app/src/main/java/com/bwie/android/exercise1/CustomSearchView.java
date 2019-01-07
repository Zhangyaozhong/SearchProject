package com.bwie.android.exercise1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomSearchView extends LinearLayout implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_classify;
    private ImageView iv_yuyin;
    private ImageView iv_search;
    private EditText et_search;

    public CustomSearchView(Context context) {
        this(context, null);
    }

    public CustomSearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化自定义view的子控件
     */
    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.title_layout, this, true);
        iv_back = view.findViewById(R.id.iv_back);
        iv_classify = view.findViewById(R.id.iv_classify);
        iv_search = view.findViewById(R.id.iv_search);
        iv_yuyin = view.findViewById(R.id.iv_yuyin);
        et_search = findViewById(R.id.et_search);

        iv_back.setOnClickListener(this);
        iv_classify.setOnClickListener(this);
        iv_search.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (searchCallback != null) {
                    searchCallback.backClick();
                }
                break;
            case R.id.iv_classify:
                if (searchCallback != null) {
                    searchCallback.classifyClick();
                }
                break;
            case R.id.iv_search:
                String content = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    if (searchCallback != null) {
                        searchCallback.judgeSearchContent();
                    }
                    return;
                } else {
                    if (searchCallback != null) {
                        searchCallback.searchclearFocus(content);
                    }
                }
                break;

        }
    }

    interface SearchCallback {
        //判断搜索框是否为空
        void judgeSearchContent();

        //        点击返回的回调方法
        void backClick();

        //        点击搜索的回调方法
        void searchclearFocus(String keywords);

        //        点击分类的回调方法
        void classifyClick();
    }

    private SearchCallback searchCallback;

    public void setSearchCallback(SearchCallback searchCallback) {
        this.searchCallback = searchCallback;
    }
}
