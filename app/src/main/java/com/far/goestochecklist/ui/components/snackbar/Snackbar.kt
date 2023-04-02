package com.far.goestochecklist.ui.components.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
					.padding(8.dp)
					.fillMaxWidth()
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
						style = MaterialTheme.typography.body1,
						color = snackbarTextColor
					)
					snackbarContent?.let {
						Spacer(modifier = Modifier.size(4.dp))
						Text(
							text = snackbarContent,
							style = MaterialTheme.typography.body2,
							fontWeight = FontWeight.Normal,
							color = snackbarTextColor
						)
					}
				}
				Spacer(modifier = Modifier.size(4.dp))
				Box(
					modifier = Modifier
						.fillMaxSize()
						.align(CenterVertically)
						.padding(top = 2.dp)
						.weight(0.6f),
					contentAlignment = Alignment.Center
				) {
					snackbarActionText?.let {
						Text(
							modifier = Modifier.clickable { onActionListener.invoke() },
							text = it.uppercase(),
							style = MaterialTheme.typography.caption.copy(color = Yellow)
						)
					}
				}
			}
		}
	}
}