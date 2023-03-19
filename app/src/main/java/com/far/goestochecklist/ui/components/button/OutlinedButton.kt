package com.far.goestochecklist.ui.components

import androidx.compose.foundation.BorderStroke
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
import com.far.goestochecklist.ui.theme.Gray700
import com.far.goestochecklist.ui.theme.Yellow

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@Composable
fun GoesToChecklistOutlinedButton(
	modifier: Modifier,
	buttonText: String,
	buttonTextColor: Color = Yellow,
	onClick: () -> Unit,
	color: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
	isEnable: Boolean = false
) {
	val borderColor = if (isEnable) Color.White else Gray700
	val textColor = if (isEnable) buttonTextColor else Gray700

	OutlinedButton(
		modifier = modifier,
		colors = color,
		onClick = onClick,
		enabled = isEnable,
		border = BorderStroke(1.5.dp, borderColor),
		shape = RoundedCornerShape(8.dp)
	) {
		Box(modifier = Modifier.fillMaxSize()) {
			Text(
				modifier = Modifier.align(Alignment.Center),
				text = buttonText.uppercase(),
				textAlign = TextAlign.Center,
				color = textColor,
				style = MaterialTheme.typography.button
			)
		}
	}
}