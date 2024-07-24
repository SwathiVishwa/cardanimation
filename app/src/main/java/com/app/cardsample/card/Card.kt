package com.app.cardsample.card

import com.app.cardsample.R

data class Card(val id: Int,val value:String?="Value")

fun shuffleCards(cards: List<Card>): MutableList<Card> {
    return cards.shuffled().toMutableList()
}

fun getCards(): MutableList<Card> {
    val list = mutableListOf<Card>()
    list.add(Card(id= R.drawable.clubs1))
    list.add(Card(id= R.drawable.clubs2))
    list.add(Card(id= R.drawable.clubs3))
    list.add(Card(id= R.drawable.clubs4))
    list.add(Card(id= R.drawable.clubs5))
    list.add(Card(id= R.drawable.clubs6))
    list.add(Card(id= R.drawable.clubs7))
    list.add(Card(id= R.drawable.clubs8))
    list.add(Card(id= R.drawable.clubs9))
    list.add(Card(id= R.drawable.clubs10))
    return list
}
