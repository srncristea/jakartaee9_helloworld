# Use latest jboss/base-jdk:11 image as the base

ARG JDK_VERSION=11
FROM jboss/base-jdk:${JDK_VERSION}

ENV WILDFLY_VERSION 23.0.2.Final
ENV WILDFLY_SHA1 9fe4754d9bd05711ca9de4a2b04c585d33c31af5
ENV JBOSS_HOME /opt/jboss/wildfly
ENV KEYCLOAK_VERSION 12.0.4
ENV HOME $PWD
ENV POSTGRESQL_VERSION 42.2.19

USER root

RUN cd $HOME \
    && curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-preview-$WILDFLY_VERSION.tar.gz \
    && sha1sum wildfly-preview-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 \
    && tar xf wildfly-preview-$WILDFLY_VERSION.tar.gz \
    && mv $HOME/wildfly-preview-$WILDFLY_VERSION $JBOSS_HOME \
    && rm wildfly-preview-$WILDFLY_VERSION.tar.gz \
    && $JBOSS_HOME/bin/add-user.sh wildfly wildfly

# create Wildfly database(postgresql) module.
COPY src/main/docker/wildfly/database/postgresql-$POSTGRESQL_VERSION.jar ${JBOSS_HOME}/modules/system/layers/base/org/postgresql/main/
COPY src/main/docker/wildfly/database/module.xml ${JBOSS_HOME}/modules/system/layers/base/org/postgresql/main/

#Copying required scripts and files
COPY src/main/docker/wildfly/configuration/standalone.xml ${JBOSS_HOME}/standalone/configuration/
COPY src/main/docker/wildfly/configuration/standalone.conf ${JBOSS_HOME}/bin/

# deploy war on wildlfy
COPY target/*.war ${JBOSS_HOME}/standalone/deployments/jk33_9_hw.war


#
# install wildfly-keyclock adapter
#
#RUN cd $JBOSS_HOME && \
#    curl -LfsSo keycloak-oidc-wildfly-adapter-$KEYCLOAK_VERSION.tar.gz  \
#        https://github.com/keycloak/keycloak/releases/download/$KEYCLOAK_VERSION/keycloak-oidc-wildfly-adapter-$KEYCLOAK_VERSION.tar.gz  && \
#    tar xf keycloak-oidc-wildfly-adapter-$KEYCLOAK_VERSION.tar.gz && \
#    rm -rf keycloak-oidc-wildfly-adapter-$KEYCLOAK_VERSION.tar.gz && \
#    cd $HOME && \
#    $JBOSS_HOME/bin/jboss-cli.sh --file=$JBOSS_HOME/bin/adapter-elytron-install-offline.cli


RUN chown -R jboss:0 ${JBOSS_HOME} && \
    chmod -R g+rw ${JBOSS_HOME}

# Ensure signals are forwarded to the JVM process correctly for graceful shutdown
ENV LAUNCH_JBOSS_IN_BACKGROUND true

USER jboss

ENV JAVA_OPTS="$JAVA_OPTS -Xdebug -agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n"

# Standalone.xml modifications.
#RUN sed -i -e 's/<extensions>/&\n        <extension module="org.keycloak.keycloak-adapter-subsystem"\/>/' $JBOSS_HOME/standalone/configuration/standalone.xml && \
#    sed -i -e 's/<profile>/&\n        <subsystem xmlns="urn:jboss:domain:keycloak:1.1"\/>/' $JBOSS_HOME/standalone/configuration/standalone.xml && \
#    sed -i -e 's/<security-domains>/&\n    <security-domain name="keycloak">\n <authentication>\n  <login-module code="org.keycloak.adapters.jboss.KeycloakLoginModule" flag="required"\/>\n <\/authentication>\n <\/security-domain>/' $JBOSS_HOME/standalone/configuration/standalone.xml && \
#    sed -i -e 's/<extensions>/&\n        <extension module="org.keycloak.keycloak-saml-adapter-subsystem"\/>/' $JBOSS_HOME/standalone/configuration/standalone.xml && \
#    sed -i -e 's/<profile>/&\n        <subsystem xmlns="urn:jboss:domain:keycloak-saml:1.1"\/>/' $JBOSS_HOME/standalone/configuration/standalone.xml

# Expose the ports we're interested in
EXPOSE 8080
EXPOSE 5005

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]