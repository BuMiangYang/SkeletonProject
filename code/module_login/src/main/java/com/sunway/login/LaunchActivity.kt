package com.sunway.login

import android.os.Build
import android.os.Handler
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.sunway.common.base.BaseActivity
import com.sunway.common.manage.IntentManage

/**
 * @author BuMingYang
 * @des
 */
class LaunchActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.act_login_launch
    }

    override fun initData() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            val decorView = window.decorView
            decorView.setOnApplyWindowInsetsListener { v, insets ->
                val defaultInsets = v.onApplyWindowInsets(insets)
                defaultInsets.replaceSystemWindowInsets(
                    defaultInsets.systemWindowInsetLeft,
                    0,
                    defaultInsets.systemWindowInsetRight,
                    defaultInsets.systemWindowInsetBottom
                )
            }
            ViewCompat.requestApplyInsets(decorView)
            //将状态栏设成透明，如不想透明可设置其他颜色
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        }
    }

    override fun initView() {


    }

    override fun start() {

        Handler().postDelayed({

            IntentManage.toActivity(LoginRouter.LOGIN_MAIN)
            finish()

        }, 3000)


    }
}
