package dev.cgomez.sample

import android.app.Application
import okhttp3.OkHttpClient

class App : Application() {
  var okHttpClient = OkHttpClient.Builder().build()
}