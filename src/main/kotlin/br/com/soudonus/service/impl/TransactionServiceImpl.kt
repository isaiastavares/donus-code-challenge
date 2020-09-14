package br.com.soudonus.service.impl

import br.com.soudonus.converter.TransactionConverter
import br.com.soudonus.model.dto.transaction.TransactionDTO
import br.com.soudonus.repository.TransactionRepository
import br.com.soudonus.service.TransactionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TransactionServiceImpl(private val repository: TransactionRepository) : TransactionService {

    override suspend fun findTransactionsByIdAccountId(accountId: UUID, page: Pageable): Page<TransactionDTO> {
        return repository.findByAccountId(accountId, page)
                .map { TransactionConverter.fromModelToDto(it) }
    }

}
