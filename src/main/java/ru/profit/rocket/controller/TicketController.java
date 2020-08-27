package ru.profit.rocket.controller;

import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.profit.rocket.dto.user.out.UserDTO;
import ru.profit.rocket.service.user.argument.UserSearchArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/ticket")
@Api("Внутренний контроллер ticket")
@RestController
public class TicketController {
    @ApiOperation("Получить все заявки")
    @GetMapping("/get")
    public List<UserDTO> getFilter(@NotNull final Pageable pageable,
                                   @RequestParam(value = "id",defaultValue = "") String id
    ){
        return null;
    }
}
