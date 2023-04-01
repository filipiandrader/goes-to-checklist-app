package com.far.goestochecklist.ui.components.topics

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.far.goestochecklist.R

/*
 * Created by Filipi Andrade Rocha on 30/03/2023.
 */

@Composable
fun GoesToChecklistTopic(
	modifier: Modifier,
	topic: String
) {
	Column(modifier = modifier) {
		Text(
			text = stringResource(id = R.string.topic_info, topic),
			style = MaterialTheme.typography.body1
		)
	}
}