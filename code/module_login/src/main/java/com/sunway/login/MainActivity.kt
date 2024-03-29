package com.sunway.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.sunway.common.base.BaseActivity
import com.sunway.common.constants.ARouterConfig
import com.sunway.common.ext.loge
import com.sunway.common.manage.IntentManage
import kotlinx.android.synthetic.main.act_login_main.*

/**
 * @author BuMingYang
 * @des
 */
@Route(path = LoginRouter.LOGIN_MAIN)
class MainActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.act_login_main
    }

    override fun initData() {
    }

    override fun initView() {

        btn_main.setOnClickListener {
            IntentManage.toActivity(ARouterConfig.MAIN_INDEX)
        }

        btn_crash.setOnClickListener {

            val test = arrayListOf(1, 2, 3, 4)

            loge("${test[4]}")

//            IntentManage.toActivity(ARouterConfig.CRASH_INDEX)


        }
    }

    override fun start() {
    }
}
