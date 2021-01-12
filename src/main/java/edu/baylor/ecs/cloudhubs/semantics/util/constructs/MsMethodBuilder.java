package edu.baylor.ecs.cloudhubs.semantics.util.constructs;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsArgument;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsClassRoles;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsId;
import edu.baylor.ecs.cloudhubs.semantics.entity.graph.MsMethod;
import edu.baylor.ecs.cloudhubs.semantics.util.MsCache;

import java.util.Optional;

public class MsMethodBuilder {


    public static void buildMsMethod(MethodDeclaration n, MsClassRoles role, String path, MsId msId){
        MsMethod msMethod = new MsMethod();
        // Set Return Type
        msMethod.setReturnType(n.getTypeAsString());
        // Set Method Name
        msMethod.setMethodName(n.getNameAsString());
        // Set Line
        msMethod.setLine(n.getBegin().get().line);
        // Set Modifiers
        NodeList<Modifier> modifiers = n.getModifiers();
        // Set Arguments
        NodeList<Parameter> parameters = n.getParameters();
        parameters.stream().forEach(e -> {
            msMethod.addArgument(new MsArgument(e.getTypeAsString()));
        });
        // Set Annotations
        msMethod.setMsAnnotations(MsAnnotationBuilder.buildAnnotations(n.getAnnotations()));
        Optional<Node> parentNode = n.getParentNode();
        if (parentNode.isPresent()) {
            // Set Class
            ClassOrInterfaceDeclaration cl = (ClassOrInterfaceDeclaration) parentNode.get();
            msMethod.setClassName(cl.getName().getIdentifier());
            // Find Package
            parentNode = parentNode.get().getParentNode();
            if (parentNode.isPresent()) {
                // Set Package
                CompilationUnit cu = (CompilationUnit) parentNode.get();
                Optional<PackageDeclaration> pd = cu.getPackageDeclaration();
                pd.ifPresent(packageDeclaration -> msMethod.setPackageName(packageDeclaration.getNameAsString()));
            }
        }
        msMethod.setIds();
        msMethod.setMsId(msId);
        MsCache.addMsMethod(msMethod);
    }
}
