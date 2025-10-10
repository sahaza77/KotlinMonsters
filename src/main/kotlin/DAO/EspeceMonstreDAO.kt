package org.example.DAO

import BDD
import org.example.monstre.EspeceMonstre
import java.sql.*

class EspeceMonstreDAO(val bdd: BDD) {

    // Récupère toutes les espèces présentes dans la table
    fun findAll(): List<EspeceMonstre> {
        val liste = mutableListOf<EspeceMonstre>()
        val requete = "SELECT * FROM EspeceMonstre"
        val connexion = bdd.connectionBDD

        if (connexion != null) {
            val statement = connexion.createStatement()
            val resultSet = statement.executeQuery(requete)

            while (resultSet.next()) {
                val espece = EspeceMonstre(
                    id = resultSet.getInt("id"),
                    nom = resultSet.getString("nom"),
                    type = resultSet.getString("type"),
                    baseAttaque = resultSet.getInt("baseAttaque"),
                    baseDefense = resultSet.getInt("baseDefense"),
                    baseVitesse = resultSet.getInt("baseVitesse"),
                    baseAttaqueSpe = resultSet.getInt("baseAttaqueSpe"),
                    baseDefenseSpe = resultSet.getInt("baseDefenseSpe"),
                    basePv = resultSet.getInt("basePv"),
                    modAttaque = resultSet.getDouble("modAttaque"),
                    modDefense = resultSet.getDouble("modDefense"),
                    modVitesse = resultSet.getDouble("modVitesse"),
                    modAttaqueSpe = resultSet.getDouble("modAttaqueSpe"),
                    modDefenseSpe = resultSet.getDouble("modDefenseSpe"),
                    modPv = resultSet.getDouble("modPv"),
                    description = resultSet.getString("description"),
                    particularites = resultSet.getString("particularites"),
                    caracteres = resultSet.getString("caracteres")
                )
                liste.add(espece)
            }
            statement.close()
        }
        return liste
    }

    // (Ajout plus tard des méthodes insert/update/delete)
}