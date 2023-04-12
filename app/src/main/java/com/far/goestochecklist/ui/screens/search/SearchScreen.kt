package com.far.goestochecklist.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.ui.theme.Gray800
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow

/*
 * Created by Filipi Andrade Rocha on 10/04/2023.
 */

@Composable
fun SearchScreen(
	bottomNavController: NavController
) {

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Gray900)
	) {
		Column(modifier = Modifier.fillMaxSize()) {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.height(130.dp)
					.background(Yellow)
					.padding(16.dp)
			) {
				Spacer(modifier = Modifier.size(32.dp))
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.height(50.dp)
						.background(Gray800, RoundedCornerShape(50.dp))
				) {
					Row(
						modifier = Modifier.fillMaxSize(),
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
								contentDescription = "",
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
							var text by remember { mutableStateOf("") }
							TextField(
								modifier = Modifier.fillMaxSize(),
								value = text,
								onValueChange = { text = it },
								placeholder = {
									Text(
										text = "Pesquisar filme...",
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
								modifier = Modifier.size(32.dp),
								painter = painterResource(id = R.drawable.ic_filter),
								contentDescription = "",
								tint = Color.White
							)
						}
						Spacer(modifier = Modifier.size(4.dp))
					}
				}
			}
		}
	}

}