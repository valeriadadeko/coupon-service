package org.home.couponservice.repository;

import org.home.couponservice.domain.Coupon;

import java.time.LocalDate;
import java.util.List;

public interface CouponRepository {

    List<Coupon> findAllByValidUntilGreaterThanEqual(LocalDate validUntil);

    List<Coupon> findAll();

    void saveAll(List<Coupon> coupons);

}
