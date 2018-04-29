package io.github.dotocan.raketa.data.service.retrofit

import io.github.dotocan.raketa.domain.model.rest.OAuthServices
import io.github.dotocan.raketa.domain.model.websocket.response.LoginResponse
//import io.github.dotocan.raketa.domain.model.rest.ServerInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @GET("settings.oauth")
    fun getOAuthServices(): Call<OAuthServices>
}