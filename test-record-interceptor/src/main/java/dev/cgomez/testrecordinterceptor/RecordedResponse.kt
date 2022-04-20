package dev.cgomez.testrecordinterceptor

data class RecordedResponse(
  val code: Int,
  val protocol: String,
  val message: String,
  val headers: List<Header>,
  val body: String?,
)
