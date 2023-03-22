package com.far.goestochecklist.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/*
 * Created by Filipi Andrade Rocha on 22/03/2023.
 */


inline fun <reified T> String.fromJson(): T? = try {
	Gson().getAdapter(TypeToken.get(T::class.java)).fromJson(this)
} catch (e: Exception) {
	null
}