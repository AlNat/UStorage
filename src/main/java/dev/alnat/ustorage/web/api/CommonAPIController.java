package dev.alnat.ustorage.web.api;

import dev.alnat.ustorage.core.service.CommonService;
import dev.alnat.ustorage.exception.UStorageException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для общих REST методов
 *
 * Created by @author AlNat on 16.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Api(
        description = "API для взаимодействия с общими методами приложения",
        authorizations = {@Authorization(value = "Basic")},
        protocols = "http, https",
        tags = "common"
)
@RestController
@RequestMapping(value = "/api")
public class CommonAPIController {

    private final CommonService commonService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CommonAPIController(CommonService commonService) {
        this.commonService = commonService;
    }


    @ApiOperation(value = "Метод проверки приложения")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Версия успешно получена"),
            @ApiResponse(code = 400, message = "Ошибка"),
            @ApiResponse(code = 401, message = "Ошибка авторизации"),
            @ApiResponse(code = 403, message = "Недостаточно прав для выполнения"),
            @ApiResponse(code = 404, message = "Данные не найдены"),
            @ApiResponse(code = 500, message = "Ошибка при выполнении")
    })
    @RequestMapping(value = "/ping", produces = "text/plain")
    public String ping() throws UStorageException {
        return "pong";
    }

    @ApiOperation(value = "Получение версии приложение")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Версия успешно получена"),
            @ApiResponse(code = 400, message = "Ошибка"),
            @ApiResponse(code = 401, message = "Ошибка авторизации"),
            @ApiResponse(code = 403, message = "Недостаточно прав для выполнения"),
            @ApiResponse(code = 404, message = "Данные не найдены"),
            @ApiResponse(code = 500, message = "Ошибка при выполнении")
    })
    @RequestMapping(value = "/version", method = RequestMethod.GET, produces = "text/plain")
    public String getVersion() throws UStorageException {
        return commonService.getVersion();
    }

    @RequestMapping(value = "/encypt", method = RequestMethod.GET, produces = "text/plain")
    public String encrypt(String enc) {
        return bCryptPasswordEncoder.encode(enc);
    }

}
