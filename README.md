# spring_template


### OpenApi 3.1 & Swagger
* http://localhost:9080/actuator/swagger-ui


### Properties encryption

Encrypt example

```bash
# general (use configuration in properties file)
mvn -f ./application/pom.xml jasypt:encrypt-value \
-Djasypt.encryptor.password="masterpassword" \
-Djasypt.plugin.value="myV4rySecreyP@ssword"

# full
mvn -f ./application/pom.xml jasypt:encrypt-value \
-Djasypt.encryptor.algorithm="PBEWithMD5AndDES" \
-Djasypt.encryptor.string-output-type="base64" \
-Djasypt.encryptor.key-obtention-iterations="1000" \
-Djasypt.encryptor.pool-size="1" \
-Djasypt.encryptor.password="masterpassword" \
-Djasypt.plugin.value="myV4rySecreyP@ssword"
```

Decrypt example

```bash
# general (use configuration in properties file)
mvn -f ./application/pom.xml jasypt:decrypt-value \
-Djasypt.encryptor.password="masterpassword" \
-Djasypt.plugin.value="myV4rySecreyP@ssword"

# full
mvn -f ./application/pom.xml jasypt:decrypt-value \
-Djasypt.encryptor.algorithm="PBEWithMD5AndDES" \
-Djasypt.encryptor.string-output-type="base64" \
-Djasypt.encryptor.key-obtention-iterations="1000" \
-Djasypt.encryptor.pool-size="1" \
-Djasypt.encryptor.password="masterpassword" \
-Djasypt.plugin.value="myV4rySecreyP@ssword"
```