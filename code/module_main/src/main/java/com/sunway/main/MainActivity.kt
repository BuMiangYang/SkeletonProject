package com.sunway.main

import com.alibaba.android.arouter.facade.annotation.Route
import com.sunway.common.base.BaseActivity
import com.sunway.common.constants.ARouterConfig
import com.sunway.common.manage.IntentManage
import kotlinx.android.synthetic.main.act_main_main.*

/**
 * @author BuMingYang
 * @des
 */
@Route(path = ARouterConfig.MAIN_INDEX)
class MainActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.act_main_main
    }

    override fun initData() {
    }

    override fun initView() {
        tv_main.setOnClickListener {

            IntentManage.toActivity(MainRouter.MAIN_ROUTE)

        }
    }

    override fun start() {
    }

}
