package com.example.flo

import android.app.Application
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import java.security.MessageDigest
import android.os.Build


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        printKeyHash()
    }

    private fun printKeyHash() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // API 28 이상
                for (signature in info.signingInfo.apkContentsSigners) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val keyHash = String(Base64.encode(md.digest(), Base64.NO_WRAP))
                    Log.d("KEY_HASH", "💡 키 해시: $keyHash")
                }
            } else {
                // API 28 미만
                for (signature in info.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val keyHash = String(Base64.encode(md.digest(), Base64.NO_WRAP))
                    Log.d("KEY_HASH", "💡 키 해시: $keyHash")
                }
            }
        } catch (e: Exception) {
            Log.e("KEY_HASH", "키 해시 얻기 실패", e)
        }
    }
}
