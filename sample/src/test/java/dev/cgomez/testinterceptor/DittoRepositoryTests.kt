package dev.cgomez.testinterceptor

import dev.cgomez.sample.DittoRepository
import dev.cgomez.testrecordinterceptor.TestFileSaverInterceptor
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.assertNotNull
import org.junit.Test

class DittoRepositoryTests {
  @Test
  fun `should perform a request that is recorded`() {
    val client =
      OkHttpClient.Builder().addInterceptor(TestFileSaverInterceptor("cached")).build()
    val repository = DittoRepository(client)
    runBlocking {
      val response = repository.call()
      assertNotNull(response)
    }
  }
}