import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// NetworkManager.kt
object NetworkManager {
    fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

