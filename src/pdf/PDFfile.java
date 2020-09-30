package pdf;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.PDFRenderer;


// Classe qui permet de lire un fichier csv, xls ou xlsx
public class PDFfile {


    private String filepath;
    private String fileName;
    private File file;

    public PDFfile(File file){

        this.file = file;
        this.filepath = file.getAbsolutePath();
        this.fileName = file.getName();

    }



    public void SetAuthor(String name){

        try {

            PDDocument document = PDDocument.load(this.file);
            PDDocumentInformation pdd = document.getDocumentInformation();
            pdd.setAuthor(name);
            document.save(this.file.getAbsolutePath());
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SetTitle(String title){

        try {

            PDDocument document = PDDocument.load(this.file);
            PDDocumentInformation pdd = document.getDocumentInformation();
            pdd.setTitle(title);
            document.save(this.file.getAbsolutePath());
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setCreator(String creator){

        try {

            PDDocument document = PDDocument.load(this.file);
            PDDocumentInformation pdd = document.getDocumentInformation();
            pdd.setCreator(creator);
            document.save(this.file.getAbsolutePath());
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setSubject(String subject){

        try {

            PDDocument document = PDDocument.load(this.file);
            PDDocumentInformation pdd = document.getDocumentInformation();
            pdd.setSubject(subject);
            document.save(this.file.getAbsolutePath());
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setKeywords(String keywords){

        try {

            PDDocument document = PDDocument.load(this.file);
            PDDocumentInformation pdd = document.getDocumentInformation();
            pdd.setKeywords(keywords);
            document.save(file.getAbsolutePath());
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addBlankPage(){

        try {

            PDDocument document = PDDocument.load(this.file);
            PDPage blankPage = new PDPage();
            document.addPage( blankPage );
            document.save(this.file.getAbsolutePath());
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removePage(int number){

        try {

            PDDocument document = PDDocument.load(this.file);
            int noOfPages= document.getNumberOfPages();

            if ((number >=0) && (number <= noOfPages-1)){
                document.removePage(number);
                document.save(this.file.getAbsolutePath());
            }
            else{
                System.out.println("la page n'existe pas");
            }

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getNumberOfPages() throws IOException {
        int tmp_page=0;
        try{
            PDDocument document = PDDocument.load(this.file);
            tmp_page = document.getNumberOfPages();
            document.close();
        }catch (IOException e) {
            e.printStackTrace();
            return tmp_page;
        }

        return tmp_page;
    }

    public void addText(String texte, int numeropage) throws IOException {

        PDDocument document = PDDocument.load(this.file);

        int noOfPages= document.getNumberOfPages();
        if (!(numeropage >=0) || !(numeropage <= noOfPages-1)){
            return;
        }


        PDPage page = document.getPage(numeropage);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();

        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

        contentStream.newLineAtOffset(80, 400);

        //String text = "This is the sample document and we are adding content to it.";

        contentStream.showText(texte);

        contentStream.endText();

        System.out.println("Content added");

        document.save(this.file.getAbsolutePath());
        contentStream.close();
        document.close();




    }



    public Image getImageFromPDF(int pageindex) throws IOException {

        PDDocument document = PDDocument.load(this.file);
        PDFRenderer renderer = new PDFRenderer(document);

        BufferedImage img = renderer.renderImageWithDPI(pageindex, 300);


        Image image = SwingFXUtils.toFXImage(img, null);
        document.close();

        return image;

    }
}