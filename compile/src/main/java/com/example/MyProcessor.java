package com.example;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Created by xigua on 2017/9/26.
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class MyProcessor extends AbstractProcessor {
    private Filer mFiler;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> sets = new LinkedHashSet<>();
        sets.add(ViewBinder.class.getCanonicalName());
        sets.add(ViewClick.class.getCanonicalName());
        sets.add(EventBus.class.getCanonicalName());
        return sets;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Map<TypeElement,MyClass> map = findAndPraseTargets(roundEnvironment);
        for (Map.Entry<TypeElement, MyClass> entry : map.entrySet()) {
//            printValue("==========" + entry.getValue().getBindingClassName());
            try {
//                entry.getValue().preJavaFile().writeTo(filer);
                entry.getValue().generateFile().writeTo(mFiler);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    private Map<TypeElement,MyClass> findAndPraseTargets(RoundEnvironment env){
        Map<TypeElement, MyClass> map = new LinkedHashMap<>();
        for (Element element : env.getElementsAnnotatedWith(ViewBinder.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            //type相当于对类的描述
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            try {
                MyClass myClass = map.get(enclosingElement);
                if (myClass == null) {
                    myClass = MyClass.createBindClass(element , enclosingElement);
                    map.put(enclosingElement, myClass);
                }
                if (element.getKind() != ElementKind.FIELD) {
                    throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s", "123"));
                }
                myClass.addFiled((VariableElement) element);
            } catch (Exception e) {

            }
        }
        for (Element element : env.getElementsAnnotatedWith(ViewClick.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            //type相当于对类的描述
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            MyClass myClass = map.get(enclosingElement);
            if (myClass == null) {
                myClass = MyClass.createBindClass(element , enclosingElement);
                map.put(enclosingElement, myClass);
            }
            if (element.getKind() != ElementKind.METHOD) {
                throw new IllegalArgumentException(String.format("Only method can be annotated with @%s", "123"));
            }
            myClass.addClick(element);
        }

        for (Element element : env.getElementsAnnotatedWith(EventBus.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            //type相当于对类的描述
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            MyClass myClass = map.get(enclosingElement);
            if (myClass == null) {
                myClass = MyClass.createBindClass(element , enclosingElement);
                map.put(enclosingElement, myClass);
            }
            if (element.getKind() != ElementKind.METHOD) {
                throw new IllegalArgumentException(String.format("Only method can be annotated with @%s", "123"));
            }
            myClass.addEvent(element);
        }
        return map;
    }

}
