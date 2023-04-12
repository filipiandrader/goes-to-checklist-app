package com.far.goestochecklist.ui.components.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.far.goestochecklist.R

/*
 * Created by Filipi Andrade Rocha on 11/04/2023.
 */

@Composable
fun GoesToChecklistMenuItem(
	modifier: Modifier,
	icon: Int,
	title: String
) {
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically
	) {
		Spacer(modifier = Modifier.size(16.dp))
		Icon(
			modifier = Modifier.size(25.dp),
			painter = painterResource(id = icon),
			contentDescription = "",
			tint = Color.White
		)
		Spacer(modifier = Modifier.size(16.dp))
		Text(text = title, style = MaterialTheme.typography.body2)
		Spacer(modifier = Modifier.size(16.dp))
	}
}