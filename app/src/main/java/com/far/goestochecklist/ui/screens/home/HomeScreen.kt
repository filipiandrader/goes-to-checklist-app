package com.far.goestochecklist.ui.screens.home

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.common.Constants.FILM_QUERY_NAME
import com.far.goestochecklist.common.OnLifecycleEvent
import com.far.goestochecklist.domain.exception.DataSourceException
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.model.Year
import com.far.goestochecklist.presentation.home.HomeEvent.*
import com.far.goestochecklist.presentation.home.HomeViewModel
import com.far.goestochecklist.ui.components.dialog.GoesToChecklistDialog
import com.far.goestochecklist.ui.components.dialog.GoesToChecklistSingleChoiceDialog
import com.far.goestochecklist.ui.components.emptylist.GoesToChecklistEmptyList
import com.far.goestochecklist.ui.components.snackbar.GoesToChecklistSnackbar
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.navigation.doNavigation
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: HomeViewModel = hiltViewModel()
) {

	var userInfo by remember { mutableStateOf<Login?>(null) }
	var yearsList by remember { mutableStateOf(listOf<Year>()) }
	var filmsList by remember { mutableStateOf(listOf<Film>()) }
	var yearPicked by remember { mutableStateOf("") }
	var isLoading by remember { mutableStateOf(true) }
	var isToUpdate by remember { mutableStateOf(false) }
	var showYearPickDialog by remember { mutableStateOf(false) }
	var showGetYearError by remember { mutableStateOf(false) }
	var showGetFilmError by remember { mutableStateOf(false) }
	var showMarkWatchedError by remember { mutableStateOf(false) }
	var errorMessage by remember { mutableStateOf("") }
	var filmIdToMarkWatched by remember { mutableStateOf("") }
	val hasSomeError = showGetYearError || showGetFilmError || showMarkWatchedError

	OnLifecycleEvent { _, event ->
		when (event) {
			Lifecycle.Event.ON_RESUME -> viewModel.onEvent(GetUserSubmit)
			else -> Unit
		}
	}

	LaunchedEffect(key1 = true) {
		viewModel.homeEventChannel.collect { event ->
			when (event) {
				is GetUserSuccess -> {
					userInfo = event.user
					viewModel.onEvent(GetYearSubmit)
				}
				is GetYearSuccess -> {
					yearsList = event.years
					if (yearPicked.isEmpty()) {
						yearPicked = yearsList[yearsList.size - 1].year
					}
					viewModel.onEvent(GetFilmSubmit(yearPicked))
				}
				is GetYearError -> {
					showGetYearError = true
					showGetFilmError = false
					showMarkWatchedError = false
					isLoading = false
					errorMessage = if (event.throwable is DataSourceException) {
						event.throwable.code.plus(": ")
							.plus(event.throwable.message.orEmpty().lowercase())
					} else {
						event.throwable.message.orEmpty()
					}
				}
				is GetFilmSuccess -> {
					filmsList = event.films
					isLoading = false
				}
				is GetFilmError -> {
					showGetYearError = false
					showGetFilmError = true
					showMarkWatchedError = false
					isLoading = false
					errorMessage = if (event.throwable is DataSourceException) {
						event.throwable.code.plus(": ")
							.plus(event.throwable.message.orEmpty().lowercase())
					} else {
						event.throwable.message.orEmpty()
					}
				}
				is MarkWatchSuccess -> {
					filmIdToMarkWatched = ""
					filmsList.map {
						if (it.filmId == event.filmId) {
							it.watched = !it.watched
							isToUpdate = !isToUpdate
						}
					}
				}
				is MarkWatchError -> {
					showGetYearError = false
					showGetFilmError = false
					showMarkWatchedError = true
					isLoading = false
				}
				else -> Unit
			}
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Gray900)
	) {
		Column {
			TopAppBar(
				modifier = Modifier
					.fillMaxWidth()
					.height(70.dp),
				backgroundColor = Yellow,
			) {
				Row(
					modifier = Modifier
						.fillMaxSize()
						.padding(16.dp)
						.weight(1.0f)
				) {
					Text(
						text = stringResource(
							id = R.string.hello,
							userInfo?.name.orEmpty()
						),
						style = MaterialTheme.typography.body1,
						fontWeight = FontWeight.Normal
					)
					if (yearsList.isNotEmpty()) {
						Box(
							modifier = Modifier.fillMaxWidth(),
							contentAlignment = CenterEnd
						) {
							Text(
								modifier = Modifier.clickable { showYearPickDialog = true },
								text = stringResource(id = R.string.year, yearPicked),
								style = MaterialTheme.typography.body1,
								fontWeight = FontWeight.Normal
							)
						}
					}
				}
				Box(
					modifier = Modifier
						.wrapContentWidth()
						.fillMaxHeight()
						.weight(0.15f)
				) {
					Image(
						modifier = Modifier
							.size(40.dp)
							.clickable {
								filmIdToMarkWatched = ""
								showMarkWatchedError = false
								/* TODO TELA DE PERFIL */
							},
						painter = painterResource(id = R.drawable.ic_profile),
						contentDescription = stringResource(id = R.string.content_description_profile_picture)
					)
				}
			}

			ShimmerHomeItem(
				modifier = Modifier
					.fillMaxSize()
					.padding(horizontal = 4.dp, vertical = 4.dp),
				isLoading = isLoading,
				contentAfterLoading = {
					if (filmsList.isEmpty() && !hasSomeError) {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Center
						) {
							GoesToChecklistEmptyList(
								modifier = Modifier.wrapContentSize(),
								title = stringResource(id = R.string.home_empty_film_list_title),
								subtitle = stringResource(id = R.string.home_empty_film_list_subtitle),
								icon = R.drawable.ic_no_documents
							)
						}
					} else {
						HomeItem(
							modifier = Modifier
								.fillMaxSize()
								.padding(horizontal = 4.dp, vertical = 4.dp),
							films = filmsList,
							update = isToUpdate,
							onClickItemListener = {
								filmIdToMarkWatched = ""
								showMarkWatchedError = false
								val bundle = Bundle()
								bundle.putParcelable(FILM_QUERY_NAME, it)
								doNavigation(Routes.FilmDetail, navController, bundle)
							},
							onMarkWatchedListener = {
								filmIdToMarkWatched = it.filmId
								showMarkWatchedError = false
								viewModel.onEvent(MarkWatchSubmit(filmIdToMarkWatched))
							}
						)
					}
				}
			)

			if (showGetYearError) {
				GoesToChecklistDialog(
					modifier = Modifier
						.width(450.dp)
						.wrapContentHeight()
						.padding(horizontal = 16.dp, vertical = 24.dp),
					textContent = errorMessage,
					positiveText = stringResource(id = R.string.try_again),
					onPositiveClick = {
						isLoading = true
						viewModel.onEvent(GetYearSubmit)
						showGetYearError = false
						showGetFilmError = false
						showMarkWatchedError = false
					}
				)
			}

			if (showGetFilmError) {
				GoesToChecklistDialog(
					modifier = Modifier
						.width(450.dp)
						.wrapContentHeight()
						.padding(horizontal = 16.dp, vertical = 24.dp),
					textContent = errorMessage,
					positiveText = stringResource(id = R.string.try_again),
					onPositiveClick = {
						isLoading = true
						viewModel.onEvent(GetFilmSubmit(yearPicked))
						showGetYearError = false
						showGetFilmError = false
						showMarkWatchedError = false
					}
				)
			}

			if (showYearPickDialog) {
				GoesToChecklistSingleChoiceDialog(
					modifier = Modifier
						.width(450.dp)
						.wrapContentHeight()
						.padding(horizontal = 16.dp, vertical = 24.dp),
					textContent = stringResource(id = R.string.pick_a_year),
					options = yearsList.map { it.year },
					selectedOption = yearPicked,
					positiveText = stringResource(id = R.string.ok),
					onPositiveClick = {
						filmIdToMarkWatched = ""
						showMarkWatchedError = false
						isLoading = true
						yearPicked = it
						viewModel.onEvent(GetFilmSubmit(yearPicked))
						showYearPickDialog = false
					},
					negativeText = stringResource(id = R.string.close),
					onNegativeClick = { showYearPickDialog = false }
				)
			}
		}

		if (showMarkWatchedError) {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.padding(10.dp)
					.align(BottomCenter)
			) {
				GoesToChecklistSnackbar(
					modifier = Modifier
						.fillMaxWidth()
						.height(58.dp),
					snackbarTitle = stringResource(id = R.string.home_mark_watched_error),
					snackbarActionText = stringResource(id = R.string.try_again)
				) {
					showMarkWatchedError = false
					if (filmIdToMarkWatched.isNotEmpty()) {
						viewModel.onEvent(MarkWatchSubmit(filmIdToMarkWatched))
					}
				}
			}
		}
	}
}