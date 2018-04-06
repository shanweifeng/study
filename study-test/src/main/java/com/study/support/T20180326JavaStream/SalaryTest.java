package com.study.support.T20180326JavaStream;

import com.sun.xml.internal.ws.util.QNameMap;
import com.sun.xml.internal.ws.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
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
    public static void main(String[] args)throws Exception{
        long limit = 10000000;
        List<SalaryGroup> result = new ArrayList<>();
        long start = System.currentTimeMillis();
        try (FileOutputStream fo = new FileOutputStream("D://salary.txt")) {
            ObjectOutputStream ob = new ObjectOutputStream(fo);//这样转化成对象流以时间换取空间
            try {
                Stream.generate(new SalarySupply()).limit(limit).filter(x -> {
                    //输出到文件
                    try {
                        //fo.write(x.toString().getBytes(Charset.forName("utf8")));
                        ob.writeObject(x);
                    } catch (IOException e) {
                        System.out.println("对象保存异常");
                    }
                    return x.getBaseSalary() * 12 + x.getBonus() > 100000;
                })
                        .collect(Collectors.groupingBy(Salary::getName, Collectors.mapping(x -> x.getBonus() + x.getBaseSalary() * 12, Collectors.toList())))
                        .entrySet().parallelStream().sorted((l1, l2) -> {
                    if (l1.getValue().stream().mapToLong(ls -> ls).reduce(0, Long::sum) >= l2.getValue().stream().mapToLong(ls -> ls).reduce(0, Long::sum))
                        return 1;
                        return -1;
                }).limit(10).map(x -> {
                    SalaryGroup sg = new SalaryGroup();
                    sg.setCount(x.getValue().size());
                    sg.setName(x.getKey());
                    sg.setSalary(x.getValue().stream().mapToLong(s -> s).reduce(0, Long::sum));
                    return sg;
                }).sorted(Comparator.comparing(SalaryGroup::getSalary).reversed()).forEach(System.out::println);
                System.out.println("改造后parallelStream输出时间:" + (System.currentTimeMillis() - start));
                //fo.close();
                ob.close();
            } catch (Exception e) {
                System.out.println("什么对象输出流异常");
            } finally {
                if (Objects.nonNull(fo)) {
                    fo.close();
                }
                if (Objects.nonNull(ob)) {
                    ob.close();
                }
            }
        }

                /*.forEach((k,v)->{
                    SalaryGroup s = new SalaryGroup();
                    s.setName(k);
                    s.setCount(v.size());
                    s.setSalary(v.stream().mapToLong(x->x).reduce(0,Long::sum));
                    result.add(s);
                });*/

        /*long temp = System.currentTimeMillis()-start;
        start = System.currentTimeMillis();
        result.stream().parallel().sorted(Comparator.comparing(SalaryGroup::getSalary).reversed()).limit(10).forEach(System.out::println);

        System.out.println("有parallel实现时间:"+(System.currentTimeMillis()-start+temp));
        start = System.currentTimeMillis();
        result.stream().sorted(Comparator.comparing(SalaryGroup::getSalary).reversed()).limit(10).forEach(System.out::println);
        System.out.println("无parallel实现时间:"+(System.currentTimeMillis()-start+temp));*/
        start = System.currentTimeMillis();
        Stream.generate(new SalarySupply()).limit(limit).collect(new MyCollector()).stream().sorted(Comparator.comparing(SalaryGroup::getSalary).reversed()).limit(10).forEach(System.out::println);
        System.out.println("自定义collector耗时:"+(System.currentTimeMillis()-start));
    }
}

class MyCollector implements Collector<Salary,Map<String,List<Integer>>,List<SalaryGroup>>{

    @Override
    public Supplier<Map<String,List<Integer>>> supplier() {
        return ()->new HashMap<>();
    }

    @Override
    public BiConsumer<Map<String,List<Integer>>, Salary> accumulator() {
        return ((map, salary) -> {
           if(Objects.isNull(map.get(salary.getName()))){
               map.put(salary.getName(),new ArrayList<>());
           }
           map.get(salary.getName()).add(salary.getBaseSalary()*12+salary.getBonus());
        });
    }

    @Override
    public BinaryOperator<Map<String,List<Integer>>> combiner() {
        return (map1,map2)->{
            map2.putAll(map1);
            return map2;
        };
    }

    @Override
    public Function<Map<String,List<Integer>>,List<SalaryGroup>> finisher()
    {
        List<SalaryGroup> result = new ArrayList<>();
        return map->{
            map.forEach((k,v)->{
                SalaryGroup sg = new SalaryGroup();
                sg.setName(k);
                sg.setCount(v.size());
                sg.setSalary(v.stream().mapToLong(x->x).reduce(0,Long::sum));
                result.add(sg);
            });
            return result;
        };
    }

    @Override
    public Set<Characteristics> characteristics()
    {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
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

class Salary implements Serializable{
    private static final long serialVersionUID = -7808847440454174136L;
    private String name;
    private Integer baseSalary;
    private Integer bonus;

    public Salary() {
    }

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