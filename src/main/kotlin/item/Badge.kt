package org.example.item

import org.example.dresseur.Entraineur
import org.example.items.Item

class Badge(
    id: Int,
    nom: String,
    description: String,
    var champion: Entraineur
) : Item(id, nom, description) {
}