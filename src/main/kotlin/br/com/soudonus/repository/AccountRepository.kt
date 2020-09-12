package br.com.soudonus.repository

import br.com.soudonus.model.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AccountRepository : JpaRepository<Account, UUID> {

    fun findByDocument(cpf: String): Account?

}