package artifact.networking.model

sealed class Response<ContentType>

sealed class Success<ContentType> : Response<ContentType>()

class Ok<ContentType>(val data: ContentType) : Success<ContentType>()

class EmptyOk<ContentType> : Success<ContentType>()

class NoContent<ContentType> : Success<ContentType>()


sealed class Failure<ContentType>(val message: String?, val statusCode: Int) :
    Response<ContentType>()

class ClientError<ContentType>(message: String?, statusCode: Int) :
    Failure<ContentType>(message, statusCode)

class ServerError<ContentType>(message: String?, statusCode: Int) :
    Failure<ContentType>(message, statusCode)

class Undefined<ContentType>(val data: ContentType?, val message: String?, val statusCode: Int) :
    Response<ContentType>()

fun <T> retrofit2.Response<T>.map(): Response<T> = when {
    body() != null && code() == 200 -> Ok(body()!!)
    code() == 200 -> EmptyOk()
    body() == null && code() == 204 -> NoContent()
    code() / 100 == 4 -> ClientError(extractErrorMessage(), code())
    code() / 100 == 5 -> ServerError(extractErrorMessage(), code())
    else -> Undefined(body(), extractErrorMessage(), code())
}

fun <T> retrofit2.Response<T>.extractErrorMessage(): String? {
    val msg = errorBody()?.string()
    return if (msg.isNullOrEmpty()) {
        message()
    } else {
        msg
    }
}
