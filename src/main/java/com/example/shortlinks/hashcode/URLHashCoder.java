package com.example.shortlinks.hashcode;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class URLHashCoder {
    Hashids hashids;

    public String encodeURL(String URL){
        long[] chars = URL.chars().mapToLong(x -> (long) x).toArray();
        String hash = hashids.encode(chars);
        System.out.println("URL = " + URL);
        System.out.println("Hash = " + hash);
        return hash;
    }

    public String decodeURL(String hash){
        long[] chars = hashids.decode(hash);

        StringBuilder builder = new StringBuilder();
        for (long c : chars) {
            builder.append((char) c);
        }

        return builder.toString();
    }

    public URLHashCoder(@Value(value = "hash.salt") String salt) {
        hashids = new Hashids(salt);
    }
}
