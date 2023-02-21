package com.example.rootservice.api

import mu.KLogging
import org.junit.Ignore
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
class RootControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @Ignore
    fun `get root of xor`() {
        // Given
        val base = 12345L
        val modifier = 67890L
        // When
        val actual = mockMvc.perform(get("/root/xor/$base/$modifier").accept(APPLICATION_JSON))
            // Then
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.value").exists())
            .andExpect(jsonPath("$.value").value(1L))
            .andExpect(jsonPath("$.result").exists())
            .andExpect(jsonPath("$.result").value(1L))
            .andReturn()
            .response
        logger.info { "Result:${actual.contentAsString}" }
    }

    companion object : KLogging()
}
