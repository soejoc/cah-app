package dkgnkndz.lebk.cah_app.backend.local.entity.session_key;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.reactivex.Maybe;

@Dao
public interface SessionKeyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void save(final SessionKey sessionKey);

    @Query("SELECT * FROM SessionKey LIMIT 1")
    Maybe<SessionKey> getSessionKey();

    @Query("DELETE FROM SessionKey")
    void delete();
}
