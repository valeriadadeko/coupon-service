package org.home.couponservice.repository;

import org.home.couponservice.domain.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface CouponRepository {

    List<Coupon> findAllByValidUntilGreaterThanEqual(LocalDate validUntil);

    Page<Coupon> findAllByValidUntilGreaterThanEqual(LocalDate validUntil, Pageable pageable);

    List<Coupon> findAll();

    void saveAll(List<Coupon> coupons);

}
