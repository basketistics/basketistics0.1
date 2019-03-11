package de.berlin.hwr.basketistics.Persistency.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.EventEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.EventTypeEntity;
import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.ViewModel.EventViewModel;

@Dao
public interface EventTypeDao {

    @Insert
    void insert(EventTypeEntity eventTypeEntity);

    @Query("SELECT * FROM EventTypeEntity")
    List<MatchEntity> getAll();
}
