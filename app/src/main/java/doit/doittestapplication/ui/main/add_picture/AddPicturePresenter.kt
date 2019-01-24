package doit.doittestapplication.ui.main.add_picture

import com.arellomobile.mvp.InjectViewState
import doit.doittestapplication.addSchedulers
import doit.doittestapplication.data.repository.MainRepositoryProvider
import doit.doittestapplication.ui.base.BasePresenter
import io.reactivex.disposables.Disposable
import okhttp3.MultipartBody
import okhttp3.RequestBody

@InjectViewState
class AddPicturePresenter : BasePresenter<AddPictureView>() {

    private var dispose : Disposable? = null

    override fun onDestroy() {
        dispose()
    }

    fun addPicture(file :  MultipartBody.Part,
                   description : RequestBody,
                   hashTag : RequestBody,
                   longitude : RequestBody,
                   latitude : RequestBody){
        dispose = MainRepositoryProvider.instance
                .addPicture(file, description, hashTag, longitude, latitude)
                .addSchedulers()
                .doOnSubscribe { viewState.showLoading() }
                .doFinally { viewState.hideLoading() }
                .subscribe({
                    viewState.successfullyLoaded()
                }, this::handleError)

    }

    private fun dispose(){
        if (dispose != null) dispose!!.dispose()
    }

}