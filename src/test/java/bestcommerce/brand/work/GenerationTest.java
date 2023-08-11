package bestcommerce.brand.work;

import bestcommerce.brand.product.dto.ProductInfoDto;
import bestcommerce.brand.product.entity.Brand;
import bestcommerce.brand.product.entity.Product;
import bestcommerce.brand.product.service.BrandService;
import bestcommerce.brand.product.service.ProductService;
import bestcommerce.brand.size.dto.QuantityDto;
import bestcommerce.brand.size.dto.SizeDto;
import bestcommerce.brand.size.entity.Quantity;
import bestcommerce.brand.size.service.QuantityService;
import bestcommerce.brand.size.service.SizeService;
import bestcommerce.brand.util.converter.DtoConverter;
import bestcommerce.brand.util.work.GenerateRandomString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class GenerationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private QuantityService quantityService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private DtoConverter dtoConverter;

    @Autowired
    private GenerateRandomString generateRandomString;

    @Value("${testUtil.fileLocation}")
    private String fileLocation;

    @Test
    void GenerateRandomStringTest() throws Exception{

        List<Brand> brandList = brandService.findAllBrand();

        int length = 5;
        System.out.println(generateRandomString.generateProductCode(length));
//
//        FileReader reader = new FileReader(fileLocation+"/40page.json");
//        JSONParser parser = new JSONParser();
//        JSONObject jsonObject = (JSONObject) parser.parse(reader);
//        JSONArray jsonArray = (JSONArray) jsonObject.get("selection1");
//        List<ProductInfoDto> productInfoDtoList = new ArrayList<>();
//        for (Object o : jsonArray) {
//            productInfoDtoList.add(ProductInfoDto.builder()
//                    .brandId(brandList.get((int) (Math.random() * brandList.size())).getId())
//                    .productCode(generateRandomString.generateProductCode(5))
//                    .productName(((JSONObject) o).get("name").toString())
//                    .productPrice((int) ((Math.random() * 10000) + 1) * 100)
//                    .productInfo(((JSONObject) o).get("name").toString())
//                    .build());
//        }
//
//        productService.saveAll(productInfoDtoList);
        List<ProductInfoDto> allProductList = dtoConverter.toProductInfoDtoList(productService.findAllProduct());
        List<ProductInfoDto> partProductList = dtoConverter.toProductInfoDtoList(productService.getSampleList(5L));
        System.out.println(allProductList.size());
        System.out.println(partProductList.size());
        assertThat(allProductList.size() - partProductList.size()).isEqualTo(3);
    }

    @Test
    void generateQuantityTest(){
//        List<ProductInfoDto> productInfoDtoList = dtoConverter.toProductInfoDtoList(productService.getSampleList(5L));
//
//        List<QuantityDto> quantityDtoList = new ArrayList<>();
//
//        for(ProductInfoDto productInfoDto : productInfoDtoList){
//            List<String> quantities = generateRandomString.getRandomQuantityList((int)(Math.random()*3));
//            for(String s : quantities){
//                quantityDtoList.add(new QuantityDto(null, productInfoDto.getId(), s, (int)((Math.random()*10000)+1)));
//            }
//        }
//
//        System.out.println(quantityDtoList.size());
//        quantityService.saveAll(quantityDtoList);
    }

    @Test
    void generateSizeTest(){

//        List<QuantityDto> quantityDtoList = dtoConverter.toQuantityDtoList(quantityService.sampleList(31L));
//
//        Map<Long, List<QuantityDto>> map = new HashMap<>();
//
//        for(QuantityDto quantityDto : quantityDtoList){
//            Long key = quantityDto.getProductId();
//            if(!map.containsKey(key)){
//                map.put(key, new ArrayList<>());
//            }
//            map.get(key).add(quantityDto);
//        }
//
//        List<SizeDto> sizeDtoList = new ArrayList<>();
//        for(Long key : map.keySet()){
//            generateRandomString.putRandomBodyValue(sizeDtoList, map.get(key));
//        }
//
//        System.out.println(sizeDtoList.size());
////        for(SizeDto sizeDto : sizeDtoList){
////            System.out.println(sizeDto.toString());
////        }
//
//        sizeService.saveAll(sizeDtoList);

    }
}
