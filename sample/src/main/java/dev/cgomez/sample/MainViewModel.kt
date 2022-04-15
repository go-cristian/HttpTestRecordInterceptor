package dev.cgomez.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(
  val repository: DittoRepository
) : ViewModel() {
  fun request(callback: (String) -> Unit) {
    viewModelScope.launch {
      val response = repository.call()
      if (response != null) {
        callback(response)
      }
    }
  }
}