package com.example.websocketclient

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter


class MainActivity : MvpAppCompatActivity(), MainView {



    @BindView(R.id.editText)
    lateinit var editText: EditText

    @BindView(R.id.button)
    lateinit var button: Button

    @BindView(R.id.buttonReconnect)
    lateinit var buttonReconnect: Button

    @BindView(R.id.textview)
    lateinit var textView: TextView

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        buttonReconnect.setOnClickListener {
            mainPresenter.connect()
        }
        button.setOnClickListener { mainPresenter.sendAddress(editText.text.toString()) }
    }

    override fun connected() {
        val txt = "Connected"
        textView.text = txt
        textView.setTextColor(Color.GREEN)
    }

    override fun disconnected() {
        val txt = "Disconnected"
        textView.text = txt
        textView.setTextColor(Color.RED)    }

    override fun deviceConnected() {
        Toast.makeText(applicationContext, "Device connected", Toast.LENGTH_SHORT).show()
    }

    override fun deviceDisconnected() {
        Toast.makeText(applicationContext, "Device disconnected", Toast.LENGTH_SHORT).show()
    }
}
