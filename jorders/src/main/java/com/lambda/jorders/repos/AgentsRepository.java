package com.lambda.jorders.repos;

import com.lambda.jorders.model.Agents;
import org.springframework.data.repository.CrudRepository;

public interface AgentsRepository extends CrudRepository<Agents, Long>
{
    Agents findByAgentname(String name);
}
