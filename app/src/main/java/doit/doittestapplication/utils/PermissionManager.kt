package doit.doittestapplication.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

object PermissionManager {

    private const val REQUEST = 1
    private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    const val REQUEST_STORAGE = 1

    fun checkPermissionGranted(activity: Activity, perm_id: Int): Boolean {
        return when (perm_id) {
            REQUEST_STORAGE -> {
                val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                permission == PackageManager.PERMISSION_GRANTED
            }
            else -> {
                false
            }
        }
    }

    fun verifyStoragePermissions(activity: Activity) {
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST)
        }
    }

}
