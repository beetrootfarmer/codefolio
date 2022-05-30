package com.codefolio.impl;

import com.codefolio.mapper.TokenMapper;
import com.codefolio.service.TokenService;
import com.codefolio.vo.TokenVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenMapper tokenMapper;

    @Override
    public void updateRefToken(TokenVO tokenVO){tokenMapper.updateRefToken(tokenVO);}

    @Override
    public void updateAcToken(TokenVO tokenVO){tokenMapper.updateAcToken(tokenVO);}

    @Override
    public TokenVO getTokenByAcToken(String tokenVO){return tokenMapper.getTokenByAcToken(tokenVO);}

    @Override
    public void joinUUID(TokenVO tokenVO){tokenMapper.joinUUID(tokenVO);}

}
