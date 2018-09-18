package com.grumpyshoe.module.intentutils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AlertDialog
import com.grumpyshoe.intentutils.R
import com.grumpyshoe.module.intentutils.model.NoAppAvailable


/**
 * <p>IntentUtils contains a extension function for handling the given intent and show the correcsponding apps.
 * At least this library handles the available list of fitting apps.
 *
 * For better UX, there are three posibilities that are handeled different:
 * - if only one app can handle the intent it is open instantly
 * - if there are more then one apps available and fitting, the app chooser is shown
 * - if no app is available for the intent, a alert dialog is shown</p>
 *
 * @since    1.0.0
 * @version  1.0.0
 * @author   grumpyshoe
 *
 */
fun Intent.open(activity: Activity, noAppAvailable: NoAppAvailable? = null) {

    //get a list of apps that meet your criteria above
    val pkgAppsList = activity.packageManager.queryIntentActivities(this, PackageManager.MATCH_DEFAULT_ONLY or PackageManager.GET_RESOLVED_FILTER)

    var targetIntent: Intent? = null
    if (pkgAppsList.isEmpty()) {

        var title = activity.applicationContext.getString(R.string.no_app_for_intent_dialog_title)
        var message = activity.applicationContext.getString(R.string.no_app_for_intent_dialog_message)
        var button = activity.applicationContext.getString(R.string.no_app_for_intent_dialog_btn_ok_text)
        noAppAvailable?.let {
            title = noAppAvailable.title ?: title
            message = noAppAvailable.message ?: message
            button = noAppAvailable.button ?: button
        }

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(button, null)
        activity.runOnUiThread { builder.create().show() }
        return
    }
    if (pkgAppsList.size == 1) {
        targetIntent = this
    } else if (pkgAppsList.size > 1) {
        targetIntent = Intent.createChooser(this, activity.applicationContext.getString(R.string.app_chooser_dialog_title))
    }
    activity.startActivity(targetIntent)

}