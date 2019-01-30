package doit.doittestapplication.ui.main

import com.arellomobile.mvp.InjectViewState
import doit.doittestapplication.addSchedulers
import doit.doittestapplication.data.repository.MainRepositoryProvider
import doit.doittestapplication.data.storage.Storage
import doit.doittestapplication.ui.base.BasePresenter
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    private var disposePicture : Disposable? = null
    private var disposeGif : Disposable? = null

    override fun onDestroy() {
        dispose()
    }

    fun getAllPictures(update : Boolean){
        disposePicture = MainRepositoryProvider.instance
                .getAllPictures()
                .delay(1, TimeUnit.SECONDS)
                .addSchedulers()
                .doOnSubscribe { viewState.showLoading() }
                .doFinally { viewState.hideLoading() }
                .subscribe({
                    viewState.showPictures(it)
                    if (update) viewState.hideSwRefresh()
                }, this::handleError)

    }

    fun getGifPicture() {
        disposeGif = MainRepositoryProvider.instance
                .getGif()
                .addSchedulers()
                .doOnSubscribe { viewState.showLoading() }
                .doFinally { viewState.hideLoading() }
                .subscribe({
                    viewState.showGifPicture(it)
                }, this::handleError)

    }

    fun logout(){
        Storage.clearToken()
        viewState.showLoginForm()
    }

    private fun dispose(){
        if (disposePicture != null) disposePicture!!.dispose()
        if (disposeGif != null) disposeGif!!.dispose()
    }

}