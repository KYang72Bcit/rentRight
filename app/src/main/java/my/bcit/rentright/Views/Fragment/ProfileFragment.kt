package my.bcit.rentright.Views.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import my.bcit.rentright.Models.User.User
import my.bcit.rentright.R
import my.bcit.rentright.Utils.GetReady
import my.bcit.rentright.ViewModels.UserViewModel


class ProfileFragment : Fragment() {
    private lateinit var uName: TextView
    private lateinit var uEmail: TextView
    private lateinit var uPhone: TextView
    private lateinit var logoutBtn: Button
    private val userViewModel: UserViewModel by activityViewModels()
    private val getReady = GetReady()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Log.i("user", user.toString())
            }
            setUserData(user)
        }


    }

    private fun setUserData(user : User?) {
        uName = requireView().findViewById(R.id.user_name)
        uEmail = requireView().findViewById(R.id.email)
        uPhone = requireView().findViewById(R.id.phone)
        logoutBtn = requireView().findViewById(R.id.btn_logout)
        setLogoutBtn(logoutBtn)
        val userName = user?.username
        val userEmail = user?.useremail
        val userPhone = user?.phone

        uName.text = userName
        uEmail.text = userEmail
        uPhone.text = userPhone

    }

    private fun setLogoutBtn(logoutBtn:Button) {
        logoutBtn.setOnClickListener{
            userViewModel.logout()
            getReady.goToHomePage(requireContext(), requireActivity())

        }
    }


}