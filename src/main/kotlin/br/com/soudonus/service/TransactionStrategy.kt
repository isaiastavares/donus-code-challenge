package br.com.soudonus.service

import br.com.soudonus.converter.TransactionConverter
import br.com.soudonus.model.domain.Transaction
import br.com.soudonus.model.dto.transaction.TransactionCreateDTO
import br.com.soudonus.model.dto.transaction.TransactionDTO
import br.com.soudonus.model.enum.TransactionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

abstract class TransactionStrategy {

    abstract fun getType(): TransactionType

    abstract fun getTransactionRepository(): JpaRepository<Transaction, UUID>

    abstract suspend fun updateBalance(transactionCreateDTO: TransactionCreateDTO): Transaction

    @Transactional
    open suspend fun execute(transactionCreateDTO: TransactionCreateDTO): TransactionDTO {
        val transaction = updateBalance(transactionCreateDTO)
        val transactionModel = getTransactionRepository().save(transaction)
        return TransactionConverter.fromModelToDto(transactionModel)
    }

}