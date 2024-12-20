//6. created a package for serviess and inteface as ProductRepository.java file.
//7. Exteded the interface from JpaRepository
//8. that JpaRepository needs 2   < type of model , primarykey datatype>
package com.mohan.spring.servies;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohan.spring.model.product;

public interface ProductRepository extends JpaRepository<product, Integer> {

}
