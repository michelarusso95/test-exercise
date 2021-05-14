# TEST EXERCISE
## Pattern recognition for points and line segments

### create db image and run container to start a postgres instance
- `docker run --name test-postgres -p 5432:5432 -e POSTGRES_USER=test_user -e POSTGRES_DB=test -e POSTGRES_PASSWORD=000000 postgres`

### Start the application
- `mvn clean package`
- `java -jar target/test-exercise-0.0.1-SNAPSHOT.jar`
