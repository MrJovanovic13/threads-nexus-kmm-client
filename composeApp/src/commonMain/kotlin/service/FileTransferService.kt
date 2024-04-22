package service

import Constants
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentDisposition
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class FileTransferService {

    private val client = HttpClient()

    suspend fun uploadFileToBackend(filePath: String, recipientDeviceId: String) {
        val file = File(filePath) // TODO Use streaming instead of loading whole file in-memory

        client.submitFormWithBinaryData(
            url = "${Constants.BACKEND_URL}/devices/$recipientDeviceId/files",
            formData = formData {
                append("recipientDeviceIds", Json.encodeToString(listOf(recipientDeviceId)), Headers.build {
                    append(HttpHeaders.ContentType, "application/json")
                })
                append("file", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                    append(HttpHeaders.ContentType, "image/png")
                })
            })
    }

    suspend fun downloadFileFromBackend() {
        val currentDeviceId = Settings().getString("deviceId", "UNKNOWN");
        val httpResponse =
            client.get(Constants.BACKEND_URL + "/devices/" + currentDeviceId + "/files")

        println(httpResponse.headers.entries())
        val disposition =
            ContentDisposition.parse(httpResponse.headers[HttpHeaders.ContentDisposition] ?: "")
        val filename = disposition.parameter("filename")
        val file = File(filename ?: "defaultName")
        httpResponse.bodyAsChannel().copyAndClose(file.writeChannel())
        println("Finished downloading..")
    }
}
