package br.com.soudonus.service.impl

import br.com.soudonus.exception.ConflictException
import br.com.soudonus.exception.EntityNotFoundException
import br.com.soudonus.exception.InsufficientBalanceException
import br.com.soudonus.model.domain.Account
import br.com.soudonus.model.dto.account.AccountCreateDTO
import br.com.soudonus.repository.AccountRepository
import br.com.soudonus.resource.AccountResource
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal
import java.util.UUID

@ExtendWith(MockKExtension::class)
class AccountServiceImplTest {

    @InjectMockKs
    private lateinit var accountService: AccountServiceImpl

    @MockK
    private lateinit var accountRepository: AccountRepository

    @Test
    fun `should find all accounts by id paginated`() = runBlocking {
        val account = AccountResource.getAccount(UUID.randomUUID())

        coEvery { accountRepository.findAll(any<Pageable>()) } returns PageImpl(listOf(account))

        val page = accountService.findAll(PageRequest.of(0, 10))
        val accountDTO = page.get().findFirst().get()

        assertEquals(account.id, accountDTO.id)
        assertEquals(account.name, accountDTO.name)
        assertEquals(account.document, accountDTO.cpf)
        assertEquals(account.balance, accountDTO.balance)
        assertEquals(account.createdAt, accountDTO.createdAt)
        assertEquals(account.updatedAt, accountDTO.updatedAt)
    }

    @Test
    fun `should find account by id`() = runBlocking {
        val account = AccountResource.getAccount(UUID.randomUUID())

        coEvery { accountRepository.findByIdOrNull(any()) } returns account

        val dto = accountService.findById(UUID.randomUUID())

        assertEquals(account.id, dto.id)
        assertEquals(account.name, dto.name)
        assertEquals(account.document, dto.cpf)
        assertEquals(account.balance, dto.balance)
        assertEquals(account.createdAt, dto.createdAt)
        assertEquals(account.updatedAt, dto.updatedAt)
    }

    @Test
    fun `should throw exception when find account by id that no exists`() = runBlocking {
        coEvery { accountRepository.findByIdOrNull(any()) } returns null

        val accountId = UUID.randomUUID()
        val exception = async { assertThrows<EntityNotFoundException> { runBlocking { accountService.findById(accountId) } } }.await()
        assertEquals("Account $accountId not found", exception.message)
    }

    @Test
    fun `should create account`() = runBlocking {
        val account = AccountResource.getAccount(UUID.randomUUID())

        coEvery { accountRepository.findByDocument(any()) } returns null
        coEvery { accountRepository.save(any<Account>()) } returns account

        val accountCreateDTO = AccountCreateDTO(
                name = account.name,
                cpf = account.document)

        val accountDTO = accountService.create(accountCreateDTO)

        assertNotNull(accountDTO.id)
        assertEquals(accountCreateDTO.name, accountDTO.name)
        assertEquals(accountCreateDTO.cpf, accountDTO.cpf)
        assertEquals(BigDecimal.ZERO, accountDTO.balance)
        assertNotNull(accountDTO.createdAt)
        assertNotNull(accountDTO.updatedAt)
    }

    @Test
    fun `should throw exception when create account that already exists`() = runBlocking {
        val account = AccountResource.getAccount(UUID.randomUUID())

        coEvery { accountRepository.findByDocument(any()) } returns account

        val accountCreateDTO = AccountCreateDTO(
                name = account.name,
                cpf = account.document)

        val exception = async { assertThrows<ConflictException> { runBlocking { accountService.create(accountCreateDTO) } } }.await()
        assertEquals("Account already exists with this CPF", exception.message)
    }

    @Test
    fun `should decrease the balance`() = runBlocking {
        val account = AccountResource.getAccount(UUID.randomUUID())
        account.balance = BigDecimal.ONE

        coEvery { accountRepository.findByIdOrNull(any()) } returns account
        coEvery { accountRepository.save(any<Account>()) } returns account

        val (balanceFrom, balanceTo) = accountService.decreaseBalance(UUID.randomUUID(), BigDecimal.ONE)

        assertEquals(BigDecimal.ONE, balanceFrom)
        assertEquals(BigDecimal.ZERO, balanceTo)
    }

    @Test
    fun `should throw exception if the balance is going to be negative`() = runBlocking {
        val account = AccountResource.getAccount(UUID.randomUUID())

        coEvery { accountRepository.findByIdOrNull(any()) } returns account

        val exception = async { assertThrows<InsufficientBalanceException> { runBlocking { accountService.decreaseBalance(UUID.randomUUID(), BigDecimal.ONE) } } }.await()
        assertEquals("Insufficient balance", exception.message)
    }

    @Test
    fun `should increase the balance`() = runBlocking {
        val account = AccountResource.getAccount(UUID.randomUUID())

        coEvery { accountRepository.findByIdOrNull(any()) } returns account
        coEvery { accountRepository.save(any<Account>()) } returns account

        val (balanceFrom, balanceTo) = accountService.increaseBalance(UUID.randomUUID(), BigDecimal.ONE)

        assertEquals(BigDecimal.ZERO, balanceFrom)
        assertEquals(BigDecimal.ONE, balanceTo)
    }

}