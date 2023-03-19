package com.far.goestochecklist.ui.components.textfield

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.R
import com.far.goestochecklist.common.isNotNullOrNotEmpty
import com.far.goestochecklist.ui.theme.Gray500
import com.far.goestochecklist.ui.theme.Yellow
import timber.log.Timber

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@Composable
fun GoesToChecklistTextField(
	modifier: Modifier,
	textFieldValue: String,
	onValueChange: (String) -> Unit,
	keyboardOptions: KeyboardOptions,
	keyboardActions: KeyboardActions,
	colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
		textColor = Yellow,
		leadingIconColor = Color.White,
		trailingIconColor = Color.White,
		focusedBorderColor = Yellow,
		unfocusedBorderColor = Gray500,
	),
	placeholder: @Composable (() -> Unit)? = null,
	leadingIcon: Int? = null,
	labelText: String? = null,
	labelTextStyle: TextStyle? = null,
	labelTextColor: Color? = null,
	passwordToggleOption: Boolean = false,
	isError: Boolean = false,
	errorMessage: String? = null
) {
	var passwordVisible by rememberSaveable { mutableStateOf(false) }

	Column(modifier = Modifier.wrapContentSize()) {
		if (labelText != null && labelTextStyle != null && labelTextColor != null) {
			Text(text = labelText, style = labelTextStyle, color = labelTextColor)
			Spacer(modifier = Modifier.size(8.dp))
		}

		val visualTransformation = when {
			passwordToggleOption && !passwordVisible -> PasswordVisualTransformation()
			else -> VisualTransformation.None
		}

		OutlinedTextField(
			modifier = modifier,
			value = textFieldValue,
			placeholder = placeholder,
			onValueChange = onValueChange,
			colors = colors,
			keyboardOptions = keyboardOptions,
			keyboardActions = keyboardActions,
			visualTransformation = visualTransformation,
			leadingIcon = {
				leadingIcon?.let { icon ->
					Icon(
						painter = painterResource(id = icon),
						contentDescription = stringResource(
							id = R.string.content_description_leading_icon
						)
					)
				}
			},
			trailingIcon = {
				if (passwordToggleOption) {
					val image = if (passwordVisible) {
						Icons.Filled.Visibility
					} else {
						Icons.Filled.VisibilityOff
					}

					val description = if (passwordVisible) {
						stringResource(id = R.string.content_description_text_field_show_password)
					} else {
						stringResource(id = R.string.content_description_text_field_show_password)
					}

					IconButton(onClick = { passwordVisible = !passwordVisible }) {
						Icon(imageVector = image, description)
					}
				}
			},
			textStyle = TextStyle.Default,
			isError = isError
		)
		if (isError && errorMessage.isNotNullOrNotEmpty()) {
			Spacer(modifier = Modifier.size(4.dp))
			Text(
				text = errorMessage.orEmpty().lowercase(),
				style = MaterialTheme.typography.caption,
				color = MaterialTheme.colors.error
			)
		}
	}
}