package com.far.goestochecklist.di

import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import com.far.goestochecklist.domain.usecase.login.DoLoginUseCase
import com.far.goestochecklist.domain.usecase.login.LoginUseCases
import com.far.goestochecklist.domain.usecase.signup.SignUpUseCase
import com.far.goestochecklist.domain.usecase.signup.SignUpUseCases
import com.far.goestochecklist.domain.usecase.user.DeleteUserUseCase
import com.far.goestochecklist.domain.usecase.user.GetUserUseCase
import com.far.goestochecklist.domain.usecase.user.InsertUserUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateNameUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidatePasswordUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateUsernameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

	@Provides
	@Singleton
	fun provideDoLoginUseCase(repository: GoesToChecklistRepository): DoLoginUseCase {
		return DoLoginUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideSignUpUseCase(repository: GoesToChecklistRepository): SignUpUseCase {
		return SignUpUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideValidateNameUseCase(): ValidateNameUseCase {
		return ValidateNameUseCase()
	}

	@Provides
	@Singleton
	fun provideValidateUsernameUseCase(): ValidateUsernameUseCase {
		return ValidateUsernameUseCase()
	}

	@Provides
	@Singleton
	fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
		return ValidatePasswordUseCase()
	}

	@Provides
	@Singleton
	fun provideInsertUserUseCase(repository: GoesToChecklistRepository): InsertUserUseCase {
		return InsertUserUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideDeleteUserUseCase(repository: GoesToChecklistRepository): DeleteUserUseCase {
		return DeleteUserUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideGetUserUseCase(repository: GoesToChecklistRepository): GetUserUseCase {
		return GetUserUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideLoginUseCases(repository: GoesToChecklistRepository): LoginUseCases {
		return LoginUseCases(
			doLoginUseCase = provideDoLoginUseCase(repository),
			validateUsernameUseCase = provideValidateUsernameUseCase(),
			validatePasswordUseCase = provideValidatePasswordUseCase(),
			insertUserUseCase = provideInsertUserUseCase(repository),
			deleteUserUseCase = provideDeleteUserUseCase(repository)
		)
	}

	@Provides
	@Singleton
	fun provideSignUpUseCases(repository: GoesToChecklistRepository): SignUpUseCases {
		return SignUpUseCases(
			signUpUseCase = provideSignUpUseCase(repository),
			validateNameUseCase = provideValidateNameUseCase(),
			validateUsernameUseCase = provideValidateUsernameUseCase(),
			validatePasswordUseCase = provideValidatePasswordUseCase(),
			doLoginUseCase = provideDoLoginUseCase(repository),
			insertUserUseCase = provideInsertUserUseCase(repository),
			deleteUserUseCase = provideDeleteUserUseCase(repository)
		)
	}
}