package com.sos.obs.decc.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SessionMetadata implements Serializable {
    private Map<String, String> data = new HashMap<>();


    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
