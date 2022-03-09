package com.example.mytaxi_task.utils.helpers

import android.app.Activity
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener


fun Activity.checkPermission(permission: String, granted: () -> Unit) {
    Dexter.withContext(this)
        .withPermission(permission)
        .withListener(permissionHandler(granted))
        .check()
}

fun Activity.checkMultiplePermissions(permissions: ArrayList<String>, granted: () -> Unit) {
    Dexter.withContext(this)
        .withPermissions(permissions)
        .withListener(multiplePermissionsHandler(granted))
        .check()
}


private fun permissionHandler(
    granted: () -> Unit,
) = object : PermissionListener {
    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        granted()
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?,
    ) {
        token?.continuePermissionRequest()
    }

}


private fun multiplePermissionsHandler(
    granted: () -> Unit
): MultiplePermissionsListener = object : MultiplePermissionsListener {
    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
        if (report.areAllPermissionsGranted()){
            granted()
        }
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: MutableList<PermissionRequest>?,
        token: PermissionToken?,
    ) {
        token?.continuePermissionRequest()
    }

}
