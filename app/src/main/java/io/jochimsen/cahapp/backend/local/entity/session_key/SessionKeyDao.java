package io.jochimsen.cahapp.backend.local.entity.session_key;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface SessionKeyDao {
    @Insert
    void save(final SessionKey sessionKey);

    @Query("SELECT * FROM SessionKey LIMIT 1")
    LiveData<SessionKey> getSessionKey();

    @Query("DELETE FROM SessionKey")
    void delete();
}
