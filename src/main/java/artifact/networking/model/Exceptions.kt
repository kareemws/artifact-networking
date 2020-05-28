package artifact.networking.model

sealed class ResponseException : Exception()

class FailureException : ResponseException()