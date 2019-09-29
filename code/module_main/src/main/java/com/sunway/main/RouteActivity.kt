package com.sunway.main

import com.alibaba.android.arouter.facade.annotation.Route
import com.sunway.common.base.BaseActivity

@Route(path = "/main/routeAct")
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
