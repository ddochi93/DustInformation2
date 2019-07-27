package com.ddochi.model.tm_coordinates;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Documents {
    @SerializedName("documents")
    private List<TMCoordinates> documents;

    public List<TMCoordinates> getDocuments() {return documents;}
    public void setDocuments(List<TMCoordinates> documents) {
        this.documents = documents;
    }
}
