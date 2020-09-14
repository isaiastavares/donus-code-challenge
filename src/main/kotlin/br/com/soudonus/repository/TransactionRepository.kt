package br.com.soudonus.repository

import br.com.soudonus.model.domain.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID> {

    fun findByAccountId(accountId: UUID, page: Pageable): Page<Transaction>

}