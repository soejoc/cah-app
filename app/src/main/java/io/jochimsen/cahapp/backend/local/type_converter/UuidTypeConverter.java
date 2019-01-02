package io.jochimsen.cahapp.backend.local.type_converter;

import android.arch.persistence.room.TypeConverter;

import java.util.UUID;

public class UuidTypeConverter {

    @TypeConverter
    public static UUID toUuid(final String val) {
        return UUID.fromString(val);
    }

    @TypeConverter
    public static String toString(final UUID val) {
        return val.toString();
    }
}
