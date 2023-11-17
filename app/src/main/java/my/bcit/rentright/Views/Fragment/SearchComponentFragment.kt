package my.bcit.rentright.Views.Fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.button.MaterialButton
import my.bcit.rentright.R
import my.bcit.rentright.ViewModels.ListingViewModel



class SearchComponentFragment : Fragment() {
        private lateinit var searchBtn:MaterialButton
        private lateinit var searchValue: String
        private val listingViewModel: ListingViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_component, container, false)
        searchBtn = view.findViewById(R.id.search_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filterItems = listOf("City")
        val filterAdapter = ArrayAdapter(requireContext(), R.layout.list_item, filterItems)

        val autoCompleteTextViewCity = view.findViewById<AutoCompleteTextView>(R.id.actv_search_value)
        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.actv_search_param)

        autoCompleteTextView.setOnClickListener{triggeringDetailClose()}
        autoCompleteTextView.setAdapter(filterAdapter)
        setAutoCompleteFilterListener(autoCompleteTextView, autoCompleteTextViewCity)
        setAutoCompleteValueListener(autoCompleteTextViewCity, searchBtn)


    }

    private fun triggeringDetailClose() {
        listingViewModel.onCloseDetailRequested()
    }

    private fun getCitiesFromListings() : List<String> {
        val listings = listingViewModel.allListings.value
        if (listings != null) {
            return listings.mapNotNull { it.location?.city }.distinct()
        }
        return emptyList()

    }

    private fun setAutoCompleteFilterListener(filter: AutoCompleteTextView, value: AutoCompleteTextView) {
        filter.setOnItemClickListener{ parent, _, position, _ ->
            val selectedFilter = parent.adapter.getItem(position).toString()
            if (selectedFilter == "City") {
                val cities = getCitiesFromListings()
                val cityAdapter = ArrayAdapter(requireContext(), R.layout.list_item, cities)
                value.setAdapter(cityAdapter)
            } else {
                value.setAdapter(null)
            }

        }
    }
    private fun setAutoCompleteValueListener(value: AutoCompleteTextView, searchBtn: MaterialButton){
        value.setOnItemClickListener{ parent, _, position, _ ->
            val selectedValue = parent.adapter.getItem(position).toString()
            if (selectedValue.isNotBlank()){
               searchValue = selectedValue
                searchBtn.setOnClickListener {
                    listingViewModel.searchListing("location.city", searchValue, requireContext())
                }
            }

        }
    }


}
