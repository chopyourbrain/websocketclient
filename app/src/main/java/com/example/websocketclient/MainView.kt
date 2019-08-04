package com.example.websocketclient

import com.arellomobile.mvp.MvpView

interface MainView : MvpView {
    fun connected()
    fun disconnected()
    fun deviceConnected()
    fun deviceDisconnected()
}