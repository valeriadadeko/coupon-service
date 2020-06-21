package org.home.couponservice.controller;


import org.home.couponservice.domain.Coupon;
import org.home.couponservice.repository.CouponRepository;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class CouponUIController {

    private static final String CONFIG_ENDPOINT_URI = "http://localhost:8080/coupon/couponSiteUrl";
    private final CouponRepository couponRepository;

    public CouponUIController(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

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

    @GetMapping(path = "couponTable")
    public String couponTable(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,
                              @RequestParam(value = "sort", defaultValue = "validUntil") String sort,
                              @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                              @RequestParam(value = "typeOfList", defaultValue = "actual") String typeOfList,
                              ModelMap map) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sortDir), sort));
        Page<Coupon> couponPage;

        if("actual".equals(typeOfList)) {
            couponPage = couponRepository.findAllByValidUntilGreaterThanEqual(LocalDate.now(), pageRequest);
        } else {
            couponPage = couponRepository.findAllByValidUntilGreaterThanEqual(LocalDate.EPOCH, pageRequest);
        }

        map.addAttribute("coupons", couponPage.getContent());
        map.addAttribute("total", couponPage.getTotalElements());
        map.addAttribute("pages", IntStream.rangeClosed(1, couponPage.getTotalPages()).toArray());
        map.addAttribute("currentPage", page);
        map.addAttribute("sortDir", sortDir);
        map.addAttribute("typeOfList", typeOfList);

        return "couponTable";
    }
    @GetMapping(path = "adminPage")
    public String adminPage() {
        return "adminPage";
    }
}
