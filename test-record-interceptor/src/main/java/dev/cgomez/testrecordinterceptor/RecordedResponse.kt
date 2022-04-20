package dev.cgomez.testrecordinterceptor

data class RecordedResponse(
  val code: Int,
  val protocol: String,
  val message: String,
  val headers: List<Header>,
  val body: ByteArray?,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RecordedResponse

    if (code != other.code) return false
    if (protocol != other.protocol) return false
    if (message != other.message) return false
    if (headers != other.headers) return false
    if (body != null) {
      if (other.body == null) return false
      if (!body.contentEquals(other.body)) return false
    } else if (other.body != null) return false

    return true
  }

  override fun hashCode(): Int {
    var result = code
    result = 31 * result + protocol.hashCode()
    result = 31 * result + message.hashCode()
    result = 31 * result + headers.hashCode()
    result = 31 * result + (body?.contentHashCode() ?: 0)
    return result
  }
}
