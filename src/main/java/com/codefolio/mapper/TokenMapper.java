package com.codefolio.mapper;

import com.codefolio.vo.TokenVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper {
    void updateRefToken(TokenVO tokenVO);

    void updateAcToken(TokenVO tokenVO);

    TokenVO getTokenByAcToken(String tokenVO);

    void joinUUID(TokenVO tokenVO);
}
