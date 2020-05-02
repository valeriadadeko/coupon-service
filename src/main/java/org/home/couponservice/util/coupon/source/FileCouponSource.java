package org.home.couponservice.util.coupon.source;

import org.home.couponservice.config.CouponSourcesConfig;
import org.home.couponservice.domain.Coupon;
import org.home.couponservice.util.coupon.source.CouponSource;

import java.util.List;

public class FileCouponSource implements CouponSource {

    @Override
    public List<Coupon> getCoupons() {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public void setConfig(CouponSourcesConfig couponSourcesConfig) {

    }
}
