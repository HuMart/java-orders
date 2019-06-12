package com.lambda.jorders.controller;

import com.lambda.jorders.model.Customers;
import com.lambda.jorders.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/customer/new",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewCustomer(@Valid
                                            @RequestBody
                                                    Customers newCustomer)
    {
        newCustomer = customerService.save(newCustomer);

        HttpHeaders responseHeader = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerid}").buildAndExpand(newCustomer.getCustcode()).toUri();
        responseHeader.setLocation((newCustomerURI));

        return new ResponseEntity<>(null, responseHeader, HttpStatus.OK);
    }

    @PutMapping(value = "/customer/update/{customerid}",
                consumes = {"application/json"})
    public ResponseEntity<?> updateCustomerById(
            @RequestBody
                    Customers updateCustomer,
            @PathVariable
                    long customerid) {
        customerService.update(updateCustomer, customerid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/customer/delete/{customerid}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long customerid)
    {
        customerService.delete(customerid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
