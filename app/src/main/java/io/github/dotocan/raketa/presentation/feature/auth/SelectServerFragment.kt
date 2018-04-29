package io.github.dotocan.raketa.presentation.feature.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dotocan.raketa.R
import kotlinx.android.synthetic.main.fragment_select_server.*

/**
 * Created by dotocan on 14.3.2018..
 */

class SelectServerFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(AuthViewModel::class.java)

        viewModel.authStatus.observe(requireActivity(), Observer {
            if (it == AuthStatus.CONNECTING)
                showProgress()
            else
                hideProgress()
        })

        viewModel.connectResponse.observe(requireActivity(), Observer {})
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_select_server, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_connect.setOnClickListener {
            viewModel.connect(et_server_url.text.toString())
        }
    }

    private fun showProgress() {
        progress_connecting?.visibility = View.VISIBLE
        tv_connecting?.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress_connecting?.visibility = View.INVISIBLE
        tv_connecting?.visibility = View.INVISIBLE
    }
}