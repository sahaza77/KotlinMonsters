package org.example.monstre

class Element(
    val id: Int,
    val nom: String
) {
    val forces: MutableSet<Element> = mutableSetOf()
    val faiblesses: MutableSet<Element> = mutableSetOf()
    val immunises: MutableSet<Element> = mutableSetOf()

    /**
     * Détermine l'efficacité d'un élément courant contre un élément cible.
     *
     * @param elementCible L'élément contre lequel l'efficacité est évaluée.
     * @return Un multiplicateur indiquant l'efficacité :
     *  2.0 si l'élément cible est dans les forces.
     *  0.5 si l'élément cible est dans les faiblesses.
     *  0.0 si l'élément cible est dans les éléments immunisés.
     *  1.0 si l'élément cible n'est ni une force ni une faiblesse.
     */
    fun efficaciteContre(elementCible: Element): Double {
        return when {
            forces.contains(elementCible) -> 2.0
            faiblesses.contains(elementCible) -> 0.5
            immunises.contains(elementCible) -> 0.0
            else -> 1.0
        }
    }
}
