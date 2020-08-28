package com.example.hellojuaracoding.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BiodataDao {
    @Query("SELECT * FROM Biodata ")
    List<Biodata> getAll();

    @Query("SELECT * FROM Biodata WHERE nama LIKE '%' || :nama || '%'")
    List<Biodata> findByNama(String nama);

    @Query("SELECT * FROM Biodata WHERE tlp = :tlp ")
    Biodata findByTelepon (String tlp);

    @Insert
    void insertAll(Biodata... users);

    @Delete
    void deleteBiodata(Biodata biodata);

    @Update
    int updateBiodata(Biodata biodata);
}
