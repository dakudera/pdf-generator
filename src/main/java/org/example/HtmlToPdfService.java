package org.example;

import com.lowagie.text.DocumentException;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class HtmlToPdfService {

    private final Set<Map.Entry<String, String>> values;
    private final String inputFilePath;
    private final String outputFilePath;

    public HtmlToPdfService(Set<Map.Entry<String, String>> values, String inputFilePath, String outputFilePath) {
        this.values = values;
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    public void execute() throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();

        // Read HTML content from file
        String content = FileUtils.readFileToString(Paths.get(inputFilePath).toFile(), StandardCharsets.UTF_8);
        for (Map.Entry<String, String> es : values) {
            content = content.replace("@{" + es.getKey() + "}", es.getValue());
        }

        // Convert HTML to XHTML
        String htmlToXhtml = htmlToXhtml(content);
        renderer.setDocumentFromString(htmlToXhtml);

        // Render the document to PDF
        renderer.layout();
        FileOutputStream fos = new FileOutputStream(outputFilePath);
        renderer.createPDF(fos);
        fos.close();
    }

    private String htmlToXhtml(String html) {
        // Convert HTML to XHTML
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

}
