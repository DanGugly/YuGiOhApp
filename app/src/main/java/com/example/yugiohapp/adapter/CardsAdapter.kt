package com.example.yugiohapp.adapter

import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yugiohapp.R
import com.example.yugiohapp.model.CardData
import com.example.yugiohapp.model.Data
import com.example.yugiohapp.utils.CardDetailsClick

class CardsAdapter(
    private val cardDetailsClick: CardDetailsClick,
    private val listOfCards: MutableList<Data> = mutableListOf()
) : RecyclerView.Adapter<CardViewHolder>() {

    fun setCards(cards : List<Data>){
        listOfCards.clear()
        listOfCards.addAll(cards)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.yugioh_cards_view, parent, false).apply{
            return CardViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = listOfCards[position]
        holder.setInfoToViewHolder(card)

        holder.itemView.setOnClickListener {
            //here i can move to next frag
            cardDetailsClick.moveToDetailsFragment(card.name)
        }
    }

    override fun getItemCount(): Int = listOfCards.size

}

class CardViewHolder(
    itemView : View
) : RecyclerView.ViewHolder(itemView) {
    val cardName : TextView = itemView.findViewById(R.id.card_name)
    val cardType : TextView = itemView.findViewById(R.id.card_type)
    val cardDesc : TextView = itemView.findViewById(R.id.card_desc)
    val cardImage : ImageView = itemView.findViewById(R.id.card_image)

    fun setInfoToViewHolder(item : Data){
        cardName.text = item.name
        cardType.text = item.type
        cardDesc.text = item.desc

        Glide.with(itemView)
            .load(item.cardImages[0].imageUrl)
            .placeholder(R.drawable.download_placeholder)
            .error(R.drawable.image_error)
            .fallback(R.drawable.fallback_image)
            .into(cardImage)
    }
}

