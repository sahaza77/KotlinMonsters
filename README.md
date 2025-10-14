🐉 KotlinMonsters
🎮 Présentation du projet

KotlinMonsters est un projet réalisé dans le cadre du BTS SIO option SLAM.
Il s’inspire de l’univers des jeux Pokémon tout en ayant pour objectif de renforcer les compétences en programmation orientée objet (POO), bases de données, et architecture logicielle avec Kotlin.

Le joueur incarne un entraîneur qui capture et combat des monstres appelés KotlinMonsters.
Chaque monstre possède une espèce, un type (Feu, Eau, Plante, etc.) et des statistiques propres.
Le jeu évolue progressivement au fil des différents sprints de développement.
🎯 Objectifs pédagogiques

Le projet permet d’acquérir et de mettre en pratique les notions suivantes :

    🧩 Programmation orientée objet (classes, héritage, polymorphisme)
    🧠 Gestion de la logique de jeu et des interactions entre entités
    🧮 Manipulation de listes et de collections en Kotlin
    💾 Connexion et interaction avec une base de données relationnelle
    🧰 Utilisation du modèle DAO (Data Access Object) pour isoler la logique d’accès aux données
    🧪 Réalisation de tests unitaires
    🎨 Organisation du code en packages cohérents
    📁 Lecture de fichiers externes (ASCII Art, données)

⚙️ Technologies utilisées
Outil / Langage 	Utilisation
Kotlin 	Langage principal du projet
IntelliJ IDEA 	Environnement de développement
SQLite / MySQL 	Base de données pour stocker les données du jeu
JDBC 	Connexion entre Kotlin et la BDD
Gradle 	Gestion du projet et des dépendances
PlantUML 	Création de diagrammes de classes (ERD, UML)
Markdown 	Documentation du projet (README, compte rendu)

🧩 Résumé des sprints 🧱 Sprint 1 — Fondations du jeu

    Création des classes principales : Entraineur, Monstre, Zone, Partie

    Mise en place des combats de base entre monstres

    Gestion des points de vie, attaque et défense

    Introduction aux tests unitaires

    Première version jouable en console

⚔️ Sprint 2 — Extension du modèle

    Ajout de la hiérarchie des monstres (EspeceMonstre, IndividuMonstre)

    Introduction des types élémentaires (Feu, Eau, Plante, etc.)

    Mise en place du polymorphisme

    Création du fichier ArbreEvolutif.kt (gestion des évolutions)

    Ajout de l’arène et des combats plus complexes

    Lecture des ASCII Art des monstres (dans resources/art/voltigon)

    Amélioration du code avec la séparation des packages

💾 Sprint 3 — Persistance des données

    Création des tables SQL (Entraineurs, EspeceMonstre, IndividuMonstre, Zone, Zone_EspeceMonstre)

    Développement des classes DAO :

    EntraineurDAO

    EspeceMonstreDAO

    IndividuMonstreDAO

    Implémentation de la méthode findAll() pour chaque DAO

    Connexion à la BDD via JDBC (BDD.kt)

    Intégration de la récupération des espèces depuis la base dans Main.kt

    Amélioration du réalisme du jeu grâce à la persistance

🌟 Fonctionnalités principales

    Création d’un entraîneur et démarrage d’une partie

    Consultation et capture de monstres

    Combats entre monstres avec types et efficacité

    Zones d’exploration avec espèces différentes

    Sauvegarde et lecture des données depuis une base

    ASCII Art coloré des monstres (Voltigon 💚⚡)

    Architecture DAO modulaire

👨‍💻 Auteur

Nom : Randrianasolo Prénom : Sahazaniaina Formation : BTS SIO option SLAM Année : 2025 Projet : KotlinMonsters (Projet de développement orienté objet en Kotlin)

📘 Compte rendu complet

Pour découvrir le détail du développement, les diagrammes UML, les évolutions par sprint et les explications techniques du projet :

➡️ Ouvrez le fichier compte_rendu.md
