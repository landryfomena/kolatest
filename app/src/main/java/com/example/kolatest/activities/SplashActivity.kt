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
        val pacificoFont = Typeface.createFromAsset(assets, "sfregular.otf")
        config.afterLogoTextView.setTypeface(pacificoFont)
        config.beforeLogoTextView.setTypeface(pacificoFont)
        config.headerTextView.setTypeface(pacificoFont)
        config.footerTextView.setTypeface(pacificoFont)
        config.afterLogoTextView.setTextColor(resources.getColor(R.color.colorPrimary))
        config.beforeLogoTextView.setTextColor(resources.getColor(R.color.colorPrimary))
        config.headerTextView.setTextColor(resources.getColor(R.color.colorPrimary))
        config.footerTextView.setTextColor(resources.getColor(R.color.colorPrimary))

        //change text color

        //finally create the view
        val easySplashScreenView = config.create()
        setContentView(easySplashScreenView)
    }
}
