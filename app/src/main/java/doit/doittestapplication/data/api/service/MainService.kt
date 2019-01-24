package doit.doittestapplication.data.api.service

import doit.doittestapplication.data.api.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface MainService {

    @Multipart
    @POST("create")
    fun registration(@Part("username") username : RequestBody,
                     @Part("email") email : RequestBody,
                     @Part("password") password : RequestBody,
                     @Part file :  MultipartBody.Part): Single<AuthResponse>

    @POST("login")
    fun login(@Body body : SignInRequestBody): Single<AuthResponse>

    @GET("all")
    fun getAllPictures(@Header("token") token : String): Single<PictureResponse>

    @GET("gif")
    fun getGif(@Header("token") token : String): Single<GifResponse>

    @Multipart
    @POST("image")
    fun addPicture(@Header("token") token : String,
                   @Part file :  MultipartBody.Part,
                   @Part("description") description : RequestBody,
                   @Part("hashtag") hashTag : RequestBody,
                   @Part("longitude") longitude : RequestBody,
                   @Part("latitude") latitude : RequestBody): Single<LoadPictureResponse>
}