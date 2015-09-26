package com.hackgt2015project.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by ZkHaider on 9/25/15.
 */
public class DropAPIService {

    private static final String TAG = DropAPIService.class.getSimpleName();
    private static final String API_URL = "";
    private static final String API_KEY = "";

    private static DropAPIService sDropAPIService;
    private static RestAdapter mAsyncRestAdapter;

    public static DropAPIService getInstance() {
        if (sDropAPIService == null)
            sDropAPIService = new DropAPIService();
        return sDropAPIService;
    }

    private DropAPIService() {
        mAsyncRestAdapter = getAsyncRestAdapter();
    }

    public static RestAdapter getAsyncRestAdapter() {
        if (mAsyncRestAdapter == null) {
            mAsyncRestAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setRequestInterceptor(getRequestInterceptor())
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
        }
        return mAsyncRestAdapter;
    }

    private static RequestInterceptor getRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addQueryParam("api_key", API_KEY);
            }
        };
    }

}
