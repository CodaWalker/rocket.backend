package ru.profit.rocket.controller;

import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.profit.rocket.dto.user.in.UserUpdateDTO;
import ru.profit.rocket.dto.user.out.UserDTO;
import ru.profit.rocket.mapper.user.UserMapper;
import ru.profit.rocket.model.search.SearchObject;
import ru.profit.rocket.model.user.Role;
import ru.profit.rocket.service.role.RoleService;
import ru.profit.rocket.service.user.UserService;
import ru.profit.rocket.service.user.argument.UserSearchArgument;
import ru.profit.rocket.service.user.argument.UserUpdateArgument;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequestMapping("api/user")
@Api("Внутренний контроллер user")
@RestController
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserMapper userMapper, UserService userService, RoleService roleService) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.roleService = roleService;
    }

    @ApiOperation("Проверить соответствие пользователь - роль")
    @GetMapping("/is-user-role")
    public boolean getAllRoles(@RequestParam(value = "user",defaultValue = "") String searchUser,
                               @RequestParam(value = "role",defaultValue = "") String searchRole){
        return roleService.isUserRole(searchUser, searchRole);
    }


    @ApiOperation("Получить все роли по id")
    @GetMapping("/get-roles/{login}")
    public Set<Role> getRoles(@PathVariable String login){
        try{
            return roleService.getAllRolesByUser(userService.findByLogin(login));
        } catch (IllegalArgumentException ignored){ }
        return null;
    }

    @ApiOperation("Получить пользователей")
    @GetMapping("/get")
    public ResponseEntity<List<UserDTO>> getAll(@NotNull Pageable pageable){
        return ResponseEntity.ok((List<UserDTO>) userMapper.toDTOCollection(userService.getAll(
                pageable)));
    }

    @ApiOperation("Поиск пользователей")
    @GetMapping("/search/{login}")
    public ResponseEntity<List<UserDTO>> search(@NotNull Pageable pageable,@PathVariable String login){
        if(!login.equals("")){
            UserSearchArgument userSearchArgument = UserSearchArgument.builder().login(SearchObject.<String>builder().value(login).build()).build();
            userMapper.toDTOCollection(userService.getAllByParam(userSearchArgument,pageable));
        }
        return ResponseEntity.ok((List<UserDTO>) userMapper.toDTOCollection(userService.getAll(
                pageable)));
    }

    @ApiOperation("Получить пользователя")
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getFilter(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(userMapper.toDTO(userService.getOne(UUID.fromString(id))));
    }

//    @ApiOperation("Получить всех пользователей с определенными ролями")
//    @GetMapping("/get/filter?role={role}")
//    public List<UserDTO> getAllByRole(@NotNull final Pageable pageable, @PathVariable String role){
//        System.out.println(role);
//        return (List<UserDTO>) userMapper.toDTOCollection(userService.getAllByParam(
//                UserSearchArgument.builder().roles(null).build(),
//                pageable));
//    }

    @ApiOperation("Обновить пользователя")
    @PostMapping("/update/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserUpdateDTO dto, @PathVariable String id){
        return ResponseEntity.ok(userMapper.toDTO(userService.update(UUID.fromString(id), UserUpdateArgument.builder().email(dto.getEmail()).login(dto.getLogin()).password(dto.getPassword()).build())));
    }

    @ApiOperation("Удалить пользователя")
    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id){
        return ResponseEntity.ok(userService.delete(UUID.fromString(id)));
    }
}
