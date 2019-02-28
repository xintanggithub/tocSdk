package com.jidouauto.market.module.lib.common.http;

import com.jidouauto.market.module.lib.api.ApiStatus;
import com.jidouauto.market.module.lib.api.MarketApi;
import com.jidouauto.market.module.lib.api.bean.MarketBaseResponse;
import com.jidouauto.market.module.lib.common.LogUtils;
import com.jidouauto.market.module.lib.common.MarketConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created tangxin
 * Time 2018/10/12 下午1:53
 */
public class RetrofitFactory {

    public static <T> T createApi(final Class<T> service) {
        return createApi(service, MarketApi.MARKET_HOST);
    }

    public static <T> T createApi(final Class<T> service, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(buildOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(service);
    }

    public static <T extends MarketBaseResponse> Disposable createData(Observable<T> observable
            , final ApiStatus<T> apiStatus) {
        apiStatus.before();
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) {
                        if (null == t) {
                            apiStatus.isEmpty();
                            return;
                        }
                        if (t.isResult()) {
                            apiStatus.success(t);
                        } else {
                            if (t.getReason().equals("invalid token")) {
                                apiStatus.tokenInvalid(new Throwable(t.getReason()));
                            }
                            apiStatus.error(new Throwable(t.getReason()));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        apiStatus.error(throwable);
                    }
                });
    }

    public static OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(60, TimeUnit.SECONDS)//读取超时
                .writeTimeout(60, TimeUnit.SECONDS);//写入超时
        builder.addInterceptor(mLoggingInterceptor);//添加日志拦截器：显示链接信息
        builder.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY));
        return builder.build();
    }

    private final static Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            String language = MarketConfig.getLanguageCountry();
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("X-AUTH-LANGUAGE", language)
                    .header("X-AUTH-DEVICE-TYPE", "android")
                    .header("locale", language)
                    .header("brandChannel", MarketConfig.getJdChannel())
                    .method(original.method(), original.body())
                    .build();
            Response response = chain.proceed(request);
            LogUtils.i(String.format("Sending request: %s on %s%n%s"
                    , request.url(), chain.connection(), request.headers()));
            return response;
        }
    };

}
