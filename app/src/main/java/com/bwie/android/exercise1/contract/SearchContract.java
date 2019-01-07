package com.bwie.android.exercise1.contract;

import com.bwie.android.exercise1.bean.SearchBean;
import com.bwie.android.exercise1.model.SearchModel;

import java.util.HashMap;
import java.util.List;

public interface SearchContract {
    abstract class SearchPresenter{
       public abstract void getData(HashMap<String,String> params);
    }
    interface ISearchModel{
        void getData(HashMap<String,String> params, SearchModel.SearchCallback searchCallback);
    }
    interface ISearchView{
        void success(List<SearchBean.ResultBean> resultBeans);
        void error(String msg);
    }
}
