package bestcommerce.brand.manager.controller;

import bestcommerce.brand.manager.dto.BrandRegisterDto;
import bestcommerce.brand.manager.service.ManagerService;
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

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void registerBrand(@RequestPart BrandRegisterDto request, @RequestPart MultipartFile img){
        log.info("file name : {}", img.getOriginalFilename());
        try{
            String fileName = img.getOriginalFilename();
            String fileUrl = "https://" + bucket + "/test" +fileName;
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(img.getContentType());
            metadata.setContentLength(img.getSize());
            amazonS3Client.putObject(bucket,fileName,img.getInputStream(),metadata);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
