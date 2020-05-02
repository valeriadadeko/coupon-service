package org.home.couponservice.util.coupon.source;


import org.home.couponservice.config.CouponSourcesConfig;
import org.home.couponservice.domain.Coupon;

import java.util.List;

public interface CouponSource {

    List<Coupon> getCoupons();

    void setConfig(CouponSourcesConfig couponSourcesConfig);
}
