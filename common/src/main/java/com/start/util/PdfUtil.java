package com.start.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.start.enumeration.util.ReplacePdfEnum;
import org.springframework.util.Assert;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PdfUtil {

    // pdf 加水印
    public static void manipulatePdf(String src, String dest, String waterText) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        int pages = reader.getNumberOfPages();

        PdfStamper stamper = new PdfStamper(reader,new FileOutputStream(dest));

        // 加密
        String password = String.valueOf(Math.abs(waterText.hashCode()));
        stamper.setEncryption(null,password.getBytes(), PdfWriter.ALLOW_PRINTING,PdfWriter.STANDARD_ENCRYPTION_40);

        BaseColor color = new BaseColor(42, 183, 248);
        Font f = new Font(Font.FontFamily.HELVETICA, 20,1,color);
        new Font();
        PdfContentByte over = stamper.getOverContent(3);

        // 将水印文字变长
        for (int i=0; i<10; i++){
            waterText += ("     "+waterText);
        }

        Phrase p = new Phrase(waterText, f);

        // 已第一页来计算水印
        Rectangle pageSize = reader.getPageSize(1);
        float width = pageSize.getWidth();
        float height = pageSize.getHeight();
        int v = (int)(width+height) / 180;

        for (int i=1; i<=pages; i++){
            over = stamper.getOverContent(i);
            over.saveState();
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.5f);
            over.setGState(gs1);
            int x = (int)height/2*-1;
            for (int j=0; j<v; j++){
                x += 180;
                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, (height/2), 45);
            }
            over.restoreState();
        }
        stamper.close();
        reader.close();
    }

    // pdf 替换信息
    private static String replacePdfContract(String pdfFile, String key, String value, String type) throws Exception{
        Assert.hasText(pdfFile, "pdfFile is required");
        Assert.hasText(key, "key is required");
        Assert.hasText(value, "value is required");
        Assert.hasText(type, "type is required");
        com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(pdfFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        com.itextpdf.text.pdf.PdfStamper stamper = new com.itextpdf.text.pdf.PdfStamper(reader, baos);
        com.itextpdf.text.pdf.AcroFields acroForm = stamper.getAcroFields();
        if (acroForm != null) {
            stamper.setFormFlattening(true);
            if (type.equals(ReplacePdfEnum.input.name())){
                acroForm.setField(key, value);
                stamper.partialFormFlattening(key);
            }else if (type.equals(ReplacePdfEnum.radio.name())){
                acroForm.setField(key+value, "是");
                stamper.partialFormFlattening(key);
            }
        }
        stamper.close();
        reader.close();
        // 替换后的pdf输出地址
        String file = "/"+ UUID.randomUUID().toString()+".pdf";
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(baos.toByteArray());
        fos.close();
        baos.close();
        return file;
    }

}
