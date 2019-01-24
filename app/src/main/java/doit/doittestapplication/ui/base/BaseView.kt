package doit.doittestapplication.ui.base

import com.arellomobile.mvp.MvpView

interface BaseView : MvpView {

    fun showNetworkError()

    fun showUnknownError()

    fun showError(message: String)

    fun showMessage(message: String)

    fun showLoading()

    fun hideLoading()

}