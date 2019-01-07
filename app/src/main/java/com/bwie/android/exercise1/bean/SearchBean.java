package com.bwie.android.exercise1.bean;

import java.util.List;

public class SearchBean {
    public String message;
    public String status;
    public List<ResultBean> result;

    public class ResultBean {
        public String commodityName;
        public String masterPic;
        public String price;

    }
}
