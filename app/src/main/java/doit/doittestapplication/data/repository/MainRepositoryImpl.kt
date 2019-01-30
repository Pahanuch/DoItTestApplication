package doit.doittestapplication.data.repository

import android.net.Uri
import doit.doittestapplication.App
import doit.doittestapplication.data.api.ApiFactory
import doit.doittestapplication.data.api.model.*
import doit.doittestapplication.data.api.service.MainService
import doit.doittestapplication.data.storage.Storage
import doit.doittestapplication.utils.PathUtil
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MainRepositoryImpl : MainRepository {

    companion object {
        const val IMAGE_NAME = "avatar"
    }

    override fun registration(username : RequestBody,
                              email : RequestBody,
                              password : RequestBody,
                              imageFileUri :  Uri): Single<AuthResponse> {

        val file = File(PathUtil.getPath(App.context, imageFileUri))
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val avatarBody = MultipartBody.Part.createFormData(IMAGE_NAME, file.name, requestFile)

        return ApiFactory.getRetrofit(MainService::class.java)
                .registration(username, email, password, avatarBody)
    }

    override fun login(user: SignInRequestBody): Single<AuthResponse> {
        return ApiFactory.getRetrofit(MainService::class.java)
                .login(user)
    }

    override fun getAllPictures(): Single<PictureResponse> {
        return ApiFactory.getRetrofit(MainService::class.java)
                .getAllPictures(Storage.getToken()!!)
    }

    override fun getGif(): Single<GifResponse> {
        return ApiFactory.getRetrofit(MainService::class.java)
                .getGif(Storage.getToken()!!)
    }

    override fun addPicture(file :  MultipartBody.Part,
                            description : RequestBody,
                            hashTag : RequestBody,
                            longitude : RequestBody,
                            latitude : RequestBody): Single<LoadPictureResponse> {

        return ApiFactory.getRetrofit(MainService::class.java)
                .addPicture(Storage.getToken()!!, file, description, hashTag, longitude, latitude)
    }
}