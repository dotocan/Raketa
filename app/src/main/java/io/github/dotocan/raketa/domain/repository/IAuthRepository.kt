package io.github.dotocan.raketa.domain.repository

interface IAuthRepository {
    fun connect(url: String)
    fun login(username: String, password: String)
    fun resumeLogin(authToken: String)
}