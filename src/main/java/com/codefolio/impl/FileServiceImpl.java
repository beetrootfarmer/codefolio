package com.codefolio.impl;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import com.codefolio.mapper.FileMapper;
import com.codefolio.service.FileService;
import com.codefolio.vo.FileVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    public
    FileMapper fileMapper;


    @Override
    public void saveFile(List<FileVO>  fileList){
        for(FileVO vo : fileList) {
            fileMapper.saveFile(vo);
        }
    }

    @Override
    public Resource selectFile(int seq){
        return fileMapper.selectFile(seq);
    }

    @Override
    public void deleteFileBySeq(int seq){
        fileMapper.deleteFileBySeq(seq);}
    
    public List<FileVO> getFileListBySeq(int seq) {
        return fileMapper.getFileListBySeq(seq);
    }


    @Override
    public List<FileVO> getFileList(){
           return fileMapper.findAll();
    }
    @Override
    public Optional<FileVO> getUploadFile(int fileSeq) {
        return fileMapper.findBySeq(fileSeq);
    }

    @Override
    public int getFileSeq() {
        return fileMapper.getFileSeq();
    }

    // temp
    @Override
    public FileVO sfile(MultipartFile file) {
        return null;
    }

}
