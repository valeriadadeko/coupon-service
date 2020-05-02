package org.home.couponservice.util.coupon.source;

import org.home.couponservice.config.CouponSourcesConfig;
import org.home.couponservice.domain.Coupon;
import org.home.couponservice.util.DateParseUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SiteCouponSource implements CouponSource {

    private CouponSourcesConfig couponSourcesConfig;

    public SiteCouponSource() {
    }


    @Override
    public List<Coupon> getCoupons() {

        System.out.println("Coupons will be get from next sites:" + couponSourcesConfig.getSites());

        List<Coupon> coupons = new ArrayList<>();
        couponSourcesConfig.getSites().forEach(
                (couponSiteConfig) -> {
                    coupons.addAll(parsePageFoCoupons(couponSiteConfig));
                }
        );

        return coupons;
    }

    List<Coupon> parsePageFoCoupons(CouponSourcesConfig.CouponSite couponSiteConfig) {

        List<Coupon> coupons = new ArrayList<>();

        try {

            Document couponPage = getCouponPage(couponSiteConfig.getUrl());
            Element couponsContainer = getCouponsContainer(couponSiteConfig.getCouponListContainerSelector(), couponPage);
            Elements promoCodes = getLinesWithPromoCodes(couponSiteConfig.getCouponContainerSelector(), couponsContainer);

            Locale siteLocale = new Locale(couponSiteConfig.getLocaleCode());
            List<Pattern> patterns = couponSiteConfig.getDatePatterns().stream().map(Pattern::compile).collect(Collectors.toList());
            promoCodes.forEach(
                    (promoCodeLine) -> {

                        String validPeriodStr = getCouponFieldHtmlValue(promoCodeLine, couponSiteConfig.getValidFieldSelector());
                        String promoCodeStr = getCouponFieldHtmlValue(promoCodeLine, couponSiteConfig.getPromoCodeFieldSelector());
                        String descriptionStr = getCouponFieldHtmlValue(promoCodeLine, couponSiteConfig.getDescriptionFieldSelector());

                        if(!validPeriodStr.isEmpty() && !promoCodeStr.isEmpty()) {

                            LocalDate validUntil = getValidUntilDate(validPeriodStr, patterns, siteLocale);

                            if(validUntil != null) {
                                coupons.add(new Coupon(promoCodeStr, descriptionStr, validUntil));
                            }

                        }
                    });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return coupons;
    }

    Document getCouponPage(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    Element getCouponsContainer(String couponContainerSelector, Document page) {

        return page.selectFirst(couponContainerSelector);
    }

    Elements getLinesWithPromoCodes(String couponSelector, Element couponContainer) {

        return couponContainer.select(couponSelector);
    }

    String getCouponFieldHtmlValue(Element promoCodeLine, String selector) {
        return  promoCodeLine.selectFirst(selector).html();
    }

    LocalDate getValidUntilDate(String htmlValue, List<Pattern> datePatterns, Locale locale) {

        LocalDate validUntilDate = null;

        try {

            validUntilDate = DateParseUtil.parseDate(datePatterns, htmlValue, locale);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return validUntilDate;
    }

    @Override
    public void setConfig(CouponSourcesConfig couponSourcesConfig) {
        this.couponSourcesConfig = couponSourcesConfig;
    }

    public CouponSourcesConfig getCouponSourcesConfig() {
        return couponSourcesConfig;
    }

    public void setCouponSourcesConfig(CouponSourcesConfig couponSourcesConfig) {
        this.couponSourcesConfig = couponSourcesConfig;
    }
}
