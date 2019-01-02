package io.jochimsen.cahapp.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.jochimsen.cahapp.backend.local.CahDatabase;
import io.jochimsen.cahapp.backend.local.entity.black_card.BlackCardDao;
import io.jochimsen.cahapp.backend.local.entity.session_key.SessionKeyDao;
import io.jochimsen.cahapp.backend.local.entity.white_card.WhiteCardDao;

@Module
public class RoomModule {

    @Singleton
    @Provides
    CahDatabase provideDatabase(final Application application) {
        return Room.databaseBuilder(application, CahDatabase.class, "cah-db").build();
    }

    @Singleton
    @Provides
    SessionKeyDao provideSessionKeyDao(final CahDatabase database) {
        return database.sessionKeyDao();
    }

    @Singleton
    @Provides
    WhiteCardDao provideWhiteCardDao(final CahDatabase database) {
        return database.whiteCardDao();
    }

    @Singleton
    @Provides
    BlackCardDao provideBlackCardDao(final CahDatabase database) { return  database.blackCardDao(); }
}
