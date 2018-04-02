package com.example.piguaiweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author Richard_Y_Wang
 * @version $Rev$
 * @des 2018/3/30
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
