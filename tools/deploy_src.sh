cd ..
./gradlew bootJar
cd ..
mv extracted/build/libs/jarvis-*.jar jarvis.jar
rm -r extracted
kill $(ps aux | grep 'jarvis.jar' | grep -v grep | awk '{print $2}')
java -jar jarvis.jar &