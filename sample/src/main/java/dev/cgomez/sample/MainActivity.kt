package dev.cgomez.sample

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val client = (application as App).okHttpClient
    val viewModel = MainViewModel(DittoRepository(client))
    val textView = findViewById<TextView>(R.id.text)
    viewModel.request {
      textView.text = it
    }
  }
}