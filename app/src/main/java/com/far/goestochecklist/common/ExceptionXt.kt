package com.far.goestochecklist.common

import com.far.goestochecklist.BuildConfig

/*
 * Created by Filipi Andrade Rocha on 28/03/2023.
 */
 
fun Exception.printError() {
	if (BuildConfig.DEBUG) printStackTrace()
}