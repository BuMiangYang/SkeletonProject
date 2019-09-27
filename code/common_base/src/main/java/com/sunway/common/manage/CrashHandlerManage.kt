package com.sunway.common.manage

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.orhanobut.logger.Logger
import com.sunway.common.base.BaseApplication
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @author BuMingYang
 * @des 用来存储设备信息和异常信息
 */
@SuppressLint("StaticFieldLeak")
object CrashHandlerManage : Thread.UncaughtExceptionHandler {

    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    private val info = HashMap<String, String>()
    private var mContext: Context? = null

    fun init(context: Context) {
        mContext = context
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
        // 设置该CrashHandler为程序的默认处理器
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) and (mDefaultHandler != null)) {
            // 如果自定义的没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            try {
                Thread.sleep(3000)
                // 如果处理了，让程序继续运行3秒再退出，保证文件保存并上传到服务器
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            (mContext as BaseApplication).exitApp()
            // 退出程序
        }
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 异常信息
     * @return true 如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        // 收集设备参数信息
        collectDeviceInfo(mContext)
        // 保存日志文件
        saveCrashInfo2File(ex)
        return true
    }

    //收集设备参数信息
    private fun collectDeviceInfo(context: Context?) {
        try {
            val pm = context!!.packageManager
            // 获得包管理器
            val pi = pm.getPackageInfo(
                context.packageName,
                PackageManager.GET_ACTIVITIES
            )
            // 得到该应用的信息，即主Activity
            if (pi != null) {
                val versionName = if (pi.versionName == null)
                    "null"
                else
                    pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                info["versionName"] = versionName
                info["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Logger.e("获取设置信息失败")
        }

        val fields = Build::class.java.declaredFields
        // 反射机制
        for (field in fields) {
            try {
                field.isAccessible = true
                info[field.name] = field.get("").toString()
                Logger.e(field.name + ":" + field.get(""))
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
    }

    private fun saveCrashInfo2File(ex: Throwable): String? {
        val sb = StringBuilder()
        for ((key, value) in info) {
            sb.append(key).append("=").append(value).append("\r\n")
        }
        val writer = StringWriter()
        val pw = PrintWriter(writer)
        ex.printStackTrace(pw)
        var cause: Throwable? = ex.cause
        // 循环着把所有的异常信息写入writer中
        while (cause != null) {
            cause.printStackTrace(pw)
            cause = cause.cause
        }
        pw.close()// 记得关闭
        val result = writer.toString()
        sb.append(result)
        //        // 保存文件
        //        String fileName = "crash-" + DateUtils.getCurrentDateStr() + "-" + DateUtils.getCurrentTimeStamp() + ".log";
        //        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        //            try {
        //                File file = new File(FileUtils.getAppCrashPath(), fileName);
        //                FileOutputStream fos = new FileOutputStream(file);
        //                fos.write(sb.toString().getBytes());
        //                fos.close();
        //                return fileName;
        //            } catch (IOException e) {
        //                e.printStackTrace();
        //            }
        //        }
        Logger.e("crash:>>>$sb")
        return sb.toString()
    }
}


//private constructor() : Thread.UncaughtExceptionHandler {
//    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
//    private var mContext: Context? = null
//    // 程序的Context对象
//    private val info = HashMap<String, String>()
//
//


//

//
////    companion object {
////
////        private val TAG = "CrashHandlerManage"
////        // 系统默认的UncaughtException处理类
////        private var INSTANCE: CrashHandlerManage? = null
////
////        val instance: CrashHandlerManage
////            @Synchronized get() {
////                if (INSTANCE == null) {
////                    INSTANCE = CrashHandlerManage()
////                }
////                return INSTANCE
////            }
////
////        /**
////         * 上传日志文件
////         */
////        fun uploadCrashFiles() {
////            //        final File outFile = FileUtils.getAppCrashPath();
////            //        LinkedList<File> files = FileUtils.listLinkedFiles(FileUtils.getAppCrashPath().getPath());
////            //        if (files == null || files.size() == 0) {
////            //            return;
////            //        }
////            //        try {
////            //            ZipUtils.zipFiles(files, outFile);
////            //        } catch (IOException e) {
////            //            e.printStackTrace();
////            //        }
////            //        if (!outFile.exists()) {
////            //            //如果这个zip文件不存在的话，则不执行如下的操作
////            //            return;
////            //        }
////            //TODO  做上传操作
////        }
////    }
//
//}
