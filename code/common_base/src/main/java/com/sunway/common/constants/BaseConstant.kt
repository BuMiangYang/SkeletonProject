package com.sunway.common.constants

import com.sunway.common.utils.Preference

/**
 *  @author BuMingYang
 *  @des
 */
open class BaseConstant {

    companion object {

        const val BASE_URL = ""

        const val DEFAULT_TIMEOUT: Long = 15

        // 50M 的缓存大小
        const val MAX_CACHE_SIZE: Long = 1024 * 1024 * 50

        const val SAVE_USER_LOGIN_KEY = "user/login"

        const val SAVE_USER_REGISTER_KEY = "user/register"

        const val COLLECTIONS_WEBSITE = "lg/collect"

        const val UNCOLLECTIONS_WEBSITE = "lg/uncollect"

        const val ARTICLE_WEBSITE = "article"

        const val TODO_WEBSITE = "lg/todo"

        const val COIN_WEBSITE = "lg/coin"

        const val SET_COOKIE_KEY = "set-cookie"

        const val COOKIE_NAME = "Cookie"


        const val BUGLY_ID = "76e2b2867d"


        const val LOGIN_KEY = "login"
        const val USERNAME_KEY = "username"
        const val PASSWORD_KEY = "password"
        const val TOKEN_KEY = "token"
        const val HAS_NETWORK_KEY = "has_network"

        const val TODO_NO = "todo_no"
        const val TODO_ADD = "todo_add"
        const val TODO_DONE = "todo_done"

        //url key
        const val CONTENT_URL_KEY = "url"
        //title key
        const val CONTENT_TITLE_KEY = "title"
        //id key
        const val CONTENT_ID_KEY = "id"
        //id key
        const val CONTENT_CID_KEY = "cid"
        /**
         * share key
         */
        const val CONTENT_SHARE_TYPE = "text/plain"
        /**
         * content data key
         */
        const val CONTENT_DATA_KEY = "content_data"

        const val TYPE_KEY = "type"

        const val SEARCH_KEY = "search_key"

        const val TODO_TYPE = "todo_type"
        const val TODO_BEAN = "todo_bean"

        object Type {
            const val COLLECT_TYPE_KEY = "collect_type"
            const val ABOUT_US_TYPE_KEY = "about_us_type_key"
            const val SETTING_TYPE_KEY = "setting_type_key"
            const val SEARCH_TYPE_KEY = "search_type_key"
            const val ADD_TODO_TYPE_KEY = "add_todo_type_key"
            const val SEE_TODO_TYPE_KEY = "see_todo_type_key"
            const val EDIT_TODO_TYPE_KEY = "edit_todo_type_key"
        }


        fun encodeCookie(cookies: List<String>): String {
            val sb = StringBuilder()
            val set = HashSet<String>()
            cookies
                .map { cookie ->
                    cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                }
                .forEach {
                    it.filterNot { set.contains(it) }.forEach { set.add(it) }
                }
            val ite = set.iterator()
            while (ite.hasNext()) {
                val cookie = ite.next()
                sb.append(cookie).append(";")
            }
            val last = sb.lastIndexOf(";")
            if (sb.length - 1 == last) {
                sb.deleteCharAt(last)
            }
            return sb.toString()
        }

        fun saveCookie(url: String?, domain: String?, cookies: String) {
            url ?: return
            var spUrl: String by Preference(url, cookies)
            @Suppress("UNUSED_VALUE")
            spUrl = cookies
            domain ?: return
            var spDomain: String by Preference(domain, cookies)
            @Suppress("UNUSED_VALUE")
            spDomain = cookies
        }


    }



}