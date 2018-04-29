package io.github.dotocan.raketa.presentation.feature.rooms

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dotocan.raketa.R
import io.github.dotocan.raketa.domain.model.websocket.response.Room
import kotlinx.android.synthetic.main.item_chat_room.view.*

/**
 * Created by dotocan on 4.3.2018..
 */
class RoomsAdapter(private val clickListener: RoomClickListener)
    : ListAdapter<Room, RoomsAdapter.RoomsViewHolder>(RoomDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_room, parent, false)
        return RoomsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RoomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(room: Room) {
            itemView.tv_room_name.text = room.name
            itemView.setOnClickListener { clickListener.onRoomClicked(room) }
        }
    }
}

class RoomDiffCallback : DiffUtil.ItemCallback<Room>() {
    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem.roomId == newItem.roomId
    }

    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem == newItem
    }

}

