package com.example.weatherservice.persistence

import com.example.weatherservice.persistence.entity.CityWeather
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CityWeatherRepository : MongoRepository<CityWeather, String> {
    fun findByCity(city: String): CityWeather?
}
