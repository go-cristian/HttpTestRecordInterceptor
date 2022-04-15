package dev.cgomez.testrecordinterceptor

import okhttp3.Protocol
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

data class FileResponse(
  val code: Int,
  val protocol: String,
  val message: String,
  val headers: List<Header>,
  val response: String?,
  val request: FiledRequest,
) {
  fun toResponse(): Response {
    val builder = Response.Builder()
    headers.forEach {
      builder.addHeader(it.name, it.value)
    }
    builder.code(code)
    builder.protocol(Protocol.get(protocol))
    builder.message(message)
    val requestBuilder = Request.Builder()
    request.headers.forEach {
      requestBuilder.addHeader(it.name, it.value)
    }
    if (request.method != "GET") {
      requestBuilder.method(request.method, request.body?.toRequestBody())
    }
    if (response != null) {
      builder.body(response.toResponseBody())
    }
    requestBuilder.url(request.url)
    builder.request(requestBuilder.build())
    return builder.build()
  }
}
