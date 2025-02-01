package PreValidation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Task1Trainer {
    final String file_name = "src/main/java/trainer/Trainer.java";
    File file = new File(file_name);
    JavaFile javaFile = new JavaFile(file);

    @Test
    void fileExists() {
        assertTrue(file.isFile());
    }

    @Test
    void loadFromFileExist(){
        assertTrue(javaFile.hasMethodByName("loadFromFile"));
    }

    @Test
    void loadFromFileCorrectInputs(){
        List<String> correctParams = new ArrayList<String>();
        correctParams.add("String");
        assertEquals(correctParams, javaFile.getMethodByName("loadFromFile").getInputParameterType());

    }


    @Test
    void loadFromFileReturnsTrainer(){
        assertEquals("Trainer", javaFile.getMethodByName("loadFromFile").getReturnType());
    }

    @Test
    void saveToFileExist(){
        assertTrue(javaFile.hasMethodByName("saveToFile"));
    }

    @Test
    void saveToFileHasCorrectInputs(){
        List<String> correctParams = new ArrayList<String>();
        correctParams.add("String");
        assertEquals(correctParams, javaFile.getMethodByName("saveToFile").getInputParameterType());

    }

    @Test
    void saveToFileReturnsVoid(){
        assertEquals("void", javaFile.getMethodByName("saveToFile").getReturnType());
    }
}


