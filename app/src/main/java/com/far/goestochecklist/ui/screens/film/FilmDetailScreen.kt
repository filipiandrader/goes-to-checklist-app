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
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.far.goestochecklist.common.Constants.ALPHA_BACKGROUND
import com.far.goestochecklist.common.Constants.PROGRESS_VISIBILITY_ONLY_TITLE
import com.far.goestochecklist.common.Constants.PROGRESS_VISIBILITY_TITLES
import com.far.goestochecklist.common.getYearNumber
import com.far.goestochecklist.common.toDate
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import timber.log.Timber

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

			Row(
				modifier = Modifier
					.fillMaxWidth()
					.height(65.dp)
					.pin()
			) {
				Spacer(modifier = Modifier.size(8.dp))
				Box(
					modifier = Modifier
						.wrapContentSize()
						.align(CenterVertically)
						.pin()
						.clickable { navController.popBackStack() },
					contentAlignment = Alignment.Center
				) {
					androidx.compose.animation.AnimatedVisibility(
						visible = state.toolbarState.progress >= PROGRESS_VISIBILITY_TITLES,
						enter = fadeIn(animationSpec = tween(700)),
						exit = fadeOut(animationSpec = tween(700))
					) {
						Box(
							modifier = Modifier
								.size(40.dp)
								.clip(RoundedCornerShape(8.dp))
								.background(color = Gray900.copy(alpha = ALPHA_BACKGROUND))
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
				Box(
					modifier = Modifier
						.wrapContentSize()
						.padding(top = 2.dp, start = 8.dp)
						.align(CenterVertically)
						.pin()
						.road(Alignment.CenterStart, Alignment.BottomCenter),
					contentAlignment = Alignment.BottomCenter
				) {
					androidx.compose.animation.AnimatedVisibility(
						visible = state.toolbarState.progress <= PROGRESS_VISIBILITY_ONLY_TITLE,
						enter = fadeIn(animationSpec = tween(700)),
						exit = fadeOut(animationSpec = tween(700))
					) {
						Text(
							text = film.name,
							style = MaterialTheme.typography.h4
						)
					}
				}
			}

			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(65.dp)
					.road(Alignment.CenterStart, Alignment.BottomCenter),
				contentAlignment = Alignment.BottomCenter
			) {
				AnimatedVisibility(
					visible = state.toolbarState.progress >= PROGRESS_VISIBILITY_TITLES,
					enter = fadeIn(animationSpec = tween(700)),
					exit = fadeOut(animationSpec = tween(700))
				) {
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.height(55.dp)
							.background(color = Gray900.copy(alpha = ALPHA_BACKGROUND))
							.graphicsLayer { alpha = state.toolbarState.progress }
					)
					Column(
						modifier = Modifier
							.fillMaxWidth()
							.height(55.dp),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Spacer(modifier = Modifier.size(8.dp))
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
			}
		}
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(Gray900)
				.padding(16.dp)
				.verticalScroll(state = rememberScrollState())
		) {
			val year = film.releaseDate.toDate()?.getYearNumber().orEmpty()
			Text(
				text = stringResource(
					id = R.string.movie_info,
					year,
					film.duration,
					film.category[0]
				),
				style = MaterialTheme.typography.body1
			)
			Spacer(modifier = Modifier.size(16.dp))
			Text(
				text = "Sinopse:",
				style = MaterialTheme.typography.h4
			)
			Spacer(modifier = Modifier.size(4.dp))
			Text(
				text = film.description,
				style = MaterialTheme.typography.body1
			)
			Spacer(modifier = Modifier.size(16.dp))
			Text(
				text = "Categorias:",
				style = MaterialTheme.typography.h4
			)
			Spacer(modifier = Modifier.size(4.dp))
			var category = film.category[0]
			film.category.mapIndexed { index, cat ->
				if (index > 0) {
					category += ", $cat"
				}
			}
			Text(
				text = category,
				style = MaterialTheme.typography.body1
			)
			Spacer(modifier = Modifier.size(16.dp))
			Text(
				text = "Onde Assistir:",
				style = MaterialTheme.typography.h4
			)
			Spacer(modifier = Modifier.size(4.dp))
			var whereToWatch = film.whereToWatch[0].subSequence(0, 6).toString()
			film.whereToWatch.mapIndexed { index, link ->
				if (index > 0) {
					whereToWatch += ", ${link.subSequence(0, 6)}"
				}
			}
			Text(
				text = whereToWatch,
				style = MaterialTheme.typography.body1
			)
		}
	}
}