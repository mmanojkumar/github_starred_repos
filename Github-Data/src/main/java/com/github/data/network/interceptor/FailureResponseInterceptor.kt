package  com.github.data.network.interceptor

import com.github.data.exception.ApiException
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response


class FailureResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()
        val response = chain.proceed(request)
        if (response.code() != 200) {
            throw Gson().fromJson<ApiException>(response.body().string(), ApiException::class.java)
        }
        return response
    }


}