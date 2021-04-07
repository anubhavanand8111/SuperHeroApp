package com.anubhav.superheroapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.anubhav.superheroapp.R
import com.anubhav.superheroapp.data.db.FavIdDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchView.isSubmitButtonEnabled=true

        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let {
                  val i=Intent(this@MainActivity,SearchActivity::class.java)
                    i.putExtra("q",it)
                    startActivity(i)

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })


        filterCv.setOnClickListener {

            FilterFragment().apply {
                show(supportFragmentManager, "FilterFragment")
            }

        }

        allHeroCv.setOnClickListener {
            val i= Intent(this,AllSuperHeroActivity::class.java)
            startActivity(i)

        }

        favHeroCv.setOnClickListener {
            val i= Intent(this,FavoriteHeroActivity::class.java)
            startActivity(i)
        }

        getFavHeroCount()

       

    }

    private fun getFavHeroCount() {
        GlobalScope.launch(Dispatchers.IO) {
            applicationContext?.let {
                val idList = FavIdDatabase(it).getFavHeroDao().getFavHeroId()
                withContext(Dispatchers.Main) {
                    favoriteNo.text = idList.size.toString()

                }

            }

        }
    }

    override fun onResume() {
        getFavHeroCount()
        super.onResume()
    }




}