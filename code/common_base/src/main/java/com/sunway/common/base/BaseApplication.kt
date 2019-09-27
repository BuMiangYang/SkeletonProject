package com.sunway.common.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogcatLogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.sunway.common.BuildConfig
import com.sunway.common.manage.CrashHandlerManage
import kotlin.properties.Delegates


/**
 *  @author BuMingYang
 *  @des 基础Application所有需要模块化开发的module都需要继承自BaseApplication
 *  初始化路由,拆包
 */
open class BaseApplication : Application() {

    companion object {
        var context: Context by Delegates.notNull()
            private set
        lateinit var instance: Application
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //MultiDex分包方法 必须最先初始化
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        initARouter()
        initLogger()
        initCrashManage()
    }

    /**
     * 初始化路由
     */
    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this)
    }

    //初始化日志打印框架
    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)                   //（可选）是否显示线程信息。 默认值为true
            .methodCount(2)                          //（可选）要显示的方法行数。 默认2
            .methodOffset(7)                         //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
            .logStrategy(LogcatLogStrategy())    //（可选）更改要打印的日志策略。 默认LogCat
            .tag("sunWay")                              //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                //DEBUG模式下不打印LOG
                return BuildConfig.DEBUG
            }
        })
    }

    //初始化崩溃管理器
    private fun initCrashManage() {

        if (!BuildConfig.DEBUG) {
            CrashHandlerManage.init(applicationContext)
        }
    }

    //退出应用
    fun exitApp() {
//        activityManage.finishAll()
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

}