package doit.doittestapplication.data.api.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(@SerializedName("creation_time") val creation_time: String,
                        @SerializedName("token") val token: String,
                        @SerializedName("avatar") val avatar: String)