package org.example.Translators;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Translator {

    Map<String, String> map = new HashMap<>();

    void populateTranslator() { //info: title -> create direct match ignore nestedness -> then have a function that traverses and assignes these vals
        map.put("title", "title");
        map.put("version", "version");
        map.put("baseUri", "url");
        map.put("mediaType", "?");
        map.put("oauth_2_0", "?");
        map.put("types?", "version");
        map.put("securedBy?", "version");
        map.put("resourceTypes?", "version");
    }
}
