package doit.doittestapplication.data.repository

import doit.doittestapplication.data.api.ApiFactory
import doit.doittestapplication.data.api.model.*
import doit.doittestapplication.data.api.service.MainService
import doit.doittestapplication.data.storage.Storage
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainRepositoryImpl : MainRepository {

    override fun registration(username : RequestBody,
                              email : RequestBody,
                              password : RequestBody,
                              file :  MultipartBody.Part): Single<AuthResponse> {

        return ApiFactory.getRetrofit(MainService::class.java)
                .registration(username, email, password, file)
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