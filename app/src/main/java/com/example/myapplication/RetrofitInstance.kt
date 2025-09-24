import com.example.myapplication.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://agri-helper-server.onrender.com/"

    // The ApiService interface is created lazily (only when it's first accessed).
    val api: ApiService by lazy {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        // OkHttpClient is the underlying HTTP client for Retrofit.
        // We add our logger to it.
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // This is where the Retrofit instance is built.
        Retrofit.Builder()
            .baseUrl(BASE_URL) // 1. Set the base URL for all API calls.
            .client(client)    // 2. Attach the OkHttpClient with the logger.
            .addConverterFactory(GsonConverterFactory.create()) // 3. Tell Retrofit to use Gson to convert JSON to Kotlin data classes.
            .build()
            .create(ApiService::class.java) // 4. Create an implementation of our ApiService interface.
    }
}