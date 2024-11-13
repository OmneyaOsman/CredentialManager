package com.omni.credentialmanagerchecking.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.omni.credentialmanagerchecking.data.remote.LoginApi
import com.omni.credentialmanagerchecking.domain.repo.AccountCredentialManager
import com.omni.credentialmanagerchecking.domain.repo.DataProvider
import com.omni.credentialmanagerchecking.domain.usecase.LoginUseCase
import com.omni.credentialmanagerchecking.domain.usecase.LoginWithSavedCredentialsUseCase
import com.omni.credentialmanagerchecking.domain.usecase.LogoutUseCase
import com.omni.credentialmanagerchecking.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APPModule {

    @Provides
    fun provideAccountCredentialManager(
        @ApplicationContext context: Context,
        api: LoginApi,
        dataProvider: DataProvider
    ): AccountCredentialManager =
        AccountCredentialManager(context, api, dataProvider)

    @Provides
    fun provideLoginUseCase(accountCredentialManager: AccountCredentialManager): LoginUseCase =
        LoginUseCase(accountCredentialManager)

    @Provides
    fun provideLoginWithSavedCredentialsUseCase(accountCredentialManager: AccountCredentialManager): LoginWithSavedCredentialsUseCase =
        LoginWithSavedCredentialsUseCase(accountCredentialManager)

    @Provides
    fun provideLogoutUseCase(accountCredentialManager: AccountCredentialManager): LogoutUseCase =
        LogoutUseCase(accountCredentialManager)

    @Provides
    fun provideSignUpUseCase(accountCredentialManager: AccountCredentialManager): SignUpUseCase =
        SignUpUseCase(accountCredentialManager)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(PREF_NAME) }
        )
    }

    private const val PREF_NAME = "credman_pref"
}
