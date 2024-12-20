//17. created ProductDto class in Model package
//18. identify to create new product (new record) field(column) in table

package com.mohan.spring.model;

import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.constraints.*;


public class ProductDto {

    @NotEmpty(message = "The name is Required")
    private String name;                                //Input column
    
    @NotEmpty(message = "The brand is Required")
    private String brand;                               //Input column
    
    @NotEmpty(message = "The category name is Required")
    private String category;                            //Input column
    
    @Min(0)
    private double price;                               //Input column

    @Size(min = 10,message = "Atleast 10 characters needed")
    @Size(max = 2000,message = "within 2000 characters only")
    private  String description;                        //Input column
    
    private  MultipartFile imageFileName;               //Input column


    //19. created getters and  setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(MultipartFile imageFileName) {
        this.imageFileName = imageFileName;
    }

}
