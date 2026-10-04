# 📅 Gestion de Réservations Avancée

> Application Java de gestion de salles et de réservations avec **JPA / Hibernate 5** : recherche de salles disponibles par créneau, recherche multi-critères et pagination.

![Java](https://img.shields.io/badge/Java-8-orange)
![Hibernate](https://img.shields.io/badge/Hibernate-5.6.5-59666C)
![JPA](https://img.shields.io/badge/JPA-2.2-blue)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36)
![H2](https://img.shields.io/badge/DB-H2-1f4f8f)

## 📌 Présentation

Ce projet modélise la gestion de salles de réunion : salles, équipements, utilisateurs et réservations. Il met l'accent sur les requêtes avancées avec JPA : disponibilité d'une salle sur un créneau horaire, filtres combinés et affichage paginé des résultats.

## ✨ Fonctionnalités

- **Recherche de salles disponibles** sur un créneau donné (exclusion des salles déjà réservées)
- **Recherche multi-critères** : capacité minimale et maximale, bâtiment, étage
- **Pagination** des salles, avec un objet `PaginationResult` (page courante, taille de page, nombre total de pages et d'éléments, pages précédente et suivante)
- **Jeu de données de démonstration** généré au lancement (salles, équipements, utilisateurs, réservations)

## 🗃️ Modèle de données

| Entité | Rôle |
|---|---|
| `Salle` | Nom, capacité, description, bâtiment, étage, équipements |
| `Equipement` | Nom et description (projecteur, écran interactif, visioconférence) |
| `Utilisateur` | Nom, prénom, e-mail |
| `Reservation` | Créneau (début et fin), motif, utilisateur, salle |

Relations : une salle possède plusieurs équipements, et une réservation relie un utilisateur à une salle sur un créneau.

## 🛠️ Stack technique

| Domaine | Technologie |
|---|---|
| Langage | Java 8 |
| Persistance | JPA 2.2 (`javax.persistence`), Hibernate ORM 5.6.5 |
| Validation | Hibernate Validator 6.2 |
| Base de données | H2 (en mémoire) |
| Logs | SLF4J |
| Build | Maven |

## 🏗️ Architecture

```
com.example
├── App.java            # Point d'entrée : données de test et démonstrations
├── model/              # Entités JPA (Salle, Equipement, Utilisateur, Reservation)
├── repository/         # Accès aux données (SalleRepository + implémentation)
├── service/            # Logique métier (SalleService + implémentation)
└── util/               # PaginationResult
```

L'unité de persistance s'appelle `gestion-reservations`.

## 🚀 Lancer le projet

### Prérequis
- JDK 8 ou supérieur
- Maven 3.6+

### Exécution

```bash
# 1. Cloner le dépôt
git clone https://github.com/safaaOUARD/gestion-reservations-avancee.git
cd gestion-reservations-avancee

# 2. Compiler et lancer la classe principale
mvn compile exec:java -Dexec.mainClass="com.example.App"
```

La base H2 étant en mémoire, aucune installation n'est nécessaire. L'application affiche successivement trois démonstrations dans la console :

1. Salles disponibles par créneau
2. Recherche multi-critères
3. Pagination

## 🧠 Concepts mis en pratique

- Mapping objet-relationnel : relations entre entités (`Salle` ↔ `Equipement`, `Reservation` ↔ `Utilisateur` / `Salle`)
- Requêtes dynamiques construites à partir de critères optionnels
- Détection de chevauchement de créneaux horaires
- Pagination côté base de données
- Pattern **Repository** et couche **Service**

## 🔭 Améliorations possibles

- Ajouter des tests unitaires JUnit sur les services
- Migrer vers Hibernate 6 / Jakarta Persistence
- Exposer une API REST avec Spring Boot
- Passer à PostgreSQL avec Docker Compose

## 👤 Auteure

**Safaa OUARD** — Étudiante ingénieure en Systèmes d'Information et de Communication, ENSA El Jadida
[GitHub](https://github.com/safaaOUARD)
