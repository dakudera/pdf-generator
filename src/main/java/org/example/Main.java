package org.example;


import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    private static String htmlToXhtml(String html) {
        // Convert HTML to XHTML
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    public static void main(String[] args) {
        String inputFile = "src/main/resources/test.html"; // Path to your XHTML/XML file
        String outputFile = "src/main/resources/output.pdf"; // Path to the output PDF file
        String image = "src/main/resources/image.png";

        try {
            // Create an ITextRenderer instance
            ITextRenderer renderer = new ITextRenderer();

            // Read HTML content from file
            String content = FileUtils.readFileToString(Paths.get(inputFile).toFile(), StandardCharsets.UTF_8);


            String base64Image = ImageUtil.encodeImageToBase64(image);
            String imgTag = "data:image/png;base64," + base64Image;
            // Perform replacements
            Map<String, String> valueMap = new HashMap<>();
            valueMap.put("statementId", "20240200001");
            valueMap.put("senderName", "Harish Jay Raj");
            valueMap.put("senderCardName", "DEBET CARD");
            valueMap.put("senderCardNumber", "4242 4242 4242 4242");

            valueMap.put("recipientName", "Harish Jaz Rachel");
            valueMap.put("recipientCardName", "Debet Card");
            valueMap.put("recipientCardNumber", "4242 4242 4242 4242");

            valueMap.put("sumPayment", "20");
            valueMap.put("fee", "1");
            valueMap.put("currency", "USD");
            valueMap.put("image", imgTag);

            Set<Entry<String, String>> entrySet = valueMap.entrySet();
            for (Entry<String, String> es : entrySet) {
                content = content.replace("@{" + es.getKey() + "}", es.getValue());
            }

            // Convert HTML to XHTML
            String htmlToXhtml = Main.htmlToXhtml(content);
            renderer.setDocumentFromString(htmlToXhtml);

            // Render the document to PDF
            renderer.layout();
            FileOutputStream fos = new FileOutputStream(outputFile);
            renderer.createPDF(fos);
            fos.close();

            System.out.println("PDF generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}