package org.example.jeu

import org.example.dresseur.Entraineur
import org.example.monstre.IndividuMonstre

/**
 * Représente un combat enchaîné entre deux dresseurs (plusieurs duels).
 *
 * @property id Identifiant du combat dresseur.
 * @property joueur Le dresseur joueur.
 * @property entraineurAdversaire Le dresseur adverse (IA / PNJ).
 */
class CombatDresseur(
    val id: Int,
    val joueur: Entraineur,
    val entraineurAdversaire: Entraineur
) {

    /**
     * Retourne vrai si le joueur a gagné (tous les monstres adverses KO).
     */
    fun avoirGagner(): Boolean {
        return entraineurAdversaire.equipeMonstre.all { it.pv <= 0 }
    }

    /**
     * Retourne vrai si le joueur a perdu (tous ses monstres KO).
     */
    fun avoirPerdu(): Boolean {
        return joueur.equipeMonstre.all { it.pv <= 0 }
    }

    /**
     * Lance la série de duels entre le joueur et l'adversaire.
     *
     * Logique :
     * - Tant que ni gagnant ni perdant :
     *    - joueur choisit un monstre (choisirMonstre())
     *    - l'adversaire envoie un monstre vivant au hasard
     *    - on crée un CombatMonstre et on appelle lanceCombat()
     * - Si joueur gagne => attribution d'une récompense (moitié de l'argent adversaire)
     * - Sinon => soigner les équipes et afficher la défaite
     */
    fun lanceCombat() {
        println("⚔️ Combat entre ${joueur.nom} et ${entraineurAdversaire.nom} commence !")

        while (!avoirGagner() && !avoirPerdu()) {
            // 1) Choix du monstre du joueur
            val monstreJoueur: IndividuMonstre? = joueur.choisirMonstre()
            if (monstreJoueur == null) {
                println("${joueur.nom} n'a pas de monstre disponible pour combattre.")
                break
            }

            // 2) Choix d'un monstre vivant chez l'adversaire
            val adversairesValides = entraineurAdversaire.equipeMonstre.filter { it.pv > 0 }
            if (adversairesValides.isEmpty()) {
                // plus d'adversaires vivants -> fin
                break
            }
            val monstreAdverse = adversairesValides.random()

            // 3) Affichages et duel
            println("${joueur.nom} envoie ${monstreJoueur.nom} !")
            println("${entraineurAdversaire.nom} envoie ${monstreAdverse.nom} !")

            val combat = CombatMonstre(monstreJoueur, monstreAdverse)
            combat.lanceCombat()
            println("======== Fin d'un duel ========")
        }

        // Résultat global
        if (avoirGagner()) {
            val recompense = entraineurAdversaire.argents / 2
            println("🏆 ${joueur.nom} a vaincu ${entraineurAdversaire.nom} ! Récompense : $recompense")
            joueur.argents += recompense
        } else {
            println("💀 ${joueur.nom} a perdu contre ${entraineurAdversaire.nom}...")
            // Soigner les deux équipes (pour repartir en sécurité)
            joueur.soigneEquipe()
            entraineurAdversaire.soigneEquipe()
            println("Les deux équipes ont été soignées.")
        }
    }
}