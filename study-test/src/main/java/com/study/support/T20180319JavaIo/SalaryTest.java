package com.study.support.T20180319JavaIo;

import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import static com.study.support.T20180319JavaIo.SalaryTest.baseChar;
import static com.study.support.T20180319JavaIo.SalaryTest.random;

/**
 * @Author shanweifeng
 * @Description:
 * @Date: Created in 13:53 2018/3/19
 * @Modified By:
 */
public class SalaryTest {
    //4  随机生成 Salary {name, baseSalary, bonus  }的记录，如“wxxx,10,1”，
    // 每行一条记录，总共1000万记录，写入文本文件（UFT-8编码），
    //然后读取文件，name的前两个字符相同的，其年薪累加，比如wx，100万，3个人，
    // 最后做排序和分组，输出年薪总额最高的10组：
    //wx, 200万，10人
    //lt, 180万，8人
    //生成对象用于存储到文件
    static Random random = new Random();
    static String baseChar = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLMNBVCXZ";
    static int tenThousand = 10000;
    static int thousand = 1000;
    static String salaryPath = "d://salary.txt";
    public static void main(String[] args){
        //System.out.println("tenThousand*thousand="+(tenThousand*thousand));
        long start = System.currentTimeMillis();
        writeFile();//生成Salary并写入文件
        System.out.println("生成Salary并写入文件耗时:"+(System.currentTimeMillis()-start));
        start = System.currentTimeMillis();
        List<Salary> salaries = readFile();//读取文件并转换成对象
        System.out.println("读取Salary并写入文件耗时:"+(System.currentTimeMillis()-start));
        System.out.println("this list size is "+salaries.size()+" and the first Object is "+salaries.get(0));
        //排序取最高10组数据
        Map<String,SalaryGroup> map = new HashMap<>();
        start = System.currentTimeMillis();
        salaries.stream().forEach(x->{
            if(Objects.isNull(map.get(x.getName().substring(0,2)))){
                SalaryGroup sg = new SalaryGroup();
                sg.setNamePre(x.getName().substring(0,2));
                map.put(x.getName().substring(0,2),sg);
            }
            map.get(x.getName().substring(0,2)).increament(x.getBaseSalary()*12+x.getBonus());
        });
        System.out.print("前十组收入:");
        map.values().stream().sorted(Comparator.comparing(SalaryGroup::getSalary).reversed()).limit(10).forEach(x->{
            System.out.println("namePre:"+x.getNamePre()+ " salary:"+x.getSalary()+"  count:"+x.getCount());
        });
        System.out.println("排序并输出前十组收入耗时:"+(System.currentTimeMillis()-start));
    }

    public static List<Salary> readFile() {
        List<Salary> salaries = new ArrayList<>();
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(new FileInputStream(salaryPath));
            try{
                while(true){
                    salaries.add((Salary)in.readObject());
                }
            }catch (EOFException e){
                System.out.println("read over");
            }

        }catch(Exception e1){
            System.out.println("read file error");
        }finally {
            if(Objects.nonNull(in)){
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("ObjectOutputStream close error");
                }
            }
        }
        return salaries;
    }

    public static void writeFile() {
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream(salaryPath));
            for (int i=0;i < tenThousand*thousand;i++){
                out.writeObject(new Salary());
            }
            out.flush();
        }catch(Exception e){
            System.out.println("写入文件异常"+e);
        }finally {
            if(Objects.nonNull(out)){
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println("ObjectOutputStream关闭异常");
                }
            }
        }
    }
}

class Salary implements Serializable{

    private static final long serialVersionUID = -7263397446705829632L;
    private String name;
    private int baseSalary;
    private int bonus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public Salary() {
        int length = random.nextInt(10)+2;
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<length;i++){
            sb.append(baseChar.charAt(random.nextInt(52)));
        }
        this.name = sb.toString();
        this.baseSalary = random.nextInt(10000);
        this.bonus = (random.nextInt(4)+1)*this.baseSalary;
    }

    public Salary(String name, int baseSalary, int bonus) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    @Override
    public String toString(){
        return "name:"+this.name+" baseSalary:"+this.baseSalary+" bonus:"+this.bonus;
    }
}

class SalaryGroup{
    private String namePre;
    private long salary;
    private int count;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increament(long salary){
        this.count++;
        this.salary += salary;
    }
}