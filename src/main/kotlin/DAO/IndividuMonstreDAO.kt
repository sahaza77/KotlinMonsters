package org.example.DAO

import BDD
import org.example.monstre.IndividuMonstre
import org.example.monstre.EspeceMonstre
import org.example.dresseur.Entraineur
import java.sql.SQLException

/**
 * DAO (Data Access Object) permettant d'interagir avec la table `IndividusMonstres`.
 *
 * Cette classe gère les opérations de lecture :
 * - 🔍 Lecture (findAll)
 *
 * @param bdd L'objet de connexion à la base de données.
 */
class IndividuMonstreDAO(val bdd: BDD) {

    /**
     * Récupère tous les individus de monstres présents dans la base de données.
     * Chaque individu est lié à une espèce et à un entraîneur (clés étrangères).
     *
     * @return Une liste d'individus de monstres trouvés.
     */
    fun findAll(): MutableList<IndividuMonstre> {
        val result = mutableListOf<IndividuMonstre>()
        val sql = """
            SELECT im.*, em.nom AS nom_espece, en.nom AS nom_entraineur, en.argents AS argent_entraineur
            FROM IndividuMonstre im
            JOIN EspeceMonstre em ON im.id = em.id
            JOIN Entraineurs en ON im.id = en.id
        """.trimIndent()

        try {
            val requete = bdd.connectionBDD!!.prepareStatement(sql)
            val resultat = requete.executeQuery()

            while (resultat.next()) {
                // Création des objets associés
                val espece = EspeceMonstre(
                    id = resultat.getInt("idEspece"),
                    nom = resultat.getString("nom_espece"),
                    type = "", // à compléter si la colonne existe
                    baseAttaque = 0,
                    baseDefense = 0,
                    baseVitesse = 0,
                    baseAttaqueSpe = 0,
                    baseDefenseSpe = 0,
                    basePv = 0,
                    modAttaque = 0.0,
                    modDefense = 0.0,
                    modVitesse = 0.0,
                    modAttaqueSpe = 0.0,
                    modDefenseSpe = 0.0,
                    modPv = 0.0,
                    description = "",
                    particularites = "",
                    caracteres = ""
                )

                val entraineur = Entraineur(
                    id = resultat.getInt("idEntraineur"),
                    nom = resultat.getString("nom_entraineur"),
                    argents = resultat.getInt("argent_entraineur")
                )

                // Création de l'individu
                val individu = IndividuMonstre(
                    id = resultat.getInt("id"),
                    nom = resultat.getString("nom"),
                    expInit = resultat.getDouble("experience"),
                    especeMonstre = espece,
                    entraineur = entraineur
                )

                result.add(individu)
            }

            requete.close()
        } catch (e: SQLException) {
            println("Erreur lors de la récupération des individus : ${e.message}")
        }

        return result
    }
}