package com.far.goestochecklist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/*
 * Created by Filipi Andrade Rocha on 18/03/2023.
 */

@HiltAndroidApp
class GoesToChecklistApplication : Application() {

	override fun onCreate() {
		super.onCreate()

		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())
		}
	}
}