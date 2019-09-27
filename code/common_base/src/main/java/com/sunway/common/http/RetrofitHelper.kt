package com.sunway.common.http

import com.sunway.common.BuildConfig
import com.sunway.common.base.BaseApi
import com.sunway.common.base.BaseApplication
import com.sunway.common.constants.BaseConstant
import com.sunway.common.http.interceptor.CacheInterceptor
import com.sunway.common.http.interceptor.HeaderInterceptor
import com.sunway.common.http.interceptor.SaveCookieInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 */
object RetrofitHelper {

    private var retrofit: Retrofit? = null

    val service: BaseApi by lazy { getRetrofit()!!.create(BaseApi::class.java) }

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitHelper::class.java) {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BaseConstant.BASE_URL)  // baseUrl
                        .client(getOkHttpClient())
                        .addConverterFactory(MoshiConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                }
            }
        }
        return retrofit
    }

    /**
     * 获取 OkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        //设置 请求的缓存的大小跟位置
        val cacheFile = File(BaseApplication.context.cacheDir, "cache")
        val cache = Cache(cacheFile, BaseConstant.MAX_CACHE_SIZE)

        builder.run {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(HeaderInterceptor())
            addInterceptor(SaveCookieInterceptor())
            addInterceptor(CacheInterceptor())
            cache(cache)  //添加缓存
            connectTimeout(BaseConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(BaseConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(BaseConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true) // 错误重连
            // cookieJar(CookieManager())
        }
        return builder.build()
    }

}