package com.example.reciperecommender.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.reciperecommender.R
import com.example.reciperecommender.adapters.ResultsAdapter
import com.example.reciperecommender.dataClasses.NutrientInfo
import com.example.reciperecommender.services.SearchService
import com.example.reciperecommender.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_search_results.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultsFragment : Fragment(), ResultsAdapter.OnItemClickListener {

    val emptyResultsFragment = EmptyResultsFragment()
    lateinit var recipeResultHeader: TextView

    lateinit var query: String
    lateinit var meal: String
    lateinit var cuisine: String
    lateinit var diet: String
    lateinit var exclusions: String
    lateinit var intolerances: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_results, container, false)
        recipeResultHeader = view.findViewById(R.id.recipeResultHeader)
        val args = this.arguments
        query = args?.get("query").toString()

        meal = args?.get("meal").toString()
        cuisine = args?.get("cuisine").toString()
        diet = args?.get("diet").toString()
        exclusions = args?.get("exclusions").toString()
        intolerances = args?.get("intolerances").toString()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchRecipe()
    }

    override fun onResume() {
        super.onResume()
        searchRecipe()
    }

    fun searchRecipe() {
        val service = ServiceBuilder.buildService(SearchService::class.java)
        val requestCall = service.searchForRecipe(query, meal, cuisine, diet, exclusions, intolerances)

        requestCall.enqueue(object : Callback<NutrientInfo> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<NutrientInfo>, response: Response<NutrientInfo>) {
                if (response.isSuccessful) {
                    if (query.isEmpty()) recipeResultHeader.text = "Search Results"
                    else recipeResultHeader.text = query + " Recipes"

                    var results: Int = response.body()!!.results.count()
                    if (results > 10) results = 10

                    if (results != 0) {
                        resultRecyclerView.layoutManager = GridLayoutManager(
                            context,
                            results,
                            GridLayoutManager.HORIZONTAL,
                            false
                        )
                        resultRecyclerView.adapter =
                            ResultsAdapter(response.body()!!.results, this@SearchResultsFragment)
                    } else {
                        fragmentManager?.beginTransaction()?.replace(R.id.fl_wrapper, emptyResultsFragment)
                            ?.commit()
                    }

                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("API error")
                            .setMessage("Response, but something went wrong ${response.message()}")
                            .setPositiveButton(android.R.string.ok) { _, _ -> }
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                }
            }
            override fun onFailure(call: Call<NutrientInfo>, t: Throwable) {
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("API error")
                        .setMessage("No response, something went wrong $t")
                        .setPositiveButton(android.R.string.ok) { _, _ ->}
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
            }
        })
    }

    override fun onItemClick(position: Int, id: String) {
        var id = id

        val bundle = Bundle()
        bundle.putString("id", id)
        val recipePage = RecipePage()
        recipePage.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.fl_wrapper, recipePage)?.commit()
    }
}