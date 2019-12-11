package org.softuni.learningportal.validation.service;

import org.softuni.learningportal.domain.models.service.UserServiceModel;

public interface UserValidationService {

    boolean isUserValid(UserServiceModel userServiceModel);
}
