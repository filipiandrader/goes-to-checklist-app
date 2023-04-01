package com.far.goestochecklist.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.common.shimmerEffect

/*
 * Created by Filipi Andrade Rocha on 26/03/2023.
 */

@Composable
fun ShimmerHomeItem(
	modifier: Modifier,
	isLoading: Boolean,
	contentAfterLoading: @Composable () -> Unit
) {
	if (isLoading) {
		LazyColumn(modifier = modifier) {
			items(4) {
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.height(200.dp)
						.padding(horizontal = 4.dp, vertical = 4.dp)
						.shimmerEffect(RoundedCornerShape(12.dp))
						.clip(RoundedCornerShape(12.dp))
				)
			}
		}
	} else {
		contentAfterLoading()
	}

}