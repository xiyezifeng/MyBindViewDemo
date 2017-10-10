package com.example;

import com.example.annotation.BindView;
import com.google.auto.service.AutoService;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by xigua on 2017/9/20.
 */

@SupportedAnnotationTypes("com.example.annotation.BindView")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class ButterKnifeProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("start create");
        Map<TypeElement,MyClass> map = findAndPraseTargets(roundEnvironment);
        for (Map.Entry<TypeElement, MyClass> entry : map.entrySet()) {
//            printValue("==========" + entry.getValue().getBindingClassName());
            try {
//                entry.getValue().preJavaFile().writeTo(filer);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> sets = new LinkedHashSet<>();
        sets.add(BindView.class.getCanonicalName());
        return sets;
    }



    private Map<TypeElement,MyClass> findAndPraseTargets(RoundEnvironment env){
        Map<TypeElement, MyClass> map = new LinkedHashMap<>();
        Set<String> names = new LinkedHashSet<>();
        for (Element element : env.getElementsAnnotatedWith(BindView.class)) {

            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            int ids = element.getAnnotation(BindView.class).value();

            try {

                MyClass myClass = map.get(enclosingElement);
                if (myClass == null) {
                    myClass = MyClass.createBindClass(enclosingElement);
                    map.put(enclosingElement, myClass);
                }
                System.out.println("find id : " + ids);

            } catch (Exception e) {

            }
        }
        return map;
    }


    /**
     * just test
     */
    private void printElement(Element element) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        printValue("========annotation 所在的类完整名称 " + enclosingElement.getQualifiedName());
        printValue("========annotation 所在类的类名 " + enclosingElement.getSimpleName());
        printValue("========annotation 所在类的父类 " + enclosingElement.getSuperclass());
        printValue("========element name " + element.getSimpleName());
        printValue("========element type " + element.asType());
        printValue("========annotation value " + element.getAnnotation(BindView.class).value());
        printValue("\n");
    }
    private static void printValue(Object obj) {
        System.out.println(obj);
    }
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println("start init");
    }


}
