package ro.sxntech.java.pocs.jee9.helloworld.infrastructure.extensions;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.literal.InjectLiteral;
import jakarta.enterprise.inject.spi.*;
import jakarta.enterprise.util.AnnotationLiteral;

import lombok.extern.slf4j.Slf4j;

import org.eclipse.microprofile.config.ConfigProvider;

import ro.sxntech.java.pocs.jee9.helloworld.dao.MigrationRunner;
import ro.sxntech.java.pocs.jee9.helloworld.infrastructure.properties.DatabaseProperties;

@Slf4j
public class DatabaseMigrationExtension implements Extension {

    private static final int MIgRATION_RUNNER_CONSTRUCTOR_PARAMS = 1;

    public void registerMigrationRunnerType(@Observes BeforeBeanDiscovery bbdEvent) {
        bbdEvent.addAnnotatedType(MigrationRunner.class, MigrationRunner.class.getName());
        log.debug("registerMigrationRunnerType");
    }

    public void processAnnotatedType(@Observes ProcessAnnotatedType<MigrationRunner> processAnnotatedType) {
        processAnnotatedType.configureAnnotatedType()
                .add(ApplicationScoped.Literal.INSTANCE)
                .add(new AnnotationLiteral<FlywayMigration>() {
                })
                .constructors()
                .stream()
                .filter(annotatedConstructor -> {
                    return annotatedConstructor.params().size() == 1;
                })
                .findFirst()
                .get()
                .add(InjectLiteral.INSTANCE);
        log.debug("processAnnotatedType");
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {
        abd.addBean()
                .types(DatabaseProperties.class)
                .qualifiers(new AnnotationLiteral<Default>() {}, new AnnotationLiteral<Any>() {})
                .scope(ApplicationScoped.class)
                .name(DatabaseProperties.class.getName())
                .beanClass(DatabaseProperties.class)
                .createWith(creationalContext -> {
                    final var config = ConfigProvider.getConfig();
                    final var dbUrl = config.getConfigValue("DATABASE_URL");
                    final var dbUser = config.getConfigValue("DATABASE_USER");
                    final var dbPassword = config.getConfigValue("DATABASE_PASSWORD");
                    final var dbMigrationEnabled = config.getConfigValue("MIGRATION_ENABLED");
                    final var dbSchema = config.getConfigValue("DATABASE_SCHEMA");
                    final var appName = config.getConfigValue("APP_NAME");

                    final var app = appName.getValue();
                    final var urlValue = dbUrl.getValue();
                    final var userValue = dbUser.getValue();
                    final var passwordValue = dbPassword.getValue();
                    final var schemaValue = dbSchema.getValue();
                    final boolean migrationEnabled = Boolean.parseBoolean(dbMigrationEnabled.getValue());

                    final var applicationProperties = new DatabaseProperties(app, urlValue, userValue, passwordValue, schemaValue, migrationEnabled);
                    log.info("Create bean: {}", applicationProperties);

                    return applicationProperties;
                });
        log.debug("afterBeanDiscovery");
    }


    public void runMigrationPlan(@Observes AfterDeploymentValidation adv, BeanManager bm) {
        final var mr = bm.createInstance()
                .select(MigrationRunner.class, new AnnotationLiteral<FlywayMigration>() {})
                .get();
        mr.run();
        log.debug("AfterDeploymentValidation");
    }
}
