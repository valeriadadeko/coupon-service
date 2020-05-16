package org.home.couponservice.controller;

import org.home.couponservice.config.CouponSourcesConfig;
import org.home.couponservice.domain.Coupon;
import org.home.couponservice.repository.CouponRepository;
import org.home.couponservice.service.CouponGetter;
import org.home.couponservice.util.coupon.source.SiteCouponSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "coupon/**")
public class CouponApiController {

    private final CouponRepository couponRepository;
    private final CouponGetter couponGetter;
    final CouponSourcesConfig couponSourcesConfig;

    public CouponApiController(CouponRepository couponRepository, CouponGetter couponGetter, CouponSourcesConfig couponSourcesConfig) {
        this.couponRepository = couponRepository;
        this.couponGetter = couponGetter;
        this.couponSourcesConfig = couponSourcesConfig;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Coupon> getAllCoupons() {

        return couponRepository.findAll();
    }


    @RequestMapping(path = "/actual", method = RequestMethod.GET)
    public List<Coupon> getActualCoupons() {

        return couponRepository.findAllByValidUntilGreaterThanEqual(LocalDate.now());
    }

    @RequestMapping(path = "/couponSiteUrl", method = RequestMethod.GET, produces = "application/json")
    public List<String> getSiteUrl() {

        return couponSourcesConfig.getSites().stream().map(couponSite -> couponSite.getUrl()).collect(Collectors.toList());
    }


    @RequestMapping(path = "/triggerSiteScan", method = RequestMethod.GET)
    public void scanSitesForCoupons() {
        couponGetter.setCouponSource(new SiteCouponSource());
        couponGetter.startCouponSearch();
    }
}
