package com.grumpyshoe.module.intentutils.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.grumpyshoe.module.intentutils.getAvailablePackages
import com.grumpyshoe.module.intentutils.open
import com.grumpyshoe.module.intentutils.openForResult
import kotlinx.android.synthetic.main.activity_main.button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
            i.putExtra(Intent.EXTRA_SUBJECT, "subject of email")
            i.putExtra(Intent.EXTRA_TEXT, "body of email")

            // by using default values (if strings not overwritten)
            i.open(this)

//          // customized message
//          i.open(this, NoAppAvailable(message ="Custom Message Text"))

            val packageCount = i.getAvailablePackages(this)
            Log.d(javaClass.simpleName, "package count: ${packageCount.size} $packageCount")

            i.openForResult(this, 3155)

        }

    }

}
