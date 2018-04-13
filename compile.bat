set path=%path%;C:\Program Files\Java\jdk1.7.0_79\bin

REM javac -cp .;./lib/ivy-java-1.2.17.jar -d ./bin src/util/*.java
REM javac -cp .;./lib/ivy-java-1.2.17.jar -d ./bin src/event/*.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/Trace.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/Entraineur.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/TraceRapportAnalyse.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/Classifieur.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/NewGesteEvent.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/NewGesteListener.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/NewGesteSender.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/ZoneDessin.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/IcarComponent.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/IcarPreview.java

javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/TimeBencher.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/ComponentScrollList.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/Interface.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/DialogEchantillonage.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/InterfaceClient.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/InterfaceAdmin.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/IcarClient.java
javac -cp .;./lib/ivy-java-1.2.17.jar;./bin -d ./bin src/IcarAdmin.java

javac -cp .;./lib/ivy-java-1.2.14.jar;./bin -d ./bin src/InterfaceClientIvy.java
javac -cp .;./lib/ivy-java-1.2.14.jar;./bin -d ./bin src/IcarClientIvy.java

pause