@file:OptIn(ExperimentalMaterialApi::class)

package com.far.goestochecklist.ui.screens.search

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.common.Constants
import com.far.goestochecklist.common.OnLifecycleEvent
import com.far.goestochecklist.common.formatErrorMessage
import com.far.goestochecklist.domain.exception.DataSourceException
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.model.Filter
import com.far.goestochecklist.presentation.search.SearchEvent.*
import com.far.goestochecklist.presentation.search.SearchViewModel
import com.far.goestochecklist.ui.components.button.GoesToChecklistButton
import com.far.goestochecklist.ui.components.button.GoesToChecklistOutlinedButton
import com.far.goestochecklist.ui.components.dialog.GoesToChecklistDialog
import com.far.goestochecklist.ui.components.emptylist.GoesToChecklistEmptyList
import com.far.goestochecklist.ui.components.snackbar.GoesToChecklistSnackbar
import com.far.goestochecklist.ui.components.textfield.GoesToChecklistSearchTextField
import com.far.goestochecklist.ui.navigation.Routes
import com.far.goestochecklist.ui.navigation.doNavigation
import com.far.goestochecklist.ui.components.filmitem.FilmItem
import com.far.goestochecklist.ui.screens.home.ShimmerHomeItem
import com.far.goestochecklist.ui.theme.Gray700
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow
import kotlinx.coroutines.launch

/*
 * Created by Filipi Andrade Rocha on 10/04/2023.
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
	bottomNavController: NavController,
	viewModel: SearchViewModel = hiltViewModel()
) {

	var filmName by remember { mutableStateOf("") }
	var year by remember { mutableStateOf("") }
	var category by remember { mutableStateOf("") }
	var filters by remember { mutableStateOf<Filter?>(null) }
	var filmsList by remember { mutableStateOf(listOf<Film>()) }
	var isLoading by remember { mutableStateOf(false) }
	var isToUpdate by remember { mutableStateOf(false) }
	var isToShowShimmer by remember { mutableStateOf(false) }
	var filmIdToMarkWatched by remember { mutableStateOf("") }
	var showGetFilmError by remember { mutableStateOf(false) }
	var showMarkWatchedError by remember { mutableStateOf(false) }
	var errorMessage by remember { mutableStateOf("") }
	val bottomSheetState = rememberModalBottomSheetState(
		initialValue = ModalBottomSheetValue.Hidden,
		confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
	)
	val keyboardController = LocalSoftwareKeyboardController.current
	val coroutineScope = rememberCoroutineScope()

	OnLifecycleEvent { _, event ->
		when (event) {
			ON_RESUME -> viewModel.onEvent(GetFiltersSubmit)
			else -> Unit
		}
	}

	LaunchedEffect(key1 = true) {
		viewModel.searchEventChannel.collect { event ->
			when (event) {
				is GetFilmByFiltersSuccess -> {
					filmsList = event.films
					isLoading = false
				}

				is GetFilmByFiltersError -> {
					showGetFilmError = true
					showMarkWatchedError = false
					isLoading = false
					errorMessage = if (event.throwable is DataSourceException) {
						event.throwable.formatErrorMessage()
					} else {
						event.throwable.message.orEmpty()
					}
				}

				is GetFiltersSuccess -> filters = event.filters
				is GetFiltersError -> {
					errorMessage = if (event.throwable is DataSourceException) {
						event.throwable.formatErrorMessage()
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
					showGetFilmError = false
					showMarkWatchedError = true
					isLoading = false
				}

				else -> {}
			}
		}
	}

	ModalBottomSheetLayout(
		modifier = Modifier.fillMaxSize(),
		sheetState = bottomSheetState,
		sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
		sheetContent = {
			FilterBottomSheet(
				filters, year, category,
				onFilterListener = { yearSelected, categorySelected ->
					isLoading = true
					isToShowShimmer = true
					year = yearSelected
					category = categorySelected
					coroutineScope.launch { bottomSheetState.hide() }
					viewModel.onEvent(GetFilmByFiltersSubmit(category, year, filmName))
				},
				onCloseListener = { coroutineScope.launch { bottomSheetState.hide() } },
				onClearFiltersListener = {
					filmName = ""
					isToShowShimmer = false
					coroutineScope.launch { bottomSheetState.hide() }
				}
			)
		}
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
					Spacer(modifier = Modifier.size(40.dp))
					GoesToChecklistSearchTextField(
						modifier = Modifier.fillMaxSize(),
						textValue = filmName,
						onValueChange = {
							filmName = it
							when {
								filmName.isNotEmpty() && (category.isNotEmpty() || year.isNotEmpty()) -> {
									isLoading = true
									isToShowShimmer = true
									viewModel.onEvent(
										GetFilmByFiltersSubmit(category, year, filmName)
									)
								}

								filmName.isNotEmpty() && (category.isEmpty() || year.isEmpty()) -> {
									isLoading = true
									isToShowShimmer = true
									viewModel.onEvent(
										GetFilmByFiltersSubmit(category, year, filmName)
									)
								}

								filmName.isEmpty() && (category.isNotEmpty() || year.isNotEmpty()) -> {
									isLoading = true
									isToShowShimmer = true
									viewModel.onEvent(
										GetFilmByFiltersSubmit(category, year, filmName)
									)
								}

								else -> {
									isLoading = false
									isToShowShimmer = false
								}
							}
						}
					) {
						filters?.let {
							if (it.year.isNotEmpty() && it.category.isNotEmpty()) {
								coroutineScope.launch {
									keyboardController?.hide()
									when (bottomSheetState.isVisible) {
										true -> bottomSheetState.hide()
										false -> bottomSheetState.show()
									}
								}
							}
						}
					}
				}

				if (isToShowShimmer) {
					ShimmerHomeItem(
						modifier = Modifier
							.fillMaxSize()
							.padding(horizontal = 4.dp, vertical = 4.dp),
						isLoading = isLoading,
						contentAfterLoading = {
							if (filmsList.isEmpty()) {
								Box(
									modifier = Modifier.fillMaxSize(),
									contentAlignment = Center
								) {
									GoesToChecklistEmptyList(
										modifier = Modifier
											.wrapContentSize(),
										title = stringResource(id = R.string.search_empty_film_list_title),
										icon = R.drawable.ic_no_documents
									)
								}
							} else {
								FilmItem(
									modifier = Modifier
										.fillMaxSize()
										.padding(horizontal = 4.dp, vertical = 4.dp),
									films = filmsList,
									update = isToUpdate,
									onClickItemListener = {
										filmIdToMarkWatched = ""
										showMarkWatchedError = false
										val bundle = Bundle()
										bundle.putParcelable(Constants.FILM_QUERY_NAME, it)
										doNavigation(Routes.FilmDetail, bottomNavController, bundle)
									},
									onMarkWatchedListener = {
										keyboardController?.hide()
										filmIdToMarkWatched = it.filmId
										showMarkWatchedError = false
										viewModel.onEvent(MarkWatchSubmit(filmIdToMarkWatched))
									}
								)
							}
						}
					)
				} else {
					Box(
						modifier = Modifier.fillMaxSize(),
						contentAlignment = Center
					) {
						GoesToChecklistEmptyList(
							modifier = Modifier.wrapContentSize(),
							title = stringResource(id = R.string.search_do_filter_warning),
							icon = R.drawable.ic_filter
						)
					}
				}
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
						viewModel.onEvent(GetFilmByFiltersSubmit(category, year, filmName))
						showGetFilmError = false
						showMarkWatchedError = false
					}
				)
			}

			AnimatedVisibility(
				visible = showMarkWatchedError,
				enter = fadeIn(animationSpec = tween(400)),
				exit = fadeOut(animationSpec = tween(400))
			) {
				Box(
					modifier = Modifier
						.fillMaxSize()
						.padding(8.dp)
				) {
					GoesToChecklistSnackbar(
						modifier = Modifier
							.fillMaxWidth()
							.wrapContentHeight()
							.align(BottomCenter),
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

	BackHandler(bottomSheetState.isVisible) {
		coroutineScope.launch { bottomSheetState.hide() }
	}
}

@Composable
fun FilterBottomSheet(
	filters: Filter?,
	yearSelected: String,
	categorySelected: String,
	onFilterListener: (String, String) -> Unit,
	onCloseListener: () -> Unit,
	onClearFiltersListener: () -> Unit
) {
	val configuration = LocalConfiguration.current
	val screenHeight = configuration.screenHeightDp.dp - 55.6.dp

	val year = filters?.year.orEmpty()
	val categories = filters?.category.orEmpty()
	var yearPick by remember { mutableStateOf(yearSelected) }
	var categoryPick by remember { mutableStateOf(categorySelected) }

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.background(color = Gray700)
			.heightIn(max = screenHeight)
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dp)
		) {
			Image(
				modifier = Modifier
					.size(28.dp)
					.align(CenterHorizontally)
					.rotate(90f)
					.clickable {
						yearPick = ""
						categoryPick = ""
						onCloseListener.invoke()
					},
				painter = painterResource(id = R.drawable.ic_arrow_chevron),
				contentDescription = stringResource(id = R.string.content_description_toolbar_button)
			)
			Spacer(modifier = Modifier.size(16.dp))
			Text(
				text = stringResource(id = R.string.search_filter_bottom_sheet_label),
				style = MaterialTheme.typography.body2,
				fontWeight = FontWeight.Bold
			)
			Spacer(modifier = Modifier.size(8.dp))
			Text(
				text = stringResource(id = R.string.search_year_bottom_sheet_label),
				style = MaterialTheme.typography.body2
			)
			Spacer(modifier = Modifier.size(4.dp))
			LazyVerticalGrid(
				modifier = Modifier.wrapContentSize(),
				columns = GridCells.Fixed(5)
			) {
				items(year) {
					Box(
						modifier = Modifier
							.width(60.dp)
							.height(45.dp)
							.padding(end = 8.dp, bottom = 8.dp)
							.border(
								width = 1.dp,
								color = if (yearPick == it.year) Yellow else Color.White,
								shape = RoundedCornerShape(4.dp)
							)
							.clickable {
								yearPick = if (yearPick == it.year) {
									""
								} else {
									it.year
								}
							},
						contentAlignment = Center
					) {
						Text(text = it.year, style = MaterialTheme.typography.body2)
					}
				}
			}
			Spacer(modifier = Modifier.size(8.dp))
			Text(
				text = stringResource(id = R.string.search_category_bottom_sheet_label),
				style = MaterialTheme.typography.body2
			)
			Spacer(modifier = Modifier.size(4.dp))
			LazyVerticalGrid(
				columns = GridCells.Fixed(4)
			) {
				items(categories) {
					Box(
						modifier = Modifier
							.wrapContentSize()
							.padding(end = 8.dp, bottom = 8.dp)
							.border(
								width = 1.dp,
								color = if (categoryPick == it.name) Yellow else Color.White,
								shape = RoundedCornerShape(4.dp)
							)
							.clickable {
								categoryPick = if (categoryPick == it.name) {
									""
								} else {
									it.name
								}
							}
					) {
						Box(
							modifier = Modifier
								.wrapContentSize()
								.padding(8.dp),
							contentAlignment = Center
						) {
							Text(text = it.name, style = MaterialTheme.typography.body2)
						}
					}
				}
			}
			Spacer(modifier = Modifier.size(8.dp))
			Row {
				GoesToChecklistButton(
					modifier = Modifier
						.wrapContentWidth()
						.height(42.dp)
						.weight(1f),
					buttonText = stringResource(id = R.string.search_button_filter_movies),
					isEnable = true,
					onClick = { onFilterListener(yearPick, categoryPick) })
				Spacer(modifier = Modifier.size(8.dp))
				GoesToChecklistOutlinedButton(
					modifier = Modifier
						.wrapContentWidth()
						.height(42.dp)
						.weight(1f),
					buttonText = stringResource(id = R.string.search_button_clean_filters),
					isEnable = true,
					onClick = {
						yearPick = ""
						categoryPick = ""
						onClearFiltersListener.invoke()
					}
				)
			}
		}
	}
}