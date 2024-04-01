package bank.model.services;

import bank.model.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Qualifier("Composer")
public class Composer {

    public User composeUser(HttpServletRequest request) {

        return User.builder()
                .firstName(request.getParameter("firstname"))
                .lastName(request.getParameter("lastname"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .phoneNumber(request.getParameter("phone_number"))
                .build();

    }

    public User composeUser(HttpServletRequest request, boolean hasFullName) {
        if (!hasFullName) {
            return composeUser(request);
        }

        String[] fullName = request.getParameter("full_name").split(" ");

        if (fullName.length != 2) {
            throw new IllegalArgumentException("You have to enter your full name (only first and last name)!");
        }

        return User.builder()
                .firstName(fullName[0])
                .lastName(fullName[1])
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .phoneNumber(request.getParameter("phone_number"))
                .build();

    }

}