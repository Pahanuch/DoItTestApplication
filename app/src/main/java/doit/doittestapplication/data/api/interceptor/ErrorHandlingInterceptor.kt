package doit.doittestapplication.data.api.interceptor

import com.google.gson.Gson
import doit.doittestapplication.data.api.exception.ServerException
import doit.doittestapplication.data.api.model.ErrorResponse
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ErrorHandlingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val contentType = response.body()?.contentType()
        val responseString = response.body()?.string() ?: ""

        val errorResponse = Gson().fromJson(responseString, ErrorResponse::class.java)

        val error = errorResponse?.error

        if (!(error == null || error.isEmpty())) {
            throw ServerException(error)
        }

        return response.newBuilder().body(ResponseBody.create(contentType, responseString)).build()
    }
}