package org.example.monstre

import org.example.dresseur.Entraineur
import kotlin.random.Random
import kotlin.math.pow
import kotlin.random.nextInt

class IndividuMonstre (
    val id: Int,
    val nom: String,
    expInit: Double, // Ce n'est pas une propriété directement
    val especeMonstre: EspeceMonstre,
    val entraineur: Entraineur?, //Nullable
) {
    var experience: Double = expInit
    var niveau: Int = 1

    var attaque: Int = especeMonstre.baseAttaque + Random.nextInt(-2, 3)
    var defense: Int = especeMonstre.baseDefense + Random.nextInt(-2, 3)
    var vitesse: Int = especeMonstre.baseVitesse + Random.nextInt(-2, 3)
    var attaqueSpe: Int = especeMonstre.baseAttaqueSpe + Random.nextInt(-2, 3)
    var defenseSpe: Int = especeMonstre.baseDefenseSpe + Random.nextInt(-2, 3)

    var pvMax: Int = especeMonstre.basePv + Random.nextInt(-5, 6)
    var potentiel: Double = Random.nextDouble(0.5, 2.0)

    var exp: Double = 0.0
        get() = field
        set(value) {
            field = value

            val estNiveau1 = (niveau == 1)

            // Tant que le monstre peut monter de niveau
            while (field >= palierExp(niveau)) {
                levelUp()
                if (!estNiveau1) {
                    println("Le monstre $nom est maintenant niveau $niveau !")
                }
            }
        }
    init {
        this.exp = expInit
    }
    /**
     * @property pv  Points de vie actuels.
     * Ne peut pas être inférieur à 0 ni supérieur à [pvMax].
     */
    var pv: Int = pvMax
        get() = field
        set(value) {
            field = when {
                value < 0 -> 0
                value > pvMax -> pvMax
                else -> value
            }
        }
    /**
     * Calcule l'expérience totale nécessaire pour atteindre un niveau donné.
     *
     * @param niveau Niveau cible.
     * @return Expérience cumulée nécessaire pour atteindre ce niveau.
     */
    fun palierExp(niveau: Int): Double {
        return 100 * Math.pow((niveau - 1).toDouble(), 2.0)
    }
    fun levelUp() {
        niveau++ // Incrément du niveau

        // Fonction de base : (modCaractéristique * potentiel), arrondi
        fun modifieStat(modCaract: Int, bonusAleatoire: IntRange): Int {
            val base = Math.round(modCaract * potentiel).toInt()
            val alea = Random.nextInt(bonusAleatoire)
            return base + alea
        }

        // Calculs
        val gainAttaque = modifieStat(especeMonstre.baseAttaque, -2..2)
        val gainDefense = modifieStat(especeMonstre.baseDefense, -2..2)
        val gainVitesse = modifieStat(especeMonstre.baseVitesse, -2..2)
        val gainAttSpe = modifieStat(especeMonstre.baseAttaqueSpe, -2..2)
        val gainDefSpe = modifieStat(especeMonstre.baseDefenseSpe, -2..2)
        val gainPvMax = modifieStat(especeMonstre.basePv, -5..5)

        // Mise à jour des stats
        attaque += gainAttaque
        defense += gainDefense
        vitesse += gainVitesse
        attaqueSpe += gainAttSpe
        defenseSpe += gainDefSpe

        // Mise à jour de pvMax et pv
        pvMax += gainPvMax
        pv += gainPvMax // Le PV augmente du gain

        // S'assurer que pv ne dépasse pas le nouveau pvMax
        if (pv > pvMax) pv = pvMax
    }
}

