package com.lambda.jorders.service;

import com.lambda.jorders.model.Customers;
import com.lambda.jorders.model.Orders;
import com.lambda.jorders.repos.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService
{
    @Autowired
    private CustomersRepository custrepos;

    @Override
    public ArrayList<Customers> findAll()
    {
        ArrayList<Customers> list = new ArrayList<>();

        custrepos.findAll().iterator().forEachRemaining(list::add);

        return list;
    }

    @Override
    public Customers findCustomerById(long id)
    {
        return custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public Customers findCustomerByName(String name)
    {
        Customers customer = custrepos.findByCustname(name);
        if (customer == null) {
            throw new EntityNotFoundException("Customer " + name + " not found.");
        }
        return customer;
    }

    @Override
    public void delete(long id)
    {
        if (custrepos.findById(id).isPresent()) {
            custrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Customers save(Customers customer)
    {
        Customers newCustomer = new Customers();

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setWorkingarea(customer.getWorkingarea());

        for(Orders o : customer.getOrders())
        {
            newCustomer.getOrders().add(new Orders(
                    o.getOrdamount(),
                    o.getAdvanceamount(),
                    newCustomer,
                    o.getAgent(),
                    o.getOrdersDescription()));
        }
        return newCustomer;
    }

    @Override
    public Customers update(Customers customer, long id)
    {
        Customers currentCustomer = custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
        if (customer.getCustname() != null)
        {
            currentCustomer.setCustname(customer.getCustname());
        }

        if (customer.getCustcity() != null)
        {
            currentCustomer.setCustcity(customer.getCustcity());
        }

        if (customer.getWorkingarea() != null)
        {
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.getCustcountry() != null)
        {
            currentCustomer.setCustcountry(customer.getCustcountry());
        }

        if (customer.getGrade() != null)
        {
            currentCustomer.setGrade(customer.getGrade());
        }

        if (customer.getPhone() != null)
        {
            currentCustomer.setPhone(customer.getPhone());
        }

        if (customer.getOpeningamt() != null)
        {
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }

        if (customer.getReceiveamt() != null)
        {
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }

        if (customer.getPaymentamt() != null)
        {
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }

        if (customer.getOutstandingamt() != null)
        {
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }

        if (customer.getOrders().size() > 0) {
            for (Orders o : customer.getOrders()) {
                currentCustomer.getOrders().add(new Orders(o.getOrdamount(),
                        o.getAdvanceamount(),
                        currentCustomer,
                        o.getAgent(),
                        o.getOrdersDescription()));
            }
        }

        return custrepos.save(currentCustomer);
    }
}
