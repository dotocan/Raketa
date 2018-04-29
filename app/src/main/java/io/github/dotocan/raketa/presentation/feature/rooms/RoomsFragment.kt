package io.github.dotocan.raketa.presentation.feature.rooms

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dotocan.raketa.R
import io.github.dotocan.raketa.domain.model.websocket.response.Room
import kotlinx.android.synthetic.main.fragment_rooms.*

/**
 * Created by dotocan on 3.3.2018..
 */

class RoomsFragment : Fragment(), RoomClickListener {

    private lateinit var viewModel: RoomsViewModel
    private lateinit var navigationListener: DrawerNavigationListener
    var adapter = RoomsAdapter(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RoomsViewModel::class.java)

        viewModel.rooms.observe(this, Observer {
            if (it != null) {
                updateUi(it)
            }
        })

        if (activity is DrawerNavigationListener)
            navigationListener = activity as DrawerNavigationListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = layoutInflater.inflate(R.layout.fragment_rooms, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RoomsAdapter(this)
        online_status.setOnlineStatus("online")
        rv_rooms.layoutManager = LinearLayoutManager(requireContext())
        rv_rooms.adapter = adapter

        tv_logout.setOnClickListener { navigationListener.onLogoutClicked() }
    }

    private fun updateUi(rooms: List<Room>) {
        adapter.submitList(rooms)
    }

    override fun onRoomClicked(room: Room) {
        // Pass the selected room to the activity (for toolbar updates)
        navigationListener.onRoomSelected(room)
    }

    interface DrawerNavigationListener {
        fun onRoomSelected(room: Room)
        fun onLogoutClicked()
    }

}