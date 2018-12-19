package dkgnkndz.lebk.cah_app.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dkgnkndz.lebk.cah_app.backend.local.CahDatabase;
import dkgnkndz.lebk.cah_app.backend.local.session_key.SessionKeyDao;

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
}
