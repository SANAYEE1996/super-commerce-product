package bestcommerce.brand.manager.service;

import bestcommerce.brand.jwt.JwtTokenProvider;
import bestcommerce.brand.jwt.TokenInfo;
import bestcommerce.brand.manager.entity.Manager;
import bestcommerce.brand.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManagerService {

    private static final Logger log = LoggerFactory.getLogger(ManagerService.class);

    private final ManagerRepository managerRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
            log.info("authentication getAuthorities: {}", authentication.getAuthorities());
            return tokenInfo;
        }catch (RuntimeException e){
            e.printStackTrace();
            return TokenInfo.builder().message(e.getMessage()).build();
        }
    }

    public Long saveMember(Manager registerManager){
        if(!managerRepository.existsByManagerEmail(registerManager.getManagerEmail())){
            return managerRepository.save(registerManager).getMemberId();
        }
        throw new RuntimeException("등록된 이메일 입니다.");
    }

    public Manager findManager(String email){
        return managerRepository.findByManagerEmail(email).orElseThrow(()-> new RuntimeException("등록된 사용자가 아닙니다."));
    }

}
