package io.jochimsen.cahapp.backend.local.entity.black_card;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor(onConstructor = @__({@Ignore}))
@NoArgsConstructor
public class BlackCardsHash {

    @PrimaryKey
    private Integer hash;
}
