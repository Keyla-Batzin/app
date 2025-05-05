package com.example.bloom

import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import com.example.bloom.ramosflores.RamoFlor
import com.example.bloom.ramosflores.RamoFlorService
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail

class RamoFlorServiceIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var ramoFlorService: RamoFlorService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val gson = GsonBuilder().create()

        ramoFlorService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RamoFlorService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test obtenerTodosRamosFlores retorna lista correctamente`() = runTest {
        val jsonResponse = """
            [
                {
                    "id": 1,
                    "nombre": "Ramo Primavera",
                    "precio": 29.99,
                    "url": "https://example.com/ramo1.jpg"
                },
                {
                    "id": 2,
                    "nombre": "Ramo Otoño",
                    "precio": 34.99,
                    "url": "https://example.com/ramo2.jpg"
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val response = ramoFlorService.obtenerTodosRamosFlores()

        assertEquals(2, response.size)
        assertEquals("Ramo Primavera", response[0].nombre)
        assertEquals(29.99f, response[0].precio)
    }

    @Test
    fun `test crearCompra retorna respuesta exitosa`() = runTest {
        val compra = Compra(0, "Ramo Primavera", 29.99f, 1, "https://example.com/ramo1.jpg")

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(201)
                .setBody("""{ "message": "Compra realizada" }""")
        )

        val response = ramoFlorService.crearCompra(compra)

        assertEquals("Compra realizada", response.message)
    }

    @Test
    fun `test crearFavorito retorna respuesta exitosa`() = runTest {
        val favorito = Favorito(0, "Ramo Otoño", 34.99f, "https://example.com/ramo2.jpg")

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(201)
                .setBody("""{ "message": "Añadido a favoritos" }""")
        )

        val response = ramoFlorService.crearFavorito(favorito)

        assertEquals("Añadido a favoritos", response.message)
    }

    @Test
    fun `test obtenerTodosRamosFlores devuelve error cuando falla`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(503)
                .setBody("""{ "error": "Service Unavailable" }""")
        )

        try {
            ramoFlorService.obtenerTodosRamosFlores()
            assertTrue("Se esperaba una excepción", false)
        } catch (e: Exception) {
            assertTrue(e.message?.contains("503") == true)
        }
    }

    @Test
    fun `test llamada al servidor inactivo lanza excepción`() = runTest {
        mockWebServer.shutdown()  // Simula que el servidor está caído

        try {
            ramoFlorService.obtenerTodosRamosFlores()
            fail("Se esperaba una excepción por servidor inactivo")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("Failed to connect") == true || e.message?.contains("Connection refused") == true)
        }
    }


}
