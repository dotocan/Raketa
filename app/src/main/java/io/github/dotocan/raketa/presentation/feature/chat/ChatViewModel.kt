package io.github.dotocan.raketa.presentation.feature.chat

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.github.dotocan.raketa.data.repository.ChatRepository
import io.github.dotocan.raketa.domain.model.websocket.response.Message
import io.github.dotocan.raketa.domain.model.websocket.response.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

class ChatViewModel : ViewModel() {

    private val chatRepository = ChatRepository()
    val isLoading = MutableLiveData<Boolean>()
    val messages = MutableLiveData<List<Message>>()

    val currentRoom = MutableLiveData<Room>()

    private val compositeDisposable = CompositeDisposable()

    fun setCurrentRoom(room: Room) {
        currentRoom.value = room
        loadMessages(room.roomId)
        chatRepository.syncMessages(room.roomId)
        chatRepository.streamMessagesFor(room.roomId)
    }

    private fun loadMessages(roomId: String) {
        isLoading.value = true
        observe(roomId)
    }

    private fun observe(roomId: String) {
        // Remove old observer before adding a new one
        compositeDisposable.clear()

        isLoading.value = true

        compositeDisposable.add(chatRepository.getMessagesFor(roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ResourceSubscriber<List<Message>>() {

                    override fun onComplete() {}

                    override fun onNext(t: List<Message>) {
                        messages.value = t
                        if (t.isNotEmpty())
                            isLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        isLoading.value = false
                    }

                }))
    }

    fun sendMessage(msg: String) {
        chatRepository.sendMessage(currentRoom.value?.roomId!!, msg)
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}