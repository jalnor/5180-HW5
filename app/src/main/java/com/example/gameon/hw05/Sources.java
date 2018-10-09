package com.example.gameon.hw05;

import java.io.Serializable;

public class Sources implements Serializable {

    String sourceId;
    String sourceName;

    public Sources() {
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
