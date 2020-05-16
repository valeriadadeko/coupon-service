package org.home.couponservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "coupon")
public class Coupon {

    @Id
    private String id;
    private String promoCode;
    private String description;
    private LocalDate validUntil;
    private LocalDate createdOn;


    public Coupon() {}

    public Coupon(String promoCode, String description, LocalDate validUntil) {
        this.promoCode = promoCode;
        this.description = description;
        this.validUntil = validUntil;
        this.createdOn = LocalDate.now();
    }

    @Override
    public String toString() {

        return id + promoCode;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
}
