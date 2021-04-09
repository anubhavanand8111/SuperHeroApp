package com.anubhav.superheroapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anubhav.superheroapp.R
import com.anubhav.superheroapp.data.db.HeroIdData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_hero.view.*

class FavoriteHeroAdapter(private val data: ArrayList<HeroIdData>, private val context: Context):
    RecyclerView.Adapter<FavoriteHeroAdapter.FavoriteHeroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=FavoriteHeroViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_hero,parent,false))

    override fun onBindViewHolder(
        holder: FavoriteHeroViewHolder,
        position: Int
    ) {
        val currentItem=data[position]

        holder.tv.text=currentItem.heroName
        Glide.with(context).load(currentItem.heroImage).circleCrop().diskCacheStrategy(
            DiskCacheStrategy.ALL).into(holder.iv)
    }

    override fun getItemId(position: Int): Long {

        return data[position].id.toLong()
    }

    override fun getItemCount()=data.size

   class FavoriteHeroViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tv: TextView =itemView.itemTv
        val iv: ImageView =itemView.itemIv

    }

}