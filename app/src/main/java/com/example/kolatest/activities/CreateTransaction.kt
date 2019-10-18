package com.example.kolatest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.nightonke.boommenu.BoomButtons.BoomButton

import com.nightonke.boommenu.BoomMenuButton


import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton
import com.nightonke.boommenu.OnBoomListener


import android.widget.Toast
import com.example.kolatest.R


class CreateTransaction : AppCompatActivity() {
    var bmb:BoomMenuButton?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_transaction)
        bmb=findViewById(R.id.bmb)
        var images= arrayListOf<Int>()
        images.add(R.drawable.gender_neutral_user_filled_24px)
        images.add(R.drawable.home_page_24px)
        images.add(R.drawable.shopping_bag_filled_24px)
        images.add(R.drawable.stack_of_money_24px)
        images.add(R.drawable.restaurant_filled_24px)
        images.add(R.drawable.car_filled_24px)
        images.add(R.drawable.subway_24px)
        images.add(R.drawable.subway_filled_24px)
        images.add(R.drawable.theatre_mask_24px)

        for (i in 0 until bmb!!.getPiecePlaceEnum().pieceNumber()) {
            val builder = TextOutsideCircleButton.Builder()
                .normalImageRes(images.get(i))
                .rotateImage(true)
                .normalText("menu "+i)
            bmb!!.addBuilder(builder)
        }
        // Use OnBoomListener to listen all methods
        bmb!!.setOnBoomListener(object : OnBoomListener {
            override fun onClicked(index: Int, boomButton: BoomButton) {
                // If you have implement listeners for boom-buttons in builders,
                // then you shouldn't add any listener here for duplicate callbacks.
                Toast.makeText(applicationContext,"Click background!!!"+index,Toast.LENGTH_LONG).show()
            }

            override fun onBackgroundClick() {
                //Toast.makeText(applicationContext,"Click background!!!",Toast.LENGTH_LONG).show()

            }

            override fun onBoomWillHide() {
                //Toast.makeText(applicationContext,"Will hide!!!",Toast.LENGTH_LONG).show()

            }

            override fun onBoomDidHide() {
                //Toast.makeText(applicationContext,"Did RE-BOOM!!!",Toast.LENGTH_LONG).show()

            }

            override fun onBoomWillShow() {
                //Toast.makeText(applicationContext,"Will BOOM!!!",Toast.LENGTH_LONG).show()

            }

            override fun onBoomDidShow() {
                //Toast.makeText(applicationContext,"Did BOOM!!!",Toast.LENGTH_LONG).show()


            }
        })
    }
}
