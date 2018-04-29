package io.github.dotocan.raketa.presentation.feature.chat

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import io.github.dotocan.raketa.R
import io.github.dotocan.raketa.domain.model.websocket.response.Message
import kotlinx.android.synthetic.main.fragment_chat.*

/**
 * Created by dotocan on 3.3.2018..
 */
class ChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private var adapter = ChatAdapter()
    private lateinit var layoutManager: LinearLayoutManager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(ChatViewModel::class.java)

        viewModel.messages.observe(requireActivity(), Observer {
            if (it != null && it.isNotEmpty()) {
                updateUi(it)
            }
        })

        viewModel.isLoading.observe(requireActivity(), Observer {
            if (it != null) {
                if (it)
                    showLoading()
                 else
                    hideLoading()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = layoutInflater.inflate(R.layout.fragment_chat, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true // Show latest messages on bottom
        rv_chat.layoutManager = layoutManager
        rv_chat.adapter = adapter

        img_send.setOnClickListener {
            val message = et_message.text.toString()
            et_message.setText("") // CLear
            viewModel.sendMessage(message)
        }
    }

    private fun updateUi(messages: List<Message>) {
        adapter.submitList(messages)

        Handler().postDelayed(100) {
            rv_chat.smoothScrollToPosition(messages.lastIndex)
        }
    }

    private fun showLoading() {
        tv_loading.visibility = View.VISIBLE
        progress_loading.visibility = View.VISIBLE
        rv_chat.visibility = View.INVISIBLE
        et_message.visibility = View.INVISIBLE
    }

    private fun hideLoading() {
        tv_loading.visibility = View.INVISIBLE
        progress_loading.visibility = View.INVISIBLE
        rv_chat.visibility = View.VISIBLE
        et_message.visibility = View.VISIBLE
    }
}