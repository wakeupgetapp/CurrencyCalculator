package com.wakeupgetapp.feature.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wakeupgetapp.core.data.repository.CurrencyRepository
import com.wakeupgetapp.core.data.repository.UserCurrencyCache
import com.wakeupgetapp.core.domain.CalculateTargetAmountUseCase
import com.wakeupgetapp.core.domain.CurrencyUpdateUseCase
import com.wakeupgetapp.core.domain.FilterCurrenciesUseCase
import com.wakeupgetapp.core.domain.NormalizeRateUseCase
import com.wakeupgetapp.core.domain.UpdateAmountUseCase
import com.wakeupgetapp.core.domain.util.toBasicString
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CustomExchange
import com.wakeupgetapp.core.model.CurrencyUpdateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyCalculatorViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val userCurrencyCache: UserCurrencyCache,
    private val currencyUpdateUseCase: CurrencyUpdateUseCase,
    private val normalizeRate: NormalizeRateUseCase,
    private val calculateTargetAmount: CalculateTargetAmountUseCase,
    private val filterCurrencies: FilterCurrenciesUseCase,
    private val updateAmountUseCase: UpdateAmountUseCase
) : ViewModel() {

    private val currencyFilter = MutableStateFlow("")
    fun setFilter(value: String) {
        currencyFilter.value = value
    }

    private val amount = MutableStateFlow("")

    private val currenciesSwitched = MutableStateFlow(false)

    fun switchCurrencies() {
        currenciesSwitched.value = !currenciesSwitched.value
    }

    private val currencySelectorTarget = MutableStateFlow(CurrencySelectorTarget.NONE)
    fun setCurrencyTarget(value: CurrencySelectorTarget) {
        currencySelectorTarget.value = value
    }

    private val animateEmptyCurrencyContainer = MutableStateFlow(false)

    private fun launchCurrencyContainerAnimation() {
        if (animateEmptyCurrencyContainer.value) return
        viewModelScope.launch {
            animateEmptyCurrencyContainer.value = true
            delay(1000)
            animateEmptyCurrencyContainer.value = false
        }
    }

    fun setCurrency(value: String) {
        viewModelScope.launch {
            var base = CurrencySelectorTarget.BASE
            var exchange = CurrencySelectorTarget.TARGET

            if (currenciesSwitched.value)
                base = exchange.also { exchange = base }

            when (currencySelectorTarget.value) {
                base -> userCurrencyCache.saveBaseCurrency(value)
                exchange -> userCurrencyCache.saveTargetCurrency(value)
                else -> {} /* no-op */

            }

            userCurrencyCache.setCustomExchangeId(value = -1L)
            setCurrencyTarget(CurrencySelectorTarget.NONE)
            repository.updateCurrencyTimestamp(value, System.currentTimeMillis())
        }
    }

    fun setCustomExchange(id: Long) = viewModelScope.launch {
        userCurrencyCache.setCustomExchangeId(id)
        setCurrencyTarget(CurrencySelectorTarget.NONE)
    }

    fun addCurrencyExchange(base: String, target: String, rate: Double) = viewModelScope.launch {
        repository.saveCustomExchange(
            CustomExchange(
                id = 0,
                timestamp = System.currentTimeMillis(),
                base = base,
                target = target,
                rate = rate
            )
        )
    }

    fun editCurrencyExchange(base: String, target: String, rate: Double, id: Long) =
        viewModelScope.launch {
            repository.editCurrencyExchange(
                CustomExchange(
                    id = id,
                    timestamp = System.currentTimeMillis(),
                    base = base,
                    target = target,
                    rate = rate
                )
            )
        }

    fun deleteCurrencyExchange(id: Long) = viewModelScope.launch {
        repository.deleteCurrencyExchange(id = id)
    }

    fun handleKeyboardInput(input: String, longClick: Boolean) {
        if (selectedCurrenciesData.value is SelectedCurrenciesData.IncompleteLoaded) {
            launchCurrencyContainerAnimation()
            return
        }
        amount.value = updateAmountUseCase(input, amount.value, longClick)
    }

    private val currencyUpdateState =
        MutableStateFlow<CurrencyUpdateState>(CurrencyUpdateState.Loading)

    private val dataState: StateFlow<DataState> = combine(
        repository.getCurrencies(), repository.getCustomExchanges()
    ) { currencyList, customExchanges ->
        if (currencyList.isNotEmpty()) {
            DataState.Loaded(
                currencyList = currencyList,
                customExchangeList = customExchanges.sortedByDescending { it.timestamp }
            )
        } else DataState.Empty
    }.stateIn(
        scope = viewModelScope, initialValue = DataState.Loading, started = SharingStarted.Lazily
    )

    private val bottomSheetData: SharedFlow<BottomSheetData> =
        combine(dataState, currencyFilter) { data, filter ->
            if (data !is DataState.Loaded) BottomSheetData()
            else BottomSheetData(
                filteredList = filterCurrencies(data.currencyList, filter),
                recentList = data.currencyList.mapNotNull { it.takeIf { it.timestamp != 0L } }
                    .sortedByDescending { it.timestamp },
                goToRecent = calculateGoToRecent(currencyList = data.currencyList)
            )

        }.shareIn(scope = viewModelScope, started = SharingStarted.Lazily)


    private val selectedCurrenciesData: StateFlow<SelectedCurrenciesData> = combine(
        userCurrencyCache.customExchangeId,
        userCurrencyCache.readBaseCurrency,
        userCurrencyCache.readTargetCurrency,
        dataState
    ) { exchangeId, baseCode, exchangeCode, data ->
        if (data !is DataState.Loaded)
            return@combine SelectedCurrenciesData.IncompleteLoaded(base = null, target = null)

        if (exchangeId != -1L) data.customExchangeList.firstOrNull { it.id == exchangeId }?.also {
            return@combine SelectedCurrenciesData.LoadedCustomExchange(it)
        }

        val base = data.currencyList.firstOrNull { it.code == baseCode }
        val target = data.currencyList.firstOrNull { it.code == exchangeCode }

        if (base != null && target != null) {
            return@combine SelectedCurrenciesData.LoadedCurrency(base = base, target = target)
        }

        SelectedCurrenciesData.IncompleteLoaded(base = base, target = target)

    }.stateIn(
        scope = viewModelScope,
        initialValue = SelectedCurrenciesData.IncompleteLoaded(null, null),
        started = SharingStarted.Lazily
    )

    private val selectedCurrencies: SharedFlow<SelectedCurrencies> = combine(
        selectedCurrenciesData, currenciesSwitched, amount, animateEmptyCurrencyContainer
    ) { data, switched, amount, animateContainer ->
        when (data) {
            is SelectedCurrenciesData.LoadedCurrency -> {
                var base = data.base
                var target = data.target
                if (switched) base = target.also { target = base }
                val rate = normalizeRate(base.rate, target.rate)

                SelectedCurrencies(
                    baseCode = base.code,
                    targetCode = target.code,
                    baseAmount = amount,
                    exchangeAmount = calculateTargetAmount(amount.toDoubleOrNull() ?: 0.0, rate),
                    rate = rate.toBasicString()
                )
            }

            is SelectedCurrenciesData.LoadedCustomExchange -> {
                var base = data.customExchange.base
                var target = data.customExchange.target
                val rate = normalizeRate(data.customExchange.rate, switched)
                if (switched) base = target.also { target = base }

                SelectedCurrencies(
                    baseCode = base,
                    targetCode = target,
                    baseAmount = amount,
                    exchangeAmount = calculateTargetAmount(amount.toDoubleOrNull() ?: 0.0, rate),
                    rate = rate.toBasicString()
                )
            }

            is SelectedCurrenciesData.IncompleteLoaded -> {
                var base = data.base
                var target = data.target
                if (switched) base = target.also { target = base }
                SelectedCurrencies(
                    baseCode = data.base?.code ?: "",
                    targetCode = data.target?.code ?: "",
                    baseAmount = "",
                    exchangeAmount = "",
                    rate = "",
                    animateContainer = animateContainer
                )
            }
        }
    }.shareIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily
    )

    val uiState: StateFlow<UiState> = combine(
        dataState, bottomSheetData, currencyUpdateState, selectedCurrencies, currencySelectorTarget
    ) { cachedState, bottomSheetData, updateState, currentCurrencies, currencySelectorTarget ->
        UiState(
            cachedState, bottomSheetData, updateState, currentCurrencies, currencySelectorTarget
        )
    }.stateIn(
        scope = viewModelScope, initialValue = UiState(
            dataState = DataState.Loading,
            bottomSheetData = BottomSheetData(),
            currencyUpdateState = CurrencyUpdateState.Loading,
            selectedCurrencies = SelectedCurrencies(),
            currencySelectorTarget = CurrencySelectorTarget.NONE
        ), started = SharingStarted.Eagerly
    )

    fun updateCurrencies() = viewModelScope.launch {
        currencyUpdateUseCase().collect {
            currencyUpdateState.value = it
        }
    }

    private var goToRecent = false

    private fun calculateGoToRecent(currencyList: List<Currency>): Boolean {
        if (!goToRecent && currencyList.count { it.timestamp != 0L } >= 3) {
            goToRecent = true
        }
        return goToRecent
    }

    init {
        updateCurrencies()
    }
}

data class UiState(
    val dataState: DataState,
    val bottomSheetData: BottomSheetData,
    val currencyUpdateState: CurrencyUpdateState,
    val selectedCurrencies: SelectedCurrencies,
    val currencySelectorTarget: CurrencySelectorTarget
)

sealed interface DataState {
    data class Loaded(
        val currencyList: List<Currency>,
        val customExchangeList: List<CustomExchange>,
    ) : DataState

    data object Empty : DataState
    data object Loading : DataState
}

data class BottomSheetData(
    val filteredList: List<Currency> = emptyList(),
    val recentList: List<Currency> = emptyList(),
    val goToRecent: Boolean = false
)

sealed interface SelectedCurrenciesData {
    data class LoadedCurrency(
        val base: Currency,
        val target: Currency,
    ) : SelectedCurrenciesData

    data class LoadedCustomExchange(val customExchange: CustomExchange) : SelectedCurrenciesData

    data class IncompleteLoaded(
        val base: Currency?, val target: Currency?
    ) : SelectedCurrenciesData
}


data class SelectedCurrencies(
    val baseCode: String = "",
    val targetCode: String = "",
    val baseAmount: String = "",
    val exchangeAmount: String = "",
    val rate: String = "",
    val animateContainer: Boolean = false
)

enum class CurrencySelectorTarget {
    NONE, BASE, TARGET
}