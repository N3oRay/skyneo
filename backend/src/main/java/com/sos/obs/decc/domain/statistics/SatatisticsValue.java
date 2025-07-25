package com.sos.obs.decc.domain.statistics;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author Ahmed EL FAYAFI on avr., 2019
 */

public class SatatisticsValue implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SatatisticsValue that = (SatatisticsValue) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SatatisticsValue{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }
}
