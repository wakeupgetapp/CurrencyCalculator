package com.wakeupgetapp.core.domain

import junit.framework.TestCase.assertEquals
import org.junit.Test

class NormalizeRateUseCaseTest {

    private val subject = NormalizeRateUseCase()

    @Test
    fun rate_not_ends_with_max_4_decimal_places() {
        assertEquals(0.913, subject(23.0, 21.0))
        assertEquals(0.9565, subject(23.0, 22.0))
        assertEquals(123.3999, subject(123.3999111111111, false))
    }
}