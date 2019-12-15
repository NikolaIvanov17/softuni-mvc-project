package org.softuni.learningportal.service.impl;

import org.modelmapper.ModelMapper;
import org.softuni.learningportal.domain.entities.Offer;
import org.softuni.learningportal.domain.entities.Book;
import org.softuni.learningportal.domain.models.service.OfferServiceModel;
import org.softuni.learningportal.domain.models.service.BookServiceModel;
import org.softuni.learningportal.repository.OfferRepository;
import org.softuni.learningportal.service.BookService;
import org.softuni.learningportal.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, BookService bookService, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OfferServiceModel> findAllOffers() {
        return this.offerRepository.findAll().stream()
                .map(o -> this.modelMapper.map(o, OfferServiceModel.class))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 333333)
    private void generateOffers() {
        this.offerRepository.deleteAll();
        List<BookServiceModel> books = this.bookService.findAllBooks();

        if (books.isEmpty()) {
            return;
        }

        Random rnd = new Random();
        List<Offer> offers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Offer offer = new Offer();
            offer.setBook(this.modelMapper.map(books.get(rnd.nextInt(books.size())), Book.class));
            offer.setPrice(offer.getBook().getPrice().multiply(new BigDecimal(0.8)));

            if (offers.stream().noneMatch(o -> o.getBook().getId().equals(offer.getBook().getId()))) {
                offers.add(offer);
            }
        }

        this.offerRepository.saveAll(offers);
    }
}
