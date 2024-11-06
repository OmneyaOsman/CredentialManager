package com.omni.credentialmanagerchecking.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.omni.credentialmanagerchecking.data.remote.LoginApi
import com.omni.credentialmanagerchecking.data.repo.AuthRepositoryImpl
import com.omni.credentialmanagerchecking.domain.AccountCredentialManager
import com.omni.credentialmanagerchecking.domain.DataProvider
import com.omni.credentialmanagerchecking.domain.repo.AuthRepository
import com.omni.credentialmanagerchecking.domain.usecase.LoginUseCase
import com.omni.credentialmanagerchecking.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APPModule {

    @Provides
    fun provideLoginRepository(
        api: LoginApi,
        accountCredentialManager: AccountCredentialManager,
        dataProvider: DataProvider
    ): AuthRepository = AuthRepositoryImpl(api, accountCredentialManager, dataProvider)

    @Provides
    fun provideAccountCredentialManager(@ApplicationContext context: Context): AccountCredentialManager =
        AccountCredentialManager(context)

    @Provides
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase = LoginUseCase(repository)

    @Provides
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase = SignUpUseCase(repository)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(PREF_NAME) }
        )
    }

    private const val PREF_NAME = "credman_pref"
}
