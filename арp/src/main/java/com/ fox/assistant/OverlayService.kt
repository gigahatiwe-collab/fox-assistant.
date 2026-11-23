package com.fox.assistant

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.*
import android.widget.TextView

class OverlayService : Service() {

    private var view: View? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (view == null) {
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
            )
            params.gravity = Gravity.TOP or Gravity.START
            params.x = 40; params.y = 200

            val tv = TextView(this).apply {
                text = "FOX"
                setTextColor(0xFFFF6A00.toInt())
                textSize = 20f
            }
            view = tv
            wm.addView(view, params)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        view?.let {
            (getSystemService(WINDOW_SERVICE) as WindowManager).removeView(it)
        }
        view = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
