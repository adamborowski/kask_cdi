#!/bin/bash
CLIENT_ALIAS=worker
CLIENT_KEYSTORE=keystore.jks
CLIENT_TRUSTSTORE=cacerts.jks
CLIENT_STORE_PASS=changeit
CLIENT_TRUSTSTORE_PASS=changeit
CLIENT_KEY_PASS=changeit
CLIENT_DOMAIN_NAME="cn=Worker, ou=Enterprise, o=KASK, c=PL"
GF_HOME=/home/psysiu/apps/glassfish-4.1
GF_DOMAIN=javaee
GF_STORE_PASS=changeit
GF_ALIAS=s1as

keytool -genkeypair -dname "$CLIENT_DOMAIN_NAME" -alias $CLIENT_ALIAS -keyalg RSA -keypass $CLIENT_KEY_PASS -storepass $CLIENT_STORE_PASS -keystore $CLIENT_KEYSTORE

keytool -list -keystore $CLIENT_KEYSTORE -storepass $CLIENT_STORE_PASS

#keytool -export -alias $CLIENT_ALIAS -storepass $CLIENT_STORE_PASS -file $CLIENT_ALIAS.cer -keystore $CLIENT_KEYSTORE

#keytool -delete -alias $CLIENT_ALIAS -keystore $GF_HOME/glassfish/domains/$GF_DOMAIN/config/cacerts.jks -storepass $GF_STORE_PASS

#keytool -import -v -trustcacerts -alias $CLIENT_ALIAS -file $CLIENT_ALIAS.cer -keystore $GF_HOME/glassfish/domains/$GF_DOMAIN/config/cacerts.jks -storepass $GF_STORE_PASS

#keytool -list -keystore $GF_HOME/glassfish/domains/$GF_DOMAIN/config/cacerts.jks -storepass $GF_STORE_PASS | grep $CLIENT_ALIAS

keytool -export -alias $GF_ALIAS -storepass $GF_STORE_PASS -file $GF_ALIAS.cer -keystore $GF_HOME/glassfish/domains/$GF_DOMAIN/config/cacerts.jks

keytool -import -v -trustcacerts -alias $GF_ALIAS -file $GF_ALIAS.cer -keystore $CLIENT_TRUSTSTORE -storepass $CLIENT_TRUSTSTORE_PASS

keytool -list -keystore $CLIENT_TRUSTSTORE -storepass $CLIENT_TRUSTSTORE_PASS
