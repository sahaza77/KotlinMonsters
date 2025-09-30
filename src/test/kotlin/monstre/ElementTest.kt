package monstre

import org.example.eau
import org.example.feu
import org.example.insecte
import org.example.monstre.Element
import org.example.monstre.Technique
import org.example.normal
import org.example.plante
import org.example.roche
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest

class ElementTest {

        @BeforeTest
        fun valorisation() {
            // 🔥 Feu
            feu.forces.addAll(listOf(plante, insecte))
            feu.faiblesses.addAll(listOf(eau, roche,feu))

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
        }
    @Test
    fun efficaciteContre() {
        assertEquals(  1.0,feu.efficaciteContre(normal))
        assertEquals(  2.0,feu.efficaciteContre(plante))
        assertEquals( 0.5,feu.efficaciteContre(feu))

        assertEquals(2.0, insecte.efficaciteContre(plante) )
    }
    @Test
    fun testCalculPrecision() {
        val technique100 = Technique(1, "Coup sûr", 100.0, 1.0, false, false, true, Element(1, "Feu"))
        val technique0 = Technique(2, "Jamais", 0.0, 1.0, false, false, true, Element(2, "Eau"))
        val technique50 = Technique(3, "Pile ou Face", 50.0, 1.0, false, false, true, Element(3, "Plante"))

        var compteurT50 = 0

        repeat(100) {
            // Test technique 100 (doit toujours réussir)
            assertTrue(technique100.calculPrecision())

            // Test technique 0 (doit toujours échouer)
            assertFalse(technique0.calculPrecision())

            // Test technique 50 (compte les réussites)
            if (technique50.calculPrecision()) {
                compteurT50++
            }
        }

        println("compteurT50 = $compteurT50")
        assertTrue(compteurT50 in 20..80)
    }
}