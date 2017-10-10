package com.example;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.swing.text.View;


public class MyClass {
    private TypeElement mTypeElement;
    private Element element;
    private ArrayList<VariableElement> feilds;
    private ArrayList<Element> clicks;
    private ArrayList<Element> events;

    public MyClass( Element element,TypeElement typeElement) {
        this.element = element;
        this.mTypeElement = typeElement;
    }

    public static MyClass createBindClass( Element element , TypeElement typeElement){
        return new MyClass(element , typeElement);
    }

    public void addFiled(VariableElement element){
        if (feilds == null) {
            feilds = new ArrayList<>();
        }
        feilds.add(element);
    }

    public void addClick(Element element) {
        if (clicks == null) {
            clicks = new ArrayList<>();
        }
        clicks.add(element);
    }

    public void addEvent(Element element){
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(element);
    }

    JavaFile generateFile() {

        //generateMethod
        MethodSpec.Builder bindViewMethod = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mTypeElement.asType()), "host" , Modifier.FINAL)
               ;
        if (null != feilds)
        for (VariableElement field : feilds) {
            // find views
            bindViewMethod.addStatement("host.$N = ($T)(host.findViewById( $L))", field.getSimpleName(), ClassName.get(field.asType()), field.getAnnotation(ViewBinder.class).value());
        }
        if (null != clicks)
        for (Element field : clicks ){
            int[] ids = field.getAnnotation(ViewClick.class).value();
            for (int id : ids) {
                bindViewMethod.addStatement("host.findViewById($L).setOnClickListener(new android.view.View.OnClickListener() {\n" +
                        "    @Override\n" +
                        "        public void onClick(android.view.View view) {\n" +
                        "           host.$N(view);\n" +
                        "        }\n" +
                        "})",id ,field.getSimpleName());
            }
        }


        MethodSpec.Builder unBindViewMethod = MethodSpec.methodBuilder("unBind")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mTypeElement.asType()), "host",Modifier.FINAL)
                ;
        for (VariableElement field : feilds) {
            unBindViewMethod.addStatement("host.$N = null", field.getSimpleName());
        }

        //generaClass
        TypeSpec injectClass = TypeSpec.classBuilder(mTypeElement.getSimpleName() + "$$ViewBinder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Binder.class), TypeName.get(mTypeElement.asType())))
//                .addSuperinterface(ParameterizedTypeName.get(Binder.class, mTypeElement.asType()))
                .addMethod(bindViewMethod.build())
                .addMethod(unBindViewMethod.build())
                .build();

//        String packageName = mElements.getPackageOf(mTypeElement).getQualifiedName().toString();
        String packageName = "com.yekong.bindview";

        return JavaFile.builder(packageName, injectClass).build();
    }/**/


}
