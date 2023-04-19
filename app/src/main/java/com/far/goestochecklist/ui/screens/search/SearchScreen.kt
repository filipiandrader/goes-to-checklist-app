@file:OptIn(ExperimentalMaterialApi::class)

package com.far.goestochecklist.ui.screens.search

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.common.OnLifecycleEvent
import com.far.goestochecklist.common.formatErrorMessage
import com.far.goestochecklist.domain.exception.DataSourceException
import com.far.goestochecklist.domain.model.Filter
import com.far.goestochecklist.presentation.search.SearchEvent.*
import com.far.goestochecklist.presentation.search.SearchViewModel
import com.far.goestochecklist.ui.components.button.GoesToChecklistButton
import com.far.goestochecklist.ui.components.button.GoesToChecklistOutlinedButton
import com.far.goestochecklist.ui.components.textfield.GoesToChecklistSearchTextField
import com.far.goestochecklist.ui.theme.Gray700
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow
import kotlinx.coroutines.launch
import timber.log.Timber

/*
 * Created by Filipi Andrade Rocha on 10/04/2023.
 */

@Composable
fun SearchScreen(
	bottomNavController: NavController,
	viewModel: SearchViewModel = hiltViewModel()
) {

	var textValue by remember { mutableStateOf("") }
	var yearSelected by remember { mutableStateOf("") }
	var categorySelected by remember { mutableStateOf("") }
	var filters by remember { mutableStateOf<Filter?>(null) }
	var errorMessage by remember { mutableStateOf("") }
	val bottomSheetState = rememberModalBottomSheetState(
		initialValue = ModalBottomSheetValue.Hidden,
		confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
	)
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
				is GetFilmByFiltersSuccess -> {}
				is GetFilmByFiltersError -> {}
				is GetFiltersSuccess -> filters = event.filters
				is GetFiltersError -> {
					errorMessage = if (event.throwable is DataSourceException) {
						event.throwable.formatErrorMessage()
					} else {
						event.throwable.message.orEmpty()
					}
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
				filters, yearSelected, categorySelected,
				onFilterListener = { year, categoryName ->
					yearSelected = year
					categorySelected = categoryName
					Timber.d("$year - $categoryName")
				},
				onCloseListener = { coroutineScope.launch { bottomSheetState.hide() } }
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
						textValue = textValue,
						onValueChange = { textValue = it }
					) {
						filters?.let {
							if (it.year.isNotEmpty() && it.category.isNotEmpty()) {
								coroutineScope.launch {
									when (bottomSheetState.isVisible) {
										true -> bottomSheetState.hide()
										false -> bottomSheetState.show()
									}
								}
							}
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
	onCloseListener: () -> Unit
) {
	val year = filters?.year.orEmpty()
	val categories = filters?.category.orEmpty()
	var yearPick by remember { mutableStateOf(yearSelected) }
	var categoryPick by remember { mutableStateOf(categorySelected) }

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Gray700)
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
				text = "Categoria",
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
						onCloseListener.invoke()
					}
				)
			}
		}
	}
}