package io.jochimsen.cahapp.backend.local.entity.session_key;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface SessionKeyDao {
    @Insert
    void save(final SessionKey sessionKey);

    @Query("SELECT * FROM SessionKey LIMIT 1")
    LiveData<SessionKey> getSessionKey();

    @Query("DELETE FROM SessionKey")
    void delete();
}
