mvn clean package
java -jar target/unisim-0.1.0.jar
mvn jacoco:report
mvn checkstyle:checkstyle