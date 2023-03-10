package com.liboshuai.mall.admin.zlearning.Test.literal.oop_app3.d9_api_object;

/**
 * @author:Sun
 * @date06/12/202212:27 PM
 */
public class Student {
    private String name;
    private char sex;
    private int age;

    public Student() {
    }

    public Student(String name, char sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
//    @Override
//    public boolean equals(Object o){
//        if (this == o) return true;
//        if (o == null || this.getClass() != o.getClass()) return false;
//        Student student = (Student) o;
//
//        return sex == student.sex && age == student.age && Object.equals(name, student.name);
//    }
//
    @Override
    public String toString(){
        return "Student{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }

}
