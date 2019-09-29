package com.sunway.login

import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sunway.common.base.BaseActivity
import com.sunway.common.constants.ARouterConfig
import com.sunway.common.manage.IntentManage
import kotlinx.android.synthetic.main.act_login_launch.*

/**
 * @author BuMingYang
 * @des
 */
@Route(path = ARouterConfig.LOGIN_LAUNCH)
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

        // 模块内跳转
        btn_main.setOnClickListener {

            IntentManage.toActivity(ARouterConfig.LOGIN_MAIN)
        }
        // 模块间跳转
        btn_module_main.setOnClickListener {

            ARouter.getInstance().build(ARouterConfig.MAIN_INDEX).navigation()

        }
    }

    override fun start() {

        Toast.makeText(this@LaunchActivity, "Login", Toast.LENGTH_SHORT).show()

    }
}
