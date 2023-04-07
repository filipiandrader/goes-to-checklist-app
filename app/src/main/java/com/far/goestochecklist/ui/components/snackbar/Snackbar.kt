package com.far.goestochecklist.ui.components.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.ui.theme.Gray800
import com.far.goestochecklist.ui.theme.Yellow

@Composable
fun GoesToChecklistSnackbar(
	modifier: Modifier,
	snackbarTitle: String,
	snackbarContent: String? = null,
	snackbarActionText: String? = null,
	snackbarBackground: Color = Gray800,
	snackbarTextColor: Color = Color.White,
	onActionListener: () -> Unit
) {
	Card(
		modifier = modifier,
		shape = RoundedCornerShape(10.dp),
		elevation = 4.dp
	) {
		Box(modifier = Modifier.background(snackbarBackground)) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp)
			) {
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.wrapContentHeight()
						.align(CenterVertically)
						.weight(1.0f)
				) {
					Text(
						text = snackbarTitle,
						style = MaterialTheme.typography.body2,
						color = snackbarTextColor
					)
					snackbarContent?.let {
						Spacer(modifier = Modifier.size(4.dp))
						Text(
							text = snackbarContent,
							style = MaterialTheme.typography.caption,
							fontWeight = FontWeight.Normal,
							color = snackbarTextColor
						)
					}
				}
				Spacer(modifier = Modifier.size(4.dp))
				Box(
					modifier = Modifier
						.wrapContentSize()
						.align(CenterVertically)
						.weight(0.5f)
				) {
					snackbarActionText?.let {
						Text(
							modifier = Modifier.clickable { onActionListener.invoke() },
							text = it.uppercase(),
							style = MaterialTheme.typography.overline.copy(color = Yellow)
						)
					}
				}
			}
		}
	}
}