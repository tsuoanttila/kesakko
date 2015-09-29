package org.vaadin.fwteam.annotations;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.google.auto.service.AutoService;

@SupportedAnnotationTypes({ "org.vaadin.fwteam.annotations.SourceHighlight" })
@AutoService(Processor.class)
public class SourceProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;
    private boolean generatedSource;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {

        Set<? extends Element> sourceHighlights = roundEnv
                .getElementsAnnotatedWith(SourceHighlight.class);

        for (Element elem : sourceHighlights) {
            if (generatedSource) {
                error(elem, "Multiple SourceHighlights.");
            }

            try {
                InputStream in = getFile(elem).openInputStream();
                CompilationUnit cu = JavaParser.parse(in);

                Node source = findNodeOfElement(elem, cu.getChildrenNodes());
                String[] split = source.toString().split("\\r?\\n");

                if (split[0].startsWith("/")) {
                    int i = 1;
                    while (split[i].startsWith(" ")) {
                        split[i] = " " + split[i].trim();
                        ++i;
                    }
                }

                String output = "";
                for (String s : split) {
                    if (!s.contains("@SourceHighlight")) {
                        output += String.format(s + "%n");
                    }
                }
                PrintWriter w = new PrintWriter("src/main/webapp/source.txt",
                        "UTF-8");
                w.print(output);
                w.close();

                generatedSource = true;

            } catch (IOException e) {
                error(elem, "Failure to find file resource.");
            } catch (ParseException e) {
                error(elem, "Failure parsing java file.");
            }
        }

        return true;
    }

    private Node findNodeOfElement(Element elem, List<Node> nodes)
            throws ParseException {

        for (Node n : nodes) {
            if (!((n instanceof MethodDeclaration)
                    || (n instanceof TypeDeclaration))) {
                continue;
            }

            if (n.toString().contains(elem.getSimpleName())) {
                if (n.getChildrenNodes().isEmpty()) {
                    return n;
                } else {
                    Node found = findNodeOfElement(elem, n.getChildrenNodes());
                    if (found == null) {
                        return n;
                    } else {
                        return found;
                    }
                }
            }
        }

        return null;
    }

    private FileObject getFile(Element elem) throws IOException {
        while (!elem.getEnclosingElement().getKind()
                .equals(ElementKind.PACKAGE)) {
            elem = elem.getEnclosingElement();
        }

        String fullName = elem.toString();
        String pkg = fullName.substring(0, fullName.lastIndexOf("."));
        String className = fullName.substring(fullName.lastIndexOf(".") + 1);

        FileObject resource = filer.getResource(StandardLocation.SOURCE_PATH,
                pkg, className + ".java");
        return resource;
    }

    /**
     * Prints an error message
     *
     * @param e
     *            The element which has caused the error. Can be null
     * @param msg
     *            The error message
     */
    public void error(Element e, String msg) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg, e);
    }
}
