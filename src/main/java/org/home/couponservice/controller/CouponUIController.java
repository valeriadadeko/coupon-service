package org.home.couponservice.controller;


import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class CouponUIController {

    private static final String CONFIG_ENDPOINT_URI = "http://localhost:8080/coupon/couponSiteUrl";

    @GetMapping(path = "index")
    public String index(ModelMap map) {


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CONFIG_ENDPOINT_URI))
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            List<Object> urls = new JacksonJsonParser().parseList(response.body());
            map.addAttribute("urls", urls);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        return "couponStartPage";
    }
}
