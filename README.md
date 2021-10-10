# Mini-projet Programmation Objet 2021-2022

Il va s'agir de développer un "magasin" en plusieurs étapes.
1. implémenter un magasin "générique" de manière guidée
2. implémenter un ensemble de produits à vendre
3. développer une mini-interface utilisateur utilisant vos produits et le magasin "générique"

## Consignes générales

1. Vous travaillerez en binôme.
2. Vous indiquerez la composition de votre binome sur le document partagé [suivant](https://docs.google.com/spreadsheets/d/1JvtT8TqX3M6Fc8hROxVpzr4ekxg1v0mb1pcAeg4zo1A/edit?usp=sharing)
3. Vous rendrez votre projet terminé d'ici à ***** vendredi 15 octobre 2021 18h00 *****.
4. Vous déposerez votre mini-projet via [Madoc](https://madoc.univ-nantes.fr/course/view.php?id=32488) en respectant le délai indiqué.


## Le magasin "générique"

Dans `src/` il ya un paquetage `magasin` qui contient une classe `Magasin` à compléter ainsi que 
plusieurs interfaces décrivant les grandes fonctionnalités attendues :
1. gestion des clients dans `iClientele`
2. gestion de stock dans `iStock`
3. gestion des paniers-clients dans `IPanier`

Dans `test/` plusieurs classes de test vous permettent
de valider votre implémentation.


## Des produits à vendre

Dans le paquetage `mesproduits` du dossier `src/` Vous définirez un ensemble de produits à vendre
selon votre inspiration. 

_Exemple :_ cela peut-être des sous-marins, des cartes pokemons ou des fruits et légumes.

l'objectif pédagogique est que vous montriez 
votre compréhension des concepts tels que l'héritage, la redéfinition, etc. ainsi que des 
fonctionnalités java avancées.

_Exemple :_ on pourrait alimenter la base produits à partir de requetes vers une API

Vous définirez également des cas de tests pour valider vos produits dans `test/mesproduits`.


## Une mini-application

Vous développerez dans `src/monapplication` une mini-application en mode console 
permettant de "jouer" avec votre magasin de produits à vendre. 
Il devra y avoir des données, des commandes, etc.  pré-chargées dans l'application.
