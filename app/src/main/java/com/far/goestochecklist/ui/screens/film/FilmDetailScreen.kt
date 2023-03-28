package com.far.goestochecklist.ui.screens.film

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.far.goestochecklist.R
import com.far.goestochecklist.common.getYearNumber
import com.far.goestochecklist.common.toDate
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/*
 * Created by Filipi Andrade Rocha on 26/03/2023.
 */

@Composable
fun FilmDetailScreen(
	navController: NavController,
	film: Film
) {
	val state = rememberCollapsingToolbarScaffoldState()

	CollapsingToolbarScaffold(
		modifier = Modifier.fillMaxSize(),
		state = state,
		scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
		toolbar = {
			Box(
				modifier = Modifier
					.wrapContentSize()
					.background(color = Yellow)
			) {
				Image(
					modifier = Modifier
						.fillMaxWidth()
						.height(215.dp)
						.parallax(0.5f)
						.graphicsLayer { alpha = state.toolbarState.progress },
					painter = rememberAsyncImagePainter(model = film.posterImage),
					contentDescription = stringResource(id = R.string.content_description_movie_poster),
					contentScale = ContentScale.FillBounds
				)
			}

			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(65.dp)
					.road(Alignment.CenterStart, Alignment.BottomCenter)
			) {
				AnimatedVisibility(
					visible = state.toolbarState.progress > 0.5,
					enter = fadeIn(animationSpec = tween(700)),
					exit = fadeOut(animationSpec = tween(700))
				) {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.background(color = Gray900.copy(alpha = 0.6f))
							.graphicsLayer { alpha = state.toolbarState.progress }
					)
				}
				Column(
					modifier = Modifier
						.fillMaxSize()
						.wrapContentHeight(),
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Spacer(modifier = Modifier.size(14.dp))
					Text(
						modifier = Modifier.align(Alignment.CenterHorizontally),
						text = film.name,
						style = MaterialTheme.typography.h4
					)
					Spacer(modifier = Modifier.size(4.dp))
					val year = film.releaseDate.toDate()?.getYearNumber().orEmpty()
					Text(
						modifier = Modifier.align(Alignment.CenterHorizontally),
						text = stringResource(
							id = R.string.movie_info,
							year,
							film.duration,
							film.category[0]
						),
						style = MaterialTheme.typography.body1,
						fontWeight = FontWeight.Light
					)
					Spacer(modifier = Modifier.size(16.dp))
				}
			}

			Box(
				modifier = Modifier
					.padding(8.dp)
					.pin()
					.clickable { navController.popBackStack() },
				contentAlignment = Alignment.Center
			) {
				AnimatedVisibility(
					visible = state.toolbarState.progress > 0.5,
					enter = fadeIn(animationSpec = tween(700)),
					exit = fadeOut(animationSpec = tween(700))
				) {
					Box(
						modifier = Modifier
							.size(40.dp)
							.clip(RoundedCornerShape(8.dp))
							.background(color = Gray900.copy(alpha = 0.6f))
							.graphicsLayer { alpha = state.toolbarState.progress }
					)
				}
				Box(modifier = Modifier.size(40.dp))
				Image(
					modifier = Modifier.size(28.dp),
					painter = painterResource(id = R.drawable.ic_close_white),
					contentDescription = stringResource(id = R.string.content_description_toolbar_button)
				)
			}
		}
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(Gray900)
				.verticalScroll(state = rememberScrollState())
		) {
			repeat(100) {
				Text(
					text = "Item $it",
					modifier = Modifier.padding(8.dp)
				)
			}
		}
	}
}