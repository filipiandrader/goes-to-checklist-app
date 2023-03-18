package com.far.goestochecklist.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@Composable
fun HomeScreen(
	navController: NavController
) {
	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Text(text = "HOME")
	}
} 