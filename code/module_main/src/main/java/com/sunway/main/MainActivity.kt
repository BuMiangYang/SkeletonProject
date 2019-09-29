package com.sunway.main

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sunway.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author BuMingYang
 * @des
 */
@Route(path = "/main/index")
class MainActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initView() {
        tv_main.setOnClickListener {

            ARouter.getInstance().build("/main/routeAct").navigation()

        }
    }

    override fun start() {
    }

}
