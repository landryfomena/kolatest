package com.example.kolatest.activities

import android.R.color.white
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kolatest.R
import gr.net.maroulis.library.EasySplashScreen
import android.graphics.Typeface
import com.example.kolatest.Acceuil


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = EasySplashScreen(this)
            .withFullScreen()
            .withTargetActivity(LoginActivity::class.java)
            .withSplashTimeOut(6000)
            .withBackgroundResource(R.color.tw__solid_white)
            .withHeaderText(resources.getString(R.string.wellcom))
            .withFooterText("Copyright 2019")
            .withBeforeLogoText(resources.getString(R.string.kola))
            .withLogo(R.drawable.logo)
            .withAfterLogoText(resources.getString(R.string.description))
        //add custom font
        val pacificoFont = Typeface.createFromAsset(assets, "Pacifico.ttf")
        config.afterLogoTextView.setTypeface(pacificoFont)

        //change text color

        //finally create the view
        val easySplashScreenView = config.create()
        setContentView(easySplashScreenView)
    }
}
