import java.io.File;
import net.sourceforge.tess4j.*;

public class Main {
    public static void main(String[] args) {

        File imageFile = new File("E:\\Java\\My Programs\\OCR\\OCR1\\res\\2.jpg");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
        instance.setDatapath("E:\\Java\\My Programs\\OCR\\OCR1\\tessdata");

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}