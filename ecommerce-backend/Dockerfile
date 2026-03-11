FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
#docker run -d \
#  --name elasticsearch \
#  -p 9200:9200 \
#  -e "discovery.type=single-node" \
#  -e "xpack.security.enabled=false" \
#  -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
#  docker.elastic.co/elasticsearch/elasticsearch:8.13.4