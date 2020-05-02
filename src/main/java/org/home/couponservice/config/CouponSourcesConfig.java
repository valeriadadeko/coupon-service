package org.home.couponservice.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Validated
@ConfigurationProperties(prefix = "coupon.sources")
public class CouponSourcesConfig {

    @NotEmpty
    private List<CouponSite> sites = new ArrayList<>();

    public List<CouponSite> getSites() {
        return sites;
    }

    public void setSites(List<CouponSite> sites) {
        this.sites = sites;
    }

    @Component
    @ConfigurationProperties(prefix="coupon.sources.sites")
    public static class CouponSite {

        String url;
        String localeCode;
        String couponListContainerSelector;
        String couponContainerSelector;
        String validFieldSelector;
        String promoCodeFieldSelector;
        String descriptionFieldSelector;
        List<String> datePatterns;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCouponListContainerSelector() {
            return couponListContainerSelector;
        }

        public void setCouponListContainerSelector(String couponListContainerSelector) {
            this.couponListContainerSelector = couponListContainerSelector;
        }

        public String getCouponContainerSelector() {
            return couponContainerSelector;
        }

        public void setCouponContainerSelector(String couponContainerSelector) {
            this.couponContainerSelector = couponContainerSelector;
        }

        public String getValidFieldSelector() {
            return validFieldSelector;
        }

        public void setValidFieldSelector(String validFieldSelector) {
            this.validFieldSelector = validFieldSelector;
        }

        public String getPromoCodeFieldSelector() {
            return promoCodeFieldSelector;
        }

        public void setPromoCodeFieldSelector(String promoCodeFieldSelector) {
            this.promoCodeFieldSelector = promoCodeFieldSelector;
        }

        public String getDescriptionFieldSelector() {
            return descriptionFieldSelector;
        }

        public void setDescriptionFieldSelector(String descriptionFieldSelector) {
            this.descriptionFieldSelector = descriptionFieldSelector;
        }

        public List<String> getDatePatterns() {
            return datePatterns;
        }

        public void setDatePatterns(List<String> datePatterns) {
            this.datePatterns = datePatterns;
        }

        public String getLocaleCode() {
            return localeCode;
        }

        public void setLocaleCode(String localeCode) {
            this.localeCode = localeCode;
        }
    }
}
