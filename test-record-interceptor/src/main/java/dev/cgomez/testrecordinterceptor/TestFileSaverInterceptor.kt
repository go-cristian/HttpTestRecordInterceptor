package dev.cgomez.testrecordinterceptor

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.File
import java.nio.charset.Charset

class TestFileSaverInterceptor(private val path: String) : Interceptor {
  private val adapter =
    Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(FileResponse::class.java)

  override fun intercept(chain: Interceptor.Chain): Response {
    val name = chain.request().fileName()
    val file = File(path, name)
    if (file.exists()) {
      val response = file.toResponse()
      if (response != null) return response
    }
    val response = chain.proceed(chain.request())
    val content = adapter.indent("  ").toJson(response.toFileResponse())

    try {
      val file = File(path, name)
      file.parentFile?.mkdirs()
      file.createNewFile()

      file.bufferedWriter().use {
        it.write(content)
      }

      val responseFromFile = file.toResponse()
      if (responseFromFile != null) {
        return responseFromFile
      }
      throw Exception("no valid response")
    } catch (e: Exception) {
      throw e
    }
  }

  private fun File.toResponse(): Response? {
    val text = bufferedReader().use { it.readText() }
    val response = adapter.fromJson(text)
    return response?.toResponse()
  }

  private fun Response.toFileResponse(): FileResponse {
    val copy = this.newBuilder().build()
    return FileResponse(
      copy.code,
      copy.protocol.toString(),
      copy.message,
      copy.headers.map { Header(it.first, it.second) },
      copy.body?.string(),
      FiledRequest(
        copy.request.url.toString(),
        copy.request.headers.map { Header(it.first, it.second) },
        copy.request.method,
        copy.request.body.string(),
      )
    )
  }

  private fun RequestBody?.string(): String {
    val buffer = Buffer()
    this?.writeTo(buffer)
    val charset = this?.contentType()?.charset() ?: Charset.defaultCharset()
    return buffer.readString(charset)
  }

  private fun Request.fileName(): String {
    val headers = headers.joinToString("-")
    val body = body.string()
    val sha = HashUtils.sha256(body + headers)
    val path = url.encodedPathSegments.joinToString("-")
    return "$method-$path-$sha"
  }
}
