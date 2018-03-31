package com.study.support.T20180326JavaStream;

import com.sun.xml.internal.ws.util.StringUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 17:10 2018/3/31
 * @Modified By:
 */
public class SalaryTest {
    /*用Stream的API实现第四题的结果，其中增加一个过滤条件，即年薪大于10万的才被累加，分别用ParellStream与普通Stream来运算，看看效果的差距。*/
    public static void main(String[] args){

        List<SalaryGroup> result = new ArrayList<>();
        long start = System.currentTimeMillis();
        Stream.generate(new SalarySupply()).limit(10000000).filter(x->x.getBaseSalary()*12+x.getBonus() > 100000)
                .collect(Collectors.groupingBy(Salary::getName,Collectors.mapping(x->x.getBonus()+x.getBaseSalary()*12,Collectors.toList())))
                .forEach((k,v)->{
                    SalaryGroup s = new SalaryGroup();
                    s.setName(k);
                    s.setCount(v.size());
                    s.setSalary(v.stream().mapToLong(x->x).reduce(0,Long::sum));
                    result.add(s);
                });
        long temp = System.currentTimeMillis()-start;
        start = System.currentTimeMillis();
        result.stream().parallel().sorted(Comparator.comparing(SalaryGroup::getSalary).reversed()).limit(10).forEach(System.out::println);

        System.out.println("有parallel实现时间:"+(System.currentTimeMillis()-start+temp));
        start = System.currentTimeMillis();
        result.stream().sorted(Comparator.comparing(SalaryGroup::getSalary).reversed()).limit(10).forEach(System.out::println);
        System.out.println("无parallel实现时间:"+(System.currentTimeMillis()-start+temp));
    }
}

class MyCollector implements Collector<Salary,SalaryGroup,SalaryGroup>{

    @Override
    public Supplier<SalaryGroup> supplier() {
        return ()->new SalaryGroup();
    }

    @Override
    public BiConsumer<SalaryGroup, Salary> accumulator() {
        return ((salaryGroup, salary) -> {
            if(null == salaryGroup.getName()){
                salaryGroup.setName(salary.getName());
            }
            salaryGroup.setSalary(salaryGroup.getSalary()+salary.getBaseSalary()*12+salary.getBonus());
            salaryGroup.setCount(salaryGroup.getCount()+1);

        });
    }

    @Override
    public BinaryOperator<SalaryGroup> combiner() {
        return (salary1,salary2)->{

            return null;
        };
    }

    @Override
    public Function<SalaryGroup, SalaryGroup> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}

class SalarySupply implements Supplier<Salary>{
    private static String base = "qwertyuiopasdfghjklkzxcvbnmMNBVCXZASDFGHJKLPOIUYTREWQ";
    @Override
    public Salary get() {
        Random random = new Random();
        int baseSalary = random.nextInt(10000)+10000;
        int index = random.nextInt(base.length()-3);
        return new Salary(base.substring(index,index+2),baseSalary,baseSalary*random.nextInt(4));
    }
}

class SalaryGroup{
    private Long salary;
    private int count;
    private String name;

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SalaryGroup{" +
                "salary=" + salary +
                ", count=" + count +
                ", name='" + name + '\'' +
                '}';
    }
}

class Salary{
    private String name;
    private Integer baseSalary;
    private Integer bonus;

    public Salary(String name, Integer baseSalary, Integer bonus) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Integer baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }
}