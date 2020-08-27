package ru.profit.rocket.mapper.user;

import org.mapstruct.Mapper;
import ru.profit.rocket.dto.user.out.UserDTO;
import ru.profit.rocket.mapper.CustomMapper;
import ru.profit.rocket.model.BaseEntity;
import ru.profit.rocket.model.user.User;

import java.util.List;

@Mapper
public interface UserMapper extends CustomMapper<UserDTO, User> {
    @Override
//    @Mapping(target="roles_name", expression = "java(entity.getRolesNames())")
    UserDTO toDTO(User entity);

    @Override
    User toEntity(UserDTO dto);

    @Override
    List<UserDTO> toDTOList(List<User> entityList);

    @Override
    Iterable<User> toEntityCollection(Iterable<UserDTO> dtoCollection);
}
