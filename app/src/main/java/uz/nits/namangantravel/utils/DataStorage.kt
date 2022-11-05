package uz.nits.namangantravel.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import uz.nits.namangantravel.R

class DataStorage {
    companion object {
        const val KEY_EXTRA = "KEY_EXTRA"
    }

    private val sharedPrefName = "Profile"
    val profileEmail = "ProfileEmail"
    val profilePassword = "ProfilePassword"
    fun saveSharedPreferences(context: Context, email: String, password: String) {
        val sharedPreferences = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(profileEmail, email)
        editor.putString(profilePassword, password)
        editor.apply()
    }

    fun readSharedPreferences(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")
    }

    fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun createDialogProgressbar(context: Context, title: String): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.progress_dialog_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
//        dialog.findViewById<TextView>(R.id.dialog_progressbar_title_id)
//            .text = title
        return dialog
    }
}