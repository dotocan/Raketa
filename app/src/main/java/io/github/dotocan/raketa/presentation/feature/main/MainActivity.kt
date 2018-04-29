package io.github.dotocan.raketa.presentation.feature.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.core.content.edit
import io.github.dotocan.raketa.R
import io.github.dotocan.raketa.domain.model.websocket.response.Room
import io.github.dotocan.raketa.presentation.SharedPrefKeys
import io.github.dotocan.raketa.presentation.feature.auth.AuthActivity
import io.github.dotocan.raketa.presentation.feature.chat.ChatFragment
import io.github.dotocan.raketa.presentation.feature.chat.ChatViewModel
import io.github.dotocan.raketa.presentation.feature.rooms.RoomsFragment
import io.github.dotocan.raketa.util.extensions.findByTag
import io.github.dotocan.raketa.util.extensions.replaceFragment
import io.github.dotocan.raketa.util.extensions.setupDrawerAnimationFor
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.startActivity

/**
 * Created by dotocan on 3.3.2018..
 */
class MainActivity : AppCompatActivity(), RoomsFragment.DrawerNavigationListener {

    private val drawerFragment by lazy { findDrawerFragment() }
    private val drawerTag: String = RoomsFragment::class.java.simpleName

    private val chatFragment by lazy { findChatFragment() }
    private val chatTag: String = ChatFragment::class.java.simpleName

    private fun findDrawerFragment(): RoomsFragment =
            findByTag(drawerTag) as? RoomsFragment ?: RoomsFragment()

    private fun findChatFragment(): ChatFragment =
            findByTag(chatTag) as? ChatFragment ?: ChatFragment()

    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showChat()
    }

    private fun showChat() {
        replaceFragment(drawer_container.id, drawerFragment, drawerTag)
        replaceFragment(fragment_container.id, chatFragment, chatTag)
        setUpToolbar()
        createDrawerAnimation()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        viewModel.currentRoom.observe(this, Observer {
            updateToolbarTitle(it?.name ?: "Error getting name")
        })
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun createDrawerAnimation() {
        setupDrawerAnimationFor(drawer_layout, toolbar)
    }

    private fun updateToolbarTitle(title: String) {
        toolbar.title = title
    }

    override fun onLogoutClicked() {
        defaultSharedPreferences.edit { putBoolean(SharedPrefKeys.isUserLoggedInKey, false) }
        startActivity<AuthActivity>()
    }

    override fun onRoomSelected(room: Room) {
        drawer_layout.closeDrawer(drawer_container)
        viewModel.setCurrentRoom(room)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(drawer_container))
            drawer_layout.closeDrawer(drawer_container)
        else super.onBackPressed()
    }
}