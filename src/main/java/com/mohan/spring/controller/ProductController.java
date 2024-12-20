//7. created controller package in project spring
//8. product controller class created


package com.mohan.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


import com.mohan.spring.model.ProductDto;
import com.mohan.spring.model.product;
import com.mohan.spring.servies.ProductRepository;

import jakarta.validation.Valid;




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
                    result.addError(new FieldError("productDto", "imageFileName", "This image is required"));
                }
                if (result.hasErrors()){                                                        //25. if error 
                    return "products/CreateProducts";
                }

                //26.save image file
                






        return "redirect:/products";                                                        // 23.if error , page return to products
    }
    
}