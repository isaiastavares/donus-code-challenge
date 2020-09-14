echo Donus Code Challenge: BUILDING
./mvnw clean install -U
clear

echo Donus Code Challenge: BUILD DONE
echo ---
echo Starting Application...
docker-compose up --build --force-recreate

sleep 60