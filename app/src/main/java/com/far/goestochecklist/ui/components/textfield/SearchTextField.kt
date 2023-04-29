package com.far.goestochecklist.ui.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.R
import com.far.goestochecklist.ui.theme.Gray800

/*
 * Created by Filipi Andrade Rocha on 13/04/2023.
 */

@Composable
fun GoesToChecklistSearchTextField(
	modifier: Modifier,
	textValue: String,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	onValueChange: (String) -> Unit,
	onFilterClickListener: () -> Unit
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(50.dp)
			.background(Gray800, RoundedCornerShape(50.dp))
	) {
		Row(
			modifier = modifier,
			verticalAlignment = Alignment.CenterVertically
		) {
			Spacer(modifier = Modifier.size(2.dp))
			Box(
				modifier = Modifier
					.size(45.dp)
					.weight(0.35f)
					.border(
						width = 2.dp,
						color = Color.White,
						shape = RoundedCornerShape(40.dp)
					),
				contentAlignment = Alignment.Center
			) {
				Icon(
					modifier = Modifier.size(35.dp),
					painter = painterResource(id = R.drawable.ic_search),
					contentDescription = stringResource(id = R.string.content_description_search_icon),
					tint = Color.White
				)
			}
			Spacer(modifier = Modifier.size(4.dp))
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.weight(2f)
			) {
				Spacer(modifier = Modifier.size(2.dp))
				TextField(
					modifier = Modifier.fillMaxSize(),
					value = textValue,
					onValueChange = onValueChange,
					keyboardOptions = keyboardOptions,
					keyboardActions = keyboardActions,
					placeholder = {
						Text(
							text = stringResource(id = R.string.search_text_field_placeholder),
							style = MaterialTheme.typography.body2
						)
					},
					colors = TextFieldDefaults.textFieldColors(
						backgroundColor = Color.Transparent,
						cursorColor = Color.White,
						focusedIndicatorColor = Color.Transparent,
						unfocusedIndicatorColor = Color.Transparent,
					)
				)
			}
			Spacer(modifier = Modifier.size(4.dp))
			Box(
				modifier = Modifier
					.size(45.dp)
					.weight(0.35f),
				contentAlignment = Alignment.Center
			) {
				Icon(
					modifier = Modifier
						.size(32.dp)
						.clickable { onFilterClickListener.invoke() },
					painter = painterResource(id = R.drawable.ic_filter),
					contentDescription = stringResource(id = R.string.content_description_filter_icon),
					tint = Color.White
				)
			}
			Spacer(modifier = Modifier.size(4.dp))
		}
	}
}