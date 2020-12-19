package com.padc.share.activities

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showSnackbar(message : String){
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG).show()
    }

    fun getFirebaseInstanceID() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TOKEN", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = "token: $token"
                Log.d("TOKEN", msg)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
    }

      fun printHashKey(pContext: Context) {
        try {
            val info = pContext.packageManager.getPackageInfo(
                pContext.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.d("Hash key", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.d("", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("", "printHashKey()", e)
        }

    }

}