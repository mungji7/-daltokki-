package com.moon.daltokki.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "msg")
public class StatisticsModel {
  private String spToName;
  private String spFromName;
  private int spType;
  private int spTypeCount1;
  private int spTypeCount2;
  private int spTypeCount3;
  private int spColor;
  private int spColorCount1;
  private int spColorCount2;
  private int spColorCount3;
  private int spColorCount4;
}
