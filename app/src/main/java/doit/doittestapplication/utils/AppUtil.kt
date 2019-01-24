package doit.doittestapplication.utils

import okhttp3.MediaType
import okhttp3.RequestBody

object AppUtil {

    fun parseStringIntoRequestBody(string: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), string)
    }

}