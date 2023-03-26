package com.far.goestochecklist.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.far.goestochecklist.R
import com.far.goestochecklist.common.getYearNumber
import com.far.goestochecklist.common.toDate
import com.far.goestochecklist.domain.model.Film
import com.far.goestochecklist.ui.theme.Gray900

/*
 * Created by Filipi Andrade Rocha on 26/03/2023.
 */

@Composable
fun HomeItem(
	films: List<Film>,
	update: Boolean,
	onClickItemListener: (Film) -> Unit,
	onMarkWatchedListener: (Film) -> Unit,
	modifier: Modifier = Modifier
) {
	LazyColumn(modifier = modifier) {
		items(films) {
			Card(
				modifier = Modifier
					.padding(horizontal = 4.dp, vertical = 4.dp)
					.clickable { onClickItemListener.invoke(it) },
				elevation = 4.dp,
				shape = RoundedCornerShape(12.dp)
			) {
				Box(modifier = Modifier.wrapContentSize()) {
					Box(modifier = Modifier.wrapContentSize()) {
						Image(
							modifier = Modifier
								.fillMaxWidth()
								.height(200.dp),
							painter = rememberAsyncImagePainter(model = it.posterImage),
							contentDescription = stringResource(id = R.string.content_description_movie_poster),
							contentScale = ContentScale.FillBounds
						)
						Box(
							modifier = Modifier
								.fillMaxWidth()
								.height(48.dp)
								.align(Alignment.BottomCenter)
								.background(color = Gray900.copy(alpha = 0.6f)),
							contentAlignment = Alignment.Center
						) {
							Column(
								modifier = Modifier.wrapContentSize(),
								verticalArrangement = Arrangement.Center
							) {
								Text(
									modifier = Modifier.align(Alignment.CenterHorizontally),
									text = it.name,
									style = MaterialTheme.typography.h4
								)
								Spacer(modifier = Modifier.size(4.dp))
								val year = it.releaseDate.toDate()?.getYearNumber().orEmpty()
								Text(
									modifier = Modifier.align(Alignment.CenterHorizontally),
									text = stringResource(
										id = R.string.movie_info,
										year,
										it.duration,
										it.category[0]
									),
									style = MaterialTheme.typography.body1,
									fontWeight = FontWeight.Light
								)
							}
						}
					}
					Box(
						modifier = Modifier
							.size(40.dp)
							.clip(RoundedCornerShape(bottomStart = 8.dp))
							.align(Alignment.TopEnd)
							.background(color = Gray900.copy(alpha = 0.6f))
							.clickable { onMarkWatchedListener.invoke(it) },
						contentAlignment = Alignment.Center
					) {
						val checkedIcon = if (it.watched) {
							painterResource(id = R.drawable.ic_checked)
						} else {
							painterResource(id = R.drawable.ic_unchecked)
						}
						Image(
							painter = checkedIcon,
							contentDescription = stringResource(id = R.string.content_description_icon_movie_watched)
						)
					}
				}
			}
		}
	}
}