package br.com.aulaandroid.di

import android.app.Application
import br.com.aulaandroid.data.networking.GithubNetworking
import br.com.aulaandroid.ui.login.LoginViewModel
import br.com.aulaandroid.data.repository.LoginRepository
import br.com.aulaandroid.data.repository.NewAccountRepository
import br.com.aulaandroid.data.repository.impl.LoginRepositoryImpl
import br.com.aulaandroid.data.repository.impl.NewAccountRepositoryImpl
import br.com.aulaandroid.data.networking.LoginNetworking
import br.com.aulaandroid.data.networking.NewAccountNetworking
import br.com.aulaandroid.data.networking.impl.GithubNetworkingImpl
import br.com.aulaandroid.data.networking.impl.LoginNetworkingImpl
import br.com.aulaandroid.data.networking.impl.NewAccountNetworkingImpl
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.data.repository.impl.GithubRepositoryImpl
import br.com.aulaandroid.data.service.GithubApi
import br.com.aulaandroid.ui.home.HomeViewModel
import br.com.aulaandroid.ui.newAccount.NewAccountViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sin


val aulaAndroidModule = module {
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }

    factory<LoginNetworking> { LoginNetworkingImpl(get()) }
    factory<LoginRepository> { LoginRepositoryImpl(get()) }

    factory<NewAccountNetworking> { NewAccountNetworkingImpl(get(), get()) }
    factory<NewAccountRepository> { NewAccountRepositoryImpl(get()) }

    factory<GithubNetworking> { GithubNetworkingImpl(get()) }
    factory<GithubRepository> { GithubRepositoryImpl(get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { NewAccountViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}

val apiModule = module {
    fun provideGithubApi(retrofit: Retrofit) : GithubApi {
        return retrofit.create(GithubApi::class.java)
    }
    single { provideGithubApi(get()) }
}

val retrofitModule = module {

    fun provideRetrofit(client: OkHttpClient, gson : Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideGson(): Gson = GsonBuilder().create()

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}