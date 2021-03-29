package ro.sxntech.java.pocs.jee9.helloworld.infrastructure.properties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Setter
@Getter
@ToString
public class DatabaseProperties {

    private String name, databaseUrl, databaseUser, databasePassword, databaseSchema;
    private boolean databaseMigration;

}
