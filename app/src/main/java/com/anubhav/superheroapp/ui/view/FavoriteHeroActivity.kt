package com.anubhav.superheroapp.ui.view

import android.graphics.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anubhav.superheroapp.R
import com.anubhav.superheroapp.data.db.FavIdDatabase
import com.anubhav.superheroapp.data.db.HeroIdData
import com.anubhav.superheroapp.ui.adapter.FavoriteHeroAdapter
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_favorite_hero.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteHeroActivity : AppCompatActivity() {

    private var favHeroList:List<HeroIdData>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_hero)
        deleteOnSwipe()

        GlobalScope.launch(Dispatchers.IO) {
            applicationContext?.let {
                val idList = FavIdDatabase(it).getFavHeroDao().getFavHeroId()
                withContext(Dispatchers.Main){


                    favHeroList=idList

                    favHeroRv.apply {
                        layoutManager = LinearLayoutManager(this@FavoriteHeroActivity)
                        adapter = FavoriteHeroAdapter(favHeroList as ArrayList<HeroIdData>,this@FavoriteHeroActivity)

                    }
                    if (favHeroList?.isNullOrEmpty() == true){
                        noHeroFoundFavoriteActivity.visibility=View.VISIBLE
                    }
                }

            }

        }



    }
    fun deleteOnSwipe(){
        val simpleItemTouchCallback=object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =viewHolder.adapterPosition

                if (direction==ItemTouchHelper.LEFT){
                    GlobalScope.launch(Dispatchers.IO) {
                        applicationContext?.let {
                           favHeroList?.get(position)?.let { it1 ->
                               FavIdDatabase(it).getFavHeroDao().deleteFavHero(
                                   it1
                               )
                           }
                            withContext(Dispatchers.Main){
                                //Toast.makeText(, "", Toast.LENGTH_SHORT).show()
                                Snackbar.make(favLayout,"Deleted",Snackbar.LENGTH_SHORT).show()

                            }

                        }

                    }
                    favHeroRv.apply {
                        adapter?.notifyDataSetChanged()
                    }
                }

            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {


//                    val itemView=viewHolder.itemView
//                    val paint=Paint()
//                    val icon:Bitmap
//                    if (dX<0){
//                        icon =BitmapFactory.decodeResource(resources,R.mipmap.ic_launcher)
//                     paint.color=   Color.parseColor("#389E4C")
//
//                        canvas.drawRect(
//
//                            itemView.right.toFloat()+dX,itemView.top.toFloat(),
//                            itemView.right.toFloat(),itemView.bottom.toFloat(),paint
//                        )
//
//                        canvas.drawBitmap(
//                            icon,itemView.right.toFloat(),
//                            itemView.top.toFloat()+(itemView.bottom.toFloat()-itemView.top.toFloat()-icon.height ),paint
//                        )
//                    }
//                    viewHolder.itemView.translationX=dX


                RecyclerViewSwipeDecorator.Builder(
                    canvas,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(Color.RED)
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate()
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper=ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(favHeroRv)
    }
}