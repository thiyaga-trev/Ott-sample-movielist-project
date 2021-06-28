package com.thiyaga.ottsampleproject.di

import android.content.Context
import androidx.multidex.BuildConfig
import com.thiyaga.ottsampleproject.repositary.ApiRespositary
import com.thiyaga.ottsampleproject.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


object KoinUtils {

    @ExperimentalCoroutinesApi
    fun setupKoin(context: Context, list: List<Module>) {
        startKoin {
            androidContext(androidContext = context)
            modules(list)

            apply {
                if (BuildConfig.DEBUG) {
                    this.printLogger()
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    val injectionModule = module {
        viewModel {
            MainViewModel(get(),get())
        }
        factory {
            ApiRespositary(get())
        }

    }

    @JvmStatic
    fun <T : Any> get(clazz: Class<T>): T {
        val kClass = clazz.kotlin
        return getKoin().get(
            kClass,
            null,
            null
        )
    }

    private fun getKoin(): Koin = GlobalContext.get()

}