package io.jochimsen.cahapp.backend.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCard;
import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCardDao;
import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCardsHash;
import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKey;
import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKeyDao;
import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCard;
import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCardDao;
import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCardsHash;
import io.jochimsen.cahapp.backend.local.type_converter.UuidTypeConverter;

@Database(entities = {
        SessionKey.class,
        WhiteCard.class,
        WhiteCardsHash.class,
        BlackCard.class,
        BlackCardsHash.class
}, version = 1, exportSchema = false)
@TypeConverters({UuidTypeConverter.class})
public abstract class CahDatabase extends RoomDatabase {
    public abstract SessionKeyDao sessionKeyDao();
    public abstract WhiteCardDao whiteCardDao();
    public abstract BlackCardDao blackCardDao();
}
