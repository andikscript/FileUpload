package com.andikscript.fileupload.controller;

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

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping(value = "/api/files/")
public class FilesController {

    @Autowired
    private FilesStorageService filesStorageService;

    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        filesStorageService.save(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("Uploaded the file successfully : " + file.getOriginalFilename()));
    }

    @GetMapping(value = "file")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> filesInfos = filesStorageService.loadAll()
                .map(path -> {
                    return new FileInfo(
                            path.getFileName().toString(),
                            MvcUriComponentsBuilder
                                    .fromMethodName(FilesController.class, "getFile"
                                    , path.getFileName().toString()).build().toString()
                    );
                }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(filesInfos);
    }

    @GetMapping(value = "/file/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable(value = "filename") String filename) {
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
