package com.example.mission8.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {

    // 서버의 Base URL을 실제 주소로 변경하세요. 예: "https://api.myserver.com/"
    private const val BASE_URL = "http://3.35.121.185"

    // (선택) 상세 로그 확인을 위한 로깅 인터셉터
    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // OkHttpClient 설정 (타임아웃, 로깅 등)
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit 인스턴스
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 회원가입 서비스 구현체
    val signUpService: SignUpService by lazy {
        retrofit.create(SignUpService::class.java)
    }

    // 로그인 서비스 구현체
    val loginService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }
}