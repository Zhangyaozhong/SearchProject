package com.bwie.android.exercise1.net;

import android.os.Handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkhttpUtil {
    private static OkhttpUtil okhttpUtil;
    private final OkHttpClient client;
    Handler handler = new Handler();

    private OkhttpUtil() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public static OkhttpUtil getInstance() {
        if (okhttpUtil == null) {
            synchronized (OkhttpUtil.class) {
                if (okhttpUtil == null) {
                    okhttpUtil = new OkhttpUtil();
                }
            }
        }
        return okhttpUtil;
    }
    /**
     * get请求方式
     */
    public void doGet(String url,HashMap<String, String> parmas, final OkCallback requestCallback) {

        StringBuilder p = new StringBuilder();
        if (parmas!=null&&parmas.size()>0){
            for (Map.Entry<String, String> map : parmas.entrySet()) {

                p.append(map.getKey()).append("=").append(map.getValue()).append("&");
            }

            System.out.println("ppppppp===="+p.toString());
        }
        Request request = new Request.Builder().url(url+"?"+p.toString())
                .get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (requestCallback != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCallback.failure("网络异常");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                if (requestCallback != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCallback.success(result);

                        }
                    });
                }
            }
        });


    }
    public void doPost(String url, HashMap<String, String> params, final OkCallback okCallback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (okCallback != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallback.failure("网络出错,请稍后再试");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (okCallback != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallback.success(result);
                        }
                    });
                }
            }
        });
    }
}
