package com.sunway.common.base

import io.reactivex.disposables.Disposable

interface IModel {

    fun addDisposable(disposable: Disposable?)

    fun onDetach()

}