package dev.alnat.ustorage.web.api;

import dev.alnat.ustorage.core.model.FileDTO;
import dev.alnat.ustorage.core.model.StorageTypeEnum;
import dev.alnat.ustorage.core.service.FileService;
import dev.alnat.ustorage.exception.UStorageException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Api(
        description = "API для взаимодействия с файлами",
        authorizations = {@Authorization(value = "Basic")},
        protocols = "http, https",
        tags = "file"
)
@RestController
@RequestMapping(value = "/api/file")
@Validated
public class FileAPIController {

    private final FileService fileService;


    @Autowired
    public FileAPIController(FileService fileService) {
        this.fileService = fileService;
    }


    @ApiOperation(value = "Сохранение файла в дефолтной системе указанного типа.", code = 201)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Файл успешно сохранен"),
            @ApiResponse(code = 400, message = "Ошибка"),
            @ApiResponse(code = 401, message = "Ошибка авторизации"),
            @ApiResponse(code = 403, message = "Недостаточно прав для выполнения"),
            @ApiResponse(code = 404, message = "Данные не найдены"),
            @ApiResponse(code = 500, message = "Ошибка при выполнении")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{storageType}/upload", method = RequestMethod.POST, produces = "text/plain")
    public String upload(@RequestParam("file")
                               MultipartFile file,
                       @ApiParam(value = "File Type", required = true, defaultValue = "FILE_SYSTEM")
                       @PathVariable
                               StorageTypeEnum storageType) throws UStorageException {
        return fileService.saveFile(file, storageType);
    }

    @ApiOperation(value = "Получение файла из хранилища")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Файл успешно найден и возвращен"),
            @ApiResponse(code = 400, message = "Ошибка"),
            @ApiResponse(code = 401, message = "Ошибка авторизации"),
            @ApiResponse(code = 403, message = "Недостаточно прав для выполнения"),
            @ApiResponse(code = 404, message = "Данные не найдены"),
            @ApiResponse(code = 500, message = "Ошибка при выполнении")
    })
    @RequestMapping(value = "/download", method = {RequestMethod.POST, RequestMethod.GET})
    public void download(@ApiParam(value = "StorageFileName", required = true, defaultValue = "test")
                         @RequestParam String fileName,
                         HttpServletResponse response) throws UStorageException, Exception {
        // TODO Возвращать 404 если файла нет
        // TODO Посмотреть на FileSystemResource
        FileDTO file = fileService.download(fileName);

        response.setContentLength(file.getFile().length);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName());

        response.getOutputStream().write(file.getFile());
        response.getOutputStream().flush();
    }

}
