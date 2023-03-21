package com.far.goestochecklist.di

import android.app.Application
import androidx.room.Room
import com.far.goestochecklist.BuildConfig
import com.far.goestochecklist.common.Constants
import com.far.goestochecklist.data.datasource.local.GoesToChecklistLocalDataSource
import com.far.goestochecklist.data.datasource.remote.GoesToChecklistRemoteDataSource
import com.far.goestochecklist.data.local.database.GoesToChecklistDatabase
import com.far.goestochecklist.data.local.datasource.GoesToChecklistLocalDataSourceImpl
import com.far.goestochecklist.data.remote.datasource.GoesToChecklistRemoteDataSourceImpl
import com.far.goestochecklist.data.remote.service.GoesToChecklistService
import com.far.goestochecklist.data.remote.utils.RequestWrapper
import com.far.goestochecklist.data.remote.utils.RequestWrapperImpl
import com.far.goestochecklist.data.repository.GoesToChecklistRepositoryImpl
import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

	@Provides
	@Singleton
	fun provideGoesToChecklistService(okHttpClient: OkHttpClient): GoesToChecklistService {
		return Retrofit.Builder()
			.baseUrl(BuildConfig.BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(GoesToChecklistService::class.java)
	}

	@Provides
	@Singleton
	fun provideOkHttpClient(): OkHttpClient {
		val loggingInterceptor = HttpLoggingInterceptor()
		loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

		return OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.connectTimeout(45, TimeUnit.SECONDS)
			.callTimeout(45, TimeUnit.SECONDS)
			.build()
	}

	@Provides
	@Singleton
	fun provideRequestWrapper(): RequestWrapper {
		return RequestWrapperImpl()
	}

	@Provides
	@Singleton
	fun provideGoesToChecklistDatabase(application: Application): GoesToChecklistDatabase {
		return Room.databaseBuilder(
			application, GoesToChecklistDatabase::class.java, Constants.DATABASE_NAME
		).build()
	}

	@Provides
	@Singleton
	fun provideGoesToChecklistRemoteDataSource(
		service: GoesToChecklistService,
		wrapper: RequestWrapper
	): GoesToChecklistRemoteDataSource {
		return GoesToChecklistRemoteDataSourceImpl(service, wrapper)
	}

	@Provides
	@Singleton
	fun provideGoesToChecklistLocalDataSource(
		database: GoesToChecklistDatabase
	): GoesToChecklistLocalDataSource {
		return GoesToChecklistLocalDataSourceImpl(database)
	}

	@Provides
	@Singleton
	fun provideGoesToChecklistRepository(
		remoteDataSource: GoesToChecklistRemoteDataSource,
		localDataSource: GoesToChecklistLocalDataSource
	): GoesToChecklistRepository {
		return GoesToChecklistRepositoryImpl(remoteDataSource, localDataSource)
	}
}