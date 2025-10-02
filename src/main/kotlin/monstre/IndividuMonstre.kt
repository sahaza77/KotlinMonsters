package org.example.monstre

import org.example.dresseur.Entraineur
import kotlin.random.Random
import kotlin.math.pow
import kotlin.random.nextInt

class IndividuMonstre (
    val id: Int,
    var nom: String,
    expInit: Double, // Ce n'est pas une propriété directement
    var especeMonstre: EspeceMonstre,
    var entraineur: Entraineur?, //Nullable
) {
    // Propriété technique ajoutée ici
    val technique: MutableList<Technique> = mutableListOf()
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
    fun evoluer() {
        // Remplace l'espèce actuelle du monstre par l'espèce d'évolution si elle existe,
        // sinon garde l'espèce actuelle (opérateur Elvis ?:)
        especeMonstre = especeMonstre.palierEvolution?.evolution ?: especeMonstre

        // Affiche un message indiquant que le monstre a évolué vers la nouvelle espèce
        println("Le monstre évolue en ${especeMonstre.nom} !")
    }

    fun palierExp(niveau: Int): Double {
        return 100 * Math.pow((niveau - 1).toDouble(), 2.0)
    }
    fun levelUp() {
        niveau++ // Incrémente le niveau du monstre de 1

        // Vérifie si l'espèce du monstre possède un palier d'évolution
        if (especeMonstre.palierEvolution != null) {
            // Vérifie si le monstre peut évoluer selon le palier
            if (especeMonstre.palierEvolution!!.peutEvoluer(this)) {
                evoluer() // Appelle la méthode pour faire évoluer le monstre
            } else {
                // Ne fait rien si les conditions d'évolution ne sont pas remplies
            }
        } else {
            // Affiche un message si aucun palier d'évolution n'est défini pour l'espèce
            println("Pas de palier d'évolution défini")
        }
        // Parcours de la liste de paliers techniques pour voir si une nouvelle technique peut être apprise
        especeMonstre.listepalierTechnique.forEach { palierTechnique ->
            if (palierTechnique.peutApprendre(this)) {
                // Suppose que 'Technique' est une propriété de 'palierTechnique'
                val techniqueAApprendre = palierTechnique.technique
                // Appel à la méthode apprendre pour ajouter la technique au monstre
                this.apprendreTechnique(techniqueAApprendre)
            }
            }
        val gainAttaque = modifieStat(especeMonstre.baseAttaque, -2..2)
        val gainDefense = modifieStat(especeMonstre.baseDefense, -2..2)
        val gainVitesse = modifieStat(especeMonstre.baseVitesse, -2..2)
        val gainAttSpe = modifieStat(especeMonstre.baseAttaqueSpe, -2..2)
        val gainDefSpe = modifieStat(especeMonstre.baseDefenseSpe, -2..2)
        val gainPvMax = modifieStat(especeMonstre.basePv, -5..5)

        attaque += gainAttaque
        defense += gainDefense
        vitesse += gainVitesse
        attaqueSpe += gainAttSpe
        defenseSpe += gainDefSpe

        pvMax += gainPvMax
        pv += gainPvMax
        if (pv > pvMax) pv = pvMax

        // Le reste du code levelUp() (mise à jour des caractéristiques, etc.) se place ici...
    }


    // Le reste du code levelUp, par exemple modifications des caractéristiques
        // ...

        // Fonction de base : (modCaractéristique * potentiel), arrondi
        fun modifieStat(modCaract: Int, bonusAleatoire: IntRange): Int {
            val base = Math.round(modCaract * potentiel).toInt()
            val alea = Random.nextInt(bonusAleatoire)
            return base + alea
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
// 1. Afficher la liste des techniques du monstre
        println("Quelle technique souhaitez-vous utiliser ?")
        for ((index, tech) in technique.withIndex()) {
            println("$index : ${tech.nom}")
        }

        // 2. Demander à l'utilisateur de choisir la technique
        var choixValide = false
        var choixTechniqueIndex = -1
        while (!choixValide) {
            print("Entrez le numéro de la technique : ")
            val input = readLine()
            val indexChoisi = input?.toIntOrNull()
            if (indexChoisi != null && indexChoisi in technique.indices) {
                choixTechniqueIndex = indexChoisi
                choixValide = true
            } else {
                println("Choix invalide, veuillez réessayer.")
            }
        }

        // 3. Récupérer la technique choisie
        val techniqueChoisie = technique[choixTechniqueIndex]

        // 4. Vérifier si la technique peut faire des dégâts
        if (!techniqueChoisie.faireDegat) {
            println("Cette technique ne peut pas infliger de dégâts.")
            return
        }

        // 5. Calculer les dégâts en utilisant la technique choisie
        val degatBrut = this.attaque * techniqueChoisie.multiplicateurDePuissance
        var degatTotal = degatBrut - (cible.defense / 2.0)
        if (degatTotal < 1) degatTotal = 1.0

        // 6. Sauvegarder PV avant
        val pvAvant = cible.pv

        // 7. Infliger les dégâts
        cible.pv -= degatTotal.toInt()

        // 8. Sauvegarder PV après
        val pvApres = cible.pv

        // 9. Afficher le résultat
        println("${this.nom} utilise ${techniqueChoisie.nom} et inflige ${pvAvant - pvApres} dégâts à ${cible.nom}.")
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
    fun apprendreTechnique(nouvelleTechnique: Technique) {
        // Vérifier si le nombre de techniques est supérieur ou égal à 3
        if (technique.size >= 3) {
            // Si oui, demander à l'utilisateur quelle technique il souhaite oublier
            println("Votre monstre connaît déjà 3 techniques. Quelle technique souhaitez-vous oublier ?")
            // Afficher la liste des techniques
            for ((index, tech) in technique.withIndex()) {
                println("$index : ${tech.nom}")
            }
            // Lire le choix utilisateur
            var choixValide = false
            var choixIndex: Int
            do {
                println("Entrez le numéro de la technique à oublier (0, 1 ou 2) :")
                val input = readLine()
                choixIndex = input?.toIntOrNull() ?: -1
                if (choixIndex in 0..2) {
                    choixValide = true
                } else {
                    println("Choix invalide. Veuillez entrer 0, 1 ou 2.")
                }
            } while (!choixValide)
            // Oublier la technique sélectionnée
            technique.removeAt(choixIndex)
            println("Technique oubliée.")
        }
        // Ajouter la nouvelle technique
        technique.add(nouvelleTechnique)
        println("Nouvelle technique apprise : ${nouvelleTechnique.nom}")
    }
}