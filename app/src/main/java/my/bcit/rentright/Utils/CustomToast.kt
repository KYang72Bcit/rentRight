package my.bcit.rentright.Utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.os.Handler
import android.os.Looper
import my.bcit.rentright.R

@SuppressLint("InflateParams")
class CustomToast(context: Context, message: String, color:String) {
    private val popupWindow: PopupWindow;
    private val view: View = LayoutInflater.from(context).inflate(R.layout.custom_toast, null, false)

    init {
        val txtMsg = view.findViewById<TextView>(R.id.toast_text)
        val button = view.findViewById<FrameLayout>(R.id.button_accent_border)
        if (color == "GREEN") {
            button.setBackgroundColor(Color.parseColor("#3EAA56"))
        }
        if (color == "RED") {
            button.setBackgroundColor(Color.parseColor("#F34336"))
        }
        txtMsg.text = message
        popupWindow = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    fun show() {
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 150)
        Handler(Looper.getMainLooper()).postDelayed({ popupWindow.dismiss() }, 2000)
    }

}


