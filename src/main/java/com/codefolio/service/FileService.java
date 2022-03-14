package com.codefolio.service;

import java.util.List;
import java.util.Optional;

import com.codefolio.vo.FileVO;

import org.springframework.core.io.Resource;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void saveFile(List<FileVO> fileList);

    public List<FileVO> getFileListBySeq(int seq);

    void deleteFileBySeq(int seq);

    public Resource selectFile(int seq);

    public Iterable<FileVO> getFileList();

    public Optional<FileVO> getUploadFile(int fileSeq);

    public int getFileSeq();

    public FileVO sfile(MultipartFile file);

    List<FileVO> getFileListBySeq(int seq);
}
