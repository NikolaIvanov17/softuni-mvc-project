package org.softuni.learningportal.service;

import org.softuni.learningportal.domain.models.service.OfferServiceModel;

import java.util.List;

public interface OfferService {

    List<OfferServiceModel> findAllOffers();
}
