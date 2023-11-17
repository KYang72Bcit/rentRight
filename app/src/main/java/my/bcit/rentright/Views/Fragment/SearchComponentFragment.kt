package my.bcit.rentright.Views.Fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.google.gson.JsonObject
import my.bcit.rentright.Models.Listing.ListingResponse
import my.bcit.rentright.R
import my.bcit.rentright.Network.RentRightRetrofit
import my.bcit.rentright.ViewModels.ListingViewModel
import retrofit2.Retrofit


class SearchComponentFragment : Fragment() {
        private lateinit var searchBtn:MaterialButton
        private lateinit var searchValueEditText: EditText
        private val listingViewModel: ListingViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_component, container, false)
        searchBtn = view.findViewById(R.id.search_button)
        searchValueEditText = view.findViewById(R.id.et_search_value)

        searchBtn.setOnClickListener {
            triggeringDetailClose()
            search()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("City")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)

        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.actv_search_param)
        autoCompleteTextView.setOnClickListener{triggeringDetailClose()}
        autoCompleteTextView.setAdapter(adapter)


    }

    private fun search() {
        val searchValue = searchValueEditText.text.toString().trim()
        if (searchValue.isNotBlank()){
            listingViewModel.searchListing("location.city", searchValue, requireContext())
        }

    }
    private fun triggeringDetailClose() {
        listingViewModel.onCloseDetailRequested()
    }


}
