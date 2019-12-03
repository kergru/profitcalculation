package com.kgr.profitcalculation.rest.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.money.MonetaryAmount;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimpleMonetaryAmountSerializer extends StdSerializer<MonetaryAmount> {

    public SimpleMonetaryAmountSerializer() {
        super(MonetaryAmount.class);
    }


    @Override
    public void serializeWithType(final MonetaryAmount value, final JsonGenerator generator,
                                  final SerializerProvider provider, final TypeSerializer serializer) throws IOException {
        serialize(value, generator, provider);
    }

    @Override
    public void serialize(final MonetaryAmount value, final JsonGenerator generator, final SerializerProvider provider)
            throws IOException {
        BigDecimal decimal = value.getNumber().numberValueExact(BigDecimal.class);
        decimal = decimal.setScale(2, RoundingMode.UNNECESSARY);

        generator.writeNumber(decimal);
    }
}
