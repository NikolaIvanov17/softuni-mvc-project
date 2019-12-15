package org.softuni.learningportal.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.learningportal.domain.models.service.OrderBookServiceModel;
import org.softuni.learningportal.domain.models.service.OrderServiceModel;
import org.softuni.learningportal.domain.models.view.OrderBookViewModel;
import org.softuni.learningportal.domain.models.view.BookDetailsViewModel;
import org.softuni.learningportal.domain.models.view.ShoppingCartItem;
import org.softuni.learningportal.service.OrderService;
import org.softuni.learningportal.service.BookService;
import org.softuni.learningportal.service.UserService;
import org.softuni.learningportal.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController extends BaseController {

    private final BookService bookService;
    private final UserService userService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public ShoppingCartController(BookService bookService, UserService userService, OrderService orderService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.userService = userService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/add-book")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCartConfirm(String id, int quantity, HttpSession session) {
        BookDetailsViewModel book = this.modelMapper
                .map(this.bookService.findBookById(id), BookDetailsViewModel.class);

        OrderBookViewModel orderBookViewModel = new OrderBookViewModel();
        orderBookViewModel.setBook(book);
        orderBookViewModel.setPrice(book.getPrice());

        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setBook(orderBookViewModel);
        cartItem.setQuantity(quantity);

        var cart = this.retrieveCart(session);
        this.addItemToCart(cartItem, cart);

        return super.redirect("/cart/details");
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Cart Details")
    public ModelAndView cartDetails(ModelAndView modelAndView, HttpSession session) {
        var cart = this.retrieveCart(session);
        modelAndView.addObject("totalPrice", this.calcTotal(cart));
        modelAndView.addObject("isOrdered", session.getAttribute("shopping-cart"));
        return super.view("cart/cart-details", modelAndView);
    }

    @PostMapping("/confirm")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView confirmOrder(HttpSession session, Principal principal) {
        var cart = this.retrieveCart(session);

        OrderServiceModel orderServiceModel = this.prepareOrder(cart, principal.getName());
        this.orderService.createOrder(orderServiceModel);

        if (session.getAttribute("shopping-cart") != null) {
            session.removeAttribute("shopping-cart");
        }
        return super.redirect("/home");
    }

    private List<ShoppingCartItem> retrieveCart(HttpSession session) {
        this.initCart(session);

        return (List<ShoppingCartItem>) session.getAttribute("shopping-cart");
    }

    private void initCart(HttpSession session) {
        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new LinkedList<>());
        }
    }

    private void addItemToCart(ShoppingCartItem item, List<ShoppingCartItem> cart) {
        for (ShoppingCartItem shoppingCartItem : cart) {
            if (shoppingCartItem.getBook().getBook().getId().equals(item.getBook().getBook().getId())) {
                shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + item.getQuantity());
                return;
            }
        }

        cart.add(item);
    }

    private BigDecimal calcTotal(List<ShoppingCartItem> cart) {
        BigDecimal result = new BigDecimal(0);
        for (ShoppingCartItem item : cart) {
            result = result.add(item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        return result;
    }

    private OrderServiceModel prepareOrder(List<ShoppingCartItem> cart, String customer) {
        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setCustomer(this.userService.findUserByUserName(customer));
        List<OrderBookServiceModel> books = new ArrayList<>();
        for (ShoppingCartItem item : cart) {
            OrderBookServiceModel bookServiceModel = this.modelMapper.map(item.getBook(), OrderBookServiceModel.class);

            for (int i = 0; i < item.getQuantity(); i++) {
                books.add(bookServiceModel);
            }
        }

        orderServiceModel.setBooks(books);
        orderServiceModel.setTotalPrice(this.calcTotal(cart));

        return orderServiceModel;
    }
}
