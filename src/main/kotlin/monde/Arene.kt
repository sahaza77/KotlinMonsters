package org.example.monde

import org.example.dresseur.Entraineur
import org.example.dresseurClara
import org.example.jeu.CombatDresseur
import org.example.item.Badge

/**
 * Représente une arène située dans une ville.
 *
 * @property id Identifiant unique de l'arène.
 * @property nom Nom de l'arène.
 * @property dresseurs Liste des dresseurs à affronter (hors champion).
 * @property champion Le dresseur-champion final de l'arène.
 */
class Arene(
    val id: Int,
    val nom: String,
    val dresseurs: MutableList<Entraineur> = mutableListOf(),
    val champion: Entraineur
) {
    /**
     * Le joueur affronte la suite des dresseurs puis le champion.
     * Seuls sont affrontés les dresseurs qui ont au moins un monstre vivant.
     *
     * À la victoire finale, on donne un badge au joueur.
     */
    fun challenger(joueur: Entraineur) {
        println("🏟️ Vous entrez dans l'arène \"$nom\" !")
        // Construire la liste : dresseurs valides + champion (si champion a des monstres vivants)
        val listeInitiale = (dresseurs + champion)
            .filter { it.equipeMonstre.any { m -> m.pv > 0 } }
            .toMutableList()

        if (listeInitiale.isEmpty()) {
            println("Aucun dresseur valide à affronter dans cette arène.")
            return
        }

        // Id counter pour les combats dresseurs (simple incrément)
        var combatIdCounter = 1

        while (listeInitiale.isNotEmpty()) {
            val adversaire = listeInitiale.first()
            println("🆚 Prochain adversaire : ${adversaire.nom}")

            val combat = CombatDresseur(combatIdCounter++, joueur, adversaire)
            combat.lanceCombat()

            // Si le joueur a perdu -> on arrête et on soigne les équipes dans CombatDresseur
            if (combat.avoirPerdu()) {
                println("❌ ${joueur.nom} a été vaincu par ${adversaire.nom}. Fin de l'arène.")
                return
            } else {
                // joueur a gagné contre cet adversaire
                println("✅ ${joueur.nom} a vaincu ${adversaire.nom} !")
                // retirer adversaire de la liste
                listeInitiale.removeAt(0)
            }
        }

        // Si boucle terminée : joueur a battu tous les dresseurs et le champion
        println("🏆 ${joueur.nom} a gagné tous les combats dans l'arène \"$nom\" !")
        // Créer et donner un badge au joueur (voir variantes ci-dessous)
        val badge = Badge( id = 500 + id, nom = "Badge $nom", description = "Badge obtenu en gagnant l'arène $nom",
            dresseurClara
        )
        joueur.sacAItems.add(badge)
        println("🎖️ ${joueur.nom} remporte le badge « ${badge.nom} » !")
    }
}