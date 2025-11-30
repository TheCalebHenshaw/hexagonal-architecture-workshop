package ch.example.web.mapper;

import ch.example.domain.model.Payment;
import ch.example.domain.model.PaymentStatus;
import ch.example.web.dto.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToEnum")
    @Mapping(target = "timestamp", source = "timestamp", qualifiedByName = "instantToOffsetDateTime")
    @Mapping(target = "fraudScore", source = "fraudScore", qualifiedByName = "doubleToJsonNullable")
    PaymentResponse toDto(Payment payment);

    @Named("statusToEnum")
    default PaymentResponse.StatusEnum statusToEnum(PaymentStatus status) {
        return status != null ? PaymentResponse.StatusEnum.fromValue(status.name()) : null;
    }

    @Named("instantToOffsetDateTime")
    default OffsetDateTime instantToOffsetDateTime(Instant instant) {
        return instant != null ? OffsetDateTime.ofInstant(instant, ZoneOffset.UTC) : null;
    }

    @Named("doubleToJsonNullable")
    default JsonNullable<Double> doubleToJsonNullable(Double value) {
        return value != null ? JsonNullable.of(value) : JsonNullable.undefined();
    }
}
