package org.example.monstre

class Technique (
    val id: Int,
    val nom: String,
    val precision: Double,   // en pourcentage (0.0 -> 100.0)
    val multiplicateurDePuissance: Double,
    val estBuff: Boolean,
    val estDebuff: Boolean,
    val faireDegat: Boolean,
    val estSpecial: Boolean,
    val elementTechnique: Element
) {
    /**
     * Détermine si la technique touche la cible ou non
     * On tire un nombre aléatoire entre 1 et 100 :
     * - si ce nombre <= précision => touche (true)
     * - sinon => rate (false)
     */
    fun calculPrecision(): Boolean {
        val nb = (1..100).random()
        return nb <= precision
    }

    /**
     * Calcule le bonus/malus STAB (Same Type Attack Bonus).
     *
     * - Si le monstre possède le même élément que la technique => +0.15
     * - Sinon => -0.15
     * - Le résultat ne doit jamais être < 0.1
     *
     * @param monstre Le monstre qui lance la technique
     * @return Le multiplicateur de puissance ajusté
     */
    fun calculBonusStab(monstre: IndividuMonstre): Double {
        return if (monstre.especeMonstre.elements.contains(elementTechnique)) {
            multiplicateurDePuissance + 0.15
        } else {
            val malus = multiplicateurDePuissance - 0.15
            if (malus < 0.1) 0.1 else malus
        }
    }
        fun effet(attaquant: IndividuMonstre, defenseur: IndividuMonstre): Double {
            // TODO gérer les buffs / debuffs plus tard
            if (!faireDegat) return 0.0

            // Choix attaque spéciale ou physique
            val degatsBase = if (estSpecial) attaquant.attaqueSpe else attaquant.attaque

            // Bonus STAB (si l’élément de la technique correspond à l’élément du monstre attaquant)
            val multiplicateur = calculBonusStab(attaquant)

            // Efficacité élémentaire contre le défenseur
            var multElement = elementTechnique.efficaciteContre(defenseur.especeMonstre.elements[0])

            // Si le défenseur a un deuxième type
            if (defenseur.especeMonstre.elements.size > 1) {
                multElement *= elementTechnique.efficaciteContre(defenseur.especeMonstre.elements[1])
            }

            // Calcul final
            return degatsBase * multiplicateur * multElement
        }
    }