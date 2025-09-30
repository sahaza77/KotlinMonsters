package org.example.monstre

class Technique (
    val id: Int,
    val nom: String,
    val precision: Double,   // en pourcentage (0.0 -> 100.0)
    val multiplicateurDePuissance: Double,
    val estBuff: Boolean,
    val estDebuff: Boolean,
    val faireDegat: Boolean,
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
            multiplicateurDePuissance + 15
        } else {
            val malus = multiplicateurDePuissance -15
            if (malus < 0.1) 0.1 else malus
        }
    }
}