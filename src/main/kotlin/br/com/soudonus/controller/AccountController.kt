package br.com.soudonus.controller

import br.com.soudonus.model.dto.account.AccountCreateDTO
import br.com.soudonus.model.dto.account.AccountDTO
import br.com.soudonus.model.dto.transaction.TransactionDTO
import br.com.soudonus.service.AccountService
import br.com.soudonus.service.TransactionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid

@RestController
@Tag(name = "Account", description = "APIs responsible for the account")
@RequestMapping("v1/accounts")
class AccountController(
        private val accountService: AccountService,
        private val transactionService: TransactionService
) {

    @Operation(description = "Find all")
    @GetMapping
    suspend fun findAll(@RequestParam(name = "page", defaultValue = "0") page: Int,
                        @RequestParam(name = "size", defaultValue = "10") size: Int): Page<AccountDTO> {
        return accountService.findAll(PageRequest.of(page, size, Sort.by(Sort.Order.asc("createdAt"))))
    }

    @Operation(description = "Find account by id")
    @GetMapping("/{accountId}")
    suspend fun findById(@PathVariable accountId: UUID) = accountService.findById(accountId)

    @Operation(description = "Find transactions by account id")
    @GetMapping("/{accountId}/transactions")
    suspend fun findTransactionsById(@PathVariable accountId: UUID,
                                     @RequestParam(name = "page", defaultValue = "0") page: Int,
                                     @RequestParam(name = "size", defaultValue = "10") size: Int): Page<TransactionDTO> {
        return transactionService.findTransactionsByIdAccountId(accountId, PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt"))))
    }

    @Operation(description = "Create account")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun create(@RequestBody @Valid accountDTO: AccountCreateDTO) = accountService.create(accountDTO)

}