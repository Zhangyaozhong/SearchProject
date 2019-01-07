package com.bwie.android.exercise1.model;

import com.bwie.android.exercise1.api.SearchApi;
import com.bwie.android.exercise1.contract.SearchContract;
import com.bwie.android.exercise1.net.OkCallback;
import com.bwie.android.exercise1.net.OkhttpUtil;

import java.util.HashMap;

public class SearchModel implements SearchContract.ISearchModel {


    @Override
    public void getData(HashMap<String, String> params, final SearchCallback searchCallback) {
        OkhttpUtil.getInstance().doGet(SearchApi.SEARCH_URL, params, new OkCallback() {
            @Override
            public void failure(String msg) {
                if (searchCallback != null) {
                    searchCallback.failure(msg);
                }
            }

            @Override
            public void success(String result) {
                if (searchCallback != null) {
                    searchCallback.success(result);
                }
            }
        });
    }

    public interface SearchCallback {
        void success(String result);

        void failure(String msg);

    }

    private SearchCallback searchCallback;

    public void setSearchCallback(SearchCallback searchCallback) {
        this.searchCallback = searchCallback;
    }
}
