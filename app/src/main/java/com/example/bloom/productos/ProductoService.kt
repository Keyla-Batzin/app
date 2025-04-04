package com.example.bloom.productos

import com.example.bloom.ResponseMessage
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import com.example.bloom.productos.Producto
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface ProductoService {

    @GET("/productos/{id}")
    suspend fun obtenerProducto(@Path("id") id: Int): Producto

    @GET("/productos/")
    suspend fun obtenerTodosProductos(): List<Producto>

    @POST("/compras/")
    suspend fun crearCompra(@Body compra: Compra): ResponseMessage

    @GET("/productos/")
    suspend fun buscarProductos(@Query("nombre") nombre: String): List<Producto>

    @POST("/favoritos/")
    suspend fun crearFavorito(@Body favorito: Favorito): ResponseMessage
}

class ProductoAPI {
    companion object {
        private var mAPI: ProductoService? = null

        @Synchronized
        fun API(): ProductoService {
            if (mAPI == null) {
                val client: OkHttpClient = getUnsafeOkHttpClient()
                val gsonDateFormat = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()
                mAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsonDateFormat))
                    .baseUrl("https://18.211.200.201") // Cambia la URL base según tu API
                    .client(getUnsafeOkHttpClient())
                    .build()
                    .create(ProductoService::class.java)
            }
            return mAPI!!
        }
    }
}

private fun getUnsafeOkHttpClient(): OkHttpClient {
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }

        return builder.build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}