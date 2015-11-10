# Livraison-OO

## Lancement du projet
Pour lancer le projet, il suffit de : 
 - Sous windows, double cliquer sur le fichier H4401.jar si java 8 est installé.
 - En ligne de commande : java - jar H4401.jar. 

Il est n'est pas nécessaire d'installer java 8 pour tester le projet. Il suffit de télécharger le jdk java et de le lancer directement à partir 
du dossier /bin. 

## Développement et chargement du projet dans un IDE

### Installation de Netbeans avec le JDK 8 intégré : 
On choisit la version qui correspond à son OS [ici !](http://www.oracle.com/technetwork/java/javase/downloads/jdk-netbeans-jsp-142931.html) 
Sur Linux ou Mac, il faut ajouter un ``sudo chmod a+x`` pour éxécuter. 

### Chargement du projet pour le développement
Il s'agit d'un projet Maven, il est donc reconnu automatiquement par les IDEs récents.
Il ne faut pas oublier de configurer les JDK en java 8 dans le paramètres de l'IDE.

 - Sous Netbeans : File -> Open Project.
 - Sous Intelij IDEA : File -> Import Project. Indiquer qu'il s'agit d'un projet Maven.
 - Sous Eclipse : Import Maven Project. 


### Pour éditer les fichiers fxml :
Pour éditer facilement les fichiers fxml, il peut être pratique d'utililser le *scene builder* de JavaFX. 
On peut le télécharger [ici !](http://www.oracle.com/technetwork/java/javafxscenebuilder-1x-archive-2199384.html#javafx-scenebuilder-2.0-oth-JPR)


