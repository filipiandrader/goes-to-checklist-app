package com.far.goestochecklist.ui.screens.welcome

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.ui.components.GoesToChecklistButton
import com.far.goestochecklist.ui.components.GoesToChecklistOutlinedButton
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.navigation.doNavigation
import com.far.goestochecklist.ui.theme.Gray900

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@Composable
fun WelcomeScreen(
	navController: NavController,
) {
	val context = LocalContext.current

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
					contentDescription = "LOGO"
				)
				val textPaintStroke = Paint().asFrameworkPaint().apply {
					isAntiAlias = true
					style = android.graphics.Paint.Style.STROKE
					textSize = 130f
					color = android.graphics.Color.BLACK
					strokeWidth = 8f
					strokeMiter = 8f
					strokeJoin = android.graphics.Paint.Join.ROUND
					typeface = ResourcesCompat.getFont(context, R.font.nexa_black)
				}

				val textPaint = Paint().asFrameworkPaint().apply {
					isAntiAlias = true
					style = android.graphics.Paint.Style.FILL
					textSize = 130f
					color = android.graphics.Color.WHITE
					typeface = ResourcesCompat.getFont(context, R.font.nexa_black)
				}
				Canvas(
					modifier = Modifier
						.fillMaxSize(),
					onDraw = {
						drawIntoCanvas {
							it.nativeCanvas.drawText(
								"WELCOME",
								200f,
								220.dp.toPx(),
								textPaintStroke
							)
							it.nativeCanvas.drawText(
								"WELCOME",
								200f,
								220.dp.toPx(),
								textPaint
							)
						}
					}
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
						buttonText = "LOGIN",
						isEnable = true,
						onClick = { doNavigation(Routes.Login, navController) }
					)
					Spacer(modifier = Modifier.size(16.dp))
					GoesToChecklistOutlinedButton(
						modifier = Modifier
							.fillMaxWidth()
							.height(48.dp),
						buttonText = "CREATE ACCOUNT",
						isEnable = true,
						onClick = { doNavigation(Routes.SignUp, navController) }
					)
				}
			}
		}
	}
}