package com.example.weatherservice.api

import mu.KLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CityWeatherControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `get weather for existing city`() {
        // Given
        val city = "London"
        // When
        val actual = mockMvc.perform(get("/weather/city/$city").accept(APPLICATION_JSON))
            // Then
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.city").exists())
            .andExpect(jsonPath("$.city").value(city))
            .andExpect(jsonPath("$.temperature").exists())
            .andReturn()
            .response
        logger.info { "Temperature:${actual.contentAsString}" }
    }

    companion object : KLogging()
}
