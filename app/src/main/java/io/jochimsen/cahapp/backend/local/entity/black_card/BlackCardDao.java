package io.jochimsen.cahapp.backend.local.entity.black_card;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface BlackCardDao {
    @Insert
    void save(final List<BlackCard> blackCards);

    @Insert
    void saveHash(final BlackCardsHash blackCardsHash);

    @Query("SELECT * FROM BlackCard")
    LiveData<List<BlackCard>> get();

    @Query("SELECT * FROM BlackCardsHash LIMIT 1")
    Single<BlackCardsHash> getHash();

    @Query("DELETE FROM BlackCard")
    void delete();

    @Query("DELETE FROM BlackCardsHash")
    void deleteHash();
}
