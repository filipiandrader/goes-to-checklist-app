package com.far.goestochecklist.ui.screens.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
	bottomNavController: NavController
) {

	var textValue by remember { mutableStateOf("") }
	val bottomSheetState = rememberModalBottomSheetState(
		initialValue = ModalBottomSheetValue.Hidden,
		confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
	)
	val coroutineScope = rememberCoroutineScope()

	ModalBottomSheetLayout(
		modifier = Modifier.fillMaxSize(),
		sheetState = bottomSheetState,
		sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
		sheetContent = { FilterBottomSheet() }
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

	BackHandler(bottomSheetState.isVisible) {
		coroutineScope.launch { bottomSheetState.hide() }
	}
}

@Composable
fun FilterBottomSheet() {
	val year = listOf("2023", "2022", "2021", "2020", "2019", "2018")
	val categories = listOf(
		"Melhor Filme",
		"Melhor Atriz",
		"Melhor Ator",
		"Melhor Direção",
		"Melhor Atriz Coadjuvante",
		"Melhor Ator Coadjuvante",
		"Melhor Roteiro Original",
		"Melhor Roteiro Adaptado",
		"Melhor Fotografia",
		"Melhor Figurino",
		"Melhor Edição",
		"Melhor Cabelo e Maquiagem",
		"Melhor Design de Produção",
		"Melhor Som",
		"Melhor Canção Original",
		"Melhor Efeitos Visuais",
		"Melhor Trilha Sonora Original",
		"Melhor Animação",
		"Melhor Curta de Animação",
		"Melhor Curta Live Action",
		"Melhor Documentário",
		"Melhor Documentário Curta-Metragem",
		"Melhor Filme Internacional"
	)

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
			Divider(
				modifier = Modifier
					.width(100.dp)
					.height(5.dp)
					.align(CenterHorizontally)
					.background(color = Gray900, shape = CircleShape)
			)
			Spacer(modifier = Modifier.size(16.dp))
			Text(
				text = "Filtrar",
				style = MaterialTheme.typography.body2,
				fontWeight = FontWeight.Bold
			)
			Spacer(modifier = Modifier.size(8.dp))
			Text(
				text = "Ano",
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
								color = Color.White,
								shape = RoundedCornerShape(4.dp)
							),
						contentAlignment = Center
					) {
						Text(text = it, style = MaterialTheme.typography.body2)
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
								color = Color.White,
								shape = RoundedCornerShape(4.dp)
							)
					) {
						Box(
							modifier = Modifier
								.wrapContentSize()
								.padding(8.dp),
							contentAlignment = Center
						) {
							Text(text = it, style = MaterialTheme.typography.body2)
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
					buttonText = "Filtrar filmes",
					isEnable = true,
					onClick = { /*TODO*/ })
				Spacer(modifier = Modifier.size(8.dp))
				GoesToChecklistOutlinedButton(
					modifier = Modifier
						.wrapContentWidth()
						.height(42.dp)
						.weight(1f),
					buttonText = "Limpar filtros",
					isEnable = true,
					onClick = { /*TODO*/ })
			}
		}
	}
}