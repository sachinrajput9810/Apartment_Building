package com.apartmentcontrols.config;

import com.apartmentcontrols.model.Apartment;
import com.apartmentcontrols.model.CommonRoom;
import com.apartmentcontrols.model.Room;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Jackson configuration for polymorphic serialisation of Room subclasses.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper mapper = builder.build();

        // Register Room subtypes so Jackson can deserialise them by "type" field
        mapper.registerSubtypes(Apartment.class, CommonRoom.class);

        // Enable default typing for the Room hierarchy using the "type" property
        mapper.addMixIn(Room.class, RoomMixin.class);

        return mapper;
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXISTING_PROPERTY,
            property = "type",
            visible = true
    )
    @com.fasterxml.jackson.annotation.JsonSubTypes({
            @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = Apartment.class, name = "apartment"),
            @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = CommonRoom.class, name = "common_room")
    })
    abstract static class RoomMixin {}
}
