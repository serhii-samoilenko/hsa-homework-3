package com.example.xorservice.service

import com.example.xorservice.config.XorServiceApplicationProperties
import com.example.xorservice.config.XorServiceApplicationProperties.Calculation
import com.example.xorservice.config.XorServiceApplicationProperties.Elasticsearch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CalculationServiceTest {

    private val calculationService = CalculationService(
        XorServiceApplicationProperties(
            Elasticsearch(""),
            Calculation(100000.0, 10)
        )
    )

    @Test
    fun test() {
        // Given
        val base = 1234567890L
        val modifier = 1234567890L
        // When
        val result = calculationService.calculateXor(base, modifier)
        // Then
        assertThat(result).isEqualTo(
            listOf(
                (1234567885L to 31L),
                (1234567886L to 28L),
                (1234567887L to 29L),
                (1234567888L to 2L),
                (1234567889L to 3L),
                (1234567890L to 0L),
                (1234567891L to 1L),
                (1234567892L to 6L),
                (1234567893L to 7L),
                (1234567894L to 4L),
                (1234567895L to 5L)
            )
        )
    }
}
