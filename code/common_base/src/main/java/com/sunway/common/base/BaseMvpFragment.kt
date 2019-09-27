package com.sunway.common.base

import android.view.View

/**
 *  @author BuMingYang
 *  @des
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpFragment<in V : IView, P : IPresenter<V>> : BaseFragment(), IView {

    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
        this.mPresenter = null
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
//        showToast(errorMsg)
    }

    override fun showDefaultMsg(msg: String) {
//        showToast(msg)
    }

    override fun showMsg(msg: String) {
//        showToast(msg)
    }
}