package com.doomsday.base.utils;

import com.doomsday.base.Base;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/12/19.
 */

public class RtUtil {

    private static String BASE_URL;

    private static OkHttpClient client;
    private static Retrofit retrofitGson;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        Cache cache = new Cache(new File(Base.instance.getCacheDir(), "OkHttp"), 50 * 1024 * 1024);
        builder.cache(cache);
        builder.addNetworkInterceptor(new LogInterceptor());
        client = builder.build();
    }

    public static void setBaseUrl(String url) {
        BASE_URL = url;
        retrofitGson = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <T> T createGsonService(Class<T> serviceClass) {
        if (retrofitGson == null)
            throw new NullPointerException("not set base url");
        return retrofitGson.create(serviceClass);
    }

    private static class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.currentTimeMillis();
            RequestBody body1 = request.body();
            Request.Builder requestBuilder = request.newBuilder();
            ArrayList<String> list = null;
            if (body1 instanceof FormBody) {
                FormBody body = (FormBody) body1;
                list = new ArrayList<>();
                for (int i = 0; i < body.size(); i++) {
                    list.add(body.name(i) + ": " + body.value(i));
                }
            }
            Response response = chain.proceed(requestBuilder.build());
            //用于打印响应body
            BufferedSource bufferedSource = response.body().source();
            bufferedSource.request(Long.MAX_VALUE);
            Buffer buffer = bufferedSource.buffer();
            Logger.i("url: " + request.url() + "\nrequest body: " + list +
                    "\nrequest time: " + (System.currentTimeMillis() - t1));
            Logger.json(buffer.clone().readUtf8());
            return response;
        }
    }

}
