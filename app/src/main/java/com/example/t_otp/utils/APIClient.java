package com.example.t_otp.utils;

import android.util.Log;

import com.example.t_otp.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String TAG = "APIClient";
    
    public static final String BASE_URL = BuildConfig.APP_ENDPOINT;

    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;

    private static String bodyToString(final Request request){
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public static Retrofit getClient() {
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(final Chain chain) throws IOException {
                            Request request = chain.request();
                            Response response;

                            if(request.method().equals("POST") || request.method().equals("PATCH")) {
                                Log.d(TAG, "intercept: " + bodyToString(request));
                            }

                            response = chain.proceed(request);

                            String jsonStr = response.body().string();

                            JSONObject jsonResponse;
                            String jsonAltResponse = null;
                            try {
                                jsonResponse = new JSONObject(jsonStr);
                                if (jsonResponse.getBoolean("encrypted")){
                                    // TODO: Decrypt response
                                    jsonAltResponse = jsonResponse.toString();
                                }else{
                                    jsonAltResponse = jsonResponse.get("payload").toString();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                            if(jsonAltResponse != null) {
                                MediaType mediaType = response.body().contentType();
                                ResponseBody body = ResponseBody.create(mediaType, jsonAltResponse);
                                return response.newBuilder().body(body).build();
                            }

                            return response;
                        }
                    })
                    .build();
        }

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
