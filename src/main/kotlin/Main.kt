package org.example

import org.example.dresseur.Entraineur
import org.example.item.Badge
import org.example.item.MonsterKube
import org.example.jeu.CombatMonstre
import org.example.jeu.Partie
import org.example.monde.Zone
import org.example.monstre.EspeceMonstre
import org.example.monstre.IndividuMonstre

var joueur = Entraineur(1,"Sacha",100)
var rival = Entraineur(2, "Regis", 200)
val especeSpringleaf = EspeceMonstre(
    id = 1,
    nom = "Springleaf",
    type = "Graine",
    baseAttaque = 9,
    baseDefense = 11,
    baseVitesse = 10,
    baseAttaqueSpe = 12,
    baseDefenseSpe = 14,
    basePv = 60,
    modAttaque = 6.5,
    modDefense = 9.0,
    modVitesse = 8.0,
    modAttaqueSpe = 7.0,
    modDefenseSpe = 10.0,
    modPv = 34.0,
    description = "Petit monstre espiègle rond comme une graine, adore le soleil.",
    particularites = "Sa feuille sur la tête indique son humeur.",
    caracteres = "Curieux, amical, timide"
)
val especeFlamkip = EspeceMonstre(
    id = 4,
    nom = "Flamkip",
    type = "Animal",
    baseAttaque = 12,
    baseDefense = 8,
    baseVitesse = 13,
    baseAttaqueSpe = 16,
    baseDefenseSpe = 7,
    basePv = 50,
    modAttaque = 10.0,
    modDefense = 5.5,
    modVitesse = 9.5,
    modAttaqueSpe = 9.5,
    modDefenseSpe = 6.5,
    modPv = 22.0,
    description = "Petit animal entouré de flammes, déteste le froid.",
    particularites = "Sa flamme change d’intensité selon son énergie.",
    caracteres = "Impulsif, joueur, loyal"
)
val especeAquamy = EspeceMonstre(
    id = 7,
    nom = "Aquamy",
    type = "Meteo",
    baseAttaque = 10,
    baseDefense = 11,
    baseVitesse = 9,
    baseAttaqueSpe = 14,
    baseDefenseSpe = 14,
    basePv = 55,
    modAttaque = 9.0,
    modDefense = 10.0,
    modVitesse = 7.5,
    modAttaqueSpe = 12.0,
    modDefenseSpe = 12.0,
    modPv = 27.0,
    description = "Créature vaporeuse semblable à un nuage, produit des gouttes pures.",
    particularites = "Fait baisser la température en s’endormant.",
    caracteres = "Calme, rêveur, mystérieux"
)
val especeLaoumi = EspeceMonstre(
    id = 8,
    nom = "Laoumi",
    type = "Animal",
    baseAttaque = 11,
    baseDefense = 10,
    baseVitesse = 9,
    baseAttaqueSpe = 8,
    baseDefenseSpe = 11,
    basePv = 58,
    modAttaque = 9.0,
    modDefense = 10.0,
    modVitesse = 7.0,
    modAttaqueSpe = 9.0,
    modDefenseSpe = 11.0,
    modPv = 25.0,
    description = "Insecte à carapace luisante, se déplace par bonds et vibre des antennes.",
    particularites = "Se déplace par bonds, vibre des antennes.",
    caracteres = "Travailleur, sociable, infatigable"
)
val especeGalum = EspeceMonstre(
    id = 13,
    nom = "Galum",
    type = "Minéral",
    baseAttaque = 12,
    baseDefense = 15,
    baseVitesse = 6,
    baseAttaqueSpe = 8,
    baseDefenseSpe = 12,
    basePv = 55,
    modAttaque = 10.0,
    modDefense = 12.0,
    modVitesse = 4.0,
    modAttaqueSpe = 6.0,
    modDefenseSpe = 6.0,
    modPv = 26.0,
    description = "Golem ancien de pierre, très résistant, semble figé dans le temps.",
    particularites = "Peut rester immobile pendant des heures comme une statue.",
    caracteres = "Sérieux, stoïque, fiable"
)
val especePyrolyx = EspeceMonstre(
    id = 10,
    nom = "Pyrolyx",
    type = "Feu",
    baseAttaque = 14,
    baseDefense = 9,
    baseVitesse = 14,
    baseAttaqueSpe = 13,
    baseDefenseSpe = 8,
    basePv = 65,
    modAttaque = 11.0,
    modDefense = 5.0,
    modVitesse = 10.0,
    modAttaqueSpe = 10.0,
    modDefenseSpe = 7.0,
    modPv = 30.0,
    description = "Dragon de feu, crache des flammes ardentes, rapide et puissant.",
    particularites = "Peut cracher des flammes très chaudes.",
    caracteres = "Féroce, impulsif, loyal"
)
// Déclarations des zones
val route1 = Zone(
    id = 1,
    nom = "Route de la Plaine",
    expZone = 5,
    especesMonstres = mutableListOf(especeSpringleaf, especeFlamkip)
)

val route2 = Zone(
    id = 2,
    nom = "Chemin Rocailleux",
    expZone = 8,
    especesMonstres = mutableListOf(especeAquamy)
)
// Définition des items
val kube1 = MonsterKube(1, "Monster Kube", "Une capsule pour capturer des monstres", 50.0)

val monstre1 = IndividuMonstre(1, "springleaf", 1500.0, especeSpringleaf, null)
val monstre2 = IndividuMonstre(2, "flamkip", 1500.0, especeFlamkip, null)
val monstre3 = IndividuMonstre(3, "aquamy", 1500.0, especeAquamy, null)

fun main() {
    route1.zoneSuivante = route2
    route2.zonePrecedente = route1
    joueur.sacAItems.add(kube1)



    val partie = nouvellePartie()
    partie.choixStarter()
    partie.jouer()

    /* Ancien test commenté // Créer un MonsterKube avec une chance de capture, par exemple 50%
     val kube = MonsterKube(1, "Monster Kube", "Une capsule pour capturer des monstres", 50.0)

     val monstreJoueur = IndividuMonstre(1, "springleaf", 1500.0, especeSpringleaf, null)
     val monstreSauvage = IndividuMonstre(3, "aquamy", 1500.0, especeAquamy, null)

     joueur.equipeMonstre.add(monstreJoueur)
     val combat = CombatMonstre(monstreJoueur, monstreSauvage)
     combat.lanceCombat()*/
}
fun nouvellePartie(): Partie {
    // Affiche un message d'accueil pour la nouvelle partie
    println("Bienvenue dans le KotlinMonsters ! Préparez-vous à débuter une nouvelle aventure.")

    // Demande à l'utilisateur d'entrer son nom
    print("Veuillez entrer votre nom : ")
    val nomJoueur = readLine() ?: "Joueur" // Si l'utilisateur ne saisit rien, on utilise "Joueur"

    // Crée un nouvel entraîneur avec le nom saisi
    val entraineur = Entraineur(1, nomJoueur, 100)

    // Crée une zone initiale pour la nouvelle partie
    val zoneInitiale = Zone(
        id = 0,
        nom = "Zone de départ",
        expZone = 5,
        especesMonstres = mutableListOf(especeSpringleaf, especeFlamkip, especeAquamy)
    )

    // Crée une nouvelle instance de Partie avec l'entraîneur et la zone initiale
    val partie = Partie(
        id = 1,
        joueur = entraineur,
        zone = zoneInitiale
    )

    // Retourne la nouvelle partie créée
    return partie
}
/**
 * Change la couleur du message donné selon le nom de la couleur spécifié.
 * Cette fonction utilise les codes d'échappement ANSI pour appliquer une couleur à la sortie console. Si un nom de couleur
 * non reconnu ou une chaîne vide est fourni, aucune couleur n'est appliquée.
 *
 * @param message Le message auquel la couleur sera appliquée.
 * @param couleur Le nom de la couleur à appliquer (ex: "rouge", "vert", "bleu"). Par défaut c'est une chaîne vide, ce qui n'applique aucune couleur.
 * @return Le message coloré sous forme de chaîne, ou le même message si aucune couleur n'est appliquée.
 */
fun changeCouleur(message: String, couleur:String=""): String {
    val reset = "\u001B[0m"
    val codeCouleur = when (couleur.lowercase()) {
        "rouge" -> "\u001B[31m"
        "vert" -> "\u001B[32m"
        "jaune" -> "\u001B[33m"
        "bleu" -> "\u001B[34m"
        "magenta" -> "\u001B[35m"
        "cyan" -> "\u001B[36m"
        "blanc" -> "\u001B[37m"
        else -> "" // pas de couleur si non reconnu
    }
    return "$codeCouleur$message$reset"
}
