// refer notepad


package com.mohan.spring.model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity                                 // 1.uses to connect/create with database
@Table(name = "products")               // 2.used to create a table and named as product
public class product {

//table creation 
    @Id                                                 // 3.primary key in table
    @GeneratedValue (strategy=GenerationType.IDENTITY)  // auto increament
    private int id;                                     // int datatype
    
    private String name;                                //column name
    private String brand;                               //column name
    private String category;                            //column name
    private double price;                               //column name

    @Column(columnDefinition = "TEXT")                  //below column defintion as text(if not defined default as varchar) 
    private  String description;                        //column name
    
    private  Date createdat;                            //column name
    private  String imageFileName;                      //column name
    
    //4.use right click on any variables ->source action -> getters and setters-> select all enter... this will create the below all code.
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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
    public Date getCreatedat() {
        return createdat;
    }
    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }
    public String getImageFileName() {
        return imageFileName;
    }
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
//5. once u saved table will automatically generated in connected database.. if need check in mysql workbench.