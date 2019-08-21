package com.journey.reflect;

import java.lang.reflect.*;

/**
 * @Author: wuwei
 * @Date: 2019-08-21 13:35
 */
public class TestReflect {

    private String param1;

    private String param2;

    public TestReflect() {
    }

    public TestReflect(String param1, String param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public static void main(String[] args) {
        TestReflect testReflect = new TestReflect();
        //获取类的三种方式
        Class clazz = testReflect.getClass();

        Class clazz1 = TestReflect.class;

        Class clazz2;
        try {
            clazz2 = Class.forName("com.journey.reflect.TestReflect");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //获取所有类的属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(Modifier.toString(field.getModifiers())
                    + " "
                    + field.getType().getSimpleName()
                    + " "
                    + field.getName()
            );
        }

        //获取所有的方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(
                    Modifier.toString(method.getModifiers())
                    + " "
                    + method.getReturnType()
                    + " "
                    + method.getName()
            );
        }

        //获取所有的构造方法
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(
                    constructor.getName()
                            + " 参数个数："
                            + constructor.getParameterCount()
            );
        }

        //动态调用函数
        try {
            Object obj = clazz.newInstance();
            //是否TestReflect类
            boolean isTestReflect = obj instanceof TestReflect;
            //获取指定的方法
            Method method = clazz.getDeclaredMethod("setParam1", new Class[]{String.class});
            method.invoke(obj, "test");
            Method method1 = clazz.getDeclaredMethod("getParam1");
            System.out.println(method1.invoke(obj, new Class[]{}));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
