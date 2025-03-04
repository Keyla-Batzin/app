
import com.example.bloom.ResponseMessage
import com.example.bloom.compra.Compra
import com.example.bloom.ramosflores.RamoFlor
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

interface RamoFlorService {

    @GET("/ramos_flores/{id}")
    suspend fun obtenerRamoFlores(@Path("id") id: Int): RamoFlor

    @GET("/ramos_flores/")
    suspend fun obtenerTodosRamoFlor(): List<RamoFlor>

    @POST("/compras/")
    suspend fun crearCompra(@Body compra: Compra): ResponseMessage
}

class RamoFlorAPI {
    companion object {
        private var mAPI: RamoFlorService? = null

        @Synchronized
        fun API(): RamoFlorService {
            if (mAPI == null){
                val client: OkHttpClient = getUnsafeOkHttpClient()
                val gsondateformat= GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
                mAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsondateformat))
                    .baseUrl("https://18.211.200.201")
                    .client(getUnsafeOkHttpClient())
                    .build()
                    .create(RamoFlorService::class.java)
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
        }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }

        val okHttpClient = builder.build()
        return okHttpClient
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}