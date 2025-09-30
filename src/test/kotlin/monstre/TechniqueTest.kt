package monstre

import org.example.monstre.Element
import org.example.monstre.IndividuMonstre
import org.example.monstre.Technique
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

import org.example.especeFlamkip
import org.example.especeSpringleaf

class TechniqueTest {

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
