package dev.cgomez.sample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class DittoRepository(private val okHttpClient: OkHttpClient) {
  suspend fun call(): String? {
    return withContext(Dispatchers.IO) {
      val request = Request.Builder().url("https://pokeapi.co/api/v2/pokemon/ditto").get().build()
      val response = okHttpClient.newCall(request).execute()
      response.body?.string()
    }
  }
}
