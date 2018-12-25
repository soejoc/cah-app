package dkgnkndz.lebk.cah_app.backend.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import dkgnkndz.lebk.cah_app.backend.local.entity.session_key.SessionKey;
import dkgnkndz.lebk.cah_app.backend.local.entity.session_key.SessionKeyDao;
import dkgnkndz.lebk.cah_app.backend.local.entity.white_card.WhiteCard;
import dkgnkndz.lebk.cah_app.backend.local.entity.white_card.WhiteCardDao;
import dkgnkndz.lebk.cah_app.backend.local.entity.white_card.WhiteCardsHash;
import dkgnkndz.lebk.cah_app.backend.local.type_converter.UuidTypeConverter;

@Database(entities = {
        SessionKey.class,
        WhiteCard.class,
        WhiteCardsHash.class
}, version = 1, exportSchema = false)
@TypeConverters({UuidTypeConverter.class})
public abstract class CahDatabase extends RoomDatabase {
    public abstract SessionKeyDao sessionKeyDao();
    public abstract WhiteCardDao whiteCardDao();
}
