package com.far.goestochecklist.domain.exception

open class DomainException(message: String, title: String? = null) :
    RuntimeException(message, RuntimeException(title))

class MissingParamsException : DomainException("os parâmetros não podem ser nulos.")
class EmptyParamException : DomainException("campo obrigatório.")
class InvalidCodeException : DomainException("código inválido.")