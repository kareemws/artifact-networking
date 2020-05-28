package artifact.networking.contract.config

import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

abstract class RetrofitConfig {
    protected abstract val converterFactory: Converter.Factory?

    protected abstract val callAdapterFactory: CallAdapter.Factory?

    fun forgeRetrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(httpClient)
            converterFactory?.let {
                addConverterFactory(it)
            }
            callAdapterFactory?.let {
                addCallAdapterFactory(it)
            }
        }.build()
}