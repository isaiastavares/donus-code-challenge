package br.com.soudonus.service.impl

import br.com.soudonus.converter.TransactionConverter
import br.com.soudonus.model.domain.Transaction
import br.com.soudonus.model.dto.transaction.TransactionCreateDTO
import br.com.soudonus.model.dto.withdraw.WithdrawCreateDTO
import br.com.soudonus.model.enum.TransactionType
import br.com.soudonus.repository.TransactionRepository
import br.com.soudonus.service.AccountService
import br.com.soudonus.service.TransactionStrategy
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID

@Service
class WithdrawStrategyImpl(
        private val repository: TransactionRepository,
        private val accountService: AccountService
) : TransactionStrategy() {

    @Value("\${withdraw.percentage}")
    private lateinit var withdrawPercentage: String

    override fun getType(): TransactionType {
        return TransactionType.WITHDRAW
    }

    override fun getTransactionRepository(): JpaRepository<Transaction, UUID> {
        return repository
    }

    override suspend fun updateBalance(transactionCreateDTO: TransactionCreateDTO): Transaction {
        val withdrawCreateDTO = transactionCreateDTO as WithdrawCreateDTO
        val amount = calculateAmount(withdrawCreateDTO.amount)
        val (balanceFrom, balanceTo) = accountService.decreaseBalance(withdrawCreateDTO.accountId, amount)
        return TransactionConverter.fromWithdrawCreateToTransaction(withdrawCreateDTO, balanceFrom, balanceTo)
    }

    private fun calculateAmount(amount: BigDecimal): BigDecimal {
        return amount.plus(amount.multiply(BigDecimal(withdrawPercentage))).setScale(2, RoundingMode.HALF_UP)
    }

}