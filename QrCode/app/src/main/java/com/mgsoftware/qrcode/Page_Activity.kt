package com.mgsoftware.qrcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient


class Page_Activity : AppCompatActivity() {
    var URL = "https://donboscolatola.edu.ec/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page)

        URL = intent.getStringExtra(EXTRA_MESSAGE).toString()

        var page = findViewById<WebView>(R.id.webView)
        page.webChromeClient = object: WebChromeClient(){

        }
        page.webViewClient = object: WebViewClient(){

        }
        val opciones : WebSettings = page.settings
        opciones.javaScriptEnabled = true

        page.loadUrl(URL)
    }
}