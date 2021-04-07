package com.anubhav.superheroapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anubhav.superheroapp.R
import com.anubhav.superheroapp.data.models.SuperHero
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_hero.view.*

class SuperHeroAdapter(private val data: ArrayList<SuperHero>, private val listener:OnItemClickListener, private val context:Context):RecyclerView.Adapter<SuperHeroAdapter.HeroViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=HeroViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hero,parent,false))

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {

        val currentItem=data[position]

        holder.tv.text=currentItem.name
        holder.tvRace.text=currentItem.appearance?.race
        Glide.with(context).load(currentItem.images?.md).circleCrop().diskCacheStrategy(
            DiskCacheStrategy.ALL).into(holder.iv)

        if (currentItem.biography?.alignment.equals("good")){
            holder.cvItem.setBackgroundResource(R.drawable.hero_card_good)
        }
        else if(currentItem.biography?.alignment.equals("bad")){
            holder.cvItem.setBackgroundResource(R.drawable.hero_card_bad)
        }


    }


    override fun getItemCount() = data.size


    inner class HeroViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val tv:TextView=itemView.itemTv
        val iv:ImageView=itemView.itemIv
        val tvRace:TextView=itemView.tvRace
        val cvItem:CardView=itemView.itemCv

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position=adapterPosition
            if (position!=RecyclerView.NO_POSITION){
           listener.onItemClick(position)}
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }


}