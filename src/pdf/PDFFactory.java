package pdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PDFFactory {

    private HashMap<String, PDFfile> pdfFileList = new HashMap<String, PDFfile>();

    public PDFFactory(){

    }


    public HashMap<String, PDFfile> getPdfFileMap() {
        return pdfFileList;
    }

    public ArrayList<String> getPdfFileList() {
        ArrayList<String> tmplist = new ArrayList<String>();
        for (Map.Entry m : pdfFileList.entrySet()) {
            tmplist.add(m.getKey().toString());
        }

        return tmplist;
    }

    public ArrayList<Integer> getPagesOfDocument(String fileName) throws IOException {
        ArrayList<Integer> tmplist = new ArrayList<Integer>();
        for (int i = 0; i < pdfFileList.get(fileName).getNumberOfPages(); i++) {
            tmplist.add(i);
        }

        return tmplist;
    }

    public void setNewPdfFile(File pdffile) {

        this.pdfFileList.put(pdffile.getName(), new PDFfile(pdffile));
    }

    public void unsetpdf(String key){
        this.pdfFileList.remove(key);
    }

    public PDFfile getPdfFileFromKey(String key){
        return this.pdfFileList.get(key);
    }

    public int getPdfFileListSize(){
        return this.pdfFileList.size();
    }
}
