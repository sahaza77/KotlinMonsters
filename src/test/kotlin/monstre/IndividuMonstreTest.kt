package monstre

import org.example.especeFlamkip
import org.example.especePyrokip
import org.example.monstre.EspeceMonstre
import org.example.monstre.IndividuMonstre
import org.example.palierEvolutionFlamkip
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class IndividuMonstreTest {
    @BeforeTest
    fun setUp() {
        especeFlamkip.palierEvolution = palierEvolutionFlamkip

    }

    @Test
    fun levelUp() {
        // Création d'u flamkip au niveau 5
        var monstre1 = IndividuMonstre(2, "flamkip", 1500.0, especeFlamkip,entraineur = null)
        assertEquals(especeFlamkip, monstre1.especeMonstre,)
        assertEquals(5, monstre1.niveau)
        monstre1.levelUp()
        assertEquals(6, monstre1.niveau)
        assertEquals(especeFlamkip, monstre1.especeMonstre)
        monstre1.levelUp()
        assertEquals(7, monstre1.niveau)
        assertEquals(especePyrokip, monstre1.especeMonstre)
    }
}

