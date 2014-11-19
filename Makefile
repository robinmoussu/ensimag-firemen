# Ensimag 2A POO - TP 2014/15
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire $(SRC)
#     Les classes d'un package toto sont dans $(SRC)/toto
#     Les classes du package par defaut sont dans $(SRC)
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire $(BIN)
#     La hierarchie des sources (par package) est conservee.
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

PROJPATH=.

PACKAGE=

SRC=$(PROJPATH)/src
BIN=$(PROJPATH)/bin
TEST=$(PROJPATH)/test
JAVADOC=$(PROJPATH)/javadoc

CARTE=cartes/carteSujet.txt

########################################################################################

all: exeTest exeAfficheSimulation javadoc

########################################################################################
# programes

makeAfficheSimulation:
	javac -d $(BIN) -classpath $(CLASSPATH):$(BIN)/ihm.jar -sourcepath $(SRC) $(SRC)/AfficheSimulation.java

exeAfficheSimulation: exeAfficheSimulation_carteSujet exeAfficheSimulation_desertOfDeath exeAfficheSimulation_mushroomOfHell exeAfficheSimulation_spiralOfMadness

exeAfficheSimulation_carteSujet: makeAfficheSimulation
	java -classpath $(BIN):$(BIN)/ihm.jar AfficheSimulation cartes/carteSujet.txt

exeAfficheSimulation_desertOfDeath: makeAfficheSimulation
	java -classpath $(BIN):$(BIN)/ihm.jar AfficheSimulation cartes/desertOfDeath-20x20.map

exeAfficheSimulation_mushroomOfHell: makeAfficheSimulation
	java -classpath $(BIN):$(BIN)/ihm.jar AfficheSimulation cartes/mushroomOfHell-20x20.map

exeAfficheSimulation_spiralOfMadness: makeAfficheSimulation
	java -classpath $(BIN):$(BIN)/ihm.jar AfficheSimulation cartes/spiralOfMadness-50x50.map

clean:
	rm -rf $(BIN)/*.class

cleanJavadoc:
	rm -rf  $(JAVADOC)/*

########################################################################################
# documentation

javadoc: cleanJavadoc
	javadoc -locale fr_FR -encoding UTF8 -d $(JAVADOC) -classpath $(BIN)/ihm.jar -sourcepath $(SRC) $(SRC)/AfficheSimulation.java  $(SRC)/ConstructionException.java  $(SRC)/EteindreIncendies.java  $(SRC)/ExceptionFormatDonnees.java  $(SRC)/Manager.java           $(SRC)/RobotDrone.java   $(SRC)/SimulationException.java $(SRC)/Astar.java              $(SRC)/Date.java                   $(SRC)/Evenement.java          $(SRC)/Incendie.java                $(SRC)/ManagerScenario0.java  $(SRC)/Robot.java        $(SRC)/ValideCase.java $(SRC)/Carte.java              $(SRC)/Direction.java              $(SRC)/EventMoveRobot.java     $(SRC)/LecteurDonnees.java          $(SRC)/ManagerScenario1.java  $(SRC)/RobotPattes.java $(SRC)/Case.java               $(SRC)/DonneesSimulation.java      $(SRC)/EventRemplirRobot.java  $(SRC)/Managed.java                 $(SRC)/NatureTerrain.java     $(SRC)/RobotRoues.java $(SRC)/ChercherEau.java        $(SRC)/DoNothing.java              $(SRC)/EventViderRobot.java    $(SRC)/ManagerDynamique.java        $(SRC)/RobotChenilles.java    $(SRC)/Simulateur.java

########################################################################################
# tests

exeTest: exeTestJunit

#makeIHM:
	#javac -d $(BIN) -classpath $(BIN)/ihm.jar -sourcepath $(SRC) $(TEST)/TestIHM.java
#exeIHM: makeIHM
	#java -classpath $(BIN):$(BIN)/ihm.jar TestIHM

#makeTestLecture:
	#javac -d $(BIN) -sourcepath $(SRC) $(SRC)/TestLecteurDonnees.java
#exeTestLecture: makeTestLecture
	#java -classpath $(BIN) TestLecteurDonnees $(CARTE)


#---------  All the acces to lib.jar and .class used during the test ------
CLASSPATH=$(BIN):$(BIN)/ihm.jar

#--------- Name of Junit test in order of wanted excecution -------------  
JUNIT_LIST= $(PACKAGE)testAstar\
            $(PACKAGE)testCase\
			$(PACKAGE)testManagerDynamique\

JUNITPATH=  /usr/share/java/junit.jar

makeTestJunit:
	javac -d $(BIN) -classpath $(CLASSPATH):$(JUNITPATH) $(TEST)/*.java $(SRC)/*.java

exeTestJunit: makeTestJunit
	java -classpath $(CLASSPATH):$(JUNITPATH) org.junit.runner.JUnitCore $(JUNIT_LIST)



