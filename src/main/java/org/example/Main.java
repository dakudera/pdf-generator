package org.example;

import java.util.Map.Entry;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) {
        String inputFilePath = "src/main/resources/test.html"; // Path to your XHTML/XML file
        String outputFilePath = "src/main/resources/output.pdf"; // Path to the output PDF file
        String imagePath = "src/main/resources/image.png";

        try {
            PrepareDataService prepareDataService = new PrepareDataService(imagePath);
            Set<Entry<String, String>> preparedData = prepareDataService.prepare();

            HtmlToPdfService htmlToPdfService = new HtmlToPdfService(preparedData, inputFilePath, outputFilePath);
            htmlToPdfService.execute();

            System.out.println("PDF generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}