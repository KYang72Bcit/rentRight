package my.bcit.rentright.views
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import my.bcit.rentright.R
import my.bcit.rentright.views.Fragment.*


class Navigation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nevigation)

        var BottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        //

        /*        if (supportActionBar != null) {
                    val drawable = resources.getDrawable(R.drawable.yum)
                    val bitmap = (drawable as BitmapDrawable).bitmap
                    val newdrawable: Drawable =
                        BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 50, 50, true))
                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                    supportActionBar!!.setHomeAsUpIndicator(newdrawable)
                }*/
        ///////////////////////////////////////////////////////////////////////////////////////////
        supportFragmentManager.beginTransaction().replace(R.id.container, FragmentHome()).commit();
        BottomNavigationView.setSelectedItemId(R.id.nav_home)
        BottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> {
                    selectedFragment = FragmentHome()
                }
                R.id.nav_explore -> {
                    selectedFragment = ExploreFragmenttodo()
                }
                R.id.nav_add -> {
                    BottomNavigationView.visibility = View.GONE
                    selectedFragment = AddFragment()
                }
                R.id.nav_favorite -> {
                    selectedFragment = FavoriteFragment()
                }
                R.id.nav_profile -> {
                    refresh()
                    selectedFragment = ProfileFragment()
                }
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            )
            transaction.replace(R.id.container, selectedFragment!!)
            transaction.commit()
            true
        }
        ///////////////////////////////////////////////////////////////////////////////////////////
    }
}