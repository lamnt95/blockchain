package com.udemy.blockchain.util;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class UtilComponent {

    public static String getId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Long getLong(int req) {
        return Long.parseLong(Integer.toString(req));
    }

    public static String getHash(String req) {
        return Hashing.sha256()
                .hashString(req, StandardCharsets.UTF_8)
                .toString();
    }

    public static List<String> toList(String req){
        return Arrays.asList(req.split("").clone());
    }


}
