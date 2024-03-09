package org.example.bookingappliation.repository.accommodation;

import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.repository.SpecificationProvider;
import org.example.bookingappliation.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AccommodationSpecificationProviderManagerBigDec implements SpecificationProviderManager<Accommodation, BigDecimal> {
    private final List<SpecificationProvider<Accommodation, BigDecimal>> specificationProvidersLongArr;

    @Override
    public SpecificationProvider<Accommodation, BigDecimal> getSpecificationProvider(String key) {
        return specificationProvidersLongArr.stream()
            .filter(spec -> spec.getKey().equals(key))
            .findFirst()
            .orElseThrow(
                    () -> new RuntimeException("Can't find correct "
                        + "accommodationSpecificationProvider where key = " + key));
    }
}
