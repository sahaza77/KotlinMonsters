package monstre

import org.example.dresseur.Entraineur
import org.example.eau
import org.example.monstre.Element
import org.example.monstre.IndividuMonstre
import org.example.monstre.Technique
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

import org.example.especeFlamkip
import org.example.especeSpringleaf
import org.example.feu
import org.example.insecte
import org.example.normal
import org.example.plante
import org.example.roche
import kotlin.Boolean
import kotlin.math.ceil
import kotlin.test.BeforeTest
import kotlin.test.assertTrue


class TechniqueTest {

    @BeforeTest
    fun valorisation() {
        // 🔥 Feu
        feu.forces.addAll(listOf(plante, insecte))
        feu.faiblesses.addAll(listOf(eau, roche, feu))

        // 🌱 Plante
        plante.forces.addAll(listOf(eau, roche))
        plante.faiblesses.addAll(listOf(feu, insecte))

        // 💧 Eau
        eau.forces.addAll(listOf(feu, roche))
        eau.faiblesses.addAll(listOf(plante))

        // 🐞 Insecte
        insecte.forces.addAll(listOf(plante))
        insecte.faiblesses.addAll(listOf(feu, roche))

        // 🪨 Roche
        roche.forces.addAll(listOf(feu, insecte))
        roche.faiblesses.addAll(listOf(eau, plante))

        // ⚪ Normal

        normal.faiblesses.add(roche)
        especeFlamkip.elements.add(feu)
        especeSpringleaf.elements.add(plante)
    }
    @Test
    fun effet() {
            val techPlante = Technique(3, "Technique plante", 100.0, 1.0, false, false, true, false, plante)
            val monstreFeu = IndividuMonstre(1, "attaquant", 1.0, especeFlamkip, entraineur = null)
            val monstrePlante = IndividuMonstre(2, "defenseur", 1.0, especeSpringleaf, entraineur = null)

            //Pour eviter les variations de score d'attaque
            monstreFeu.attaque = 10
            monstrePlante.attaque = 10
            monstreFeu.attaqueSpe = 12
            monstrePlante.attaqueSpe = 12

            val degats1 = techPlante.effet(monstreFeu, monstrePlante)
            // degats1 = (10 * 0.85) * 1.0 = 8.5
            assertEquals( 8.5, degats1)

            val degats2 = techPlante.effet(monstrePlante, monstreFeu)
            // degats2 = (10 * 1.15) * 0.5 = 5.75
        assertEquals( 5.75, degats2)


            // ✅ Test attaque spéciale
            val techFeuSpe = Technique(4, "Flamme spéciale", 100.0, 1.0, false, false, true, true, feu)
            val degatsSpe = techFeuSpe.effet(monstreFeu, monstrePlante)
            // Exemple : (12 * 1.15) * efficacité (feu contre plante = 2.0)
        assertEquals( 27,degatsSpe.toInt())
        }

    @Test
    fun testCalculBonusStab() {
        val feu = Element(1, "Feu")
        val plante = Element(2, "Plante")

        // Technique de type Feu
        val techFeu = Technique(
            id = 1,
            nom = "Lance-Flammes",
            precision = 100.0,
            multiplicateurDePuissance = 50.0,
            estBuff = false,
            estDebuff = false,
            faireDegat = true,
            estSpecial = true,
            elementTechnique = feu
        )

        // Monstre de type Feu
        val monstreFeu = IndividuMonstre(
            id = 1,
            nom = "Flamkip",
            expInit = 0.0,
            especeMonstre = especeFlamkip,
            entraineur = null
        )

        monstreFeu.niveau = 5
        monstreFeu.exp = 0.0
        monstreFeu.attaque = 10
        monstreFeu.defense = 10
        monstreFeu.vitesse = 10
        monstreFeu.attaqueSpe = 10
        monstreFeu.defenseSpe = 10
        monstreFeu.pv = 20
        monstreFeu.pvMax = 20
        monstreFeu.especeMonstre.elements.add(feu)


        // Monstre de type Plante
        val monstrePlante = IndividuMonstre(
            id = 2,
            nom = "SpringLeaf",
            expInit = 0.0,
            especeMonstre = especeSpringleaf,
            entraineur = null
        )
        monstreFeu.niveau = 5
        monstreFeu.exp = 0.0
        monstreFeu.attaque = 10
        monstreFeu.defense = 10
        monstreFeu.vitesse = 10
        monstreFeu.attaqueSpe = 10
        monstreFeu.defenseSpe = 10
        monstreFeu.pv = 20
        monstreFeu.pvMax = 20
        monstrePlante.especeMonstre.elements.add(plante)

        val bonusFeu = techFeu.calculBonusStab(monstreFeu)
        val bonusPlante = techFeu.calculBonusStab(monstrePlante)

        println("Bonus Feu : $bonusFeu")
        println("Bonus Plante : $bonusPlante")

        assertEquals(65.0, bonusFeu)    // 50 + 0.15 = 65.0
        assertEquals(35.0, bonusPlante) // 50 - 0.15 = 35.0
    }
}
