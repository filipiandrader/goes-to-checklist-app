package com.far.goestochecklist.ui.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.R
import com.far.goestochecklist.common.Constants
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow

/*
 * Created by Filipi Andrade Rocha on 28/04/2023.
 */

@Composable
fun GoesToChecklistButtonScrollToTop(
	modifier: Modifier = Modifier,
	onTopListener: () -> Unit
) {
	Box(modifier = modifier) {
		Button(
			modifier = Modifier.size(60.dp),
			colors = ButtonDefaults.buttonColors(
				backgroundColor = Gray900.copy(alpha = Constants.ALPHA_BACKGROUND)
			),
			onClick = { onTopListener.invoke() }
		) {
			Icon(
				modifier = Modifier
					.rotate(90f)
					.size(32.dp),
				painter = painterResource(id = R.drawable.ic_back_arrow_gray),
				contentDescription = stringResource(id = R.string.content_description_scroll_to_top_icon),
				tint = Yellow.copy(alpha = Constants.ALPHA_BACKGROUND)
			)
		}
	}
}