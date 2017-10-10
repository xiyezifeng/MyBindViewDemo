package com.example;

import com.squareup.javapoet.TypeName;

import java.lang.reflect.Type;

import javax.lang.model.element.TypeElement;

public class MyClass {
    private TypeElement element;

    private MyClass(TypeElement typeElement) {
        this.element = typeElement;
        init();
    }

    public static MyClass createBindClass(TypeElement enclosingElement){
        //从obj中寻找 类名+$$ViewBinder 的类。然后注入 obj
        return new MyClass(enclosingElement);
    }
    private void init(){
        String name = element.getSimpleName().toString();//包名
        TypeName type = TypeName.get((Type) element.asType());//类型名称
    }
}
