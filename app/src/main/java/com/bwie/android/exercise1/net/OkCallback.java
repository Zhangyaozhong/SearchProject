package com.bwie.android.exercise1.net;

public interface OkCallback {
    void failure(String msg);

    void success(String result);
}
