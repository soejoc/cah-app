package dkgnkndz.lebk.cah_app.backend.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import dkgnkndz.lebk.cah_app.backend.local.session.SessionKey;
import dkgnkndz.lebk.cah_app.backend.local.session.SessionKeyDao;

@Database(entities = {
        SessionKey.class
}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract SessionKeyDao sessionKeyDao();
}
