package com.jlspider.homelink.bo;

import java.util.List;
import com.jlspider.homelink.model.House;
/**
 * Created with IntelliJ IDEA.
 * User: jinlin
 * Date: 16-11-11
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
 */
public interface HousePriceBo {
      public List<House> grepHouseInfo(String url);
    public void recommendPrice(List<House> houseList);
}
