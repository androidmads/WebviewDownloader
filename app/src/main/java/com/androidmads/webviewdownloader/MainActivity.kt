package com.androidmads.webviewdownloader

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.loadUrl("http://cbseacademic.in/SQP_CLASSXII_2016_17.html")
        webview.webViewClient = MyClient()

        webview.setDownloadListener({ url, userAgent, contentDisposition, mimeType, contentLength ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimeType)
            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Downloading file...")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this@MainActivity, Environment.DIRECTORY_DOWNLOADS, ".png")
            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_LONG).show()
        })

    }

    class MyClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, Url: String): Boolean {
            view.loadUrl(Url)
            return true

        }
    }

    override fun onBackPressed() {
        if (webview.canGoBack())
            webview.goBack()
        else
            super.onBackPressed()

    }
}
