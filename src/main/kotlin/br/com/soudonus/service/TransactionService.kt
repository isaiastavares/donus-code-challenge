package br.com.soudonus.service

import br.com.soudonus.model.dto.transaction.TransactionDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface TransactionService {

    suspend fun findTransactionsByIdAccountId(accountId: UUID, page: Pageable): Page<TransactionDTO>

}