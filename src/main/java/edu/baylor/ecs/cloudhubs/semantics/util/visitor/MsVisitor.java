package edu.baylor.ecs.cloudhubs.semantics.util.visitor;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.*;
import edu.baylor.ecs.cloudhubs.semantics.util.MsCache;
import edu.baylor.ecs.cloudhubs.semantics.util.constructs.MsMethodBuilder;
import edu.baylor.ecs.cloudhubs.semantics.util.factory.MsRestCallFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MsVisitor {

    public static void visitClass(File file, String path, MsClassRoles role, MsId msId) {
        try {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                    super.visit(n, arg);
                    MsClass msClass = new MsClass();
                    msClass.setClassName(n.getNameAsString());
                    Optional<Node> parentNode = n.getParentNode();
                    if (parentNode.isPresent()) {
                        CompilationUnit cu = (CompilationUnit) parentNode.get();
                        Optional<PackageDeclaration> pd = cu.getPackageDeclaration();
                        pd.ifPresent(packageDeclaration -> msClass.setPackageName(packageDeclaration.getNameAsString()));
                    }
                    NodeList<AnnotationExpr> nl = n.getAnnotations();
                    msClass.setRole(role);
                    for (AnnotationExpr annotationExpr : nl) {
                        if (annotationExpr.getNameAsString().equals("Service")){
                            msClass.setRole(MsClassRoles.SERVICE);
                        }
                        if (annotationExpr.getNameAsString().equals("RestController")){
                            msClass.setRole(MsClassRoles.CONTROLLER);
                            // get annotation request mapping and value
                        }
                        if (annotationExpr.getNameAsString().equals("Repository")){
                            msClass.setRole(MsClassRoles.REPOSITORY);
                        }
                    }
                    if (nl.size() == 0 && n.getNameAsString().contains("Service")) {
                        msClass.setRole(MsClassRoles.SERVICE_INTERFACE);
                    }

                    msClass.setIds();
                    msClass.setMsId(msId);
                    MsCache.addMsClass(msClass);
                }
            }.visit(StaticJavaParser.parse(file), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void visitMethods(File file, MsClassRoles role, String path, MsId msId) {
        try {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MethodDeclaration n, Object arg) {
                    super.visit(n, arg);
                    MsMethodBuilder.buildMsMethod(n, role, path, msId);
                }
            }.visit(StaticJavaParser.parse(file), null);
            // System.out.println(); // empty line
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void visitMethodCalls(File file, String path, MsId msId) {
        try {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MethodCallExpr n, Object arg) {
                    super.visit(n, arg);
                    Optional<Expression> scope = n.getScope();
                    if (scope.isPresent()) {
                        if (scope.get() instanceof  NameExpr) {
                            // get common properties
                            int lineNumber = n.getBegin().get().line;
                            // decide between service / restTemplate
                            NameExpr fae = scope.get().asNameExpr();
                            String name = fae.getNameAsString();
                            if (name.toLowerCase().contains("repository")){
                                MsMethodCall msMethodCall = new MsMethodCall();

                                msMethodCall.setLineNumber(lineNumber);
                                msMethodCall.setStatementDeclaration(n.toString());
                                msMethodCall.setMsParentMethod(MsParentVisitor.getMsParentMethod(n));
                                msMethodCall.setCalledServiceId(name);
                                MethodCallExpr methodCallExpr = (MethodCallExpr) fae.getParentNode().get();
                                msMethodCall.setCalledMethodName(methodCallExpr.getNameAsString());
                                msMethodCall.setParentClassId();
                                msMethodCall.setMsId(msId);
                                // register method call to cache
                                MsCache.addMsMethodCall(msMethodCall);
                            }
                            if (name.toLowerCase().contains("service")) {
                                // service is being called
                                MsMethodCall msMethodCall = new MsMethodCall();

                                msMethodCall.setLineNumber(lineNumber);
                                msMethodCall.setStatementDeclaration(n.toString());
                                msMethodCall.setMsParentMethod(MsParentVisitor.getMsParentMethod(n));
                                msMethodCall.setCalledServiceId(name);
                                MethodCallExpr methodCallExpr = (MethodCallExpr) fae.getParentNode().get();
                                msMethodCall.setCalledMethodName(methodCallExpr.getNameAsString());
                                msMethodCall.setParentClassId();
                                msMethodCall.setMsId(msId);
                                // register method call to cache
                                MsCache.addMsMethodCall(msMethodCall);
                            } else if (name.equals("restTemplate")) {
                                // rest template is being called
                                MsRestCall msRestCall = MsRestCallFactory.getMsRestCall(n);
                                msRestCall.setLineNumber(lineNumber);
                                msRestCall.setMsParentMethod(MsParentVisitor.getMsParentMethod(n));
                                msRestCall.setParentClassId();
                                msRestCall.setMsId(msId);
                                MsCache.addMsRestMethodCall(msRestCall);
                            }
                        }
                    }
                }
            }.visit(StaticJavaParser.parse(file), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void visitFields(File file, String path, MsId msId) {
        try {
            new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(FieldDeclaration n, Object arg) {
                    super.visit(n, arg);
                    MsFieldVisitor.visitFieldDeclaration(n, path, msId);
                }
            }.visit(StaticJavaParser.parse(file), null);
            // System.out.println(); // empty line
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
