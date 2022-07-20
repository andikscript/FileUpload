package com.andikscript.fileupload.controller;

import com.andikscript.fileupload.enums.PathStore;
import com.andikscript.fileupload.message.ResponseMessage;
import com.andikscript.fileupload.model.FileInfo;
import com.andikscript.fileupload.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping(value = "/api/file/admin")
public class FilesAdminController {

    @Autowired
    private FilesStorageService filesStorageService;

    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        filesStorageService.setRoot(PathStore.ADMIN);
        filesStorageService.save(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Uploaded the file successfully : " + file.getOriginalFilename()));
    }

    @PostMapping(value = "/upload/files")
    public ResponseEntity<ResponseMessage> uploadMultipleFile(@RequestParam(value = "file") MultipartFile[] file) {
        filesStorageService.setRoot(PathStore.ADMIN);
        List<String> fileName = new ArrayList<>();
        Arrays.asList(file)
                .stream()
                .forEach(file1 -> {
                    filesStorageService.save(file1);
                    fileName.add(file1.getOriginalFilename());
                });

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Uploaded the file successfully : " + fileName));
    }

    @GetMapping(value = "/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        filesStorageService.setRoot(PathStore.ADMIN);
        List<FileInfo> filesInfos = filesStorageService.loadAll()
                .map(path -> {
                    return new FileInfo(
                            path.getFileName().toString(),
                            MvcUriComponentsBuilder
                                    .fromMethodName(FilesAdminController.class, "getFile"
                                    , path.getFileName().toString()).build().toString()
                    );
                }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(filesInfos);
    }

    @GetMapping(value = "/download/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable(value = "filename") String filename) {
        filesStorageService.setRoot(PathStore.ADMIN);
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
