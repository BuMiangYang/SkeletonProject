package com.sunway.main

import com.alibaba.android.arouter.facade.annotation.Route
import com.sunway.common.base.BaseActivity
import com.sunway.common.constants.ARouterConfig

@Route(path = ARouterConfig.MAIN_ROUTE)
class RouteActivity : BaseActivity() {
    override fun attachLayoutRes(): Int {
        return R.layout.act_main_route
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun start() {
    }
}
