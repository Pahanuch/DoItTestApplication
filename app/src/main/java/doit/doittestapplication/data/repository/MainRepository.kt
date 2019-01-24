package doit.doittestapplication.data.repository

import doit.doittestapplication.data.api.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface MainRepository{

    fun registration(username : RequestBody,
                     email : RequestBody,
                     password : RequestBody,
                     file :  MultipartBody.Part) : Single<AuthResponse>

    fun login(user : SignInRequestBody) : Single<AuthResponse>

    fun getAllPictures() : Single<PictureResponse>

    fun getGif() : Single<GifResponse>

    fun addPicture(file :  MultipartBody.Part,
                   description : RequestBody,
                   hashTag : RequestBody,
                   longitude : RequestBody,
                   latitude : RequestBody) : Single<LoadPictureResponse>
}