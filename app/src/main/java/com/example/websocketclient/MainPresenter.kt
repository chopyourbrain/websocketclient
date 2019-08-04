package com.example.websocketclient

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import okhttp3.*


@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    private var ws: WebSocket? = null

    fun connect() {
        ws?.close(1000, "1")
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://192.168.1.3:8080/example/macaddress").build()
        val webSocketListener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.i("Main", "onOpen")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.i("Main", "onClosed")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.i("Main", "onMessage")
                val handler = object : Handler(Looper.getMainLooper()) {
                    override fun handleMessage(msg: Message) {
                        when (text) {
                            "connected" -> {
                                viewState.connected()
                            }
                            "true" -> viewState.deviceConnected()
                            "false" -> viewState.deviceDisconnected()
                        }
                    }
                }
                handler.sendEmptyMessage(1)

            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.i("Main", "onFailure")
                val handler = object : Handler(Looper.getMainLooper()) {
                    override fun handleMessage(msg: Message) {
                        viewState.disconnected()
                    }
                }
                handler.sendEmptyMessage(1)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.i("Main", "onClosing")
                val handler = object : Handler(Looper.getMainLooper()) {
                    override fun handleMessage(msg: Message) {
                        viewState.disconnected()
                    }
                }
                handler.sendEmptyMessage(1)
            }
        }
        ws = client.newWebSocket(request, webSocketListener)
        client.dispatcher().executorService().shutdown()
    }

    fun sendAddress(txt: String) {
        ws?.send(txt)
    }
}