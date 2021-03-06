package doit.doittestapplication.ui.registration

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import doit.doittestapplication.addSchedulers
import doit.doittestapplication.data.repository.MainRepositoryProvider
import doit.doittestapplication.data.storage.Storage
import doit.doittestapplication.ui.base.BasePresenter
import io.reactivex.disposables.Disposable
import okhttp3.RequestBody

@InjectViewState
class RegistrationPresenter : BasePresenter<RegistrationView>() {

    private var dispose : Disposable? = null

    override fun onDestroy() {
        dispose()
    }

    fun registration(username : RequestBody,
                     email : RequestBody,
                     password : RequestBody,
                     imageFileUri : Uri) {

        dispose = MainRepositoryProvider.instance
                .registration(username, email, password, imageFileUri)
                .addSchedulers()
                .doOnSubscribe { viewState.showLoading() }
                .doFinally { viewState.hideLoading() }
                .subscribe({
                    Storage.putToken(it.token)
                    viewState.registrationSuccess()
                }, this::handleError)
    }

    private fun dispose(){
        if (dispose != null) dispose!!.dispose()
    }

}