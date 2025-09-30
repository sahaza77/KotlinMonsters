package org.example.monde

import org.example.monstre.EspeceMonstre


class Arene {

}
class LigneMagasin {
    // TODO : ajouter les propriétés comme item et quantité
}
// Classe représentant une ville, qui est une zone spéciale
// Elle hérite de Zone et ajoute des propriétés spécifiques aux villes
class Ville(
    id: Int,
    nom: String,
    expZone: Int,
    especesMonstres: MutableList<EspeceMonstre> = mutableListOf()
) : Zone(id, nom, expZone, especesMonstres) {

    // TODO - à implémenter plus tard
    lateinit var arene: Arene  // Une ville peut contenir une arène (à définir plus tard)
    var lignesMagasin: MutableList<LigneMagasin> = mutableListOf()     // Une ville peut avoir un magasin avec plusieurs articles à vendre
}
