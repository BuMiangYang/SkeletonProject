package com.sunway.common.manage

import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import java.util.*

/**
 *  @author BuMingYang
 *  @des
 */
object IntentManage {

    fun toActivity(url: String) {
        toActivity(url, null)
    }

    fun toActivity(url: String, params: Map<String, *>?) {
        if (TextUtils.isEmpty(url)) {
            return
        }
        val postcard = ARouter.getInstance()
            .build(url)
        if (params != null) {
            for ((key) in params) {
                when (val value = params[key]) {
                    is String -> postcard.withString(key, value)
                    is Boolean -> postcard.withBoolean(key, value)
                    is Int -> postcard.withInt(key, value)
                    is Float -> postcard.withFloat(key, value)
                    is Double -> postcard.withDouble(key, value)
                    is Long -> postcard.withLong(key, value)
                    is Short -> postcard.withShort(key, value)
                    //                else if (value is BaseBean) {
                    //                    postcard.withSerializable(key, value as BaseBean)
                    //                }
                    is ArrayList<*> -> postcard.withSerializable(key, value)
                    is HashMap<*, *> -> postcard.withSerializable(key, value)
                }
            }
        }
        postcard.navigation()
    }




}