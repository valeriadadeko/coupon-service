package org.home.couponservice.controller;

import org.home.couponservice.domain.Coupon;
import org.home.couponservice.repository.CouponRepository;
import org.home.couponservice.service.CouponGetter;
import org.home.couponservice.util.coupon.source.SiteCouponSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "coupon/**")
public class CouponController {

    private final CouponRepository couponRepository;
    private final CouponGetter couponGetter;

    public CouponController(CouponRepository couponRepository, CouponGetter couponGetter) {
        this.couponRepository = couponRepository;
        this.couponGetter = couponGetter;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Coupon> getAllCoupons() {

        return couponRepository.findAll();
    }


    @RequestMapping(path = "/actual", method = RequestMethod.GET)
    public List<Coupon> getActualCoupons() {

        return couponRepository.findAllByValidUntilGreaterThanEqual(LocalDate.now());
    }


    @RequestMapping(path = "/triggerSiteScan", method = RequestMethod.GET)
    public void scanSitesForCoupons() {
        couponGetter.setCouponSource(new SiteCouponSource());
        couponGetter.startCouponSearch();
    }
}
