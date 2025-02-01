package PreValidation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Task2Monster {

    final String file_name = "src/main/java/monster/Monster.java";
    File file = new File(file_name);
    JavaFile javaFile = new JavaFile(file);

    @Test
    void fileExists() {
        assertTrue(file.isFile());
    }

    @Test
    void saveToFileExist(){
        assertTrue(javaFile.hasMethodByName("compareTo"));
    }

    @Test
    void compareToCorrectInputs(){
        List<String> correctParams = new ArrayList<String>();
        correctParams.add("Monster");
        assertEquals(correctParams, javaFile.getMethodByName("compareTo").getInputParameterType());

    }

    @Test
    void saveToFileReturnsInt(){
        assertEquals("int", javaFile.getMethodByName("compareTo").getReturnType());
    }
}


