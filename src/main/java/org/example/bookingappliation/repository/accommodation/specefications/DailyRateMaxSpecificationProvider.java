package org.example.bookingappliation.repository.accommodation.specefications;

import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DailyRateMaxSpecificationProvider implements SpecificationProvider<Accommodation, BigDecimal> {
    private static final String DAILY_RATE_MAX_KEY = "dailyRateMax";
    private static final String DAILY_RATE_FIELD_NAME = "dailyRate";
    private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(0);
    @Override
    public String getKey() {
        return DAILY_RATE_MAX_KEY;
    }

    @Override
    public Specification<Accommodation> getSpecification(BigDecimal params) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .between(root.get(DAILY_RATE_FIELD_NAME), MIN_VALUE, params);
    }
}
