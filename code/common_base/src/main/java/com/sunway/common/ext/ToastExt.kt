package com.sunway.common.ext

import com.orhanobut.logger.Logger

/**
 *  @author BuMingYang
 *  @des
 */
fun Any.loge(content: String?) {
    loge(this.javaClass.simpleName, content ?: "")
}

fun loge(tag: String, content: String?) {
    content?.run {
        Logger.e(tag, content)
    }
}

