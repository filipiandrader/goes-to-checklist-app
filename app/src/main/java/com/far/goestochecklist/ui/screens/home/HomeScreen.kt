package com.far.goestochecklist.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.far.goestochecklist.R
import com.far.goestochecklist.common.OnLifecycleEvent
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.domain.model.Login
import com.far.goestochecklist.domain.model.Year
import com.far.goestochecklist.presentation.home.HomeEvent.*
import com.far.goestochecklist.presentation.home.HomeViewModel
import com.far.goestochecklist.ui.components.dialog.GoesToChecklistSingleChoiceDialog
import com.far.goestochecklist.ui.theme.Gray900
import com.far.goestochecklist.ui.theme.Yellow

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen(
	navController: NavController,
	viewModel: HomeViewModel = hiltViewModel()
) {

	var userInfo by remember { mutableStateOf<Login?>(null) }
	var yearsList by remember { mutableStateOf(mutableListOf<Year>()) }
	var filmsList by remember { mutableStateOf(mutableListOf<Film>()) }
	var yearPicked by remember { mutableStateOf("") }
	var isLoading by remember { mutableStateOf(true) }
	var showYearPickDialog by remember { mutableStateOf(false) }

	OnLifecycleEvent { _, event ->
		when (event) {
			Lifecycle.Event.ON_RESUME -> {
				viewModel.onEvent(GetUserSubmit)
			}
			else -> Unit
		}
	}


	LaunchedEffect(key1 = true) {
		viewModel.homeEventChannel.collect {
			when (it) {
				is GetUserSuccess -> {
					userInfo = it.user
					viewModel.onEvent(GetYearSubmit)
				}
				is GetYearSuccess -> {
					yearsList = it.years.toMutableList()
					if (yearPicked.isEmpty()) {
						yearPicked = yearsList[yearsList.size - 1].year
					}
					viewModel.onEvent(GetFilmSubmit(yearPicked))
				}
				is GetYearError -> {

				}
				is GetFilmSuccess -> {
					filmsList = it.films.toMutableList()
					isLoading = false
				}
				is GetFilmError -> {

				}
				is MarkWatchSuccess -> {

				}
				is MarkWatchError -> {

				}
				else -> Unit
			}
		}
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Gray900)
	) {
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
					)
				)
				if (yearsList.isNotEmpty()) {
					Box(
						modifier = Modifier.fillMaxWidth(),
						contentAlignment = CenterEnd
					) {
						Text(
							modifier = Modifier.clickable { showYearPickDialog = true },
							text = stringResource(id = R.string.year, yearPicked)
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
						.clickable { /* TODO TELA DE PERFIL */ },
					painter = painterResource(id = R.drawable.ic_profile),
					contentDescription = stringResource(id = R.string.content_description_profile_picture)
				)
			}
		}

		ShimmerHomeItem(
			isLoading = isLoading,
			contentAfterLoading = {
				HomeItem(
					films = filmsList,
					onClickItemListener = { /* TODO TELA DE DETALHE DO FILME */ },
					onMarkWatchedListener = { /* TODO FEAT DE MARCAR COMO VISTO */ },
					modifier = Modifier
						.fillMaxSize()
						.padding(horizontal = 4.dp, vertical = 4.dp)
				)
			},
			modifier = Modifier
				.fillMaxSize()
				.padding(horizontal = 4.dp, vertical = 4.dp)
		)

		if (showYearPickDialog) {
			GoesToChecklistSingleChoiceDialog(
				modifier = Modifier
					.width(400.dp)
					.wrapContentHeight()
					.padding(16.dp),
				textContent = stringResource(id = R.string.pick_a_year),
				options = listOf("2021", "2022", "2023"),
				selectedOption = yearPicked,
				positiveText = stringResource(id = R.string.ok),
				onPositiveClick = {
					isLoading = true
					yearPicked = it
					viewModel.onEvent(GetFilmSubmit(yearPicked))
					showYearPickDialog = false
				}
			)
		}
	}
}