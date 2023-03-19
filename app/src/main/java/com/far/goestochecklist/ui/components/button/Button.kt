package com.far.goestochecklist.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.ui.theme.Yellow

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@Composable
fun GoesToChecklistButton(
	modifier: Modifier,
	buttonText: String,
	buttonTextColor: Color = Color.White,
	onClick: () -> Unit,
	elevation: ButtonElevation? = ButtonDefaults.elevation(),
	color: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Yellow),
	isEnable: Boolean = false
) {
	Button(
		modifier = modifier,
		colors = color,
		onClick = onClick,
		enabled = isEnable,
		elevation = elevation,
		shape = RoundedCornerShape(8.dp)
	) {
		Box(modifier = Modifier.fillMaxSize()) {
			Text(
				modifier = Modifier.align(Alignment.Center),
				text = buttonText.uppercase(),
				textAlign = TextAlign.Center,
				color = buttonTextColor,
				style = MaterialTheme.typography.button
			)
		}
	}
}