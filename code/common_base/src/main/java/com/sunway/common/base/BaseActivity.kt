package com.sunway.common.base

import android.content.Context
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.launcher.ARouter
import com.sunway.common.R
import com.sunway.common.constants.BaseConstant
import com.sunway.common.event.NetworkChangeEvent
import com.sunway.common.receiver.NetworkReceiver
import com.sunway.common.utils.CommonUtil
import com.sunway.common.utils.KeyBoardUtil
import com.sunway.common.utils.Preference
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *  @author BuMingYang
 *  @des Act 基类
 */
abstract class BaseActivity : AppCompatActivity() {

    //check login
    private var isLogin: Boolean by Preference(BaseConstant.LOGIN_KEY, false)

    //缓存上一次的网络状态
    private var hasNetwork: Boolean by Preference(BaseConstant.HAS_NETWORK_KEY, true)

    //网络状态变化的广播
    private var mNetworkChangeReceiver: NetworkReceiver? = null

    //theme color
//    private var mThemeColor: Int = SettingUtil.getColor()

    //多种状态的 View 的切换
//    protected var mLayoutStatusView: MultipleStatusView? = null

    //提示View
    private lateinit var mTipView: View
    private lateinit var mWindowManager: WindowManager
    private lateinit var mLayoutParams: WindowManager.LayoutParams

    //布局文件id
    protected abstract fun attachLayoutRes(): Int

    //初始化数据
    abstract fun initData()

    //初始化 View
    abstract fun initView()

    //开始请求
    abstract fun start()

    //是否使用 EventBus
    open fun useEventBus(): Boolean = true

    /**
     * 是否需要显示 TipView
     */
    open fun enableNetworkTip(): Boolean = true

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // AutoDensityUtil.setCustomDensity(this, App.instance)
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(attachLayoutRes())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initData()
        initTipView()
        initView()
        start()
        initListener()

    }

    override fun onResume() {
        // 动态注册网络变化广播
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mNetworkChangeReceiver = NetworkReceiver()
        registerReceiver(mNetworkChangeReceiver, filter)
        super.onResume()

//        initColor()

    }

//    open fun initColor() {
//        mThemeColor = if (!SettingUtil.getIsNightMode()) {
//            SettingUtil.getColor()
//        } else {
//            resources.getColor(R.color.colorPrimary)
//        }
//        StatusBarUtil.setColor(this, mThemeColor, 0)
//        if (this.supportActionBar != null) {
//            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            window.statusBarColor = CircleView.shiftColorDown(mThemeColor)
////            // 最近任务栏上色
////            val tDesc = ActivityManager.TaskDescription(
////                    getString(R.string.app_name),
////                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
////                    mThemeColor)
////            setTaskDescription(tDesc)
//            if (SettingUtil.getNavBar()) {
//                window.navigationBarColor = CircleView.shiftColorDown(mThemeColor)
//            } else {
//                window.navigationBarColor = Color.BLACK
//            }
//        }
//    }

    /**
     * 初始化 TipView
     */
    private fun initTipView() {
        mTipView = layoutInflater.inflate(R.layout.layout_network_tip, null)
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        )
        mLayoutParams.gravity = Gravity.TOP
        mLayoutParams.x = 0
        mLayoutParams.y = 0
        mLayoutParams.windowAnimations = R.style.anim_float_view // add animations
    }

    private fun initListener() {
//        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    protected fun initToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean, title: String) {
        toolbar?.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    }

    //Network Change
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        hasNetwork = event.isConnected
        checkNetwork(event.isConnected)
    }

    private fun checkNetwork(isConnected: Boolean) {
        if (enableNetworkTip()) {
            if (isConnected) {
                doReConnected()
                if (mTipView != null && mTipView.parent != null) {
                    mWindowManager.removeView(mTipView)
                }
            } else {
                if (mTipView.parent == null) {
                    mWindowManager.addView(mTipView, mLayoutParams)
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            // 如果不是落在EditText区域，则需要关闭输入法
            if (KeyBoardUtil.isHideKeyboard(v, ev)) {
                KeyBoardUtil.hideKeyBoard(this, v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Fragment 逐个出栈
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onPause() {
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver)
            mNetworkChangeReceiver = null
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        CommonUtil.fixInputMethodManagerLeak(this)
//        App.getRefWatcher(this)?.watch(this)
    }

    override fun finish() {
        super.finish()
        if (mTipView != null && mTipView.parent != null) {
            mWindowManager.removeView(mTipView)
        }
    }
}