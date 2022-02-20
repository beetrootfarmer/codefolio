package com.codefolio.mapper;

import java.util.List;
import java.util.Optional;

import com.codefolio.vo.FileVO;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


@Mapper
public interface FileMapper {
    List<FileVO> getFileListBySeq(@Param("seq") int seq);

    public Resource selectFile(@Param("seq") int seq);

    void deleteFileBySeq(@Param("seq") int seq);

    public Iterable<FileVO> findAll();

    public Optional<FileVO> findBySeq(int fileSeq);

    int getFileSeq();

    void saveFile(FileVO vo);
}