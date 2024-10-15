FROM harbor-gn2.cicd.s15m.nl/docker-hub/library/eclipse-temurin:21

WORKDIR "/opt/app"

COPY target/app*.jar app.jar

ENTRYPOINT exec java -Dloader.path=${PROPERTIES_CONFIG_ZIP} -Djasypt.encryptor.password=${JASYPT_PWD} -jar app.jar
