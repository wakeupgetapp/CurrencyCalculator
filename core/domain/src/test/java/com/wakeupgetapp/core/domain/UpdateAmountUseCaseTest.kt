package com.wakeupgetapp.core.domain

import junit.framework.TestCase.assertEquals
import org.junit.Test

class UpdateAmountUseCaseTest {

    private val subject = UpdateAmountUseCase()

    @Test
    fun delete_all_long_click() {
        assertEquals("", subject("<", "123", true))
        assertEquals("", subject("<", "123.1231", true))
    }

    @Test
    fun delete_zero_dot_deletes_all() {
        assertEquals("", subject("<", "0.", false))
    }

    @Test
    fun delete_casual() {
        assertEquals("12", subject("<", "123", false))
    }

    @Test
    fun blank_amount_input_zero_returns_zero_with_dot() {
        assertEquals("0.", subject("0", "", false))
    }

    @Test
    fun non_blank_amount_can_be_extended_with_zero() {
        assertEquals("1230", subject("0", "123", false))
    }

    @Test
    fun blank_amount_input_dot_returns_zero_with_dot() {
        assertEquals("0.", subject(".", "", false))
    }

    @Test
    fun second_dot_does_not_appear() {
        assertEquals("12.3", subject(".", "12.3", false))
    }

    @Test
    fun input_exceeds_length_does_not_return_value() {
        val amount = "1".repeat(16)
        assertEquals(amount, subject("5", amount, false))
    }

    @Test
    fun test_normal_input() {
        assertEquals("1234", subject("4", "123", false))
    }
}