package com.example.mfahrezy_project_24tic

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mfahrezy_project_24tic.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {

    // [PERTEMUAN 4] ViewBinding: Deklarasi binding untuk WebViewActivity
    private lateinit var binding: ActivityWebviewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // [PERTEMUAN 4] ViewBinding: Inisialisasi ViewBinding untuk WebViewActivity
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // [PERTEMUAN 6] Toolbar: Listener tombol panah kembali pada Toolbar WebView
        binding.toolbarWeb.setNavigationOnClickListener {
            finish()
        }

        // CATATAN: Ini adalah WebView untuk menampilkan artikel kesehatan eksternal dari WHO
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://www.who.int/health-topics/obesity")
        }

        // Tangani navigasi tombol back di WebView
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}
