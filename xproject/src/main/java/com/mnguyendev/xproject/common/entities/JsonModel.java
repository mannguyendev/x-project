package com.mnguyendev.xproject.common.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class JsonModel {
    HashMap<String, Object> hashMap;

    ObjectMapper mapper;

    public JsonModel(){
        hashMap = new HashMap<>();
        mapper = new ObjectMapper();
    }

    public void put(String key, Object value){
        hashMap.put(key, value);
    }

    public JsonNode build(){
        return mapper.convertValue(hashMap, JsonNode.class);
    }
}
