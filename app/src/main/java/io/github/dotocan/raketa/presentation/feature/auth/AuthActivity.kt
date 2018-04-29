package io.github.dotocan.raketa.presentation.feature.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.dotocan.raketa.R
import io.github.dotocan.raketa.data.service.websocket.WebsocketHandler
import io.github.dotocan.raketa.data.service.websocket.WebsocketService
import io.github.dotocan.raketa.presentation.feature.auth.AuthStatus.*
import io.github.dotocan.raketa.presentation.feature.main.MainActivity
import io.github.dotocan.raketa.util.extensions.*
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.startActivity

class AuthActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    private val selectServerFragment by lazy { findSelectServerFragment() }
    private val selectServerTag = SelectServerFragment::class.java.simpleName

    private val loginFragment by lazy { findLoginFragment() }
    private val loginTag = LoginFragment::class.java.simpleName

    private fun findSelectServerFragment(): SelectServerFragment = findByTag(selectServerTag)
            as? SelectServerFragment ?: SelectServerFragment()

    private fun findLoginFragment(): LoginFragment = findByTag(loginTag) as? LoginFragment
            ?: LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        WebsocketHandler.websocketService = WebsocketService()

        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        viewModel.authStatus.observe(this, Observer { status ->
            when (status) {
                BEGINNING -> showServerSelection()
                CONNECTING -> showServerSelection()
                CONNECTED -> showLogin()
                LOGGING_IN -> showLogin()
                CONNECTED_AND_LOGGED_IN -> goToChat()
            }
        })
    }

    private fun showServerSelection() {
        replaceFragmentAddToBackstack(fragment_container.id, selectServerFragment, selectServerTag)
    }

    private fun showLogin() {
        replaceFragmentAddToBackstack(fragment_container.id, loginFragment, loginTag)
    }

    private fun goToChat() {
        startActivity<MainActivity>()
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out)
    }

}