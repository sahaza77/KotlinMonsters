package org.example.item

import org.example.items.Item
import org.example.monstre.IndividuMonstre

class MonsterKube(
    id: Int,
    nom: String,
    description: String,
    var chanceCapture: Double
) : Item(id, nom, description), Utilisable {

    override fun utiliser(cible: IndividuMonstre): Boolean {
        println("Vous utilisez un Monster Kube sur ${cible.nom}")

        // Tirage aléatoire entre 0 et 100
        val nbAleatoire = Math.random() * 100

        return if (nbAleatoire < chanceCapture) {
            println("Félicitations ! Le monstre ${cible.nom} a été capturé.")
            true
        } else {
            println("La capture a échoué.")
            false
        }
    }
}