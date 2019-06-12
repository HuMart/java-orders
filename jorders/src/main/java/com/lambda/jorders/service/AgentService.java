package com.lambda.jorders.service;

import com.lambda.jorders.model.Agents;

import java.util.ArrayList;

public interface AgentService
{
    ArrayList<Agents> findAll();

    Agents findAgentById(long id);

    Agents findAgentByName(String name);

    void delete(long id);

    Agents save(Agents agent);

    Agents update(Agents agent, long id);
}
