package id.panicLabs

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import kotlin.test.Test
import kotlin.test.assertEquals


@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World", response.content)
            }
        }
    }

    @Test
    fun testGetAllUsers() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/api/users").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}