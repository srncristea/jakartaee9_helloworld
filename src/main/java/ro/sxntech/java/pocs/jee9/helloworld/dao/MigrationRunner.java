package ro.sxntech.java.pocs.jee9.helloworld.dao;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.flywaydb.core.Flyway;

import ro.sxntech.java.pocs.jee9.helloworld.infrastructure.properties.DatabaseProperties;
import ro.sxntech.java.pocs.jee9.helloworld.service.JakartaEE9_Exception;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class MigrationRunner {

    private DatabaseProperties databaseProperties;

    public void run() {
        log.debug("Run database migration !!!");

        final var url = databaseProperties.getDatabaseUrl();
        final var user = databaseProperties.getDatabaseUser();
        final var password = databaseProperties.getDatabasePassword();
        final var schema = databaseProperties.getDatabaseSchema();
        final boolean run = databaseProperties.isDatabaseMigration();

        if (run) {
            log.debug("database migration is enable .");
            try {
                final var flyway = Flyway.configure()
                        .locations("classpath:database/migrations")
                        .envVars()
                        .dataSource(url, user, password)
                        .schemas(schema)
                        .defaultSchema(schema)
                        .mixed(true)
                        //.createSchemas(true)
                        .load();
                flyway.migrate();
            } catch (Exception x) {
                throw new JakartaEE9_Exception(x);
            }
        } else {
            log.warn("database migration is disable .");
        }
    }
}
