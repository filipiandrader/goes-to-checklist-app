package com.far.goestochecklist.di

import com.far.goestochecklist.domain.repository.GoesToChecklistRepository
import com.far.goestochecklist.domain.usecase.film.FilmDetailUseCases
import com.far.goestochecklist.domain.usecase.film.GetFilmByFiltersUseCase
import com.far.goestochecklist.domain.usecase.film.GetFilmByIdUseCase
import com.far.goestochecklist.domain.usecase.film.GetFilmUseCase
import com.far.goestochecklist.domain.usecase.filter.GetFiltersUseCase
import com.far.goestochecklist.domain.usecase.home.HomeUseCases
import com.far.goestochecklist.domain.usecase.login.DoLoginUseCase
import com.far.goestochecklist.domain.usecase.login.LoginUseCases
import com.far.goestochecklist.domain.usecase.profile.EditProfileDataUseCases
import com.far.goestochecklist.domain.usecase.profile.ProfileDataUseCases
import com.far.goestochecklist.domain.usecase.profile.ProfileMenuUseCases
import com.far.goestochecklist.domain.usecase.search.SearchUseCases
import com.far.goestochecklist.domain.usecase.signup.SignUpUseCase
import com.far.goestochecklist.domain.usecase.signup.SignUpUseCases
import com.far.goestochecklist.domain.usecase.user.*
import com.far.goestochecklist.domain.usecase.validators.ValidateNameUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidatePasswordUseCase
import com.far.goestochecklist.domain.usecase.validators.ValidateUsernameUseCase
import com.far.goestochecklist.domain.usecase.year.GetYearUseCase
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
	fun provideMarkWatchUseCase(repository: GoesToChecklistRepository): MarkWatchUseCase {
		return MarkWatchUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideUpdateUserInfoUseCase(repository: GoesToChecklistRepository): UpdateUserInfoUseCase {
		return UpdateUserInfoUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideUpdateUserInfoLocallyUseCase(repository: GoesToChecklistRepository): UpdateUserInfoLocallyUseCase {
		return UpdateUserInfoLocallyUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideGetFilmUseCase(repository: GoesToChecklistRepository): GetFilmUseCase {
		return GetFilmUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideGetFilmByIdUseCase(repository: GoesToChecklistRepository): GetFilmByIdUseCase {
		return GetFilmByIdUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideGetFilmByFiltersUseCase(repository: GoesToChecklistRepository): GetFilmByFiltersUseCase {
		return GetFilmByFiltersUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideGetYearUseCase(repository: GoesToChecklistRepository): GetYearUseCase {
		return GetYearUseCase(repository)
	}

	@Provides
	@Singleton
	fun provideGetFiltersUseCase(repository: GoesToChecklistRepository): GetFiltersUseCase {
		return GetFiltersUseCase(repository)
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

	@Provides
	@Singleton
	fun provideHomeUseCases(repository: GoesToChecklistRepository): HomeUseCases {
		return HomeUseCases(
			getUserUseCase = provideGetUserUseCase(repository),
			getYearUseCase = provideGetYearUseCase(repository),
			getFilmUseCase = provideGetFilmUseCase(repository),
			markWatchUseCase = provideMarkWatchUseCase(repository),
		)
	}

	@Provides
	@Singleton
	fun provideFilmDetailUseCases(repository: GoesToChecklistRepository): FilmDetailUseCases {
		return FilmDetailUseCases(
			markWatchUseCase = provideMarkWatchUseCase(repository)
		)
	}

	@Provides
	@Singleton
	fun provideProfileDataUseCasesUseCases(repository: GoesToChecklistRepository): ProfileDataUseCases {
		return ProfileDataUseCases(
			getUserUseCase = provideGetUserUseCase(repository)
		)
	}

	@Provides
	@Singleton
	fun provideProfileMenuUseCasesUseCases(repository: GoesToChecklistRepository): ProfileMenuUseCases {
		return ProfileMenuUseCases(
			deleteUserUseCase = provideDeleteUserUseCase(repository)
		)
	}

	@Provides
	@Singleton
	fun provideEditProfileDataUseCases(repository: GoesToChecklistRepository): EditProfileDataUseCases {
		return EditProfileDataUseCases(
			validateNameUseCase = provideValidateNameUseCase(),
			validateUsernameUseCase = provideValidateUsernameUseCase(),
			updateUserInfoUseCase = provideUpdateUserInfoUseCase(repository),
			updateUserInfoLocallyUseCase = provideUpdateUserInfoLocallyUseCase(repository)
		)
	}

	@Provides
	@Singleton
	fun provideSearchUseCases(repository: GoesToChecklistRepository): SearchUseCases {
		return SearchUseCases(
			getFiltersUseCase = provideGetFiltersUseCase(repository),
			getFilmByFiltersUseCase = provideGetFilmByFiltersUseCase(repository)
		)
	}
}