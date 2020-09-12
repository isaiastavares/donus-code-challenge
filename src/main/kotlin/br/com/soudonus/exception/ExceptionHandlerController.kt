package br.com.soudonus.exception

import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(e: ConflictException): HttpStatus {
        throw ResponseStatusException(HttpStatus.CONFLICT, e.message, e)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e: EntityNotFoundException): HttpStatus {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
    }

    @ExceptionHandler(InsufficientBalanceException::class)
    fun handleEntityNotFoundException(e: InsufficientBalanceException): HttpStatus {
        throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.message, e)
    }

    @ExceptionHandler(TypeMismatchException::class)
    fun handleTypeMismatchException(e: TypeMismatchException): HttpStatus {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid value '${e.value}'", e)
    }

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleWebExchangeBindException(e: WebExchangeBindException): HttpStatus {
        throw object : WebExchangeBindException(e.methodParameter!!, e.bindingResult) {
            override val message = "${fieldError?.field} has invalid value '${fieldError?.rejectedValue}'"
        }
    }
}