package com.softsquared.template.kotlin.config

import com.softsquared.template.kotlin.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        // sharedPreference에서 X_ACCESS_TOKEN 이라는 키로 그 값을 가져옴, 값을 가져오지 못했을 경우 기본값은 null
        val jwtToken: String? = sSharedPreferences.getString(X_ACCESS_TOKEN, null)
        // 만약 그것이 null이 아니라면 헤더의 그 토큰 값을 여기에 jwt 토
        if (jwtToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}