mvn clean package
mvn jacoco:report
mvn checkstyle:checkstyle
java -jar target/unisim-0.1.0.jar