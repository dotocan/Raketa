package io.github.dotocan.raketa.presentation.feature.auth

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidx.core.content.edit
import io.github.dotocan.raketa.data.repository.AuthRepository
import io.github.dotocan.raketa.db
import io.github.dotocan.raketa.domain.model.websocket.response.ConnectResponse
import io.github.dotocan.raketa.domain.model.websocket.response.LoginResponse
import io.github.dotocan.raketa.domain.model.websocket.response.ResumeLoginResponse
import io.github.dotocan.raketa.data.service.websocket.MessageParser
import io.github.dotocan.raketa.presentation.SharedPrefKeys
import io.github.dotocan.raketa.presentation.feature.auth.AuthStatus.*
import io.github.dotocan.raketa.sharedPrefs
import io.github.dotocan.raketa.util.extensions.debug

/**
 * Created by dotocan on 17.3.2018..
 */

class AuthViewModel : ViewModel() {

    var authRepository = AuthRepository()

    var authStatus = MutableLiveData<AuthStatus>()
    var connectResponse = MediatorLiveData<ConnectResponse>()
    var loginResponses = MediatorLiveData<List<LoginResponse>>()
    var resumeLoginResponse = MediatorLiveData<ResumeLoginResponse>()

    init {
        val isServerSelected = sharedPrefs.getBoolean(
                SharedPrefKeys.isServerSelectedKey, false)

        if (isServerSelected) {
            connect(sharedPrefs.getString(SharedPrefKeys.serverUrlKey, ""))
        } else {
            setAuthStatusTo(BEGINNING)
        }

        connectResponse.addSource(MessageParser.connectResponseLiveData, {
            if (it != null) {
                setAuthStatusTo(CONNECTED)
            }
        })

        loginResponses.addSource(db.loginResponseDao().getAll(), {
            if (it != null && it.isNotEmpty()) {
                debug("Saved LoginResponse found, sending resume login...")
                resumeLogin(it.last().result.token)
            }
        })

        resumeLoginResponse.addSource(MessageParser.resumeLoginResponseLiveData, {
            if (it != null) {
                debug("Resumed login successful, showing chat...")
                setAuthStatusTo(CONNECTED_AND_LOGGED_IN)
            }
        })
    }

    private fun setAuthStatusTo(newAuthStatus: AuthStatus) {
        authStatus.value = newAuthStatus
    }

    fun connect(url: String) {
        setAuthStatusTo(CONNECTING)

        sharedPrefs.edit { putString(SharedPrefKeys.serverUrlKey, url) }
        sharedPrefs.edit { putBoolean(SharedPrefKeys.isServerSelectedKey, true) }

        authRepository.connect(url)
    }

    fun login(username: String, password: String) {
        sharedPrefs.edit { putBoolean(SharedPrefKeys.isUserLoggedInKey, true) }
        setAuthStatusTo(LOGGING_IN)
        authRepository.login(username, password)
    }

    private fun resumeLogin(authToken: String) {
        setAuthStatusTo(LOGGING_IN)
        authRepository.resumeLogin(authToken)
    }
}