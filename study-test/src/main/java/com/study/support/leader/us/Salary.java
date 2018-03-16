package com.study.support.leader.us;

import java.io.Serializable;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 10:12 2018/3/16
 * @Modified By:
 */
public class Salary implements Serializable{
    private static final long serialVersionUID = -9117278997775993831L;
    private String name;
    private long baseSalary;
    private int bonus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(long baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString(){
        return "[name:"+this.name+",baseSalary:"+this.baseSalary+",bonus:"+this.bonus+"]";
    }
}
