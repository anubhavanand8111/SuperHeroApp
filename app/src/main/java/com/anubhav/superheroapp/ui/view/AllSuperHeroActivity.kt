package com.anubhav.superheroapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anubhav.superheroapp.R
import com.anubhav.superheroapp.data.models.SuperHero
import com.anubhav.superheroapp.ui.adapter.SuperHeroAdapter
import com.anubhav.superheroapp.ui.viewmodel.SuperHeroViewModelG
import com.anubhav.superheroapp.utils.Status
import kotlinx.android.synthetic.main.activity_all_super_hero.*
import kotlinx.android.synthetic.main.activity_filtered.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_super_hero_detail.*


class AllSuperHeroActivity : AppCompatActivity(), SuperHeroAdapter.OnItemClickListener {


    private val vmG by lazy {
        ViewModelProvider(this).get(SuperHeroViewModelG::class.java)
    }

    private var superHeroesList = arrayListOf<SuperHero>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_super_hero)

        getAllHero()


    }

    private fun getAllHero() {
        vmG.getAllHeroes().observe(this, {

            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBarAllHero.visibility = View.GONE
                        resource.data?.let { v ->
                            superHeroesList.addAll(v)

                            rvAll.apply {
                                layoutManager = LinearLayoutManager(this@AllSuperHeroActivity)
                                adapter = SuperHeroAdapter(
                                    superHeroesList,
                                    this@AllSuperHeroActivity,
                                    this@AllSuperHeroActivity
                                )
                            }
                        }
                    }

                    Status.ERROR -> {
                        progressBarAllHero.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBarAllHero.visibility = View.VISIBLE

                    }

                }

            }

        })
    }

 override fun onItemClick(position: Int) {
        val clickedItem = superHeroesList[position]
        val id = clickedItem.id
        val intent = Intent(this, SuperHeroDetailActivity::class.java)
        intent.putExtra("ID", id)
        startActivity(intent)
    }
}