package bank.mapper;

import bank.dto.UserForm;
import bank.model.Role;
import bank.model.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final TypeMap<UserForm, User> typeMap;

    public UserMapper(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {

        Converter<String, String> passwordConverter = converter -> passwordEncoder.encode(converter.getSource());
        this.typeMap = modelMapper.createTypeMap(UserForm.class, User.class)
                .setProvider(p -> User.builder().role(Role.USER).build())
                .addMappings(
                        mapper -> mapper.using(passwordConverter).map(UserForm::getPassword, User::setPassword)
                );
    }

    public User mapToUser(UserForm userForm) {
        return typeMap.map(userForm);
    }

    public User mapToUser(UserForm userForm, Long userId) {
        User user = typeMap.map(userForm);
        user.setId(userId);
        return user;
    }

}
