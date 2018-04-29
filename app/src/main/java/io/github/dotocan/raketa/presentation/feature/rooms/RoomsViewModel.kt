package io.github.dotocan.raketa.presentation.feature.rooms

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.dotocan.raketa.data.repository.RoomsRepository
import io.github.dotocan.raketa.domain.model.websocket.response.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

/**
 * Created by dotocan on 4.3.2018..
 */
class RoomsViewModel(application: Application) : AndroidViewModel(application) {

    private val roomsRepository = RoomsRepository()
    val rooms = MutableLiveData<List<Room>>()
    private var isLoading = MutableLiveData<Boolean>()

    private val compositeDisposable = CompositeDisposable()

    init {
        observe()
        roomsRepository.getRooms()
        roomsRepository.syncRooms()
    }

    private fun observe() {
        isLoading.value = true

        compositeDisposable.add(roomsRepository.getRooms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: ResourceSubscriber<List<Room>>() {
                    override fun onComplete() {
                        isLoading.value = false
                    }

                    override fun onNext(t: List<Room>?) {
                        isLoading.value = false
                        rooms.value = t
                    }

                    override fun onError(t: Throwable?) {
                        isLoading.value = false
                    }
                }))
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}