package org.home.couponservice.service;

import org.home.couponservice.config.CouponSourcesConfig;
import org.home.couponservice.domain.Coupon;
import org.home.couponservice.repository.CouponRepository;
import org.home.couponservice.util.coupon.source.CouponSource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponGetter {

    final CouponRepository couponRepository;
    final CouponSourcesConfig couponSourcesConfig;
    private final MongoTemplate mongoTemplate;

    private CouponSource couponSource;

    public CouponGetter(CouponRepository couponRepository, CouponSourcesConfig couponSourcesConfig, MongoTemplate mongoTemplate) {
        this.couponRepository = couponRepository;
        this.couponSourcesConfig = couponSourcesConfig;
        this.mongoTemplate = mongoTemplate;
    }

    public void startCouponSearch() {
        List<Coupon> coupons = couponSource.getCoupons();
        if(!coupons.isEmpty()) {
            couponRepository.saveAll(coupons);
        }

    }

    public CouponSource getCouponSource() {
        return couponSource;
    }

    public void setCouponSource(CouponSource couponSource) {
        this.couponSource = couponSource;
        this.couponSource.setConfig(couponSourcesConfig);
    }
}
