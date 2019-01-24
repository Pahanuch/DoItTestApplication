package doit.doittestapplication.ui.base

import com.arellomobile.mvp.MvpPresenter
import doit.doittestapplication.BuildConfig
import doit.doittestapplication.data.api.exception.ServerException
import retrofit2.HttpException

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    protected fun handleError(e: Throwable) {

        when (e) {
            is ServerException -> {
                val message: String? = e.message
                if (message == null || message.isEmpty()) {
                    viewState.showUnknownError()
                } else viewState.showError(message)
            }
            is HttpException -> viewState.showNetworkError()
            else -> {
                viewState.showUnknownError()
            }
        }

        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }
    }
}