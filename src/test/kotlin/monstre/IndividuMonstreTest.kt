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
    // Cette méthode est exécutée automatiquement avant chaque test
    // Elle permet ici d'associer le palier d’évolution de Flamkip vers Pyrokip
    @BeforeTest
    fun setUp() {
        // Test principal : vérifie que l'évolution se fait bien au niveau 7
        especeFlamkip.palierEvolution = palierEvolutionFlamkip

    }

    @Test
    fun levelUp() {
        // Création d'u flamkip au niveau 5
        var monstre1 = IndividuMonstre(2, "flamkip", 1500.0, especeFlamkip,entraineur = null)
        // Vérification initiale : le monstre est bien un Flamkip de niveau 5
        assertEquals(especeFlamkip, monstre1.especeMonstre,)
        assertEquals(5, monstre1.niveau)
        // Passage au niveau 6 : il ne doit pas encore évoluer
        monstre1.levelUp()
        assertEquals(6, monstre1.niveau)
        assertEquals(especeFlamkip, monstre1.especeMonstre)
        // Passage au niveau 7 : le monstre doit évoluer en Pyrokip
        monstre1.levelUp()
        assertEquals(7, monstre1.niveau)
        assertEquals(especePyrokip, monstre1.especeMonstre)
    }
}



