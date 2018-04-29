package io.github.dotocan.raketa.presentation.feature.chat

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import io.github.dotocan.raketa.R
import io.github.dotocan.raketa.domain.model.websocket.response.Message
import kotlinx.android.synthetic.main.item_chat_message.view.*

class ChatAdapter : ListAdapter<Message, ChatAdapter.ChatViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val rootView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_message, parent, false)

        return ChatViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val currentMessage = getItem(position)
        var previousMessage: Message? = null

        if (position > 0)
            previousMessage = getItem(position - 1)

        holder.bind(previousMessage, currentMessage)
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(previousMessage: Message?, currentMessage: Message) {

            // If the same user sends multiple messages, show his username and avatar only once
            if (previousMessage != null && currentMessage.messageOwner.ownerUsername == previousMessage.messageOwner.ownerUsername) {
                itemView.tv_username.visibility = View.GONE
                itemView.img_user.visibility = View.INVISIBLE
            }

            if(previousMessage != null && currentMessage.messageOwner.ownerUsername != previousMessage.messageOwner.ownerUsername) {
                itemView.tv_username.visibility = View.VISIBLE
                itemView.img_user.visibility = View.VISIBLE
            }

            itemView.tv_username.text = currentMessage.messageOwner.ownerUsername
            itemView.tv_message.text = currentMessage.msg
            Glide.with(itemView.context)
                    .load("https://open.rocket.chat/avatar/${currentMessage.messageOwner.ownerUsername}")
                    .apply(RequestOptions().transform(RoundedCorners(10)))
                    .thumbnail(Glide.with(itemView.context).load(R.drawable.ic_account_black_24dp))
                    .into(itemView.img_user)
            itemView.img_user
        }
    }
}

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {

    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.messageId == newItem.messageId
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }

}