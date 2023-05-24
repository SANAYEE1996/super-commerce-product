package bestcommerce.brand.manager.controller;

import bestcommerce.brand.jwt.TokenInfo;
import bestcommerce.brand.manager.dto.LoginDto;
import bestcommerce.brand.manager.dto.ManagerDto;
import bestcommerce.brand.manager.entity.Manager;
import bestcommerce.brand.manager.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManageController {

    private final ManagerService managerService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginDto loginDto){
        return managerService.login(loginDto.getLoginEmail(), loginDto.getLoginPassword());
    }

    @PostMapping("/register")
    public void register(@RequestBody ManagerDto managerDto){
        Manager registerManager = Manager.builder()
                                        .managerEmail(managerDto.getEmail())
                                        .managerPassword(passwordEncoder.encode(managerDto.getPassword()))
                                        .managerName(managerDto.getName())
                                        .contactNumber(managerDto.getContactNumber())
                                        .registerDate(managerDto.getRegisterDate())
                                        .build();
        managerService.saveMember(registerManager);
    }

}
