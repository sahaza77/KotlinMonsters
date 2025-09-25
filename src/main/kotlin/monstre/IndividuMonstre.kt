package org.example.monstre

import org.example.dresseur.Entraineur
import kotlin.random.Random
import kotlin.math.pow
import kotlin.random.nextInt

class IndividuMonstre (
    val id: Int,
    var nom: String,
    expInit: Double, // Ce n'est pas une propriété directement
    val especeMonstre: EspeceMonstre,
    var entraineur: Entraineur?, //Nullable
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
    /**
     * Attaque un autre [IndividuMonstre] et inflige des dégâts.
     *
     * Les dégâts sont calculés comme suit :
     * `dégâts = attaque - (défense / 2)` (minimum 1 dégât).
     *
     * @param cible Monstre cible de l'attaque.
     */
    fun attaquer(cible: IndividuMonstre) {
        // Initialiser degatBrut
        val degatBrut = this.attaque

        // Calcul degatTotal
        var degatTotal = degatBrut - (cible.defense / 2)

        // S'assurer que degatTotal >= 1
        if (degatTotal < 1) {
            degatTotal = 1
        }

        // Enregistrer pvAvant
        val pvAvant = cible.pv

        // Infliger les dégâts (le setter pv gère les bornes)
        cible.pv -= degatTotal

        // Enregistrer pvApres
        val pvApres = cible.pv

        // Affichage
        println("${this.nom} inflige ${pvAvant - pvApres} dégâts à ${cible.nom}")
    }
    /**
     * Demande au joueur de renommer le monstre.
     * Si la saisie est vide, le nom n'est pas modifié.
     */
    fun renommer() {
        println("Renommer ${this.nom} ? (laisser vide pour garder le nom actuel)")

        val nouveauNom = readlnOrNull()?.trim() ?: ""

        if (nouveauNom.isEmpty()) {
            println("Le nom de ${this.nom} reste inchangé.")
        } else {
            println("Le nom de ${this.nom} devient $nouveauNom.")
            this.nom = nouveauNom
        }
    }
    /**
     * Affiche l’art ASCII de l’espèce et les détails du monstre côte à côte.
     */
    fun afficheDetail() {
        // Récupérer l’art ASCII depuis l’espèce
        val art = especeMonstre.afficheArt()
        val artLines = art.lines()

        // Construire la liste des détails
        val details = listOf(
            "Nom: $nom   Niveau: $niveau",
            "Exp: $experience",
            "PV: $pv / $pvMax",
            "====================",
            "Atq: $attaque   Def: $defense   Vitesse: $vitesse",
            "AtqSpe: $attaqueSpe   DefSpe: $defenseSpe",
            "===================="
        )

        // Largeur max de l’art (pour l’alignement)
        val maxArtWidth = artLines.maxOfOrNull { it.length } ?: 0
        // Nombre max de lignes à afficher
        val maxLines = maxOf(artLines.size, details.size)

        for (i in 0 until maxLines) {
            val artLine = if (i < artLines.size) artLines[i] else ""
            val detailLine = if (i < details.size) details[i] else ""
            val paddedArt = artLine.padEnd(maxArtWidth + 4) // 4 espaces de séparation
            println(paddedArt + detailLine)
        }
    }
}