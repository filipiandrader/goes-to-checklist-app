package com.far.goestochecklist.ui.navigation

import com.far.goestochecklist.R
import com.far.goestochecklist.common.Constants.HOME
import com.far.goestochecklist.common.Constants.PROFILE
import com.far.goestochecklist.common.Constants.SEARCH

/*
 * Created by Filipi Andrade Rocha on 10/04/2023.
 */

sealed class BottomMenuItem(val label: String, val icon: Int, var route: Routes) {
	object Home : BottomMenuItem(HOME, R.drawable.ic_home, Routes.Home)
	object Search : BottomMenuItem(SEARCH, R.drawable.ic_movie_search, Routes.Search)
	object Profile : BottomMenuItem(PROFILE, R.drawable.ic_profile, Routes.Profile)
}
