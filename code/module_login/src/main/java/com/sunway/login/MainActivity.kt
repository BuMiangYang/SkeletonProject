package com.sunway.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.sunway.common.base.BaseActivity
import com.sunway.common.constants.ARouterConfig

@Route(path = ARouterConfig.LOGIN_MAIN)
class MainActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun start() {
    }
}
