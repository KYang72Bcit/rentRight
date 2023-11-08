package my.bcit.rentright.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import my.bcit.rentright.views.Activity.HomePageActivity

class GetReady {

    fun showView(view : View) {
        view.visibility = View.VISIBLE
    }
    fun hideView(view : View) {
        view.visibility = View.GONE
    }

//    fun goToActivityLogin(context: Context, Activity : Activity) {
//        val intent = Intent(context, Login::class.java)
//        context.startActivity(intent)
//        Activity.finish()
//    }
    fun goToHomePage(context: Context, activity : Activity) {
        val intent = Intent(context, HomePageActivity::class.java)
        context.startActivity(intent)
        activity.finish()
    }

}