package bestcommerce.brand.manager.service;

import bestcommerce.brand.manager.entity.Manager;
import bestcommerce.brand.manager.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerDetailService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(ManagerDetailService.class);

    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        log.info("load User by User Name :: {} ", username);
        return managerRepository.findByManagerEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(()-> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Manager manager){
        return Manager.builder()
                .managerEmail(manager.getManagerEmail())
                .managerPassword(manager.getManagerPassword())
                .roles(manager.getRoles())
                .build();
    }
}
