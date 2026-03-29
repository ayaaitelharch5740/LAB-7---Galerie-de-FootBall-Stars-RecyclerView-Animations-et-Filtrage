# ⚽ Football Players Gallery


> Application Android permettant d'afficher une galerie des meilleurs joueurs de football avec images, notes, filtrage dynamique et animations.

---

## 🎬 Démo Vidéo

```
https://github.com/user-attachments/assets/eaa6f454-102c-4e50-b7da-ca560d7f88ac

```

---

## 📱 Aperçu de l'Application

| Splash Screen | Liste des Joueurs | Modifier Note |
|:---:|:---:|:---:|
| Ballon animé + terrain | Cards avec photo, club, note | Popup RatingBar |

---

## ✨ Fonctionnalités

- 🎬 **Splash Screen animé** — ballon de foot qui tombe du ciel avec rotation et rebond
- 📋 **Liste RecyclerView** — affichage en cards avec photo circulaire, position, club et note
- 🔍 **Recherche dynamique** — filtrage en temps réel par nom de joueur ou par club
- ⭐ **Modification de note** — popup personnalisé avec RatingBar au clic sur un joueur
- 📤 **Partage** — partage de l'application via Intent Android
- 🏟️ **Design terrain de foot** — thème vert/jaune inspiré d'un terrain de football

---

## 🏗️ Architecture du Projet

```
com.example.footballplayersgallery
│
├── beans/
│   └── Player.java              # Modèle de données (id, nom, photo, position, club, note)
│
├── dao/
│   └── IDao.java                # Interface générique CRUD (add, modify, remove, fetchById, fetchAll)
│
├── service/
│   └── PlayerService.java       # Singleton — gestion des données + initialisation des joueurs
│
├── adapter/
│   └── PlayerAdapter.java       # RecyclerView.Adapter + Filterable + popup de modification
│
└── ui/
    ├── SplashActivity.java      # Écran d'accueil avec animations (AnimatorSet, BounceInterpolator)
    └── HomeActivity.java        # Activité principale : RecyclerView + SearchView + menu
```

---

## 🛠️ Technologies Utilisées

| Technologie | Version | Usage |
|---|---|---|
| **RecyclerView** | 1.3.1 | Affichage de la liste des joueurs |
| **Glide** | 4.15.1 | Chargement des images distantes |
| **CircleImageView** | 3.1.0 | Photos circulaires avec bordure |
| **CardView** | 1.0.0 | Design en cartes arrondies |
| **SearchView** | AndroidX | Barre de recherche dans la Toolbar |
| **AlertDialog** | AndroidX | Popup de modification de la note |
| **AnimatorSet** | Android SDK | Animations du splash screen |
| **ShareCompat / Intent** | AndroidX | Partage de l'application |

---

## ⚙️ Installation & Configuration

### Prérequis

- Android Studio **Hedgehog** ou version supérieure
- SDK Android minimum **API 24** (Android 7.0)
- Connexion Internet (pour le chargement des images)

### Étapes

**1. Cloner ou créer le projet**

```bash
# Créer un nouveau projet Android dans Android Studio
# Nom : FootballPlayersGallery
# Package : com.example.footballplayersgallery
# Langage : Java
# SDK minimum : API 24
```

**2. Configurer `settings.gradle.kts`**

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**3. Configurer `build.gradle.kts` (app)**

```kotlin
dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    implementation("androidx.cardview:cardview:1.0.0")
}
```

**4. Ajouter la permission Internet dans `AndroidManifest.xml`**

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

**5. Lancer l'application**

```
Run → Run 'app'  (Shift + F10)
```

---

## 📂 Structure des Fichiers Ressources

```
res/
├── layout/
│   ├── activity_splash.xml      # Layout du splash screen
│   ├── activity_home.xml        # Layout de l'activité principale
│   ├── item_player.xml          # Layout d'un item de la liste
│   └── dialog_edit_score.xml    # Layout du popup de modification
│
├── menu/
│   └── main_menu.xml            # Menu Toolbar (SearchView + Partager)
│
├── drawable/
│   ├── football_ball.xml        # Ballon de foot vectoriel (SVG)
│   ├── circle_field.xml         # Cercle central du terrain
│   └── badge_position.xml       # Badge de position (ATT, MIL...)
│
└── values/
    ├── colors.xml               # Palette de couleurs (vert terrain, jaune)
    ├── themes.xml               # Thème NoActionBar personnalisé
    └── strings.xml              # Chaînes de caractères
```

---

## 🎨 Palette de Couleurs

| Nom | Couleur | Usage |
|---|---|---|
| `grass_dark` | `#2D6A4F` 🟢 | Toolbar, bordures, badges |
| `grass_light` | `#52B788` 🟩 | Texte club, accents |
| `accent_yellow` | `#FFD60A` 🟡 | RatingBar, barre TOP PLAYERS |
| `bg_main` | `#F0F7F0` | Fond de l'application |
| `bg_card` | `#FFFFFF` | Fond des cartes joueurs |
| `text_primary` | `#1A1A2E` | Nom du joueur |

---

## 🔄 Fonctionnement du Filtrage

Le filtrage est implémenté via l'interface `Filterable` dans `PlayerAdapter` :

```
Utilisateur tape → onQueryTextChange()
      ↓
adapter.getFilter().filter(texte)
      ↓
performFiltering() → parcourt allPlayers
      ↓
Filtre par nom OU par club (contains)
      ↓
publishResults() → met à jour displayedPlayers
      ↓
notifyDataSetChanged() → RecyclerView rafraîchi
```

---

## ⭐ Fonctionnement de la Modification de Note

```
Clic sur un item
      ↓
showEditDialog(player, position)
      ↓
Popup AlertDialog avec CircleImageView + RatingBar
      ↓
Utilisateur déplace la barre
      ↓
Clic "OK" → player.setScore(newRating)
      ↓
PlayerService.getInstance().modify(player)
      ↓
notifyItemChanged(position) → carte mise à jour
```

---


## 📋 Patterns & Concepts Android utilisés

- ✅ **Pattern Singleton** — `PlayerService.getInstance()`
- ✅ **Pattern ViewHolder** — `PlayerViewHolder` dans `PlayerAdapter`
- ✅ **Pattern MVC** — séparation beans / service / adapter / ui
- ✅ **Interface générique** — `IDao<T>` avec méthodes CRUD
- ✅ **Interface Filterable** — filtrage dynamique du RecyclerView
- ✅ **AnimatorSet** — combinaison d'animations au splash
- ✅ **ObjectAnimator** — animations individuelles (translation, rotation, scale, alpha)
- ✅ **BounceInterpolator** — effet rebond du ballon
- ✅ **Glide** — chargement asynchrone des images avec placeholder

---





