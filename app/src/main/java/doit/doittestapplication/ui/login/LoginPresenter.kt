package doit.doittestapplication.ui.login

import com.arellomobile.mvp.InjectViewState
import doit.doittestapplication.addSchedulers
import doit.doittestapplication.data.api.model.SignInRequestBody
import doit.doittestapplication.data.repository.MainRepositoryProvider
import doit.doittestapplication.data.storage.Storage
import doit.doittestapplication.ui.base.BasePresenter
import io.reactivex.disposables.Disposable

@InjectViewState
class LoginPresenter : BasePresenter<LoginView>() {

    private var dispose : Disposable? = null

    override fun onDestroy() {
        dispose()
    }

    fun login(userData: SignInRequestBody) {

        dispose = MainRepositoryProvider.instance
                .login(userData)
                .addSchedulers()
                .doOnSubscribe { viewState.showLoading() }
                .doFinally { viewState.hideLoading() }
                .subscribe({
                    Storage.putToken(it.token)
                    viewState.showMainScreen()
                }, this::handleError)
    }

    private fun dispose(){
        if (dispose != null) dispose!!.dispose()
    }


}