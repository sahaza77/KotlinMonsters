package org.example.dresseur

import org.example.items.Item
import org.example.monstre.IndividuMonstre

/**
 * Représente un entraîneur dans le contexte du jeu.
 *
 * Un entraîneur est responsable de gérer une équipe de monstres, une boîte pour stocker des monstres supplémentaires
 * et un sac contenant des objets appelés MonsterKubes. L'entraîneur a également une somme d'argent associée.
 *
 * @property id L'identifiant unique de l'entraîneur.
 * @property nom Le nom de l'entraîneur.
 * @property argents La quantité d'argent en possession de l'entraîneur.

 */
class Entraineur(
    var id: Int,
    var nom: String,
    var argents:Int,
    var equipeMonstre: MutableList<IndividuMonstre> = mutableListOf(),
    var boiteMonstre: MutableList<IndividuMonstre> = mutableListOf(),
    var sacAItems: MutableList<Item> = mutableListOf()
) {
    /**
     * Affiche les détails de l'entraîneur, y compris son nom et la quantité d'argent en sa possession.
     *
     * Cette méthode affiche les informations de l'entraîneur sous la forme de deux lignes :
     * 1. Le nom de l'entraîneur.
     * 2. La somme d'argent qu'il possède.
     */
    fun afficheDetail(){
        println("Dresseur : ${this.nom}")
        println("Argents: ${this.argents} ")
    }fun soigneEquipe() {
        // Parcours de chaque monstre dans l'équipe
        for (monstre in equipeMonstre) {
            // On assigne les PV actuels (pv) à la valeur maximale (pvMax)
            monstre.pv = monstre.pvMax
        }
    }
    fun choisirMonstre(): IndividuMonstre {
        // Filtrer la liste des monstres vivants (pv > 0)
        val monstresVivants = equipeMonstre.filter { it.pv > 0 }

        // Si un seul monstre est vivant, le retourner directement (choix automatique)
        if (monstresVivants.size == 1) {
            return monstresVivants[0]
        }

        // Afficher le message pour inviter à choisir un monstre
        println("Choisir un monstre de l'équipe :")

        // Afficher la liste des monstres vivants avec leur index et leurs PV
        monstresVivants.forEachIndexed { index, monstre ->
            println("$index: ${monstre.especeMonstre.nom} (PV: ${monstre.pv}/${monstre.pvMax})")
        }

        // Boucle pour demander une saisie valide à l'utilisateur
        while (true) {
            print("Entrez le numéro du monstre : ")

            // Lire la saisie utilisateur et tenter de la convertir en entier
            val choix = readLine()?.toIntOrNull()

            // Vérifier si le choix est valide (index dans la liste)
            if (choix != null && choix in monstresVivants.indices) {
                // Retourner le monstre choisi
                return monstresVivants[choix]
            }

            // En cas d'erreur, afficher un message et recommencer la saisie
            println("Choix invalide, réessayez.")
        }
    }



}