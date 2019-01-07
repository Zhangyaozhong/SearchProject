package com.bwie.android.exercise1.presenter;

import com.bwie.android.exercise1.bean.SearchBean;
import com.bwie.android.exercise1.contract.SearchContract;
import com.bwie.android.exercise1.model.SearchModel;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class SearchPresenter extends SearchContract.SearchPresenter {
    private SearchContract.ISearchView iClassifyView;
    private SearchModel searchModel;

    public SearchPresenter(SearchContract.ISearchView iClassifyView) {
        this.iClassifyView = iClassifyView;
        this.searchModel = new SearchModel();
    }

    @Override
    public void getData(HashMap<String, String> params) {
        if (searchModel!=null){
            searchModel.getData(params, new SearchModel.SearchCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    SearchBean searchBean = gson.fromJson(result, SearchBean.class);
                    List<SearchBean.ResultBean> list = searchBean.result;
                    if (iClassifyView!=null){
                        iClassifyView.success(list);
                    }
                }

                @Override
                public void failure(String msg) {
                    if (iClassifyView!=null){
                        iClassifyView.error(msg);
                    }
                }
            });
        }
    }
}
