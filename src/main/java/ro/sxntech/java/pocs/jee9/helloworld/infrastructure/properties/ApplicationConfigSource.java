package ro.sxntech.java.pocs.jee9.helloworld.infrastructure.properties;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.spi.ConfigSource;
import ro.sxntech.java.pocs.jee9.helloworld.service.JakartaEE9_Exception;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class ApplicationConfigSource implements ConfigSource {

    private static final String NAME = "JakartaEE_9_HelloWorld_Configurations";
    private static final String PROPERTIES_FILE = "/META-INF/microprofile-config.properties";

    private final Pattern pattern = Pattern.compile(".*[${](.*)}.*");
    private final Map<String, String> appProperties = new HashMap<>();

    public ApplicationConfigSource() {
        loadProperties();
    }

    private void loadProperties() {
        try (var inputStream = ApplicationConfigSource.class.getResourceAsStream(PROPERTIES_FILE)) {
            final var properties = new Properties();
            properties.load(inputStream);
            properties.forEach((k, v) -> {
                parsePropertyEntry((String) k, (String) v);
            });
        } catch (Exception x) {
            throw new JakartaEE9_Exception("Unable to load app properties", x);
        }
    }

    @Override
    public Set<String> getPropertyNames() {
        return appProperties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return appProperties.get(propertyName);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Map<String, String> getProperties() {
        return appProperties;
    }

    @Override
    public int getOrdinal() {
        return 0;
    }


    private void parsePropertyEntry(final String key, final String value) {
        final var matcher = pattern.matcher(value);
        if (matcher.find()) {
            var pName = matcher.group(1);
            String envPropValue = System.getenv(pName);
            if (Objects.isNull(envPropValue)) {
                log.trace("No env property {} found", pName);
                var sysPropValue = System.getProperty(pName);
                if (Objects.isNull(sysPropValue)) {
                    appProperties.put(key, "");
                    log.warn("No system property {} found", pName);
                } else {
                    log.trace("Found {} system property", pName);
                    log.trace("Property entry: {} = {}", key, sysPropValue);
                    appProperties.put(key, sysPropValue);
                }
            } else {
                log.trace("Found {} env property", pName);
                var v = value.replaceFirst("\\$\\{(.*?)\\}", envPropValue);
                log.trace("Property entry: {} = {}", key, v);
                appProperties.put(key, v);
            }
        } else {
            appProperties.put(key, value);
        }
    }
}
