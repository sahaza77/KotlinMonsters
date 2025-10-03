package org.example.items

import org.example.item.Utilisable
import org.example.monstre.IndividuMonstre
import org.example.monstre.Technique

class CapsuleTechnique(
    id: Int,
    nom: String,
    description: String,
    val technique: Technique
) : Item(id, nom, description = description), Utilisable {

    /**
     * Utilise la capsule sur un monstre -> lui apprend la technique.
     * Retourne true si l'utilisation a réussi.
     * N'apprend la technique que si la cible a un entraineur (donc appartient à un joueur).
     */
    override fun utiliser(cible: IndividuMonstre): Boolean {
        if (cible.entraineur == null) {
            println("Impossible d'utiliser $nom sur ${cible.nom} : le monstre est sauvage.")
            return false
        }
        println("Utilisation de la capsule $nom sur ${cible.nom} !")
        cible.apprendreTechnique(technique)
        return true
    }
}