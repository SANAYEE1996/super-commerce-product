package bestcommerce.brand.manager.controller;

import bestcommerce.brand.manager.dto.BrandRegisterDto;
import bestcommerce.brand.manager.entity.Brand;
import bestcommerce.brand.manager.entity.Manager;
import bestcommerce.brand.manager.service.BrandService;
import bestcommerce.brand.manager.service.ManagerService;
import bestcommerce.brand.util.EntityConverter;
import bestcommerce.brand.util.ResponseDto;
import bestcommerce.brand.util.ResponseStatus;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {

    private final AmazonS3Client amazonS3Client;

    private final ManagerService managerService;

    private final BrandService brandService;

    private final EntityConverter entityConverter;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto registerBrand(@RequestPart BrandRegisterDto request, @RequestPart MultipartFile img){
        log.info("file name : {}", img.getOriginalFilename());
        Manager manager = managerService.findManager(request.getManagerEmail());
        if(manager.getRoles().size() == 1 && manager.getRoles().get(0).equals("NONE")){
            return ResponseDto.builder().message("등록 할 수 없는 계정").responseStatus(ResponseStatus.OK).build();
        }
        try{
            String fileName = img.getOriginalFilename() + System.currentTimeMillis();
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(img.getContentType());
            metadata.setContentLength(img.getSize());
            amazonS3Client.putObject(bucket,fileName,img.getInputStream(),metadata);
            brandService.registerBrand(entityConverter.toBrandEntity(request, fileName));
            manager.getRoles().add("BRAND_MASTER");
            managerService.saveMember(manager);
            return ResponseDto.builder().message("등록 성공").responseStatus(ResponseStatus.OK).build();
        } catch (IOException e){
            e.printStackTrace();
            return ResponseDto.builder().message(e.getMessage()).responseStatus(ResponseStatus.EXCEPTION).build();
        }
    }
}