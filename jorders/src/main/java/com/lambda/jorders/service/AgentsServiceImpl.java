package com.lambda.jorders.service;

import com.lambda.jorders.model.Agents;
import com.lambda.jorders.model.Customers;
import com.lambda.jorders.model.Orders;
import com.lambda.jorders.repos.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Service(value = "agentService")
public class AgentsServiceImpl implements AgentService
{
    @Autowired
    private AgentsRepository agentrepos;

    @Override
    public ArrayList<Agents> findAll()
    {
        ArrayList<Agents> list = new ArrayList<>();

        agentrepos.findAll().iterator().forEachRemaining(list::add);

        return list;
    }

    @Override
    public Agents findAgentById(long id)
    {
        return null;
    }

    @Override
    public Agents findAgentByName(String name)
    {
        Agents agent = agentrepos.findByAgentname(name);
        if (agent == null) {
            throw new EntityNotFoundException("Agent " + name + " not found.");
        }
        return agent;
    }

    @Override
    public void delete(long id)
    {
        if (agentrepos.findById(id).isPresent()) {
            agentrepos.deleteById(id);
        }
        else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Agents save(Agents agent)
    {
        Agents newAgent = new Agents();

        newAgent.setAgentname(agent.getAgentname());
        newAgent.setCommission(agent.getCommission());
        newAgent.setCountry(agent.getCountry());
        newAgent.setPhone(agent.getPhone());
        newAgent.setWorkingarea(agent.getWorkingarea());

        for (Orders o : agent.getOrders())
        {
            newAgent.getOrders().add(new Orders(
                    o.getOrdamount(),
                    o.getAdvanceamount(),
                    o.getCustomer(),
                    newAgent,
                    o.getOrdersDescription()));
        }

        for (Customers c : agent.getCustomers()) {
            newAgent.getCustomers().add(new Customers(
                    c.getCustname(),
                    c.getCustcity(),
                    c.getWorkingarea(),
                    c.getCustcountry(),
                    c.getGrade(),
                    c.getOpeningamt(),
                    c.getReceiveamt(),
                    c.getPaymentamt(),
                    c.getOutstandingamt(),
                    c.getPhone(),
                    newAgent));
        }

        return agentrepos.save(newAgent);
    }


    @Transactional
    @Override
    public Agents update(Agents agent, long id)
    {
        Agents currentAgent = agentrepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
        if (agent.getAgentname() != null)
        {
            currentAgent.setAgentname(agent.getAgentname());
        }

        if (agent.getCountry() != null)
        {
            currentAgent.setCountry(agent.getCountry());
        }

        if (agent.getWorkingarea() != null)
        {
            currentAgent.setWorkingarea(agent.getWorkingarea());
        }

        if (agent.getCustomers().size() > 0)
        {
            for (Customers c : agent.getCustomers())
            {
                currentAgent.getCustomers().add(new Customers(
                        c.getCustname(),
                        c.getCustcity(),
                        c.getWorkingarea(),
                        c.getCustcountry(),
                        c.getGrade(),
                        c.getOpeningamt(),
                        c.getReceiveamt(),
                        c.getPaymentamt(),
                        c.getOutstandingamt(),
                        c.getPhone(),
                        currentAgent));
            }
        }
        return agentrepos.save(currentAgent);
    }
}


