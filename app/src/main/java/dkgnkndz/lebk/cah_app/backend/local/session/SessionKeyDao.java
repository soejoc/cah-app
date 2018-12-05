package dkgnkndz.lebk.cah_app.backend.local.session;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface SessionKeyDao {
    @Insert
    void save(final SessionKey sessionKey);

    @Query("SELECT * FROM SessionKey LIMIT 1")
    SessionKey getSessionKey();
}
