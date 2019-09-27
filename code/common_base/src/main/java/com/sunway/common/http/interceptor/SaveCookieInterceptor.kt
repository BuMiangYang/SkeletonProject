package com.sunway.common.http.interceptor

import com.sunway.common.constants.BaseConstant
import okhttp3.Interceptor
import okhttp3.Response

class SaveCookieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url().toString()
        val domain = request.url().host()
        // set-cookie maybe has multi, login to save cookie
        if ((requestUrl.contains(BaseConstant.SAVE_USER_LOGIN_KEY)
                    || requestUrl.contains(BaseConstant.SAVE_USER_REGISTER_KEY))
            && response.headers(BaseConstant.SET_COOKIE_KEY).isNotEmpty()
        ) {
            val cookies = response.headers(BaseConstant.SET_COOKIE_KEY)
            val cookie = BaseConstant.encodeCookie(cookies)
            BaseConstant.saveCookie(requestUrl, domain, cookie)
        }
        return response
    }
}