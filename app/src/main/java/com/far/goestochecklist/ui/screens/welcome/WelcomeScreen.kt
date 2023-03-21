package com.far.goestochecklist.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.ui.components.button.GoesToChecklistButton
import com.far.goestochecklist.ui.components.button.GoesToChecklistOutlinedButton
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.navigation.doNavigation
import com.far.goestochecklist.ui.theme.Gray900

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@Composable
fun WelcomeScreen(
	navController: NavController
) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Gray900)
	) {
		Column {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.weight(1.0f)
					.paint(
						painterResource(id = R.drawable.ic_yellow_background),
						contentScale = ContentScale.FillBounds
					),
				contentAlignment = Alignment.Center
			) {
				Image(
					painter = painterResource(id = R.drawable.ic_logo),
					contentDescription = stringResource(id = R.string.content_description_logo)
				)
			}

			Box(
				modifier = Modifier
					.fillMaxSize()
					.weight(0.7f),
				contentAlignment = Alignment.Center
			) {
				Column(
					modifier = Modifier
						.padding(horizontal = 16.dp)
				) {
					GoesToChecklistButton(
						modifier = Modifier
							.fillMaxWidth()
							.height(48.dp),
						buttonText = stringResource(id = R.string.login_label),
						isEnable = true,
						onClick = { doNavigation(Routes.Login, navController) }
					)
					Spacer(modifier = Modifier.size(16.dp))
					GoesToChecklistOutlinedButton(
						modifier = Modifier
							.fillMaxWidth()
							.height(48.dp),
						buttonText = stringResource(id = R.string.create_account_label),
						isEnable = true,
						onClick = { doNavigation(Routes.SignUp, navController) }
					)
				}
			}
		}
	}
}