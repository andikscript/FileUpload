package com.andikscript.fileupload.exception;

import com.andikscript.fileupload.message.ResponseMessage;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxFileSize(SizeLimitExceededException exc) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseMessage("File to large"));
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ResponseMessage> fileNotFound(MultipartException exc) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseMessage("File not found"));
    }
}
