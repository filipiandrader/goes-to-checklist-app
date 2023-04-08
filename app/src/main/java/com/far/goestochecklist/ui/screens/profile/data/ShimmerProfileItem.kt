package com.far.goestochecklist.ui.screens.profile.data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.R
import com.far.goestochecklist.common.shimmerEffect
import com.far.goestochecklist.ui.components.button.GoesToChecklistButton

/*
 * Created by Filipi Andrade Rocha on 04/04/2023.
 */

@Composable
fun ShimmerProfileItem(
	modifier: Modifier,
	isLoading: Boolean,
	contentAfterLoading: @Composable () -> Unit
) {
	if (isLoading) {
		LazyColumn(modifier = modifier) {
			item {
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.height(70.dp)
						.shimmerEffect(RoundedCornerShape(4.dp))
						.clip(RoundedCornerShape(4.dp))
				)
				Spacer(modifier = Modifier.size(16.dp))
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.height(70.dp)
						.shimmerEffect(RoundedCornerShape(4.dp))
						.clip(RoundedCornerShape(4.dp))
				)
				Spacer(modifier = Modifier.size(16.dp))
				GoesToChecklistButton(
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp),
					buttonText = stringResource(id = R.string.edit_profile_button_text),
					isEnable = false,
					onClick = {}
				)
				Spacer(modifier = Modifier.size(8.dp))
				GoesToChecklistButton(
					modifier = Modifier
						.fillMaxWidth()
						.height(48.dp),
					buttonText = stringResource(id = R.string.logout_button_text),
					isEnable = false,
					onClick = {}
				)
			}
		}
	} else {
		contentAfterLoading()
	}
}