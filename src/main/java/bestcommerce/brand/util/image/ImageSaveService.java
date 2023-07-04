package bestcommerce.brand.util.image;

import bestcommerce.brand.product.dto.ProductImageDto;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ImageSaveService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void saveProductImage(AmazonS3Client amazonS3Client, Long productId, List<MultipartFile> productImage, List<MultipartFile> infoImage, List<ProductImageDto> productImageDtoList) throws IOException{
        saveImage(productImage, amazonS3Client, productId, productImageDtoList, "TITLE");
        saveImage(infoImage, amazonS3Client, productId, productImageDtoList, "INFO");
    }

    private void saveImage(List<MultipartFile> imageList, AmazonS3Client amazonS3Client, Long productId, List<ProductImageDto> productImageDtoList, String type) throws IOException{
        int odr = 0;
        for(MultipartFile img : imageList){
            saveImage(amazonS3Client, img, productImageDtoList, productId, type, odr);
            odr++;
        }
    }

    private void saveImage(AmazonS3Client amazonS3Client, MultipartFile img, List<ProductImageDto> list, Long productId, String type, int odr) throws IOException {
        String fileName = img.getOriginalFilename() +"_"+ System.currentTimeMillis();
        log.info("file name : {}", fileName);
        list.add(new ProductImageDto(productId, 0L, type, fileName, odr));
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(img.getContentType());
        metadata.setContentLength(img.getSize());
        amazonS3Client.putObject(bucket,fileName,img.getInputStream(),metadata);
    }

    public void updateProductTitleImage(AmazonS3Client amazonS3Client,
                                        Long productId, List<MultipartFile> productImage,
                                        List<ProductImageDto> imageDtoList, List<Integer> odrList) throws IOException{
        if(odrList.size() != productImage.size()){
            throw new IOException("list size is not equal");
        }
        for(int i = 0; i < odrList.size(); i++){
            saveImage(amazonS3Client, productImage.get(i), imageDtoList, productId, "TITLE", odrList.get(i));
        }
    }

    public void saveBrandImage(AmazonS3Client amazonS3Client, MultipartFile img, String fileName) throws IOException{
        log.info("save file name : {}", fileName);
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(img.getContentType());
        metadata.setContentLength(img.getSize());
        amazonS3Client.putObject(bucket,fileName,img.getInputStream(),metadata);
    }
}
