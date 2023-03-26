package com.far.goestochecklist.ui.components.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
fun GoesToChecklistSingleChoiceDialog(
	modifier: Modifier,
	title: String? = null,
	textContent: String? = null,
	options: List<String>,
	selectedOption: String,
	positiveText: String,
	onPositiveClick: (String) -> Unit,
	negativeText: String? = null,
	onNegativeClick: () -> Unit = {},
	onDismiss: () -> Unit = {}
) {

	val (selectedValue, onOptionSelected) = remember { mutableStateOf(selectedOption) }

	Dialog(onDismissRequest = onDismiss) {
		Card(
			elevation = 8.dp,
			shape = RoundedCornerShape(12.dp)
		) {
			Column(
				modifier = modifier,
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Spacer(modifier = Modifier.size(24.dp))
				if (title.isNotNullOrNotEmpty()) {
					Text(
						modifier = Modifier.align(Alignment.CenterHorizontally),
						text = title.orEmpty(),
						style = MaterialTheme.typography.h3.copy(color = Gray900),
					)
				}

				if (textContent.isNotNullOrNotEmpty()) {
					Spacer(modifier = Modifier.size(4.dp))
					Text(
						text = textContent.orEmpty(),
						style = MaterialTheme.typography.body1.copy(color = Gray900),
						fontWeight = FontWeight.Normal,
						textAlign = TextAlign.Center
					)
					Spacer(modifier = Modifier.size(8.dp))
				}

				LazyColumn(
					modifier = Modifier
						.fillMaxWidth()
						.wrapContentHeight(),
				) {
					items(items = options) { text ->
						Row(
							modifier = Modifier
								.fillMaxWidth()
								.wrapContentHeight()
								.selectable(
									selected = (text == selectedValue),
									onClick = { onOptionSelected(text) }
								)
						) {
							RadioButton(
								modifier = Modifier.align(CenterVertically),
								selected = (text == selectedValue),
								onClick = { onOptionSelected(text) }
							)
							Text(
								modifier = Modifier.align(CenterVertically),
								text = text,
								style = MaterialTheme.typography.body1.copy(color = Gray900)
							)
						}
					}
				}

				Spacer(modifier = Modifier.size(8.dp))

				GoesToChecklistButton(
					modifier = Modifier
						.width(300.dp)
						.height(40.dp),
					buttonText = positiveText,
					isEnable = true,
					onClick = { onPositiveClick.invoke(selectedValue) }
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
				Spacer(modifier = Modifier.size(24.dp))
			}
		}
	}
}