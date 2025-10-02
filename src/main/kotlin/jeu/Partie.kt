package org.example.jeu

import org.example.dresseur.Entraineur
import org.example.especeAquamy
import org.example.especeFlamkip
import org.example.especeSpringleaf
import org.example.monde.Zone
import org.example.monstre.IndividuMonstre


class Partie(
    val id: Int,
    val joueur: Entraineur,
    var zone: Zone
) {
    fun choixStarter() {
        val monstre1 = IndividuMonstre(1, "springleaf", 1500.0, especeSpringleaf, null)
        val monstre2 = IndividuMonstre(2, "flamkip", 1500.0, especeFlamkip, null)
        val monstre3 = IndividuMonstre(3, "aquamy", 1500.0, especeAquamy, null)
        // Affichage du menu de sélection
        println("Sélectionnez votre monstre de départ :")
        println("1. ${monstre1.nom}")
        println("2. ${monstre2.nom}")
        println("3. ${monstre3.nom}")

        // Lecture du choix
        val choix = readlnOrNull()?.toIntOrNull()
        if (choix == null || choix !in 1..3) {
            println("Choix invalide.")
            return
        }

        // Confirmation du choix
        val starterChoisi = when (choix) {
            1 -> monstre1
            2 -> monstre2
            else -> monstre3
        }

        // Ajout à l'équipe du joueur
        joueur.equipeMonstre.add(starterChoisi)

        // Mise à jour du monstre avec son entraîneur
        starterChoisi.entraineur = joueur

        println("Vous avez choisi ${starterChoisi.nom} comme starter !")
    }

    fun modifierOrdreEquipe() {
        val equipe = joueur.equipeMonstre

        // Vérifier qu'il y a au moins deux monstres dans l'équipe
        if (equipe.size < 2) {
            println("L'équipe doit contenir au moins deux monstres pour modifier leur ordre.")
            return
        }

        // Afficher l'équipe avec leurs positions
        println("Votre équipe :")
        equipe.forEachIndexed { index, monstre ->
            println("${index + 1}. ${monstre.nom}")
        }

        // Demander la position du monstre à déplacer
        println("Entrez la position du monstre à déplacer (1 à ${equipe.size}):")
        val pos1 = readlnOrNull()?.toIntOrNull()

        // Demander la nouvelle position
        println("Entrez la nouvelle position (1 à ${equipe.size}):")
        val pos2 = readlnOrNull()?.toIntOrNull()

        // Vérifier que les positions sont valides
        if (pos1 == null || pos2 == null || pos1 !in 1..equipe.size || pos2 !in 1..equipe.size) {
            println("Positions invalides.")
            return
        }

        // Inverser la position des monstres
        val temp = equipe[pos1 - 1]
        equipe[pos1 - 1] = equipe[pos2 - 1]
        equipe[pos2 - 1] = temp

        println("L'ordre de l'équipe a été modifié.")
    }

    fun examineEquipe() {
        val equipe = joueur.equipeMonstre

        if (equipe.isEmpty()) {
            println("Votre équipe ne contient aucun monstre.")
            return
        }

        while (true) {
            // Afficher l'équipe avec leurs positions
            println("Votre équipe :")
            equipe.forEachIndexed { index, monstre ->
                println("${index + 1}. ${monstre.nom}")
            }
            println("Tapez le numéro du monstre pour voir ses détails, 'm' pour modifier l'ordre, ou 'q' pour revenir au menu principal:")

            val input = readlnOrNull()

            when {
                input == null -> {
                    println("Entrée invalide.")
                }

                input.equals("q", ignoreCase = true) -> {
                    // Quitter la fonction
                    return
                }

                input.equals("m", ignoreCase = true) -> {
                    // Modifier l'ordre de l'équipe
                    modifierOrdreEquipe()
                }

                else -> {
                    // Tenter de convertir en numéro
                    val index = input.toIntOrNull()
                    if (index != null && index in 1..equipe.size) {
                        // Afficher les détails du monstre
                        val monstre = equipe[index - 1]
                        afficherDetailsMonstre(monstre)
                    } else {
                        println("Numéro invalide.")
                    }
                }
            }
        }
    }

    // Fonction pour afficher tous les détails d'un monstre
    fun afficherDetailsMonstre(monstre: IndividuMonstre) {
        println("=== Détails du monstre ===")
        println("Nom : ${monstre.nom}")
        println("ID : ${monstre.id}")
        println("Points de vie : ${monstre.pv}")
        println("Espece : ${monstre.especeMonstre.nom}")
        // Ajoute ici d’autres caractéristiques si disponibles
        println("Art : ${monstre.especeMonstre.afficheArt()}")
        println("===========================")
    }

    fun jouer() {
        // Afficher la zone actuelle
        println("Vous êtes dans la zone : ${zone.nom}")

        // Afficher les actions possibles
        println("Que souhaitez-vous faire ?")
        println("1 - Rencontrer un monstre sauvage")
        println("2 - Examiner l’équipe de monstres")
        println("3 - Aller à la zone suivante")
        println("4 - Aller à la zone précédente")
        print("Entrez le numéro de votre choix : ")

        // Lire la réponse de l'utilisateur
        when (readLine()?.trim()) {
            "1" -> {
                // Appeler la méthode rencontreMonstre() de la zone
                zone.rencontreMonstre(entraineur = joueur)
            }

            "2" -> {
                // Appeler la méthode examineEquipe() de la zone
                this.examineEquipe()
            }

            "3" -> {
                // Vérifier si zoneSuivante existe
                val zoneSuivante = zone.zoneSuivante
                if (zoneSuivante != null) {
                    // Modifier la zone courante
                    zone = zoneSuivante
                    println("Vous avancez vers la zone suivante.")
                } else {
                    println("Il n'y a pas de zone suivante.")
                }
            }

            "4" -> {
                // Vérifier si zonePrecedente existe
                val zonePrecedente = zone.zonePrecedente
                if (zonePrecedente != null) {
                    // Modifier la zone courante
                    zone = zonePrecedente
                    println("Vous revenez à la zone précédente.")
                } else {
                    println("Il n'y a pas de zone précédente.")
                }
            }

            else -> {
                println("Choix invalide. Veuillez réessayer.")
            }
        }
    }
}