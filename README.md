# TEST EXERCISE
## Pattern recognition for points and line segments

### Initial setup
- `docker run --name test-postgres -p 5432:5432 -e POSTGRES_USER=test_user -e POSTGRES_DB=test -e POSTGRES_PASSWORD=000000 postgres`
- docker run -v ./init.sql:/docker-entrypoint-initdb.d/init.sql test-postgres
### Start the application
- `docker start test-postgres`
- `mvn clean package`
- `java -jar target/test-exercise-0.0.1-SNAPSHOT.jar`
