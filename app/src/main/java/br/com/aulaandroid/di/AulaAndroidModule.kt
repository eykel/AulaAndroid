package br.com.aulaandroid.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import br.com.aulaandroid.data.local.GitUserDataBase
import br.com.aulaandroid.data.local.GitUserDataBase.Companion.DATABASE_NAME
import br.com.aulaandroid.data.local.dao.UserDAO
import br.com.aulaandroid.data.local.utils.SessionCache
import br.com.aulaandroid.data.local.utils.SessionCacheImpl
import br.com.aulaandroid.data.local.utils.SessionManager
import br.com.aulaandroid.data.networking.GithubNetworking
import br.com.aulaandroid.ui.login.LoginViewModel
import br.com.aulaandroid.data.repository.LoginRepository
import br.com.aulaandroid.data.repository.NewAccountRepository
import br.com.aulaandroid.data.repository.impl.LoginRepositoryImpl
import br.com.aulaandroid.data.repository.impl.NewAccountRepositoryImpl
import br.com.aulaandroid.data.networking.LoginNetworking
import br.com.aulaandroid.data.networking.NewAccountNetworking
import br.com.aulaandroid.data.networking.SettingsNetworking
import br.com.aulaandroid.data.networking.impl.GithubNetworkingImpl
import br.com.aulaandroid.data.networking.impl.LoginNetworkingImpl
import br.com.aulaandroid.data.networking.impl.NewAccountNetworkingImpl
import br.com.aulaandroid.data.networking.impl.SettingsNetworkingImpl
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.data.repository.SettingsRepository
import br.com.aulaandroid.data.repository.impl.GithubRepositoryImpl
import br.com.aulaandroid.data.repository.impl.SettingsRepositoryImpl
import br.com.aulaandroid.data.service.GithubApi
import br.com.aulaandroid.ui.detail.DetailViewModel
import br.com.aulaandroid.ui.favorite.FavoriteViewModel
import br.com.aulaandroid.ui.home.HomeViewModel
import br.com.aulaandroid.ui.newAccount.NewAccountViewModel
import br.com.aulaandroid.ui.settings.SettingsViewModel
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


const val SESSION_CACHE = "SESSION_CACHE"

val aulaAndroidModule = module {

    fun provideSharedPreferences(context: Application) : SharedPreferences =
        context.getSharedPreferences(SESSION_CACHE, Context.MODE_PRIVATE)

    single { provideSharedPreferences(androidApplication()) }

    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }

    single<SessionCache> { SessionCacheImpl(get()) }

    single { SessionManager(get()) }

    factory<LoginNetworking> { LoginNetworkingImpl(get(),get()) }
    factory<LoginRepository> { LoginRepositoryImpl(get(),get()) }

    factory<NewAccountNetworking> { NewAccountNetworkingImpl(get(), get(), get()) }
    factory<NewAccountRepository> { NewAccountRepositoryImpl(get(),get()) }

    factory<GithubNetworking> { GithubNetworkingImpl(get(), get()) }
    factory<GithubRepository> { GithubRepositoryImpl(get(), get(), get(), get()) }

    factory<SettingsNetworking> { SettingsNetworkingImpl(get(), get()) }
    factory<SettingsRepository> { SettingsRepositoryImpl(get()) }

    viewModel { LoginViewModel(get()) }
    viewModel { NewAccountViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
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


val dataBaseModule = module {
    fun provideDataBase(application: Application) : GitUserDataBase {
        return GitUserDataBase.getDatabase(application)
    }

    fun provideDao(dataBase: GitUserDataBase) : UserDAO {
        return dataBase.userDao()
    }

    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }
}