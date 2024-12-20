//7. created controller package in project spring
//8. product controller class created


package com.mohan.spring.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.mohan.spring.model.product;
import com.mohan.spring.model.ProductDto;
import com.mohan.spring.servies.ProductRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;





@Controller   // 9.Controller annotation is used to control the 
@RequestMapping("/products")  // 10.used to tell path in webpage in browser
public  class ProductController{

    @Autowired //11.
    private ProductRepository repo;                                                           //12. variable created for ProductRepository

    @GetMapping({"", "/"})                                                                    //13. showing the list of products in database.. table
    public String showProductList(Model model) {
        // List<product> products=repo.findAll();                                             //14.A->Assending order, new product will add at last
       List<product> products=repo.findAll(Sort.by(Sort.Direction.DESC, "id")); //14.B->desending order, new product will add at first
        model.addAttribute("products", products);
        return "products/index";                                                              //15.DATA Base records will show here..index page(its in ...main\resources\templates\products\index.html )
    }

    @GetMapping("/create")                                                                  //20.to cntroll the url for new product creation page
    public String showCreatePage( Model model) {
        ProductDto productDto=new ProductDto();
        model.addAttribute("productDto",productDto);
        return "products/CreateProducts";                                                   //21.this url page available in ...main\resources\templates\products folder)
    }
    
    @PostMapping("/create")// post mapping is used to make action by clicking submit button
    public String createProduc( @Valid @ModelAttribute ProductDto productDto,BindingResult result )  {  //22.this is for validation the details of values in productDto fields

                if (productDto.getImageFileName().isEmpty()){                                   //24.if the image is not uploaded or or empty-- to handle 
                    result.addError(new FieldError("productDto", "imageFile", "This image is required"));
                }
                if (result.hasErrors()){                                                        //25. if error 
                    return "products/CreateProducts";
                }

                //26.save image file
                MultipartFile image = productDto.getImageFileName();                            // to read image from productDto form
                Date createdat = new Date();                                                    // need for uniqe file for image
                String storageFileName = createdat.getTime() + "_" + image.getOriginalFilename();//storage file name will be currentTime_originalFileName

                try{
                    String uploadDir = "public/images/"; // to save image in this folder
                    Path uploadPath = Paths.get(uploadDir);

                    if (!Files.exists(uploadPath)){
                        Files.createDirectories(uploadPath);
                    }
                    try(InputStream inputStream = image.getInputStream()) {
                        Files.copy(inputStream, Paths.get(uploadDir+storageFileName),StandardCopyOption.REPLACE_EXISTING);
                    }
                }catch (Exception ex) {
                        System.out.println("Exception: "+ ex.getMessage());
                    }

                    product product = new product();
                    product.setName(productDto.getName());
                    product.setBrand(productDto.getBrand());
                    product.setCategory(productDto.getCategory());
                    product.setPrice(productDto.getPrice());
                    product.setDescription(productDto.getDescription());
                    product.setImageFileName(storageFileName);
                    product.setCreatedat((java.sql.Date) createdat);

                    repo.save(product);
                    

                
        return "redirect:/products";                                                        // 23.if error , page return to products
    }

    //27.to edit 
    @GetMapping("/edit")
    public String showEditPage(Model model,@RequestParam int id) {

        try {
            product product = repo.findById(id).get();
            model.addAttribute("product", product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());

            model.addAttribute("productDto", productDto);
            
        } 
        catch (Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
            return "redirect:/products";
        }

        return "products/EditProduct";
        }
        @PostMapping("/edit")
        public String updateProduct(
        Model model,
        @RequestParam int id,
        @Valid @ModelAttribute ProductDto productDto,
        BindingResult result){
            try {
                product product= repo.findById(id).get();
                model.addAttribute("product", product);
                
                if (result.hasErrors()){
                    return "products/EditProduct";
                }

                if (!productDto.getImageFileName().isEmpty()){
                    //delete old image
                    String uploadDir = "public/images/";
                    Path oldImagePath = Paths.get(uploadDir+product.getImageFileName());
                
                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println("Exception: "+ ex.getMessage());
                }

                //save new image file
                MultipartFile image = productDto.getImageFileName();
                Date createdAt= new Date();
                String storageFileName = createdAt.getTime()+ "_"+ image.getOriginalFilename();

                try(InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(uploadDir +storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }

                product.setImageFileName(storageFileName);
            }

            product.setName(productDto.getName());
            product.setBrand(productDto.getBrand());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());

            repo.save(product);

            } catch (Exception ex) {
                System.out.println("Exception: "+ ex.getMessage());
            }
            return "redirect:/products";   
    }    
    
    @GetMapping("/delete")
    public String deleteProduc(@RequestParam int id) {

        try {
            product product = repo.findById(id).get();

            //delete product image
            Path imagePath = Paths.get("public/images/"+ product.getImageFileName());

            try {
                Files.delete(imagePath);
                
            } catch (Exception ex) {
                System.out.println("Exception: "+ ex.getMessage());
            }

            //delete the product
            repo.delete(product);
            
        } catch (Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }

        return "redirect:/products"; 
    }
    
}