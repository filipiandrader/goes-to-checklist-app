package com.far.goestochecklist.ui.signup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

/*
 * Created by Filipi Andrade Rocha on 28/02/2023.
 */

@Composable
fun SignUpScreen(
    navController: NavController,
) {
	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Text(text = "SIGN UP")
	}
}
