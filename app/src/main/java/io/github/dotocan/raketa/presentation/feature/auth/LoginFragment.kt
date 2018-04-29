package io.github.dotocan.raketa.presentation.feature.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dotocan.raketa.R
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by dotocan on 14.3.2018..
 */

class LoginFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(AuthViewModel::class.java)

        viewModel.authStatus.observe(requireActivity(), Observer {
            if (it == AuthStatus.LOGGING_IN)
                showProgress()
            else
                hideProgress()
        })

        viewModel.loginResponses.observe(requireActivity(), Observer {})
        viewModel.resumeLoginResponse.observe(requireActivity(), Observer {})
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener { viewModel.login(
                et_username.text.toString(), et_password.text.toString()
        ) }
    }

    private fun showProgress() {
        progress_logging_in?.visibility = View.VISIBLE
        tv_logging_in?.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress_logging_in?.visibility = View.INVISIBLE
        tv_logging_in?.visibility = View.INVISIBLE
    }

}