package com.far.goestochecklist.ui.components.emptylist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.common.isNotNullOrNotEmpty
import com.far.goestochecklist.ui.components.button.GoesToChecklistButton
import com.far.goestochecklist.ui.theme.Gray800

/*
 * Created by Filipi Andrade Rocha on 31/03/2023.
 */

@Composable
fun GoesToChecklistEmptyList(
	modifier: Modifier,
	title: String,
	subtitle: String? = null,
	buttonTitle: String? = null,
	buttonAction: () -> Unit = {},
	icon: Int? = null
) {
	Column(modifier = modifier) {
		if (icon != null) {
			Box(
				modifier = Modifier
					.size(100.dp)
					.background(color = Gray800, shape = CircleShape)
					.align(Alignment.CenterHorizontally),
				contentAlignment = Alignment.Center
			) {
				Image(
					modifier = Modifier.size(58.dp),
					painter = painterResource(id = icon),
					contentDescription = ""
				)
			}
			Spacer(modifier = Modifier.size(8.dp))
		}
		Text(
			modifier = Modifier.align(Alignment.CenterHorizontally),
			text = title,
			style = MaterialTheme.typography.h4,
			textAlign = TextAlign.Center
		)
		if (subtitle.isNotNullOrNotEmpty()) {
			Spacer(modifier = Modifier.size(4.dp))
			Text(
				modifier = Modifier.align(Alignment.CenterHorizontally),
				text = subtitle.orEmpty(),
				style = MaterialTheme.typography.body1,
				fontWeight = FontWeight.Normal,
				textAlign = TextAlign.Center
			)
		}
		if (buttonTitle.isNotNullOrNotEmpty()) {
			Spacer(modifier = Modifier.size(16.dp))
			GoesToChecklistButton(
				modifier = Modifier
					.width(180.dp)
					.height(40.dp)
					.align(Alignment.CenterHorizontally),
				buttonText = buttonTitle.orEmpty(),
				isEnable = true,
				onClick = { buttonAction.invoke() }
			)
		}
	}
}