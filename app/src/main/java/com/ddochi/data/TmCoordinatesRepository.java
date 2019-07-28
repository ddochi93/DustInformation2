package com.ddochi.data;

import com.ddochi.model.tm_coordinates.Documents;

import retrofit2.Callback;

public interface TmCoordinatesRepository {
    boolean isAvailable();
    void getTmCoordinatesDocuments(Callback<Documents> callback);
}
