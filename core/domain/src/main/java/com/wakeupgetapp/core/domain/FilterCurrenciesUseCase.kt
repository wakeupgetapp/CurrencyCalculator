package com.wakeupgetapp.core.domain

import com.wakeupgetapp.core.model.Currency
import javax.inject.Inject

class FilterCurrenciesUseCase @Inject constructor() : (List<Currency>, String) -> List<Currency> {
    override fun invoke(currencyList: List<Currency>, filter: String): List<Currency> =
        currencyList.mapNotNull {
            it.takeIf {
                it.name.contains(filter, ignoreCase = true) || it.code.contains(
                    filter, ignoreCase = true
                )
            }
        }
}
