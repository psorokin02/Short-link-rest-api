package com.example.shortlinks.hashcode;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class URLHashCoder {
    Hashids hashids;

    public String encodeURL(String URL){
        long[] chars = URL.chars().mapToLong(x -> (long) x).toArray();
        String hash = hashids.encode(chars);
        return hash;
    }

    public String decodeURL(String hash){
        long[] chars = hashids.decode(hash);
        String URL = Arrays.toString(chars);
        return URL;
    }

    public URLHashCoder(@Value(value = "hash.salt") String salt) {
        hashids = new Hashids(salt);
    }
}
