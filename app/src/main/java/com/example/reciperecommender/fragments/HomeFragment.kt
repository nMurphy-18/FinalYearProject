package com.example.reciperecommender.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.reciperecommender.MainActivity
import com.example.reciperecommender.R
import com.example.reciperecommender.dataClasses.Recipe
import com.example.reciperecommender.services.CardService
import com.example.reciperecommender.services.ServiceBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var recipeOneId: String
    lateinit var recipeTwoId: String
    lateinit var recipeThreeId: String
    lateinit var recipeFourId: String

    lateinit var txtOne: TextView
    lateinit var txtTwo: TextView
    lateinit var txtThree: TextView
    lateinit var txtFour: TextView

    lateinit var imgOne: ImageView
    lateinit var imgTwo: ImageView
    lateinit var imgThree: ImageView
    lateinit var imgFour: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        txtOne = view.findViewById(R.id.txtTitle1)
        txtTwo = view.findViewById(R.id.txtTitle2)
        txtThree = view.findViewById(R.id.txtTitle3)
        txtFour = view.findViewById(R.id.txtTitle4)

        imgOne = view.findViewById(R.id.imgRecipe1)
        imgTwo = view.findViewById(R.id.imgRecipe2)
        imgThree = view.findViewById(R.id.imgRecipe3)
        imgFour = view.findViewById(R.id.imgRecipe4)

        view.recipeCard1.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", recipeOneId)
            val recipePage = RecipePage()
            recipePage.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fl_wrapper, recipePage)?.commit()

        }

        view.recipeCard2.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", recipeTwoId)
            val recipePage = RecipePage()
            recipePage.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fl_wrapper, recipePage)?.commit()

        }

        view.recipeCard3.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", recipeThreeId)
            val recipePage = RecipePage()
            recipePage.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fl_wrapper, recipePage)?.commit()

        }

        view.recipeCard4.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", recipeFourId)
            val recipePage = RecipePage()
            recipePage.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fl_wrapper, recipePage)?.commit()

        }

        view.searchLinkBtn.setOnClickListener {
            (activity as MainActivity).searchLink()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRecipeCardData()
    }

    override fun onResume() {
        super.onResume()

        loadRecipeCardData()
    }

    fun loadRecipeCardData() {
        val service = ServiceBuilder.buildService(CardService::class.java)
        val requestCall = service.searchRecipe()

        requestCall.enqueue(object : Callback<Recipe> {
            override fun onResponse(call: Call<Recipe>, response: Response<Recipe>) {
                if (response.isSuccessful) {
                    val recipe = response.body()!!

                    recipeOneId = recipe.results[0].id.toString()
                    txtOne.text = recipe.results[0].title
                    Picasso.get().load(recipe.results[0].image).into(imgOne)

                    recipeTwoId = recipe.results[1].id.toString()
                    txtTwo.text = recipe.results[1].title
                    Picasso.get().load(recipe.results[1].image).into(imgTwo)

                    recipeThreeId = recipe.results[2].id.toString()
                    txtThree.text = recipe.results[2].title
                    Picasso.get().load(recipe.results[2].image).into(imgThree)

                    recipeFourId = recipe.results[3].id.toString()
                    txtFour.text = recipe.results[3].title
                    Picasso.get().load(recipe.results[3].image).into(imgFour)

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
            override fun onFailure(call: Call<Recipe>, t: Throwable) {
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
}