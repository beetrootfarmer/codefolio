package com.codefolio.service;


import com.codefolio.vo.TokenVO;

public interface TokenService {
    void updateRefToken(TokenVO tokenVO);

    void updateAcToken(TokenVO tokenVO);

    TokenVO getTokenByAcToken(String acToken);

    void joinUUID(TokenVO tokenVO);
}
