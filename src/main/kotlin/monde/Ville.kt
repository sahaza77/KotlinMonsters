package org.example.monde

import org.example.monstre.EspeceMonstre


// Classe représentant une ville, qui est une zone spéciale
// Elle hérite de Zone et ajoute des propriétés spécifiques aux villes

open class Ville(
    id: Int,
    nom: String,
    expZone: Int,
    especesMonstres: MutableList<org.example.monstre.EspeceMonstre> = mutableListOf(),
    var arene: Arene? = null // <-- ajout ici
) : Zone(id, nom, expZone, especesMonstres) {
}
