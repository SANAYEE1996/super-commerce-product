package bestcommerce.brand.manager.controller;

import bestcommerce.brand.manager.dto.BrandRegisterDto;
import bestcommerce.brand.manager.entity.Manager;
import bestcommerce.brand.manager.service.BrandService;
import bestcommerce.brand.manager.service.ManagerService;
import bestcommerce.brand.manager.service.RoleService;
import bestcommerce.brand.util.converter.EntityConverter;
import bestcommerce.brand.manager.ManagerRole;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
import bestcommerce.brand.util.exception.DuplicateBrandManagerException;
import bestcommerce.brand.util.image.ImageSaveService;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {

    private final AmazonS3Client amazonS3Client;

    private final ManagerService managerService;

    private final BrandService brandService;

    private final RoleService roleService;

    private final ImageSaveService imageSaveService;

    private final EntityConverter entityConverter;

    @Transactional
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto registerBrand(@RequestPart BrandRegisterDto request, @RequestPart MultipartFile img){

        String fileName = img.getOriginalFilename() +"_"+ System.currentTimeMillis();

        try{
            Manager manager = managerService.findManager(request.getManagerEmail());
            validatePossibleBrandRegisterManager(manager.getRoles());
            imageSaveService.saveBrandImage(amazonS3Client,img,fileName);
            managerService.registerBrand(manager,brandService.findBrand(brandService.registerBrand(entityConverter.toBrandEntity(request, fileName))));
            roleService.updateRole(manager.getId(), ManagerRole.BRAND_MASTER.getRole());
        } catch (IOException e){
            e.printStackTrace();
            return ResponseDto.builder().message(e.getMessage()).responseStatus(ResponseStatus.EXCEPTION).build();
        } catch (DuplicateBrandManagerException e){
            log.error(e.getMessage());
            brandService.deleteBrand(e.getRemoveBrandId());
            return ResponseDto.builder().message(e.getMessage()).responseStatus(ResponseStatus.EXCEPTION).build();
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().message(e.getMessage()).responseStatus(ResponseStatus.EXCEPTION).build();
        }

        return ResponseDto.builder().message("등록 성공").responseStatus(ResponseStatus.OK).build();
    }

    private void validatePossibleBrandRegisterManager(List<String> roleList) throws IOException{
        if(!(roleList.size() == 1 && roleList.contains(ManagerRole.NONE.getRole()))){
            throw new IOException("등록 할 수 없는 계정");
        }
    }
}
