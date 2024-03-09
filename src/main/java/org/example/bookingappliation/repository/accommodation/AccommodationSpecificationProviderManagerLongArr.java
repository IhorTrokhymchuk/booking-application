package org.example.bookingappliation.repository.accommodation;

import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.model.accommodation.Accommodation;
import org.example.bookingappliation.repository.SpecificationProvider;
import org.example.bookingappliation.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AccommodationSpecificationProviderManagerLongArr implements SpecificationProviderManager<Accommodation, Long[]> {
    private final List<SpecificationProvider<Accommodation, Long[]>> specificationProvidersLongArr;

    @Override
    public SpecificationProvider<Accommodation, Long[]> getSpecificationProvider(String key) {
        return specificationProvidersLongArr.stream()
            .filter(spec -> spec.getKey().equals(key))
            .findFirst()
            .orElseThrow(
                    () -> new RuntimeException("Can't find correct "
                        + "accommodationSpecificationProvider where key = " + key));
    }
}
