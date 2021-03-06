package org.home.couponservice.repository;

import com.mongodb.bulk.BulkWriteResult;
import org.home.couponservice.domain.Coupon;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CouponRepositoryImp implements CouponRepository {

    private final MongoOperations mongoOperations;

    public CouponRepositoryImp(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<Coupon> findAllByValidUntilGreaterThanEqual(LocalDate validUntil) {

        return mongoOperations.find(Query.query(Criteria.where("validUntil").gte(validUntil)), Coupon.class);
    }

    @Override
    public Page<Coupon> findAllByValidUntilGreaterThanEqual(LocalDate validUntil, Pageable pageable) {

        Query query = Query.query(Criteria.where("validUntil").gte(validUntil)).with(pageable);
        List<Coupon> coupons = mongoOperations.find(query, Coupon.class);
        long count = mongoOperations.count(Query.of(query).limit(-1).skip(-1), Coupon.class);
        return new PageImpl<>(coupons, pageable, count);
    }

    @Override
    public List<Coupon> findAll() {
        return mongoOperations.findAll(Coupon.class);
    }

    @Override
    public void saveAll(List<Coupon> coupons) {

        BulkOperations bulkOperations = mongoOperations.bulkOps(BulkOperations.BulkMode.UNORDERED, Coupon.class);

        bulkOperations.insert(coupons);

        BulkWriteResult bulkWriteResult;
        try {
            bulkWriteResult = bulkOperations.execute();
            System.out.println("The number of inserted records: " + bulkWriteResult.getInsertedCount());
        } catch (DuplicateKeyException ignored) {
        }


    }
}
