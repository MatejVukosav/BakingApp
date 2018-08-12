package com.vuki.bakingapp.network;

import com.squareup.moshi.Moshi;
import com.vuki.bakingapp.network.logging.LoggingInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by mvukosav
 */
public class ApiManager implements ApiManagerInterface {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static ApiManager apiManagerInstance;
    private static ApiManagerService apiManagerService;
    private static Moshi moshi;

    private ApiManager() {
    }

    public static synchronized ApiManager getInstance() {
        if ( apiManagerInstance == null ) {
            apiManagerInstance = new ApiManager();

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor( new LoggingInterceptor( LoggingInterceptor.LogLevel.NONE ) )
                    .build();

            moshi = new Moshi.Builder()
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl( BASE_URL )
                    .addConverterFactory( MoshiConverterFactory.create( moshi ) )
                    .client( client )
                    .build();

            apiManagerService = retrofit.create( ApiManagerService.class );
        }
        return apiManagerInstance;
    }

    @Override
    public ApiManagerService getService() {
        return apiManagerService;
    }

    static Moshi getMoshi() {
        return moshi;
    }
}