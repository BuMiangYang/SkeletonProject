package com.sunway.common.http.interceptor

import com.sunway.common.constants.BaseConstant
import com.sunway.common.utils.Preference
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    private var token: String by Preference(BaseConstant.TOKEN_KEY, "")

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("Content-type", "application/json; charset=utf-8")
        // .header("token", token)
        // .method(request.method(), request.body())

        val domain = request.url().host()
        val url = request.url().toString()
        if (domain.isNotEmpty() && (url.contains(BaseConstant.COLLECTIONS_WEBSITE)
                    || url.contains(BaseConstant.UNCOLLECTIONS_WEBSITE)
                    || url.contains(BaseConstant.ARTICLE_WEBSITE)
                    || url.contains(BaseConstant.TODO_WEBSITE)
                    || url.contains(BaseConstant.COIN_WEBSITE))
        ) {
            val spDomain: String by Preference(domain, "")
            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
            if (cookie.isNotEmpty()) {
                // 将 Cookie 添加到请求头
                builder.addHeader(BaseConstant.COOKIE_NAME, cookie)
            }
        }

        return chain.proceed(builder.build())
    }

}