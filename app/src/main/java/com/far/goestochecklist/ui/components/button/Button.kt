package com.far.goestochecklist.ui.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.ui.theme.Gray200
import com.far.goestochecklist.ui.theme.Yellow

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@Composable
fun GoesToChecklistButton(
	modifier: Modifier,
	buttonText: String,
	buttonTextColor: Color = Color.White,
	buttonTextStyle: TextStyle = MaterialTheme.typography.button,
	onClick: () -> Unit,
	elevation: ButtonElevation? = ButtonDefaults.elevation(),
	color: ButtonColors = ButtonDefaults.buttonColors(
		backgroundColor = Yellow,
		disabledBackgroundColor = Gray200
	),
	isLoading: Boolean = false,
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
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(bottom = 2.dp)
		) {
			Text(
				modifier = Modifier.align(Alignment.Center),
				text = buttonText.uppercase(),
				color = buttonTextColor,
				style = buttonTextStyle,
				fontWeight = FontWeight.Bold
			)

			if (isLoading) {
				CircularProgressIndicator(
					modifier = Modifier
						.size(20.dp)
						.align(Alignment.CenterEnd),
					color = Color.White,
					strokeWidth = 2.dp
				)
			}
		}
	}
}