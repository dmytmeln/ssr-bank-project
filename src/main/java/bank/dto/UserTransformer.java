package bank.dto;

import bank.domain.User;

public class UserTransformer {

    public static User convertToEntity(UserForm userForm) {
        return User.builder()
                .firstname(userForm.getFirstname())
                .lastname(userForm.getLastname())
                .email(userForm.getEmail())
                .password(userForm.getPassword())
                .phoneNumber(userForm.getPhoneNumber())
                .creationDate(userForm.getCreationDate())
                .build();
    }

    public static User convertToEntity(UserForm userForm, Long userId) {
        return User.builder()
                .id(userId)
                .firstname(userForm.getFirstname())
                .lastname(userForm.getLastname())
                .email(userForm.getEmail())
                .password(userForm.getPassword())
                .phoneNumber(userForm.getPhoneNumber())
                .creationDate(userForm.getCreationDate())
                .build();
    }

    public static UserForm convertToUserAuth(User user) {
        return UserForm.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .creationDate(user.getCreationDate())
                .build();
    }

}
