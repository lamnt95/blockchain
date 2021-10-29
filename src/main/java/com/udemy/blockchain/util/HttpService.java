package com.udemy.blockchain.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class HttpService {
    private static final String GET = "GET";
    private static final String POST = "POST";

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";

    public Response get(String url, String token) throws IOException {
        OkHttpClient cl = new OkHttpClient().newBuilder().build();
        Request req = new Request.Builder()
                .url(url)
                .method(GET, null)
                .addHeader(AUTHORIZATION, token)
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();
        Response res = cl.newCall(req).execute();
        return res;
    }

    public Response post(String url, String body, String token) throws IOException {
        OkHttpClient cl = new OkHttpClient().newBuilder().build();
        RequestBody rb = RequestBody.create(MediaType.parse(APPLICATION_JSON), body);
        Request req = new Request.Builder()
                .url(url)
                .method(POST, rb)
                .addHeader(AUTHORIZATION, token)
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();
        Response res = cl.newCall(req).execute();
        return res;
    }

}
