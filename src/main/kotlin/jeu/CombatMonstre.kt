package org.example.jeu

import org.example.joueur
import org.example.monstre.IndividuMonstre
import org.example.item.Utilisable
import org.example.items.Item

class CombatMonstre (
    var monstreJoueur: IndividuMonstre, // changé en var pour pouvoir le remplacer dans changerMonstre
    val monstreSauvage: IndividuMonstre
) {
    var round: Int = 1

    /**
     * Vérifie si le joueur a perdu le combat.
     *
     * Condition de défaite :
     * - Aucun monstre de l'équipe du joueur n'a de PV > 0.
     *
     * @return `true` si le joueur a perdu, sinon `false`.
     */
    fun gameOver(): Boolean {
        return joueur.equipeMonstre.all { it.pv <= 0 }
    }

    fun joueurGagne(): Boolean {
        // Vérifie si le monstre sauvage a été capturé ou si ses PV sont <= 0
        if (monstreSauvage.entraineur == joueur || monstreSauvage.pv <= 0) {
            // Si PV <= 0, le joueur gagne de l'expérience
            if (monstreSauvage.pv <= 0) {
                // Calculer gain d'expérience
                val gainExp = monstreSauvage.exp * 0.2
                // Ajouter l'expérience au monstre du joueur
                monstreJoueur.exp += gainExp
                // Afficher le message de victoire
                println("${monstreJoueur.nom} a gagné !")
            }
            // Si capture, afficher le message
            if (monstreSauvage.entraineur == joueur) {
                println("${monstreSauvage.nom} a été capturé !")
            }
            return true
        }
        // Sinon, le joueur n'a pas gagné
        return false
    }

    fun actionAdversaire() {
        if (monstreSauvage.pv > 0) {
            // Le monstre sauvage attaque le monstre du joueur
            // Supposons que vous avez une méthode 'attaquer' dans IndividuMonstre
            monstreSauvage.attaquer(monstreJoueur)
            println("${monstreSauvage.nom} attaque ${monstreJoueur.nom} !")
        }
    }

    fun actionJoueur(): Boolean {
        if (gameOver()) return false

        println("Que souhaitez-vous faire ?")
        println("1 : Attaquer")
        println("2 : Utiliser un item")
        println("3 : Changer de monstre")
        print("Entrez votre choix : ")

        val choix = readLine()?.toIntOrNull() ?: 0

        when (choix) {
            1 -> {
                monstreJoueur.attaquer(monstreSauvage)
                println("${monstreJoueur.nom} attaque ${monstreSauvage.nom} !")
            }

            2 -> {
                if (!utiliserItem()) {
                    println("Action échouée, le combat continue.")
                }
            }

            3 -> {
                changerMonstre()
            }

            else -> {
                println("Choix invalide.")
            }
        }

        return !(monstreSauvage.pv <= 0 || gameOver())
    }

    fun utiliserItem(): Boolean {
        if (joueur.SacAItems.isEmpty()) { // corrigé sacItems -> sacAKube
            println("Votre sac est vide !")
            return false
        }

        println("Voici vos items :")
        joueur.SacAItems.forEachIndexed { index, item ->
            println("$index : ${item.nom} - ${item.description}") // item doit avoir nom et description
        }

        print("Entrez l'indice de l'item à utiliser : ")
        val index = readLine()?.toIntOrNull() ?: -1

        if (index in joueur.SacAItems.indices) {
            val item: Item = joueur.SacAItems[index]
            if (item is Utilisable) {
                val succes = item.utiliser(monstreSauvage)
                if (succes) {
                    println("Vous avez utilisé ${item.nom} avec succès.")
                    return true
                } else {
                    println("L'utilisation de ${item.nom} a échoué.")
                }
            } else {
                println("Cet item ne peut pas être utilisé.")
            }
        } else {
            println("Choix invalide.")
        }
        return false
    }

    fun changerMonstre() {
        println("Équipe de monstres disponibles :")
        joueur.equipeMonstre.forEachIndexed { index, monstre ->
            println("$index : ${monstre.nom} (PV: ${monstre.pv})")
        }

        print("Entrez l'indice du monstre à envoyer : ")
        val index = readLine()?.toIntOrNull() ?: -1

        if (index in joueur.equipeMonstre.indices) {
            val choixMonstre = joueur.equipeMonstre[index]
            if (choixMonstre.pv > 0) {
                println("${choixMonstre.nom} remplace ${monstreJoueur.nom} !")
                monstreJoueur = choixMonstre
            } else {
                println("Impossible ! Ce monstre est KO.")
            }
        } else {
            println("Choix invalide.")
        }
    }

    fun afficheCombat() {
        println("======= Combat ! =======")
        println()

        // Monstre sauvage
        println("Monstre sauvage :")
        println("Niveau: ${monstreSauvage.niveau}")
        println("PV: ${monstreSauvage.pv} / ${monstreSauvage.pvMax}")
        println("ASCII Art (face) :")
        println(monstreSauvage.especeMonstre.afficheArt(true))
        println()

        // Votre monstre
        println("Votre monstre :")
        println("Nom: ${monstreJoueur.nom}")
        println("Niveau: ${monstreJoueur.niveau}")
        println("PV: ${monstreJoueur.pv} / ${monstreJoueur.pvMax}")
        println("ASCII Art (dos) :")
        println(monstreJoueur.especeMonstre.afficheArt(false))
        println()
    }
    fun jouer() {
        // Détermine quel monstre joue en premier selon la vitesse
        val premier = if (monstreJoueur.vitesse >= monstreSauvage.vitesse) monstreJoueur else monstreSauvage
        val second = if (premier == monstreJoueur) monstreSauvage else monstreJoueur

        // Appelle actionJoueur() sur le premier monstre
        val continueJeu = actionJoueur()
        if (!continueJeu) {
            return  // Arrête la méthode si actionJoueur retourne false
        }

        // Si le premier monstre est encore en vie, le faire agir
        if (second.pv > 0) {
            // Appelle actionAdversaire() pour le second
            actionAdversaire()
        }
    }
    /**
     * Lance le combat et gère les rounds jusqu'à la victoire ou la défaite.
     *
     * Affiche un message de fin si le joueur perd et restaure les PV
     * de tous ses monstres.
     */
    fun lanceCombat() {
        while (!gameOver() && !joueurGagne()) {
            this.jouer()
            println("======== Fin du Round : $round ========")
            round++
        }
        if (gameOver()) {
            joueur.equipeMonstre.forEach { it.pv = it.pvMax }
            println("Game Over !")
        }
    }
}