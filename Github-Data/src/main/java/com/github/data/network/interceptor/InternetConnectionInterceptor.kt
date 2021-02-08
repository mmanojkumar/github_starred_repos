package  com.github.data.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import com.github.data.exception.NoInternetException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class InternetConnectionInterceptor @Inject constructor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (isConnected()) {
            val builder: Request.Builder = chain.request().newBuilder()
            return chain.proceed(builder.build())
        }

        throw NoInternetException()
    }


    private fun isConnected(): Boolean {
        val isConnected: Boolean
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting
        return isConnected
    }
}