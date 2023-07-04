package bestcommerce.brand.manager.controller;

import bestcommerce.brand.jwt.TokenInfo;
import bestcommerce.brand.manager.dto.LoginDto;
import bestcommerce.brand.manager.dto.ManagerDto;
import bestcommerce.brand.manager.entity.Manager;
import bestcommerce.brand.manager.service.ManagerService;
import bestcommerce.brand.manager.service.RoleService;
import bestcommerce.brand.manager.ManagerRole;
import bestcommerce.brand.util.ResponseBody;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
import bestcommerce.brand.util.TimeFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManageController {

    private final ManagerService managerService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginDto loginDto){
        try {
            TokenInfo token = managerService.login(loginDto.getLoginEmail(), loginDto.getLoginPassword());
            return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("").body(new ResponseBody<>(token)).build();
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(ResponseStatus.EXCEPTION.getStatusCode()).message(e.getMessage()).build();
        }
    }

    @PostMapping("/register")
    public ResponseDto register(@RequestBody ManagerDto managerDto){
        try {
            Manager registerManager = Manager.builder()
                                            .managerEmail(managerDto.getEmail())
                                            .managerPassword(passwordEncoder.encode(managerDto.getPassword()))
                                            .managerName(managerDto.getName())
                                            .contactNumber(managerDto.getContactNumber())
                                            .registerDate(LocalDateTime.now().format(TimeFormat.timeFormatter))
                                            .build();
            managerService.saveManager(registerManager);
            Manager manager = managerService.findManager(managerDto.getEmail());
            roleService.addRole(manager.getId(), ManagerRole.NONE.getRole());
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(ResponseStatus.EXCEPTION.getStatusCode()).message(e.getMessage()).build();
        }
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("가입 성공").build();
    }

}
