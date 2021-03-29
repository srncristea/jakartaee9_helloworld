package ro.sxntech.java.pocs.jee9.helloworld.service.model;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Credentials {

    @JsonbProperty("type")
    private final String type;
    @JsonbProperty("value")
    private final String value;
    @JsonbProperty("temporary")
    private final boolean temporary;
}
