package com.far.goestochecklist.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.far.goestochecklist.common.isNotNullOrNotEmpty
import com.far.goestochecklist.ui.components.button.GoesToChecklistButton
import com.far.goestochecklist.ui.components.button.GoesToChecklistOutlinedButton
import com.far.goestochecklist.ui.theme.Gray900

/*
 * Created by Filipi Andrade Rocha on 20/03/2023.
 */

@Composable
fun GoesToChecklistDialog(
	modifier: Modifier,
	title: String? = null,
	textContent: String? = null,
	positiveText: String,
	onPositiveClick: () -> Unit,
	negativeText: String? = null,
	onNegativeClick: () -> Unit = {},
	onDismiss: () -> Unit = {}
) {
	Dialog(onDismissRequest = onDismiss) {
		Card(
			elevation = 8.dp,
			shape = RoundedCornerShape(12.dp)
		) {
			Box(
				modifier = Modifier
					.wrapContentSize()
					.background(Gray900)
			) {
				Column(
					modifier = modifier,
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					if (title.isNotNullOrNotEmpty()) {
						Text(
							modifier = Modifier.align(Alignment.CenterHorizontally),
							text = title.orEmpty(),
							style = MaterialTheme.typography.h3,
						)
					}

					if (title.isNotNullOrNotEmpty() && textContent.isNotNullOrNotEmpty()) {
						Spacer(modifier = Modifier.size(4.dp))
					}

					if (textContent.isNotNullOrNotEmpty()) {
						Text(
							text = textContent.orEmpty(),
							style = MaterialTheme.typography.h4,
							fontWeight = FontWeight.Normal,
							textAlign = TextAlign.Center
						)
						Spacer(modifier = Modifier.size(24.dp))
					}

					GoesToChecklistButton(
						modifier = Modifier
							.width(300.dp)
							.height(40.dp),
						buttonText = positiveText,
						isEnable = true,
						onClick = onPositiveClick
					)

					if (negativeText.isNotNullOrNotEmpty()) {
						Spacer(modifier = Modifier.size(8.dp))
						GoesToChecklistOutlinedButton(
							modifier = Modifier
								.width(300.dp)
								.height(40.dp),
							buttonText = negativeText.orEmpty(),
							isEnable = true,
							onClick = onNegativeClick
						)
					}
				}
			}
		}
	}
}