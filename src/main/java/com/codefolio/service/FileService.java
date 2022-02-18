package com.codefolio.service;

import java.util.List;
import java.util.Optional;

import com.codefolio.vo.FileVO;

import org.springframework.core.io.Resource;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void saveFile(List<FileVO> fileList);

    public Resource selectFile(String fileName);

    public Iterable<FileVO> getFileList();

    public Optional<FileVO> getUploadFile(int fileSeq);
    public int getFileSeq();

    public FileVO sfile(MultipartFile file);
}
