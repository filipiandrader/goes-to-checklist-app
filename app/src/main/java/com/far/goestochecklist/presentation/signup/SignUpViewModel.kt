package com.far.goestochecklist.presentation.signup

import androidx.lifecycle.ViewModel
import com.far.goestochecklist.domain.usecase.signup.SignUpUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
 * Created by Filipi Andrade Rocha on 19/03/2023.
 */

@HiltViewModel
class SignUpViewModel @Inject constructor(
	private val signUpUseCases: SignUpUseCases
) : ViewModel() {
}