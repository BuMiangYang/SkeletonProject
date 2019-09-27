package com.sunway.common.base

/**
 *  @author BuMingYang
 *  @des
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    private var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
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