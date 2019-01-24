package doit.doittestapplication.data.api

import com.google.gson.GsonBuilder
import doit.doittestapplication.BuildConfig
import doit.doittestapplication.addLogging
import doit.doittestapplication.data.api.interceptor.ErrorHandlingInterceptor
import doit.doittestapplication.data.api.service.MainService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private val serviceToUrl = mapOf<Class<*>, String>(
            MainService::class.java to BuildConfig.BASE_API_URL
    )

    private val urlToService = HashMap<String, Any>()

    private val lock = Any()

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(ErrorHandlingInterceptor())
                .addLogging()
                .build()
    }

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(
                        GsonBuilder()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                                .create()
                ))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttp)
    }

    fun <T : Any> getRetrofit(retrofitClass: Class<T>): T {
        synchronized(lock) {
            val url = serviceToUrl.get(retrofitClass)!!

            var service = urlToService.get(url) as T?
            if (service == null) {
                service = retrofitBuilder
                        .baseUrl(url)
                        .build()
                        .create(retrofitClass) as T
                urlToService.put(url, service)
            }
            return service
        }
    }

}