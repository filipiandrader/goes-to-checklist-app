package com.far.goestochecklist.ui.components.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.R

/*
 * Created by Filipi Andrade Rocha on 31/03/2023.
 */

@Composable
fun GoesToChecklistText(
	modifier: Modifier,
	text: String,
	icon: Int
) {
	Row(modifier = modifier) {
		Image(
			modifier = Modifier
				.size(17.dp)
				.align(CenterVertically),
			painter = painterResource(id = icon),
			contentDescription = stringResource(id = R.string.content_description_icons_movie_info)
		)
		Spacer(modifier = Modifier.size(4.dp))
		Text(
			modifier = Modifier.align(CenterVertically),
			text = text,
			style = MaterialTheme.typography.body1
		)
	}
}