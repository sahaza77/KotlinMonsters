package org.example.items

/**
 * Représente un objet dans le jeu.
 *
 * @property id L'identifiant unique de l'objet.
 * @property nom Le nom de l'objet.
 * @property description La description de l'objet.
 */
open class Item(
    var id: Int,
    var nom: String,
    var description: String
)