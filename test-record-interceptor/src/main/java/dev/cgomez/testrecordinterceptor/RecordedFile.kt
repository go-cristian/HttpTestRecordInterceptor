package dev.cgomez.testrecordinterceptor

import okhttp3.Protocol
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

data class RecordedFile(
  val recordedResponse: RecordedResponse,
  val recordedRequest: RecordedRequest
) {
  fun toResponse(): Response {
    val builder = Response.Builder()
    recordedResponse.headers.forEach {
      builder.addHeader(it.name, it.value)
    }
    builder.code(recordedResponse.code)
    builder.protocol(Protocol.get(recordedResponse.protocol))
    builder.message(recordedResponse.message)
    val requestBuilder = Request.Builder()
    recordedRequest.headers.forEach {
      requestBuilder.addHeader(it.name, it.value)
    }
    if (recordedRequest.method != "GET") {
      requestBuilder.method(recordedRequest.method, recordedRequest.body?.toRequestBody())
    }
    if (recordedResponse.body != null) {
      builder.body(recordedResponse.body.toResponseBody())
    }
    requestBuilder.url(recordedRequest.url)
    builder.request(requestBuilder.build())
    return builder.build()
  }
}
