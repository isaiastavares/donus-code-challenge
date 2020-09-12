package br.com.soudonus.controller

import br.com.soudonus.model.dto.deposit.DepositCreateDTO
import br.com.soudonus.model.dto.transaction.TransactionDTO
import br.com.soudonus.model.dto.transfer.TransferCreateDTO
import br.com.soudonus.model.dto.withdraw.WithdrawCreateDTO
import br.com.soudonus.model.enum.TransactionType
import br.com.soudonus.service.TransactionStrategyFactory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@Tag(name = "Transactions", description = "APIs responsible for transactions")
@RequestMapping("v1/transactions")
class TransactionController(
        private val transactionStrategyFactory: TransactionStrategyFactory
) {

    @Operation(description = "Do deposit")
    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun deposit(@RequestBody @Valid depositCreateDTO: DepositCreateDTO): TransactionDTO {
        return transactionStrategyFactory.getStrategyForType(TransactionType.DEPOSIT).execute(depositCreateDTO)
    }

    @Operation(description = "To withdraw")
    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun withdraw(@RequestBody @Valid withdrawCreateDTO: WithdrawCreateDTO): TransactionDTO {
        return transactionStrategyFactory.getStrategyForType(TransactionType.WITHDRAW).execute(withdrawCreateDTO)
    }

    @Operation(description = "Do transfer")
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun transfer(@RequestBody @Valid transferCreateDTO: TransferCreateDTO): TransactionDTO {
        return transactionStrategyFactory.getStrategyForType(TransactionType.TRANSFER).execute(transferCreateDTO)
    }

}