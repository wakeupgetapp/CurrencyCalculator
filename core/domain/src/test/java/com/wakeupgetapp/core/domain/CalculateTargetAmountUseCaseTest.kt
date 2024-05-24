package com.wakeupgetapp.core.domain

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculateTargetAmountUseCaseTest {

    private val subject = CalculateTargetAmountUseCase()

    @Test
    fun target_amount_smallerEquals_100_has_max_4_decimal_places() {
        assertEquals("0.0001", subject(0.00005, 2.0))
        assertEquals("2.2222", subject(1.11111111, 2.0))
        assertEquals("51.0000", subject(102.00, 0.5))
        assertEquals("100.0000", subject(50.00, 2.0))
    }

    @Test
    fun target_amount_bigger_than_100_smallerEquals_1_000_000_has_max_2_decimal_places() {
        assertEquals("222.22", subject(111.11111111, 2.0))
        assertEquals("198.22", subject(99.11, 2.0))
        assertEquals("1000000.00", subject(500000.00, 2.0))
    }

    @Test
    fun target_amount_bigger_than_1_000_000_has_0_decimal_places() {
        assertEquals("1000002", subject(500001.00, 2.0))
    }

    @Test
    fun too_high_amount_returns_error() {
        assertEquals("-1.00", subject(1000000000.0, 1000000000000.0))
    }

    @Test
    fun target_amount_is_rounded() {
        assertEquals("2.2223", subject(1.11119, 2.0))
        assertEquals("198.23", subject(99.119, 2.0))
        assertEquals("1000003", subject(500001.99, 2.0))
    }
}