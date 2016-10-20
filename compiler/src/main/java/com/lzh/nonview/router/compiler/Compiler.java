package com.lzh.nonview.router.compiler;

import com.lzh.nonview.router.anno.RouterRule;
import com.lzh.nonview.router.compiler.exception.RouterException;
import com.lzh.nonview.router.compiler.factory.RuleFactory;
import com.lzh.nonview.router.compiler.model.Parser;
import com.lzh.nonview.router.compiler.util.LogUtil;
import com.lzh.nonview.router.compiler.util.UtilMgr;
import com.lzh.nonview.router.compiler.util.Utils;
import com.squareup.javapoet.ClassName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class Compiler extends AbstractProcessor{
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        LogUtil.debug = true;
        Map<String,List<Parser>> map = new HashMap<>();
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RouterRule.class);
        TypeElement type = null;
        try {
            for (Element ele : elements) {
                type = (TypeElement) ele;
                if (!Utils.checkTypeValid(type)) continue;

                Parser parser = Parser.create(type);
                parser.parse();

                RouterRule rule = type.getAnnotation(RouterRule.class);
                String packName = Utils.isEmpty(rule.pack()) ? "com.lzh.router" : rule.pack();
                if (!map.containsKey(packName)) {
                    map.put(packName,new ArrayList<Parser>());
                }
                map.get(packName).add(parser);
            }

            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                new RuleFactory(ClassName.get(key,"RouterRuleCreator"),map.get(key)).generateCode();
            }
        } catch (RouterException e) {
            e.printStackTrace();
            error(e.getElement(),e.getMessage());
            return true;
        } catch (Throwable e) {
            error(type,e.getMessage());
            e.printStackTrace();
            return true;
        }
        return false;
    }

    /**
     * compiler output method,when compiler occurs exception.should be notice here.
     *
     * @param element Element of class who has a exception when compiled
     * @param message The message should be noticed to user
     * @param args args to inflate message
     */
    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(RouterRule.class.getCanonicalName());
        return set;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        UtilMgr.getMgr().init(processingEnv);
    }
}
