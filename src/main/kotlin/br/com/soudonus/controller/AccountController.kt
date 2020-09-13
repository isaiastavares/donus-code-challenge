package br.com.soudonus.controller

import br.com.soudonus.model.dto.account.AccountCreateDTO
import br.com.soudonus.service.AccountService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid

@RestController
@Tag(name = "Account", description = "APIs responsible for the account")
@RequestMapping("v1/accounts")
class AccountController(
        private val accountService: AccountService
) {

    @Operation(description = "Find account by id")
    @GetMapping("/{accountId}")
    suspend fun findById(@PathVariable accountId: UUID) = accountService.findById(accountId)

    @Operation(description = "Create account")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun create(@RequestBody @Valid accountDTO: AccountCreateDTO) = accountService.create(accountDTO)

}