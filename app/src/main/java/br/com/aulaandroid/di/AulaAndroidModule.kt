package br.com.aulaandroid.di

import br.com.aulaandroid.ui.login.LoginViewModel
import br.com.aulaandroid.data.LoginRepository
import br.com.aulaandroid.data.NewAccountRepository
import br.com.aulaandroid.data.impl.LoginRepositoryImpl
import br.com.aulaandroid.data.impl.NewAccountRepositoryImpl
import br.com.aulaandroid.data.networking.LoginNetworking
import br.com.aulaandroid.data.networking.NewAccountNetworking
import br.com.aulaandroid.data.networking.impl.LoginNetworkingImpl
import br.com.aulaandroid.data.networking.impl.NewAccountNetworkingImpl
import br.com.aulaandroid.ui.newAccount.NewAccountViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val aulaAndroidModule = module {
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }
    factory<LoginNetworking> { LoginNetworkingImpl(get()) }
    factory<LoginRepository> { LoginRepositoryImpl(get()) }
    factory<NewAccountNetworking> { NewAccountNetworkingImpl(get(), get()) }
    factory<NewAccountRepository> { NewAccountRepositoryImpl(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { NewAccountViewModel(get()) }
}