package com.far.goestochecklist.domain.exception

open class DomainException(message: String, title: String? = null) :
    RuntimeException(message, RuntimeException(title))

class MissingParamsException : DomainException("Os parâmetros não podem ser nulos.")
class EmptyParamException : DomainException("Campo obrigatório.")
class InvalidUsernameException : DomainException("Usuário inválido.")