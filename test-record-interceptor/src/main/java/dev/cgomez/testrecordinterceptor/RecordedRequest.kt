package dev.cgomez.testrecordinterceptor

data class RecordedRequest(
  val url: String,
  val headers: List<Header>,
  val method: String,
  val body: String?,
)
