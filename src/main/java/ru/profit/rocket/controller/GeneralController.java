package ru.profit.rocket.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.profit.rocket.dto.user.in.UserRegisterDTO;
import ru.profit.rocket.dto.user.out.UserAuthDTO;
import ru.profit.rocket.dto.user.out.UserDTO;
import ru.profit.rocket.dto.user.out.UserTokenDTO;
import ru.profit.rocket.mapper.user.UserMapper;
import ru.profit.rocket.service.user.UserService;
import ru.profit.rocket.service.user.argument.UserCreateArgument;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/service")
@Api("Внутренний контроллер service")
public class GeneralController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Value("${server.hostnameFull}")
    private String SHostname;

    @Value("${client.hostnameFull}")
    private String CHostname;

    @Autowired
    public GeneralController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @ApiOperation("Регистрация")
    @ResponseStatus(CREATED)
    @PostMapping(value = "/register")
    public UserDTO registerUser(HttpServletResponse response, @RequestBody UserRegisterDTO dto) throws Exception {
        System.out.println(response.getStatus());
        return userMapper.toDTO(userService.create(UserCreateArgument.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .build()));
    }

    @ApiOperation("Авторизация")
    @ResponseStatus(OK)
    @PostMapping(value = "/login")
    public UserTokenDTO login(@RequestBody UserAuthDTO dto) {
        final UUID id = userService.findByLogin(dto.getLogin()).getId();
        return UserTokenDTO.builder().token(userService.getToken(dto)).id(id).build();
    }

    @GetMapping("/activate/{code}")
    public void activateUser(HttpServletResponse response, @PathVariable String code) throws IOException {
        boolean isActivated = userService.activateUser(code);

        if (!isActivated) {
            response.sendRedirect( String.format("http://%s/home", CHostname) +"?activate=false"); }
        else {
            response.sendRedirect( String.format("http://%s/home", CHostname) +"?activate=true"); }
    }

    @PostMapping("/is-email")
    public boolean isEmail(@RequestBody String email) throws IOException { //
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(email);
        JsonNode node = mapper.readTree(parser);
        final String e = node.get("email").asText("");
        System.out.println(e);
        boolean isEmail = userService.isEmail(e);
        System.out.println(isEmail);
        return isEmail;
    }

}
