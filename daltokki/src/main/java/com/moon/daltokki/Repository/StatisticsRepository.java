package com.moon.daltokki.Repository;

import com.moon.daltokki.Model.StatisticsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StatisticsRepository extends MongoRepository<StatisticsModel, String> {
    List<StatisticsModel> findBySpToName(String spToName);
}
