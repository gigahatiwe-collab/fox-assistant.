package com.fox.assistant

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val REQ_CAPTURE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnOverlay).setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                startActivity(Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    android.net.Uri.parse("package:$packageName")
                ))
            }
        }

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            val mgr = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            startActivityForResult(mgr.createScreenCaptureIntent(), REQ_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            val svc = Intent(this, CaptureService::class.java).apply {
                putExtra("resultCode", resultCode)
                putExtra("data", data)
            }
            startForegroundService(svc)

            startService(Intent(this, OverlayService::class.java))
        }
    }
}
