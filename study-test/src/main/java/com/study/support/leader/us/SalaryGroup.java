package com.study.support.leader.us;

/**
 * Created by Administrator on 2018/3/16.
 */
public class SalaryGroup {
    private String namePre;
    private long salary;
    private int peoples;

    public String getNamePre() {
        return namePre;
    }

    public void setNamePre(String namePre) {
        this.namePre = namePre;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public int getPeoples() {
        return peoples;
    }

    public void setPeoples(int peoples) {
        this.peoples = peoples;
    }

    public void increament(long salary){
        this.salary += salary;
        this.peoples += 1;
    }
}
