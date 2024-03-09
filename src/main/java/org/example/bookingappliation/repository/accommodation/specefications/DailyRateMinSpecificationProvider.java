package org.example.bookingappliation.repository.accommodation.specefications;

import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DailyRateMinSpecificationProvider implements SpecificationProvider<Accommodation, BigDecimal> {
    private static final String DAILY_RATE_MIN_KEY = "dailyRateMin";
    private static final String DAILY_RATE_FIELD_NAME = "dailyRate";
    private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(Long.MAX_VALUE);
    @Override
    public String getKey() {
        return DAILY_RATE_MIN_KEY;
    }

    @Override
    public Specification<Accommodation> getSpecification(BigDecimal params) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .between(root.get(DAILY_RATE_FIELD_NAME), params, MAX_VALUE);
    }
}
