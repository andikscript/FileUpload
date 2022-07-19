package com.andikscript.fileupload.controller;

import com.andikscript.fileupload.message.ResponseMessage;
import com.andikscript.fileupload.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RestController
@RequestMapping(value = "/api/files/")
public class FilesController {

    @Autowired
    private FilesStorageService filesStorageService;

    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        try {
            filesStorageService.save(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseMessage("Uploaded the file successfully : " + file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseMessage("Could not upload file : " + file.getOriginalFilename()
                    + ", Error : " + e.getMessage()));
        }
    }
}
