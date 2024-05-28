package com.wakeupgetapp.currencycalculator.core.network.internal

import com.wakeupgetapp.core.data.repository.CurrencyUpdateRepository
import com.wakeupgetapp.core.model.Currency
import com.wakeupgetapp.core.model.CurrencyTable
import com.wakeupgetapp.currencycalculator.core.network.internal.json.CcJsonReader
import com.wakeupgetapp.core.network.internal.api.CurrencyApiService
import com.wakeupgetapp.core.network.internal.api.CurrencyBackupApiService
import com.wakeupgetapp.core.network.internal.repository.CurrencyUpdateRepositoryImpl
import com.wakeupgetapp.core.network.model.CallResult
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CurrencyApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var repository: CurrencyUpdateRepository
    private val jsonReader = CcJsonReader()

    private inline fun <reified T> createApiService(mockWebServer: MockWebServer): T {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient().newBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(T::class.java)
    }

    private fun mockResponse(path: String, code: Int) {
        val mockResponse =
            MockResponse()
                .setResponseCode(code)
                .setBody(
                    jsonReader(path)
                )

        mockWebServer.enqueue(mockResponse)
    }

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        repository = CurrencyUpdateRepositoryImpl(
            createApiService<CurrencyApiService>(mockWebServer),
            createApiService<CurrencyBackupApiService>(mockWebServer)
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    object Paths {
        const val ResponseSuccess = "responseSuccess.json"
        const val ResponseMalformed = "responseMalformed.json"
        const val ResponseEmpty = "responseEmpty.json"
    }

    @Test
    fun repository_fetchCurrencies_Success_return_empty_data() = runTest {
        mockResponse(Paths.ResponseEmpty, 201)
        assertEquals(
            CallResult.NetworkFailure, repository.fetchLatestData()
        )
    }

    @Test
    fun repository_fetchCurrenciesBackup_Success_return_empty_data() = runTest {
        mockResponse(Paths.ResponseEmpty, 201)
        assertEquals(
            CallResult.NetworkFailure, repository.fetchLatestDataBackup()
        )
    }

    @Test
    fun repository_fetchCurrencies_Failure_return_empty() = runTest {
        mockResponse(Paths.ResponseEmpty, 401)
        assertEquals(
            CallResult.Failure(
                errorCode = 401,
                errorBody = "Could not parse JSON body",
                errorMessage = "HTTP 401 Client Error"
            ), repository.fetchLatestData()
        )
    }

    @Test
    fun repository_fetchCurrenciesBackup_Failure_return_empty() = runTest {
        mockResponse(Paths.ResponseEmpty, 401)
        assertEquals(
            CallResult.Failure(
                errorCode = 401,
                errorBody = "Could not parse JSON body",
                errorMessage = "HTTP 401 Client Error"
            ), repository.fetchLatestDataBackup()
        )
    }

    @Test
    fun repository_fetchCurrencies_Failure_ServerError_return_empty() = runTest {
        mockResponse(Paths.ResponseEmpty, 501)
        assertEquals(
            CallResult.Failure(
                errorCode = 501,
                errorBody = "Could not parse JSON body",
                errorMessage = "HTTP 501 Server Error"
            ), repository.fetchLatestData()
        )
    }

    @Test
    fun repository_fetchCurrenciesBackup_ServerError_return_empty() = runTest {
        mockResponse(Paths.ResponseEmpty, 501)
        assertEquals(
            CallResult.Failure(
                errorCode = 501,
                errorBody = "Could not parse JSON body",
                errorMessage = "HTTP 501 Server Error"
            ), repository.fetchLatestDataBackup()
        )
    }

    @Test
    fun repository_fetchCurrenciesBackup_Failure_return_malformed_data() = runTest {
        mockResponse(Paths.ResponseMalformed, 401)
        assertEquals(
            CallResult.Failure(
                errorCode = 401,
                errorBody = """{"date":"2024-05-20"}""",
                errorMessage = "HTTP 401 Client Error"
            ), repository.fetchLatestData()
        )
    }

    @Test
    fun repository_fetchCurrencies_Failure_return_malformed_data() = runTest {
        mockResponse(Paths.ResponseMalformed, 401)
        assertEquals(
            CallResult.Failure(
                errorCode = 401,
                errorBody = """{"date":"2024-05-20"}""",
                errorMessage = "HTTP 401 Client Error"
            ), repository.fetchLatestData()
        )
    }

    @Test
    fun repository_fetchCurrencies_Success_return_malformed_data() = runTest {
        mockResponse(Paths.ResponseMalformed, 201)
        assertEquals(CallResult.NetworkFailure, repository.fetchLatestData())
    }

    @Test
    fun repository_fetchCurrenciesBackup_Success_return_malformed_data() = runTest {
        mockResponse(Paths.ResponseMalformed, 201)
        assertEquals(CallResult.NetworkFailure, repository.fetchLatestDataBackup())
    }


    @Test
    fun repository_fetchCurrencies_Success_return_transformed_data() = runTest {
        mockResponse(Paths.ResponseSuccess, 201)
        assertEquals(
            resultSuccess, repository.fetchLatestData()
        )
    }

    @Test
    fun repository_fetchCurrenciesBackup_Success_return_transformed_data() = runTest {
        mockResponse(Paths.ResponseSuccess, 201)
        assertEquals(resultSuccess, repository.fetchLatestDataBackup())
    }


    private val resultSuccess =
        CallResult.Success(
            CurrencyTable(
                date = "2024-05-20",
                currencyList =
                listOf(
                    Currency(
                        name = "",
                        code = "PLN",
                        symbol = "",
                        rate = 3.91821649,
                        timestamp = 0
                    ),
                    Currency(
                        name = "",
                        code = "GBP",
                        symbol = "",
                        rate = 0.78715106,
                        timestamp = 0
                    ),
                    Currency(
                        name = "",
                        code = "EUR",
                        symbol = "",
                        rate = 0.91957897,
                        timestamp = 0
                    ),
                    Currency(
                        name = "",
                        code = "AED",
                        symbol = "",
                        rate = 3.6725,
                        timestamp = 0
                    ),
                    Currency(
                        name = "",
                        code = "JPY",
                        symbol = "",
                        rate = 155.82530539,
                        timestamp = 0
                    )
                )
            )
        )

}
