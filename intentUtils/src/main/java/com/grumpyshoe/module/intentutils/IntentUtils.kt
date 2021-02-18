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

    runPackageCheck(
        activity = activity,
        baseIntent = this,
        noAppAvailable = noAppAvailable
    ) {

        activity.startActivity(it)
    }

}


/**
 * <p>IntentUtils contains a extension function for handling the given intent and show the correcsponding apps.
 * At least this library handles the available list of fitting apps.
 *
 * For better UX, there are three posibilities that are handeled different:
 * - if only one app can handle the intent it is open instantly
 * - if there are more then one apps available and fitting, the app chooser is shown
 * - if no app is available for the intent, a alert dialog is shown</p>
 *
 * @since    1.1.0
 * @version  1.0.0
 * @author   grumpyshoe
 *
 */
fun Intent.openForResult(activity: Activity, requestCode: Int, noAppAvailable: NoAppAvailable? = null) {

    runPackageCheck(
        activity = activity,
        baseIntent = this,
        noAppAvailable = noAppAvailable
    ) {

        activity.startActivityForResult(it, requestCode)
    }

}

/**
 * get the available packages that can handle the given intent
 *
 */
fun Intent.getAvailablePackages(activity: Activity): List<String> {

    //get a list of apps that meet your criteria above
    return activity.packageManager.queryIntentActivities(this, PackageManager.MATCH_DEFAULT_ONLY or PackageManager.GET_RESOLVED_FILTER)
        .map {
            it.activityInfo.packageName
        }
}


/**
 * <p>Check if there are one ore more installed apps that can handle the given intent.
 *
 * For better UX, there are three possibilities that are handeled different:
 * - if only one app can handle the intent it is open instantly
 * - if there are more then one apps available and fitting, the app chooser is shown
 * - if no app is available for the intent, a alert dialog is shown
 * </p>
 */
private fun runPackageCheck(activity: Activity, baseIntent: Intent, noAppAvailable: NoAppAvailable? = null, onHandleIntent: (Intent) -> Unit) {

    //get a list of apps that meet your criteria above
    val pkgAppsList = baseIntent.getAvailablePackages(activity)

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
    } else {
        lateinit var targetIntent: Intent
        if (pkgAppsList.size == 1) {
            targetIntent = baseIntent
        } else if (pkgAppsList.size > 1) {
            targetIntent = Intent.createChooser(baseIntent, activity.applicationContext.getString(R.string.app_chooser_dialog_title))
        }
        return onHandleIntent(targetIntent)
    }
}