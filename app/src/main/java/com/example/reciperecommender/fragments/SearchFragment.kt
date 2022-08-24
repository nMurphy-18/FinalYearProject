package com.example.reciperecommender.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.example.reciperecommender.R
import com.example.reciperecommender.dataClasses.RandomRecipe
import com.example.reciperecommender.services.RandomService
import com.example.reciperecommender.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    lateinit var query: String
    lateinit var meal: String
    lateinit var cuisine: String
    lateinit var diet: String
    lateinit var exclusions: String
    lateinit var intolerances: String

    lateinit var queryEditText: EditText
    lateinit var mealSpinner: Spinner
    lateinit var cuisineSpinner: Spinner
    lateinit var exclusionsTxt: EditText

    lateinit var dairyCheckbox: CheckBox
    lateinit var eggCheckbox: CheckBox
    lateinit var glutenCheckbox: CheckBox
    lateinit var grainsCheckbox: CheckBox
    lateinit var peanutCheckbox: CheckBox
    lateinit var seafoodCheckbox: CheckBox
    lateinit var sesameCheckbox: CheckBox
    lateinit var shellfishCheckbox: CheckBox
    lateinit var soyCheckbox: CheckBox
    lateinit var treenutCheckbox: CheckBox
    lateinit var wheatCheckbox: CheckBox
    lateinit var cornCheckbox: CheckBox
    lateinit var vegetarianCheckBox: CheckBox
    lateinit var veganCheckBox: CheckBox

    lateinit var randomId: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        queryEditText = view.findViewById(R.id.searchView)
        mealSpinner = view.findViewById(R.id.mealsSpinner)
        cuisineSpinner = view.findViewById(R.id.cuisineSpinner)
        exclusionsTxt = view.findViewById(R.id.excludeField)

        dairyCheckbox = view.findViewById(R.id.checkBoxDairy)
        eggCheckbox = view.findViewById(R.id.checkBoxEgg)
        glutenCheckbox = view.findViewById(R.id.checkBoxGluten)
        grainsCheckbox = view.findViewById(R.id.checkBoxGrains)
        peanutCheckbox = view.findViewById(R.id.checkBoxPeanut)
        seafoodCheckbox = view.findViewById(R.id.checkBoxSeafood)
        sesameCheckbox = view.findViewById(R.id.checkBoxSesame)
        shellfishCheckbox = view.findViewById(R.id.checkBoxShellfish)
        soyCheckbox = view.findViewById(R.id.checkBoxSoy)
        treenutCheckbox = view.findViewById(R.id.checkBoxTreeNut)
        wheatCheckbox = view.findViewById(R.id.checkBoxWheat)
        cornCheckbox = view.findViewById(R.id.checkBoxCorn)
        vegetarianCheckBox = view.findViewById(R.id.vegetarianCheckbox)
        veganCheckBox = view.findViewById(R.id.veganCheckbox)

        view.searchBtn.setOnClickListener {
            getSearchValues()
            val bundle = Bundle()
            bundle.putString("query", query)
            bundle.putString("meal", meal)
            bundle.putString("cuisine", cuisine)
            bundle.putString("diet", diet)
            bundle.putString("exclusions", exclusions)
            bundle.putString("intolerances", intolerances)
            val searchResultsFragment = SearchResultsFragment()
            searchResultsFragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fl_wrapper, searchResultsFragment)
                ?.commit()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        getSearchValues()
        searchBtn.setOnClickListener {
            getSearchValues()
            val bundle = Bundle()
            bundle.putString("query", query)
            bundle.putString("meal", meal)
            bundle.putString("cuisine", cuisine)
            bundle.putString("diet", diet)
            bundle.putString("exclusions", exclusions)
            bundle.putString("intolerances", intolerances)
            val searchResultsFragment = SearchResultsFragment()
            searchResultsFragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fl_wrapper, searchResultsFragment)
                ?.commit()
        }
    }

    private fun getSearchValues() {

        query = queryEditText.text.toString()
        meal = mealSpinner.selectedItem.toString()
        cuisine = cuisineSpinner.selectedItem.toString()
        exclusions = exclusionsTxt.text.toString()

        var intoleranceBuilder = mutableListOf<String>()
        var intoleranceCheck: Boolean = false
        if(dairyCheckbox.isChecked) intoleranceBuilder.add("dairy"); intoleranceCheck = true
        if(eggCheckbox.isChecked) intoleranceBuilder.add("egg"); intoleranceCheck = true
        if(glutenCheckbox.isChecked) intoleranceBuilder.add("gluten"); intoleranceCheck = true
        if(grainsCheckbox.isChecked) intoleranceBuilder.add("grains"); intoleranceCheck = true
        if(peanutCheckbox.isChecked) intoleranceBuilder.add("peanut"); intoleranceCheck = true
        if(seafoodCheckbox.isChecked) intoleranceBuilder.add("seafood"); intoleranceCheck = true
        if(sesameCheckbox.isChecked) intoleranceBuilder.add("sesame"); intoleranceCheck = true
        if(shellfishCheckbox.isChecked) intoleranceBuilder.add("shellfish"); intoleranceCheck = true
        if(soyCheckbox.isChecked) intoleranceBuilder.add("soy"); intoleranceCheck = true
        if(treenutCheckbox.isChecked) intoleranceBuilder.add("treenut"); intoleranceCheck = true
        if(wheatCheckbox.isChecked) intoleranceBuilder.add("wheat"); intoleranceCheck = true
        if(cornCheckbox.isChecked) intoleranceBuilder.add("corn"); intoleranceCheck = true

        if (!intoleranceCheck) intolerances = "none"
        intolerances = intoleranceBuilder.toString()

        diet = if (veganCheckbox.isChecked) {
            "vegan"
        } else if (vegetarianCheckbox.isChecked) {
            "vegetarian"
        } else {
            "all"
        }
    }
}