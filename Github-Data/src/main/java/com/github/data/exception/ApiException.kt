package com.github.data.exception

class ApiException(var code: String, override val message: String) : Exception()
