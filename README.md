Coding dojo : randori : fire pixels
===================================

Fourni
------

* Classe `ImgUtils` + tests associés : lecture / écriture d'images (Cf. javadoc),
* Images de référence dans `/src/test/resources`,
* Spritesheet viewer : page web d'affichage d'une animation de sprite sheets dans `/src/main/resources`.

Steps proposés
--------------

* Step 0 : Image blanche de 12 x 12 px ; image de référence dans `/src/test/resources/0.png`

* Step 1 : Dégradé linéaire de gris (blanc en bas de l'image, noir en haut) de 12 x 12 px ; 
  image de référence dans `/src/test/resources/1.png`

* Interlude : Obtenir un pixel (Color) à partir d'une chaleur exprimée par un entier `[0,511]` ; 
  on doit obtenir un gradient avec les points d'arrêts suivants, 
  et une interpolation linéaire entre ces points. 
  On dira qu'un pixel de valeur `0` est froid, et qu'un pixel de valeur `511` est chaud.

  | chaleur       | couleur | R     | G     | B     |
  | ------------- | ------- | ----- | ----- | ----- |
  | `0`           | noir    | `255` | `0`   | `0`   |
  | `128`         | rouge   | `128` | `0`   | `0`   |
  | `255`         | orange  | `255` | `127` | `0`   |
  | `383`         | jaune   | `255` | `255` | `127` |
  | `511`         | blanc   | `255` | `255` | `255` |

* Step 2 : Image de départ (1 ligne de pixels chaud en bas, des pixels froids partout ailleurs) ; 
  image de référence dans `/src/test/resources/2.png`

* Step 3 : Générer 120 frames ; le passage entre 1 frame et la suivante est réalisé par un filtre linéaire :
  noyau de convolution 

```
    { 
        { 2, 0, 2 }, 
        { 0, 1, 0 }, 
        { 3, 0, 3 } 
    } 
```

  sur l'intérieur de l'image (i.e. sans compter les lignes & colonnes des limites). 
  A chaque fois, la ligne du bas est remplie de pixels chauds, le reste de l'extérieur de pixels froids. 
  Images de référence dans `/src/test/resources/3/`

* Step 4 : Composition en sprite sheet (20 frames horizontales, 6 frames verticales) ; 
  image de référence dans `/src/test/resources/4.png`

* Step 5 : Enjoy the animation ! (utiliser `/src/main/resources/SpritesheetReader.html`)


Extensions possibles
--------------------

* Rendre aléatoire la ligne de pixels du bas de chaque image ;
* Rendre aléatoire la matrice de convolution (elle doit donc changer à chaque calcul d'un nouveau pixel)
* Changer la taille des images ;
* Rendu "8 bits" natif.
