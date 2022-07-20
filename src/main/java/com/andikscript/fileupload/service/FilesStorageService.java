package com.andikscript.fileupload.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

    public void setRoot(String path);

    public void save(MultipartFile file);

    public Resource load(String filename);

    public Stream<Path> loadAll();
}
