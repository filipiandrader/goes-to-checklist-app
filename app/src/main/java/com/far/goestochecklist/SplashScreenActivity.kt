package com.far.goestochecklist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		val splashScreen = installSplashScreen()
		splashScreen.setKeepOnScreenCondition { true }
		super.onCreate(savedInstanceState)
		lifecycleScope.launchWhenCreated {
			delay(2000)
			startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
			finishAffinity()
		}
	}
}