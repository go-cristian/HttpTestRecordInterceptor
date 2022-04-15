package dev.cgomez.testrecordinterceptor

data class FiledRequest(
  val url: String,
  val headers: List<Header>,
  val method: String,
  val body: String?,
)
