package com.moon.daltokki.Service;

import com.moon.daltokki.Model.StatisticsModel;
import com.moon.daltokki.Repository.StatisticsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StatisticsService {

    @Autowired
    StatisticsRepository statisticsRepository;

    public void saveMessage(StatisticsModel message){
        statisticsRepository.save(message);
    }

    public List<StatisticsModel> selectStatistics(String name){
        log.info("~~~~DDARAN~~~~");

        return statisticsRepository.findBySpToName(name);
    }





}
