package com.far.goestochecklist.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.far.goestochecklist.R

val fonts = FontFamily(
	Font(R.font.josefin_sans_light, weight = FontWeight.Light),
	Font(R.font.josefin_sans_regular, weight = FontWeight.Normal),
	Font(R.font.josefin_sans_medium, weight = FontWeight.Medium),
	Font(R.font.josefin_sans_semi_bold, weight = FontWeight.SemiBold),
	Font(R.font.josefin_sans_bold, weight = FontWeight.Bold),
)

val Typography = Typography(
	h1 = TextStyle(
		fontFamily = fonts,
		fontWeight = FontWeight.Bold,
		fontSize = 32.sp,
		color = Gray900,
		platformStyle = PlatformTextStyle(
			includeFontPadding = false
		)
	),
	h2 = TextStyle(
		fontFamily = fonts,
		fontWeight = FontWeight.Bold,
		fontSize = 24.sp,
		color = Gray900,
		platformStyle = PlatformTextStyle(
			includeFontPadding = false
		)
	),
	h3 = TextStyle(
		fontFamily = fonts,
		fontWeight = FontWeight.Bold,
		fontSize = 20.sp,
		color = Gray900,
		platformStyle = PlatformTextStyle(
			includeFontPadding = false
		)
	),
	body1 = TextStyle(
		fontFamily = fonts,
		fontWeight = FontWeight.Normal,
		fontSize = 16.sp,
		color = Gray900,
		platformStyle = PlatformTextStyle(
			includeFontPadding = false
		)
	),
	body2 = TextStyle(
		fontFamily = fonts,
		fontWeight = FontWeight.Light,
		fontSize = 14.sp,
		color = Gray900,
		platformStyle = PlatformTextStyle(
			includeFontPadding = false
		)
	),
	button = TextStyle(
		fontFamily = fonts,
		fontWeight = FontWeight.Bold,
		fontSize = 16.sp,
		color = Gray900,
		platformStyle = PlatformTextStyle(
			includeFontPadding = false
		)
	),
	caption = TextStyle(
		fontFamily = fonts,
		fontWeight = FontWeight.Normal,
		fontSize = 12.sp,
		color = Gray900,
		platformStyle = PlatformTextStyle(
			includeFontPadding = false
		)
	),
)
