package service

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class EventsService {

    private val client = HttpClient()

    suspend fun getCurrentIp(): String {
        val response = client.get("https://checkip.amazonaws.com")
        return response.bodyAsText()
    }
}
