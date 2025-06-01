import com.example.api.LoginRequest
import com.example.api.LoginResponse
import com.example.api.SignUpRequest
import com.example.api.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// ApiService.kt
interface ApiService {
    @POST("/join")  // 여기 수정
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}

