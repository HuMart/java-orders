package com.lambda.jorders.repos;

import com.lambda.jorders.model.Agents;
import com.lambda.jorders.model.Customers;
import org.springframework.data.repository.CrudRepository;

public interface CustomersRepository extends CrudRepository<Customers, Long>
{
    Customers findByCustname(String custname);
}
