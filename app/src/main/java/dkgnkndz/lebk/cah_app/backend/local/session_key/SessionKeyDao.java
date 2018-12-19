package dkgnkndz.lebk.cah_app.backend.local.session_key;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import io.reactivex.Maybe;

@Dao
public interface SessionKeyDao {
    @Insert
    void save(final SessionKey sessionKey);

    @Query("SELECT * FROM SessionKey LIMIT 1")
    Maybe<SessionKey> getSessionKey();
}
