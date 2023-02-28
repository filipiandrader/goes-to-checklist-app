package com.far.goestochecklist.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.far.goestochecklist.R

val fonts = FontFamily(
    Font(R.font.nexa_black, weight = FontWeight.Black),
    Font(R.font.nexa_extra_bold, weight = FontWeight.ExtraBold),
    Font(R.font.nexa_light, weight = FontWeight.Light),
    Font(R.font.nexa_regular, weight = FontWeight.Normal),
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
    ),
    h2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
    ),
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
    ),
    button = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Black,
    ),
)
