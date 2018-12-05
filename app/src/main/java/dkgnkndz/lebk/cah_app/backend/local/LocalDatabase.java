package dkgnkndz.lebk.cah_app.backend.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import dkgnkndz.lebk.cah_app.backend.local.session.SessionKey;
import dkgnkndz.lebk.cah_app.backend.local.session.SessionKeyDao;
import dkgnkndz.lebk.cah_app.backend.local.type_converter.UuidTypeConverter;

@Database(entities = {
        SessionKey.class
}, version = 1)
@TypeConverters({UuidTypeConverter.class})
public abstract class LocalDatabase extends RoomDatabase {
    public abstract SessionKeyDao sessionKeyDao();
}
