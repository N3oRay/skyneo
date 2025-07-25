package com.sos.obs.decc.domain.statistics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @Author Ahmed EL FAYAFI on avr., 2019
 */

public class CenterStatisticsMgr {
    private static CenterStatisticsMgr INSTANCE = null;
    private Map<Integer, CenterStats> statsMap =new HashMap<>();
    private Object mutex= new Object();

    private CenterStatisticsMgr() {
    }

    public static CenterStatisticsMgr getInstance() {
        if (INSTANCE == null) {
            synchronized (CenterStatisticsMgr.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CenterStatisticsMgr();
                }
            }
        }
        return INSTANCE;
    }


    public void addStatisics(List<SatatisticsValue> stats,String type){
        stats.forEach(item->{
            if(statsMap.get(item.getId())==null){
                synchronized (mutex) {
                    if (statsMap.get(item.getId()) == null) {
                        statsMap.put(item.getId(),new CenterStats());
                    }
                }
            }
            CenterStats  centerStats= statsMap.get(item.getId());
            centerStats.update(item, type);

        });
    }




}
