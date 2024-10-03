package org.product.info.services;

import org.info.product.models.Shipping;

import java.util.List;

public interface ShippingService {

    List<Shipping> findAllShipping();

    Shipping findShippingById(long id);

    Shipping save(Shipping shipping);

    void delete(Shipping shipping);

    void deleteShippingById(long id);

}
