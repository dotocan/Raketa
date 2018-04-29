package io.github.dotocan.raketa.presentation.feature.auth

enum class AuthStatus {
    BEGINNING,
    CONNECTING,
    CONNECTED,
    LOGGING_IN,
    CONNECTED_AND_LOGGED_IN
}