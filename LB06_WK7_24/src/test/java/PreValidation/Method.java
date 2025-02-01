package PreValidation;

import java.util.ArrayList;
import java.util.List;

public class Method {

    private String name;
    private String returnType;
    private String methodDeclare;
    private List<String> inputParameterType;
    private String content;

    public Method(String name, String returnType, String methodDeclare) {
        this.name = name;
        this.returnType = returnType;
        this.methodDeclare = methodDeclare;
        this.inputParameterType = determineInputParameter(methodDeclare);
    }

    public void setContent(String content) {
        this.content = content;
    }


    private List<String> determineInputParameter(String methodDeclare){
        List<String> inputParameterType = new ArrayList<String>();
        if (!methodDeclare.contains("()") && !methodDeclare.contains("( )")) {
            String parameterDeclare = methodDeclare.split("\\(")[1].split("\\)")[0];
            String[] parameters = parameterDeclare.split(",");
            String[] words;
            for (String parameter : parameters) {
                words = WordUtility.separateWords(parameter);
                if (words.length > 1) {
                    inputParameterType.add(words[0]);
                }
            }
        }
        return inputParameterType;
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getMethodDeclare() {
        return methodDeclare;
    }

    public List<String> getInputParameterType() {
        return inputParameterType;
    }

    public boolean hasInputParameterType(String parameterType){
        return inputParameterType.contains(parameterType);
    }

    public boolean contains(String pattern){
        return content.contains(pattern);
    }

    public boolean containsAll(String[] patterns){
        boolean matches = true;
        int counter = 0;
        while ( matches && counter < patterns.length){
            matches = contains(patterns[counter]);
            counter ++;
        }
        return matches;
    }

    public boolean containsAny(String[] patterns){
        boolean matches = false;
        int counter = 0;
        while ( !matches && counter < patterns.length){
            matches = contains(patterns[counter]);
            counter ++;
        }
        return matches;
    }
}

