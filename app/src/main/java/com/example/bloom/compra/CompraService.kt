package com.example.bloom.compra

import com.example.bloom.ResponseMessage
import com.example.bloom.compra.Compra
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface CompraService {

    @GET("/compras/{id}")
    suspend fun obtenerCompra(@Path("id") id: Int): Compra

    @GET("/compras/")
    suspend fun obtenerTodasCompras(): List<Compra>

    @DELETE("/compras/{id}")  // Endpoint para eliminar una compra por ID
    suspend fun eliminarCompra(@Path("id") id: Int): ResponseMessage

    @GET("/compras/precio-total")
    suspend fun obtenerPrecioTotal(): PrecioTotalResponse  // Devuelve un objeto en lugar de un Float

    // Operaciones para Cantidad
    @PUT("/compras/suma-cantidad/{id}")
    suspend fun sumaCantidad(@Path("id") id: Int): ResponseMessage

    @PUT("/compras/resta-cantidad/{id}")
    suspend fun restaCantidad(@Path("id") id: Int): ResponseMessage
}

class CompraAPI {
    companion object {
        private var mAPI: CompraService? = null

        @Synchronized
        fun API(): CompraService {
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
                    .create(CompraService::class.java)
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