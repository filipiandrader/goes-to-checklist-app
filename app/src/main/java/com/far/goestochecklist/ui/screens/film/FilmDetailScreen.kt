package com.far.goestochecklist.ui.screens.film

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.far.goestochecklist.R
import com.far.goestochecklist.common.Constants.ALPHA_BACKGROUND
import com.far.goestochecklist.common.Constants.PROGRESS_VISIBILITY_ONLY_TITLE
import com.far.goestochecklist.common.Constants.PROGRESS_VISIBILITY_TITLES
import com.far.goestochecklist.common.getNameWhereToWatch
import com.far.goestochecklist.common.getYearNumber
import com.far.goestochecklist.common.isStartWithHttp
import com.far.goestochecklist.common.toDate
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.presentation.film.FilmDetailEvent.*
import com.far.goestochecklist.presentation.film.FilmDetailViewModel
import com.far.goestochecklist.ui.components.button.GoesToChecklistOutlinedButton
import com.far.goestochecklist.ui.components.text.GoesToChecklistText
import com.far.goestochecklist.ui.components.topics.GoesToChecklistTopic
import com.far.goestochecklist.ui.theme.Gray
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
	film: Film,
	viewModel: FilmDetailViewModel = hiltViewModel()
) {
	val state = rememberCollapsingToolbarScaffoldState()
	val uriHandler = LocalUriHandler.current

	var isWatched by remember { mutableStateOf(film.watched) }

	LaunchedEffect(key1 = true) {
		viewModel.filmDetailEventChannel.collect { event ->
			when (event) {
				is MarkWatchSuccess -> isWatched = !isWatched
				is MarkWatchError -> {
					// TODO SHOW DIALOG OR SNACKBAR WITH ERROR AND RETRY
				}
				else -> Unit
			}
		}
	}

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
				.verticalScroll(state = rememberScrollState())
		) {
			Spacer(modifier = Modifier.size(16.dp))
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.wrapContentHeight()
			) {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.weight(1.0f)
						.align(CenterVertically)
				) {
					GoesToChecklistText(
						modifier = Modifier
							.padding(start = 16.dp, end = 8.dp)
							.wrapContentSize()
							.align(CenterVertically),
						text = film.releaseDate,
						icon = R.drawable.ic_calendar_white
					)
					Text(
						modifier = Modifier.align(CenterVertically),
						text = stringResource(id = R.string.topic),
						style = MaterialTheme.typography.body1
					)
					GoesToChecklistText(
						modifier = Modifier
							.padding(start = 8.dp)
							.wrapContentSize()
							.align(CenterVertically),
						text = film.duration,
						icon = R.drawable.ic_clock_white
					)
				}
				Box(
					modifier = Modifier.weight(0.3f),
					contentAlignment = Alignment.Center
				) {
					val checkedIcon = when (isWatched) {
						true -> painterResource(id = R.drawable.ic_checked)
						false -> painterResource(id = R.drawable.ic_unchecked)
					}
					Image(
						modifier = Modifier
							.size(32.dp)
							.clickable { viewModel.onEvent(MarkWatchSubmit(film.filmId)) },
						painter = checkedIcon,
						contentDescription = stringResource(id = R.string.content_description_icon_movie_watched)
					)
				}
			}
			Spacer(modifier = Modifier.size(16.dp))
			Divider(
				modifier = Modifier
					.fillMaxWidth()
					.height(0.5.dp),
				color = Gray
			)
			Spacer(modifier = Modifier.size(16.dp))
			Text(
				modifier = Modifier.padding(start = 16.dp),
				text = stringResource(id = R.string.movie_info_description_label),
				style = MaterialTheme.typography.h4
			)
			Spacer(modifier = Modifier.size(8.dp))
			Text(
				modifier = Modifier.padding(horizontal = 16.dp),
				text = film.description,
				style = MaterialTheme.typography.body1
			)
			Spacer(modifier = Modifier.size(16.dp))
			Divider(
				modifier = Modifier
					.fillMaxWidth()
					.height(0.5.dp),
				color = Gray
			)
			Spacer(modifier = Modifier.size(16.dp))
			Text(
				modifier = Modifier.padding(start = 16.dp),
				text = stringResource(id = R.string.movie_info_category_label),
				style = MaterialTheme.typography.h4
			)
			Spacer(modifier = Modifier.size(8.dp))
			film.category.mapIndexed { index, category ->
				val modifier = when (index) {
					0 -> Modifier.padding(horizontal = 16.dp)
					else -> Modifier.padding(start = 16.dp, end = 16.dp, top = 6.dp)
				}
				GoesToChecklistTopic(
					modifier = modifier,
					topic = category
				)
			}
			if (film.whereToWatch.isNotEmpty()) {
				Spacer(modifier = Modifier.size(16.dp))
				Divider(
					modifier = Modifier
						.fillMaxWidth()
						.height(0.5.dp),
					color = Gray
				)
				Spacer(modifier = Modifier.size(16.dp))
				Text(
					modifier = Modifier.padding(start = 16.dp),
					text = stringResource(id = R.string.movie_info_where_to_watch_label),
					style = MaterialTheme.typography.h4
				)
				Spacer(modifier = Modifier.size(8.dp))
				LazyRow {
					items(film.whereToWatch.size) {
						val link = film.whereToWatch[it]
						val modifier = when (it) {
							0 -> Modifier
								.wrapContentSize()
								.padding(start = 16.dp)
							film.whereToWatch.size - 1 -> Modifier
								.wrapContentSize()
								.padding(start = 8.dp, end = 16.dp)
							else -> Modifier
								.wrapContentSize()
								.padding(start = 8.dp)
						}
						GoesToChecklistOutlinedButton(
							modifier = modifier,
							buttonText = link.getNameWhereToWatch(),
							isEnable = true,
							shape = CircleShape,
							onClick = { if (link.isStartWithHttp()) uriHandler.openUri(link) }
						)
					}
				}
			}
		}
	}
}