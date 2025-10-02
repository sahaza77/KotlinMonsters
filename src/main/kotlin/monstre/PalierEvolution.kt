package org.example.monstre

class PalierEvolution (
    val id: Int,                   // Identifiant unique du palier d'évolution
    val niveauRequis: Int,         // Niveau minimum requis pour que le monstre puisse évoluer
    val evolution: EspeceMonstre   // Espèce vers laquelle le monstre évolue
) {
    // Méthode qui vérifie si un individu peut évoluer en fonction de son niveau
    fun peutEvoluer(individu: IndividuMonstre): Boolean {
        // Retourne true si le niveau de l'individu est supérieur ou égal au niveau requis
        if (individu.niveau >= niveauRequis) {
            return true
        } else {
            // Sinon, retourne false
            return false
        }
    }
}