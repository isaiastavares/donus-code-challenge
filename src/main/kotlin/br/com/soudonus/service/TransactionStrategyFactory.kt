package br.com.soudonus.service

import br.com.soudonus.model.enum.TransactionType
import org.apache.commons.lang3.NotImplementedException
import org.springframework.stereotype.Component

@Component
class TransactionStrategyFactory(
        strategies: List<TransactionStrategy>
) {

    private val strategyMap: Map<TransactionType, TransactionStrategy> = strategies.associateBy { it.getType() }

    fun getStrategyForType(type: TransactionType): TransactionStrategy {
        return strategyMap[type] ?: throw NotImplementedException()
    }

}
