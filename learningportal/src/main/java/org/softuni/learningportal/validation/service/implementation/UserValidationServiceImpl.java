package org.softuni.learningportal.validation.service.implementation;

import org.softuni.learningportal.domain.models.service.UserServiceModel;
import org.softuni.learningportal.validation.service.UserValidationService;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isUserValid(UserServiceModel userServiceModel) {
        return userServiceModel != null;
    }
}
