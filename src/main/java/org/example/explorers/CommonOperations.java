package org.example.explorers;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.VisitorList;
import com.google.common.base.Strings;

import java.io.File;

public class CommonOperations {
    public static void listMethodCalls(File projectDir){
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            System.out.println(path);
            System.out.println(Strings.repeat("=", path.length()));
            try {
                new VoidVisitorAdapter<Object>(){
                    @Override
                    public void visit(PackageDeclaration n, Object arg) {
                        super.visit(n, arg);
                        String packageName= n.getNameAsString();
                        if(packageName.matches("com\\.companyname\\..*")){
                            System.out.println("Package name: "+ packageName);
                        }else{
                            System.out.println("Package is not in the correct format com.companyname.*");
                        }

                    }

                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                        String className = n.getNameAsString();
                        if(checkUpperCase(className)){
                            System.out.println(className);
                        }else{
                            System.out.println("Class names is not start with upper case");
                        }
                    }

                    @Override
                    public void visit(FieldDeclaration n, Object arg) {
                        super.visit(n, arg);
                        for (VariableDeclarator variableDeclarator: n.getVariables()) {
                            String fieldName = variableDeclarator.getNameAsString();
                            if(checkLowerCase(fieldName)){
                                System.out.println(fieldName);
                            }else {
                                System.out.println("Field name is not start with lower case");
                            }
                        }
                    }


                }.visit(StaticJavaParser.parse(file), null);
            }catch (Exception e){
                e.printStackTrace();
            }
        }).explore(projectDir);
    }


    /*
    Kiểm tra kí tự đầu có viết hoa hay không và có chứa khoảng trắng không
     */
    public static boolean checkUpperCase(String text){
        if(!Character.isUpperCase(text.charAt(0))){
            return false;
        }
        if(text.contains(" ")){
            return false;
        }
        return true;
    }

    public static boolean checkLowerCase(String text){
        if(Character.isUpperCase(text.charAt(0))){
            return false;
        }
        if(text.contains(" ")){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        File projectDir = new File("T:\\AllHK\\HK8\\KTPM\\BaiTap\\Exercise2");
        listMethodCalls(projectDir);
    }
}
