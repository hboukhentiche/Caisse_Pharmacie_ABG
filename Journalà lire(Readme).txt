**sql commandes :
  create database caisse;
Le reste est bien intégré dans le code


**indications:
le fichier "data.txt" doit etre mis en partition "C:"(il est recu depuis le réseau)
il y a du awt et swing dans le code source


**Caisse_Pharmacie_MVN_Shel: exemple maven sous shel
   docs: http://moz-code.org/uqam/demos/INF2015/semaine12/

   créer un projet maven après avoir mis en palace "JAVA_HOME", "parfeu", path :
   mvn archetype:generate -DgroupId=ca.uqam.inf2015.mvntest -DartifactId=mvn-test -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

   Pour generer le fichier .jar du projet: (après la mis-à-jour du fichier pom.xml)        
   mvn assembly:single

**sous netbeans :
  Caisse_Pharmacie_MVN_NetBeans

** Approche de déployer le projet sur github à partir des spécifications xml sur le fichier pom
github:
mvn clean deploy
mvn deploy
mvn -U clean install
mvn -DaltDeploymentRepository=snapshot-repo::default::file:/Users/robin/Code/mvn-repo/ clean deploy
mvn -X deploy : debeugage
pom.xml é setting.xml
https://github.com/github/maven-plugins.git
commencer une bonne pratique de xml





P.S : au début, le deployement il fait plein de dowloanding et il beug à la fin; après quelques modifications disant majeures sur le fichier pom
 il beug dès le debut!!!!!!!!!!!!!!!!!
logicel github (le site aussi )ca marche pas aussi
logiciel : Apprendre Github(Git), SourceTree...

Finalement: sur bitbucket







Documentation des plugins github
2. Configure project to use gitHub plugins. 

 This step is the most important. We're going to configure the git maven plugins. I like to use maven profiles, so here is the whole profile used:
<profile>
   <id>gitrelease</id>
   <build>
    <plugins>
     <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-source-plugin</artifactId>
      <version>2.1.2</version>
      <executions>
       <execution>
        <phase>verify</phase>
        <goals>
         <goal>jar-no-fork</goal>
        </goals>
       </execution>
      </executions>
     </plugin>
     <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-javadoc-plugin</artifactId>
      <version>2.8.1</version>
      <executions>
       <execution>
        <phase>verify</phase>
        <goals>
         <goal>jar</goal>
        </goals>
       </execution>
      </executions>
     </plugin>
     <plugin>
      <groupId>com.github.github</groupId>
      <artifactId>site-maven-plugin</artifactId>
      <version>0.7</version>
      <configuration>
       <description>Official ${project.name} build of the
        ${project.version} release</description>
       <message>Official ${project.name} build</message>
       <server>github.com</server>
       <repositoryName>${git.name}</repositoryName>
       <repositoryOwner>Intelygenz</repositoryOwner>
      </configuration>
      <executions>
       <execution>
        <goals>
         <goal>site</goal>
        </goals>
        <phase>site</phase>
       </execution>
      </executions>
     </plugin>
     <plugin>
      <groupId>com.github.github</groupId>
      <artifactId>downloads-maven-plugin</artifactId>
      <version>0.6</version>
      <configuration>
       <description>${project.version} release of ${project.name}</description>
       <override>true</override>
       <includeAttached>true</includeAttached>
       <server>github.com</server>
      </configuration>
      <executions>
       <execution>
        <goals>
         <goal>upload</goal>
        </goals>
        <phase>deploy</phase>
       </execution>
      </executions>
     </plugin>
     <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-site-plugin</artifactId>
      <version>3.0</version>
      <configuration>
       <reportPlugins>
        <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-project-info-reports-plugin</artifactId>
         <version>2.5</version>
         <reportSets>
          <reportSet>
           <reports>
            <report>dependencies</report>
            <report>project-team</report>
            <report>mailing-list</report>
            <report>cim</report>
            <report>issue-tracking</report>
            <report>license</report>
            <report>scm</report>
           </reports>
          </reportSet>
         </reportSets>
        </plugin>
        <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-javadoc-plugin</artifactId>
         <version>2.7</version>
        </plugin>
        <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-surefire-report-plugin</artifactId>
         <version>2.6</version>
        </plugin>
       </reportPlugins>
      </configuration>
     </plugin>
    </plugins>
   </build>
</profile>