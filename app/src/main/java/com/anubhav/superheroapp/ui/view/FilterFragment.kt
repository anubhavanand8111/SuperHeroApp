package com.anubhav.superheroapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.anubhav.superheroapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_filter.view.*
import java.util.*


class FilterFragment : BottomSheetDialogFragment() {

    lateinit var filterGender:String
    lateinit var filterAlignment:String
    lateinit var filterPublisher:String
    lateinit var sortBy:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setStyle(DialogFragment.STYLE_NO_FRAME, R.style.BottomSheetDialogTheme)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view:View= inflater.inflate(R.layout.fragment_filter, container, false)
        view.genderSpinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,SpinnerData.genderSpinner)
        view.alignmentSpinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,SpinnerData.alignmentSpinner)
        view.publisherSpinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,SpinnerData.publisherSpinner)
        view.sortBySpinner.adapter=ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,SpinnerData.sortBySpinner)


        view.genderSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               filterGender= SpinnerData.genderSpinner[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        view.alignmentSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               filterAlignment= SpinnerData.alignmentSpinner[position].toLowerCase(Locale.ROOT)


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        }

        view.publisherSpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                filterPublisher=SpinnerData.publisherSpinner[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        view.sortBySpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sortBy=SpinnerData.sortBySpinner[position].toLowerCase(Locale.ROOT)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        view.filterBtn.setOnClickListener {

        val intent = Intent(requireContext(),FilteredActivity::class.java)
            intent.putExtra("FILTEREDGENDER",filterGender)
            intent.putExtra("FILTEREDALIGNMENT",filterAlignment)
            intent.putExtra("FILTEREDPUBLISHER",filterPublisher)
            intent.putExtra("SORTBY",sortBy)
            startActivity(intent)

        }

        return view
    }
    object SpinnerData{
        val genderSpinner =arrayOf("Any","Male","Female")
        val alignmentSpinner = arrayOf("All","Good","Bad")
        val publisherSpinner = arrayOf("All","Marvel Comics","DC Comics")
        val sortBySpinner= arrayOf("None","Intelligence","Strength","Speed","Durability","Power","Combat")

    }

}