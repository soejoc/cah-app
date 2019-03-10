package io.jochimsen.cahapp.backend.local.entity.white_card;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface WhiteCardDao {
    @Insert
    void save(final List<WhiteCard> whiteCards);

    @Insert
    void saveHash(final WhiteCardsHash whiteCardsHash);

    @Query("SELECT * FROM WhiteCard")
    LiveData<List<WhiteCard>> get();

    @Query("SELECT * FROM WhiteCardsHash LIMIT 1")
    Single<WhiteCardsHash> getHash();

    @Query("DELETE FROM WhiteCard")
    void delete();

    @Query("DELETE FROM WhiteCardsHash")
    void deleteHash();
}
