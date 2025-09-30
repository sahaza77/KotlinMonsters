package org.example.monde

import org.example.dresseur.Entraineur
import org.example.jeu.CombatMonstre
import org.example.monstre.IndividuMonstre
import kotlin.random.Random
import org.example.monstre.EspeceMonstre

/**
 * Représente une zone géographique du monde (route, caverne, mer...).
 * Une zone peut contenir plusieurs espèces de monstres sauvages.
 *
 * @property id Identifiant unique de la zone.
 * @property nom Nom de la zone.œ
 * @property expZone Niveau d'expérience recommandé pour cette zone.
 * @property especesMonstres Liste des espèces de monstres présentes dans cette zone.
 * @property zoneSuivante Référence vers la zone suivante (ou null s'il n'y en a pas).
 * @property zonePrecedente Référence vers la zone précédente (ou null s'il n'y en a pas).
 */
// Classe de base représentant une zone du jeu
// On ajoute 'open' pour qu'elle puisse être héritée par Ville
open class Zone(
    val id: Int,
    val nom: String,
    val expZone: Int,
    val especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    var zoneSuivante: Zone? = null,
    var zonePrecedente: Zone? = null
) {
    /**
     * Génère un monstre sauvage appartenant à une espèce de la zone.
     * Son expérience est comprise entre -20% et +20% de l'expZone.
     *
     * @return Un [IndividuMonstre] généré aléatoirement.
     */
    fun genereMonstre(): IndividuMonstre {
        // 1. Choisir une espèce aléatoire
        val espece = especesMonstres.random()

        // 2. Calculer une expérience aléatoire (+/- 20 % de expZone)
        val minExp = (expZone * 0.8).toInt()
        val maxExp = (expZone * 1.2).toInt()
        val expAleatoire = Random.nextInt(minExp, maxExp + 1)

        // 3. Générer un ID unique provisoire (ici basé sur un random)
        val idMonstre = Random.nextInt(1000, 9999)

        // 4. Créer le monstre (entraineur = null car sauvage)
        return IndividuMonstre(
            id = idMonstre,
            nom = espece.nom,
            expInit = expAleatoire.toDouble(),
            especeMonstre = espece,
            entraineur = null
        )
    }
    fun rencontreMonstre(entraineur: Entraineur) {
        // 1. Générer un monstre sauvage
        val monstreSauvage = genereMonstre()
        println("Un ${monstreSauvage.nom} sauvage apparaît !")

        // 2. Chercher le premier monstre de l’équipe du joueur avec PV > 0
        var premierMonstre: IndividuMonstre? = null
        for (monstre in entraineur.equipeMonstre) {
            if (monstre.pv > 0) {
                premierMonstre = monstre
                break
            }
        }

        if (premierMonstre == null) {
            println("Tous vos monstres sont K.O. ! Vous ne pouvez pas combattre.")
            return
        }

        // 3. Créer un combat
        val combat = CombatMonstre(premierMonstre, monstreSauvage)

        // 4. Lancer le combat
        combat.lanceCombat()
    }
}