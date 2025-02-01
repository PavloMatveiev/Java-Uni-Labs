package PreValidation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class JavaFile {

    private File file;
    private String name;
    private List<Method> methods;
    private final List<String> modifiersAndTypes = Arrays.asList("public", "private", "protected", "static", "void");

    public JavaFile(File file) {
        this.file = file;
        this.name = file.getName();
        if (file.isFile()) {
            this.methods = generateMethods();
        } else {
            this.methods = new ArrayList<Method>();
        }

    }

    private String getMethodReturnType(String[] words){
        String returnType;
        if (words[0].contains("void")){
            returnType = words[0];
        } else if (words[1].contains("static")) {
            returnType = words[2];
        } else {
            returnType = words[1];
        }
        return returnType;
    }

    private String getMethodName(String line){
        String beforeBracket = line.split("\\(")[0];
        String[] words = WordUtility.separateWords(beforeBracket);
        return words[words.length -1];
    }

    private Method getMethodFromLine(String line, String[] words){
        return new Method(getMethodName(line), getMethodReturnType(words), line);
    }

    private Method determineMethod(String line){
        Method method = null;
        String[] words = WordUtility.separateWords(line);
        if (words.length > 0 && modifiersAndTypes.contains(words[0]) && line.contains("(") && line.contains(")")){
            method = getMethodFromLine(line.split("\\)")[0] + ")", words);
        }
        return method;
    }

    private List<Method> generateMethods(){
        List<Method> methods = new ArrayList<Method>();
        Method method = null;
        Method previousMethod = null;
        String methodContent = "";
        String line;
        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()){
                line = fileReader.nextLine();
                method = determineMethod(line);
                if (method != null) {
                    methods.add(method);
                    if (previousMethod != null){
                        previousMethod.setContent(methodContent);
                    }
                    methodContent = line;
                    previousMethod = method;
                }
                if (!methodContent.equals("")) {
                    methodContent += line;
                }
            }
        } catch (IOException ioExp){
            ioExp.printStackTrace();
        } finally {
            
        }
        if (previousMethod != null){
            previousMethod.setContent(methodContent);
        }
        return methods;
    }

    public Method getMethodByName(String methodName){
        return methods.stream().filter(method -> method.getName().equals(methodName)).findFirst().orElse(null);
    }

    public boolean hasMethodByName(String methodName){
        return getMethodByName(methodName) != null;
    }

    private String getMethodNames(){
        String names = "";
        for (Method method : methods){
            if (names.equals("")) {
                names = method.getName();
            } else {
                names += ", " + method.getName();
            }
        }
        return names;
    }

    public String toString(){
        return name + " has " + getMethodNames();
    }

    public static void main(String[] args ){
        String file_name = "src/test/java/MonsterTest.java";
        File file = new File(file_name);
        JavaFile javaFile = new JavaFile(file);
        System.out.println(javaFile);
    }
}




