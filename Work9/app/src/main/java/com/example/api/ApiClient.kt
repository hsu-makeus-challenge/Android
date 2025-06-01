// ApiClient.kt
object ApiClient {
    private const val BASE_URL = "https://aos.inyro.site/"  // 서버 기본 주소 (Swagger에서 확인한 URL)

    val apiService: ApiService by lazy {
        NetworkManager.getRetrofit(BASE_URL).create(ApiService::class.java)
    }
}
