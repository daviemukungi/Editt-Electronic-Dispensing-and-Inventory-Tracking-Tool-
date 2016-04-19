package org.msh.fdt.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.Code;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;
import org.msh.fdt.dao.ReferenceDAO;
import org.msh.fdt.dao.ReportsDAO;
import org.msh.fdt.dao.ReportsDAOImpl;
import org.msh.fdt.dao.StocksDAO;
import org.msh.fdt.model.*;
import org.msh.fdt.util.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Created by kenny on 6/18/14.
 */
@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private ReportsDAO reportsDAO;

    @Autowired
    private StocksDAO stocksDAO;

    @Autowired
    private ReferenceDAO referenceDAO;

    private String[] full_months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private Property facilityName;

    /**
     *  Fetches the Facility Name
     * @return
     */

    public String getFacilityName() {
        facilityName = referenceDAO.getProperty("facility_name");
        if(facilityName != null) {
            return facilityName.getValue();
        } else {
            return "";
        }
    }

    public String getFacilityDistrict() {
        Property facilityDistrict = referenceDAO.getProperty("facility_district");
        if(facilityDistrict != null) {
            return facilityDistrict.getValue();
        } else {
            return "";
        }
    }

    public String getFacilityCode() {
        Property facilityCode = referenceDAO.getProperty("facility_code");
        if(facilityCode != null) {
            return facilityCode.getValue();
        } else {
            return "";
        }
    }

    @Override
    @Transactional
    public File createRoutineRefillReport(String date) {
        List data = reportsDAO.createRoutineRefillReport(date);
        String fileName = "routine-refill-report.pdf";
        File file = new File(fileName);
        Document document = new Document(PageSize.A3);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("Listing of patients who visited for routine refill on " + date, headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph count = new Paragraph("Total count " + data.size(), headerStyle);
            count.setSpacingAfter(5);
            count.setAlignment(Element.ALIGN_CENTER);
            header.add(count);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            document.add(header);

            PdfPTable table = new PdfPTable(8);
            table.setWidths(new int[]{2, 1, 1, 1, 1, 1, 1, 1});
            table.setWidthPercentage(100);

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("Type of service", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Medical Record Number", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Current Age", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Sex", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Regimen", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Weight", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0,0,0);
            headerStyle.setSize(8);
            Iterator iterator = data.iterator();
            while(iterator.hasNext()) {
                Object[] row = (Object[])iterator.next();


                cell = new PdfPCell(new Phrase((String)row[0], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[7], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[1], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[2], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[3]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[4], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[5], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[6]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createListPatientExpectedToVisit(String date) {
        List data = reportsDAO.createListPatientExpectedToVisit(date);
        String fileName = "patients-expected-to-visit.pdf";
        File file = new File(fileName);
        Document document = new Document(PageSize.A3);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("Listing of patients expected to visit on " + date, headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph count = new Paragraph("Patient count " + data.size(), headerStyle);
            count.setSpacingAfter(5);
            count.setAlignment(Element.ALIGN_CENTER);
            header.add(count);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            document.add(header);

            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2, 1, 1, 1, 1, 1, 1});

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("Medical Record Number", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Sex", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Age", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Patient contact", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Address", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);


            cell = new PdfPCell(new Phrase("Last Regimen", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0,0,0);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);
            Iterator iterator = data.iterator();
            while(iterator.hasNext()) {
                Object[] row = (Object[])iterator.next();

                cell = new PdfPCell(new Phrase((String)row[7], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[0], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[1], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[2], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[3]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[4], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[5], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[6], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                /*cell = new PdfPCell(new Phrase((String)row[7], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);*/
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createMissedAppointmentReport(String date1, String date2, Integer serviceId) {
        List data = reportsDAO.createMissedAppointmentReport(date1, date2, serviceId);
        String fileName = "missed-appointments-report.pdf";
        File file = new File(fileName);
        Document document = new Document(PageSize.A3);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            String service = "";
            if(serviceId != -1) {
                service = referenceDAO.getServiceName(serviceId);
            }
            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("Patients who did not report on period ", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            String periodText = "Between : " + getDateFormat(date1) + " and : " + getDateFormat(date2);
            if(service != "") {
                periodText += " for " + service + " Service";
            }
            Paragraph period = new Paragraph(periodText, headerStyle);
            period.setAlignment(Element.ALIGN_CENTER);
            header.add(period);


            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            document.add(header);

            PdfPTable table = new PdfPTable(9);
            table.setWidths(new int[]{1, 2, 1, 1, 1, 1, 1, 1,1});
            table.setWidthPercentage(100);

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0, 0, 0);
            headerStyle.setStyle("bold");

            PdfPCell cell = new PdfPCell(new Phrase("Total", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(data.size() + "", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(8);
            table.addCell(cell);
            //Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);

            cell = new PdfPCell(new Phrase("Medical Record Number", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Sex", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Address", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Patient contact", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Appointed date", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Late by (days)", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adherence", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0,0,0);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);
            Iterator iterator = data.iterator();
            while(iterator.hasNext()) {
                Object[] row = (Object[])iterator.next();

                cell = new PdfPCell(new Phrase((String)row[10], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[0], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[1], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[2], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[3]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[4], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[5]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[6]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[7]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createChangedRegimenReport(String date1, String date2, Integer serviceId) {
        List data = reportsDAO.createRegimenChangeReport(date1, date2, serviceId);
        String fileName = "changed-regimen-report.pdf";
        File file = new File(fileName);
        Document document = new Document(PageSize.A3);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            String service = "";
            if(serviceId != -1) {
                service = referenceDAO.getServiceName(serviceId);
            }
            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("Patients who changed regimen ", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            String periodText = "Between : " + getDateFormat(date1) + " and : " + getDateFormat(date2);
            if(service != "") {
                periodText += " for " + service + " Service";
            }
            Paragraph period = new Paragraph(periodText, headerStyle);
            period.setAlignment(Element.ALIGN_CENTER);
            header.add(period);


            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            document.add(header);

            PdfPTable table = new PdfPTable(7);
            //table.setWidths(new int[]{2, 1, 1, 1, 1, 1, 1,1});
            table.setWidthPercentage(100);

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0, 0, 0);
            headerStyle.setStyle("bold");

            PdfPCell cell = new PdfPCell(new Phrase("Total", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(data.size() + "", headerStyle));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(6);
            table.addCell(cell);
            //Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);

            cell = new PdfPCell(new Phrase("From", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("To", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Type of Service", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Date Changed", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Reason for change", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0,0,0);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);
            Iterator iterator = data.iterator();
            while(iterator.hasNext()) {
                Object[] row = (Object[])iterator.next();

                cell = new PdfPCell(new Phrase((String)row[0], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[1], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[2], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[3]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[4], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[5]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[6]), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createExpiredDrugsReport(Integer accountId) {
        List<Batch> batchList = stocksDAO.listBatch(null, accountId, true, false);
        String fileName = "expired-drugs.pdf";
        File file = new File(fileName);
        Document document = new Document(PageSize.A3);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph rtype = new Paragraph("List of expired drugs as at " + df.format(new Date()), headerStyle);
            rtype.setAlignment(Element.ALIGN_CENTER);
            header.add(rtype);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            document.add(header);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 4, 1, 1, 1, 1, 1});

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Commodity name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Unit", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Batch Number", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Date expired", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity(Pks)", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Days since expiry", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);


            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0,0,0);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);
            for(int i = 0; i < batchList.size(); i++) {
                Batch batch = batchList.get(i);
                Integer packSize = batch.getPackSize();
                BigDecimal stocks = batch.getStockInBatch();
                float packs = 0;
                if(packSize != null && stocks != null)
                    packs = (float)stocks.doubleValue() / packSize;
                if(packs <= 0) { //If the batch stock value is 0 or less, we just ignore.
                    continue;
                }

                cell = new PdfPCell(new Phrase( String.valueOf(i + 1), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(batch.getDrugName(), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(batch.getDispensingUnitName(), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(batch.getBatch_no(), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(df.format(batch.getExpiry_date()), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(roundValue(packs), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(batch.getDaysFromExpiry()), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createShortDatedStockReport(Integer accountId) {
        List<Batch> batchList = stocksDAO.listBatch(null, accountId, false, true);
        String fileName = "short-dated-drugs.pdf";
        File file = new File(fileName);
        Document document = new Document(PageSize.A3);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph rtype = new Paragraph("List of short dated stock as at " + df.format(new Date()), headerStyle);
            rtype.setAlignment(Element.ALIGN_CENTER);
            rtype.setSpacingAfter(5);
            header.add(rtype);


            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            document.add(header);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 4, 1, 1, 1, 1, 1});

            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            headerStyle.setColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Commodity name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Unit", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Batch Number", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Expiry Date", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity(Packs)", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("No. of days to expiry", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);



            headerStyle = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            headerStyle.setColor(0,0,0);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);
            for(int i = 0; i < batchList.size(); i++) {
                Batch batch = batchList.get(i);

                cell = new PdfPCell(new Phrase( String.valueOf(i + 1), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(batch.getDrugName(), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(batch.getDispensingUnitName(), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(batch.getBatch_no(), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(df.format(batch.getExpiry_date()), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(batch.getStockInBatch().toString(), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(batch.getDaysFromExpiry() * -1), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public File createReportFile(List headers) {
        List data = reportsDAO.createListPatientExpectedToVisit("");
        String fileName = "stock-on-hand.pdf";
        File file = new File(fileName);
        Document document = new Document(PageSize.A3);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("Listing of patients expected to visit on " + "", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph count = new Paragraph("Patient count " + data.size(), headerStyle);
            count.setSpacingAfter(5);
            count.setAlignment(Element.ALIGN_CENTER);
            header.add(count);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            document.add(header);

            PdfPTable table = new PdfPTable(7);
            table.setWidths(new int[]{2, 1, 1, 1, 1, 1, 1});
            table.setWidthPercentage(100);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Sex", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Age", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Patient contact", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Address", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);


            cell = new PdfPCell(new Phrase("Last Regimen", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            Iterator iterator = data.iterator();
            while(iterator.hasNext()) {
                Object[] row = (Object[])iterator.next();


                cell = new PdfPCell(new Phrase((String)row[0], bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[1], bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[2], bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[3]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[4], bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[5], bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[6], bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase((String)row[7], bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createIssueReceiveReport(boolean issued,Integer accountId, String date1, String date2, Integer supplierId) {
        List data = reportsDAO.createIssueReceiveReport(issued, accountId, date1, date2, supplierId);
        String fileName = "receive-issue.pdf";
        List<Account> accounts = referenceDAO.listAccount();
        File file = new File(fileName);
        List suppliers = null;
        if(!issued && supplierId == -1) {
            suppliers = reportsDAO.execute("SELECT t.id, ti.account_id FROM transaction t LEFT JOIN transaction_item ti ON t.id = ti.transaction_id WHERE t.transaction_type_id = " + stocksDAO.getTransactionTypeId("Received from") + " AND ti.units_in IS NULL");
        }

        Document document = new Document(PageSize.A3);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("Commodities Received from all Suppliers", headerStyle);
            if(issued) {
                reportType = new Paragraph("Commodities Issued", headerStyle);
            } else {
                if(supplierId != -1) {
                    Account sup = referenceDAO.getAccount(supplierId);
                    reportType = new Paragraph("Commodities Received from " + sup.getName(), headerStyle);
                }
            }
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph count = new Paragraph("Commodities count " + data.size(), headerStyle);
            count.setSpacingAfter(5);
            count.setAlignment(Element.ALIGN_CENTER);
            header.add(count);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            document.add(header);

            PdfPTable table = new PdfPTable(8);
            table.setWidths(new int[]{2, 1, 1, 1, 1, 1, 1, 1});
            table.setWidthPercentage(100);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("Commodity Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Supplier", headerStyle));
            if(issued) {
                cell = new PdfPCell(new Phrase("Issued To", headerStyle));
            }
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Pack size", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("No of packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Units", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Cost", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Date", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Reference No", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);

            Iterator iterator = data.iterator();
            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            while(iterator.hasNext()) {
                Object[] row = (Object[])iterator.next();


                cell = new PdfPCell(new Phrase((String)row[0], bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                if(suppliers != null) {
                    Integer supp = getFromList(suppliers, (Integer)row[6]);
                    if(supp != null) {
                        cell = new PdfPCell(new Phrase(getAccountName(accounts, supp), bodyStyle));
                    } else {
                        cell = new PdfPCell(new Phrase("", bodyStyle));
                    }
                } else {
                    if(supplierId != -1) {
                        cell = new PdfPCell(new Phrase(getAccountName(accounts, supplierId), bodyStyle));
                    } else {
                        cell = new PdfPCell(new Phrase(getAccountName(accounts, (Integer) row[1]), bodyStyle));
                    }
                }
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[2]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[3]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                int units = 0;
                if(row[2] != null && row[3] != null) {
                    units = ((Integer)row[2] * (Integer)row[3]);
                }
                if(row[5] != null) {
                    units = units + (Integer)row[5];
                }
                cell = new PdfPCell(new Phrase(String.valueOf(units), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                Integer totalCost = 0;
                if(row[8] != null)
                    totalCost = ((BigDecimal)row[8]).intValue();

                cell = new PdfPCell(new Phrase(String.valueOf(totalCost), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);

                table.addCell(cell);

                String date = "";
                if(row[9] != null) {
                    Timestamp t = (Timestamp)row[9];
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(t.getTime());
                    date = df.format(c.getTime());
                }
                cell = new PdfPCell(new Phrase(String.valueOf(date), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);

                table.addCell(cell);

                String ref = "";
                if(row[10] != null) {
                    ref = (String)row[10];
                }
                cell = new PdfPCell(new Phrase(String.valueOf(ref), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);

                table.addCell(cell);

            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getFromList(List data, Integer key) {
        for(int i = 0; i < data.size(); i++) {
            Object[] v = (Object[])data.get(i);
            if(Integer.parseInt(String.valueOf(v[0])) == key) {
                return (Integer)v[1];
            }
        }
        return null;
    }
    @Override
    @Transactional
    public File createDrugConsumptionReport(String year, Integer drugId, Integer serviceTypeId) {
        try {
            Map<Integer, JSONObject> data = reportsDAO.createDrugConsumptionReport(year, drugId, serviceTypeId);
            String fileName = "drug-consumption-" + year + ".pdf";

            File file = new File(fileName);
            Document document = new Document(PageSize.A3);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("Consumption of drugs (in packs)", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph count = new Paragraph("Year " + year, headerStyle);
            count.setAlignment(Element.ALIGN_CENTER);
            count.setSpacingAfter(5);
            header.add(count);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            document.add(header);
            //document.setPageSize(.getWidth());

            Calendar c = Calendar.getInstance();

            int m = 12;

            int month = c.get(Calendar.MONTH);
            if(year.equalsIgnoreCase(String.valueOf(c.get(Calendar.YEAR)))) {
                m = month + 1;
            }

            PdfPTable table = new PdfPTable(m + 1);
            table.setWidthPercentage(100);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);


            PdfPCell cell = new PdfPCell(new Phrase("Drug", headerStyle));
            cell.setBorder(Rectangle.BOTTOM);

            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

            for(int i = 0; i < m; i++) {
                cell = new PdfPCell(new Phrase(months[i], headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.BOTTOM);
                table.addCell(cell);
            }

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            Iterator iterator = data.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry pairs = (Map.Entry)iterator.next();
                JSONObject obj = data.get(pairs.getKey());
                if(obj.has("name")) {
                    cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                } else {
                    cell = new PdfPCell(new Phrase("No name", bodyStyle));
                }
                //cell.setNoWrap(true);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                JSONArray unitCounts = obj.getJSONArray("count");
                System.out.println(obj.toString());
                for(int i = 0; i < m; i++) {
                    if(i < unitCounts.length()) {
                        if(unitCounts.isNull(i)) {
                            String cu = String.valueOf(unitCounts.optInt(i));
                        }
                        cell = new PdfPCell(new Phrase(unitCounts.isNull(i) ? "0" : String.valueOf(unitCounts.optInt(i)), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    } else {
                        cell = new PdfPCell(new Phrase("0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    }
                }

            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Transactional
    public File createActivePatientsByRegimenReports(Integer month, Integer year, Integer serviceTypeId) {
        try {
            String serviceName = referenceDAO.getServiceName(serviceTypeId);
            if(referenceDAO.getServiceName(serviceTypeId).equalsIgnoreCase("ART")) {
                //return createActivePatientsByRegimenReportSubService(month,year, serviceTypeId);
            }
            Map<Integer, JSONObject> data = reportsDAO.createActivePatientsByRegimenReport("", serviceTypeId, 0);
            String fileName = "active-patients-by-regimen.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph(serviceName + " Active patients by Regimen", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph duration = new Paragraph( full_months[month] + ", " + year, headerStyle);
            duration.setAlignment(Element.ALIGN_CENTER);
            header.add(duration);

            Paragraph serviceType = new Paragraph( "Service Type : " + referenceDAO.getServiceName(serviceTypeId), headerStyle);
            serviceType.setAlignment(Element.ALIGN_CENTER);
            serviceType.setSpacingAfter(5);
            header.add(serviceType);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            //table.setWidths(new int[]{2, 1, 1, 1, 1});


            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(10);

            PdfPCell cell = new PdfPCell(new Phrase("Current Regimen", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            cell.setNoWrap(true);

            table.addCell(cell);

            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                cell = new PdfPCell(new Phrase(gaf.name(), headerStyle));
                cell.setBorder(Rectangle.TOP);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            Iterator iterator = data.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry pairs = (Map.Entry)iterator.next();
                JSONObject obj = data.get(pairs.getKey());
                if(obj.has("name")) {
                    cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                } else {
                    cell = new PdfPCell(new Phrase("No name", bodyStyle));
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setNoWrap(true);
                table.addCell(cell);

                JSONArray unitCounts = obj.getJSONArray("data");
                for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                    if(gaf.getIndex() < unitCounts.length()) {
                        cell = new PdfPCell(new Phrase(unitCounts.isNull(gaf.getIndex()) ? "0" : unitCounts.get(gaf.getIndex()).toString(), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    } else {
                        cell = new PdfPCell(new Phrase("0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    }
                }
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Transactional
    public File createCumNumberOfPatientsReports(Integer year, Integer month, Integer serviceTypeId) {
        try {
            String serviceName = referenceDAO.getServiceName(serviceTypeId);
            if(serviceName.equalsIgnoreCase("ART")) {
               // return createCumNumberOfPatientsSubServices(year, month, serviceTypeId);
            }
            Map<Integer, JSONObject> data = reportsDAO.createCumNumberOfPatientsReport("", serviceTypeId);
            String fileName = "cumulative-number-of-patients.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            //table.setWidths(new int[]{2, 1, 1, 1, 1});

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);


            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("Cumulative Number of " + serviceName + " Patient by Current Status", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph duration = new Paragraph( full_months[month] + ", " + year, headerStyle);
            duration.setAlignment(Element.ALIGN_CENTER);
            duration.setSpacingAfter(5);
            header.add(duration);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("Current Status", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("N", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("%", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                cell = new PdfPCell(new Phrase(gaf.name(), headerStyle));
                cell.setBorder(Rectangle.TOP);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            Iterator iterator = data.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry pairs = (Map.Entry)iterator.next();
                JSONObject obj = data.get(pairs.getKey());
                if(obj.has("name")) {
                    cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                } else {
                    cell = new PdfPCell(new Phrase("No name", bodyStyle));
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.get("total").toString(), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.get("total").toString(), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                JSONArray unitCounts = null;
                if(obj.has("data")) {
                    unitCounts = obj.getJSONArray("data");
                } else {
                    unitCounts = new JSONArray();
                }
                for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                    if(gaf.getIndex() < unitCounts.length()) {
                        cell = new PdfPCell(new Phrase(unitCounts.isNull(gaf.getIndex()) ? "0" : unitCounts.get(gaf.getIndex()).toString(), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    } else {
                        cell = new PdfPCell(new Phrase("0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    }
                }
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  This function generates CDRR data for ART Service and returns JSON Object.
     *  This can be used to generate various types of reports.
     * @param month
     * @param year
     * @return
     */
	@Override
	@Transactional
    public JSONObject createARTCDRRData(String month, String year, Integer accountId) {
        String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";
        ServiceType st = referenceDAO.getServiceType("ART"); // TODO : To be replaces with UUID
        String endDate = getLastDay(month, year);
        List<Drug> drugs = stocksDAO.listDrugs(st.getId(), true);
        List<DispensingUnit> dispensingUnits = referenceDAO.listDispensingUnit();
        List<Object[]> beginningBalance = reportsDAO.drugBalance(startDate, st.getId(), accountId);
        Integer received = stocksDAO.getTransactionTypeId("Received from");
        List<Object[]> quantitiesReceived = reportsDAO.cumulativeTransactions(new Integer[]{received}, startDate, endDate, st.getId());

        Integer dispensed = stocksDAO.getTransactionTypeId("Dispensed to Patients");
        List<Object[]> quantitiesDispensed = reportsDAO.cumulativeTransactions(new Integer[]{dispensed}, startDate, endDate, st.getId());

        Integer lost = stocksDAO.getTransactionTypeId("Losses(-)");
        Integer expired = stocksDAO.getTransactionTypeId("Expired(-)");
        List<Object[]> losses = reportsDAO.cumulativeTransactions(new Integer[]{lost, expired}, startDate, endDate, st.getId());

        Integer adjustment = stocksDAO.getTransactionTypeId("Ajustment (+)");
        List<Object[]> adjustments = reportsDAO.cumulativeTransactions(new Integer[]{adjustment}, startDate, endDate, st.getId());

        Integer adjustmentNeg = stocksDAO.getTransactionTypeId("Ajustment (-)");
        List<Object[]> adjustmentsNVe = reportsDAO.cumulativeTransactions(new Integer[]{adjustmentNeg}, startDate, endDate, st.getId());

        List<Object[]> shortDated = reportsDAO.cumulativeShortDatedDrugs(endDate, st.getId(), accountId);

        List<Object[]> outOfStock = reportsDAO.daysOutOfStock(endDate, st.getId());

        List<Object[]> closingBalance = reportsDAO.drugBalance(endDate, st.getId(), accountId);

        /**
         *  Creates the base JSON Object
         */
        JSONObject reportItems = new JSONObject();

        JSONObject groupedItems = new JSONObject();

        Iterator iterator = drugs.iterator();

        while(iterator.hasNext()) {
            Drug d = (Drug)iterator.next();
            JSONObject obj = null;
            //if (reportItems.has(String.valueOf(d.getId()))) {
                //obj = reportItems.getJSONObject(String.valueOf(d.getId()));
                obj = new JSONObject();
                obj.put("name", d.getName());
                obj.put("dhisid", d.getDhisId());
                obj.put("unit", getDispensingUnit(dispensingUnits, d.getDispensingUnitId()));
                obj.put("unitPackSize", d.getPackSize());
                obj.put("min", d.getReorderPoint());
                reportItems.put(String.valueOf(d.getId()), obj);
          //  }

        }

        int i = drugs.size() - 1;
        while(i >= 0) {
            String id = null;
            JSONObject object = new JSONObject();

            if (!reportItems.has(String.valueOf(drugs.get(i).getId()))) {
                reportItems.put(String.valueOf(drugs.get(i).getId()), object);
            }

            reportItems.getJSONObject(String.valueOf(drugs.get(i).getId())).put("cdrrCategoryId", drugs.get(i).getCdrrCategoryId());
            reportItems.getJSONObject(String.valueOf(drugs.get(i).getId())).put("cdrr_index", drugs.get(i).getCdrrIndex());

            if(beginningBalance.size() > i) {
                Object[] bal = beginningBalance.get(i);
                id = String.valueOf(bal[0]);

                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                if(obj.has("unitPackSize")) {
                    Integer packSize = obj.getInt("unitPackSize");
                    Integer packs = ((BigDecimal) bal[1]).intValue() / packSize;
                    obj.put("balance", packs);
                } else {
                    obj.put("balance", String.valueOf(bal[1]));
                }

                reportItems.put(id, obj);
            }

            if(closingBalance.size() > i) {
                Object[] bal = closingBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                if(obj.has("unitPackSize")) {
                    Integer packSize = obj.getInt("unitPackSize");
                    Integer packs = ((BigDecimal) bal[1]).intValue() / packSize;
                    obj.put("closingBalance", packs);
                } else {
                    obj.put("closingBalance", String.valueOf(bal[1]));
                }

                reportItems.put(id, obj);
            }

            if(quantitiesReceived.size() > i) {
                Object[] qtyRec = quantitiesReceived.get(i);
                id = String.valueOf(qtyRec[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                if(obj.has("unitPackSize")) {
                    Integer packSize = obj.getInt("unitPackSize");
                    Integer packs = ((BigDecimal) qtyRec[0]).intValue() / packSize;
                    obj.put("quantityReceived", packs);
                } else {
                    obj.put("quantityReceived", qtyRec[0]);
                }

                reportItems.put(id, obj);
            }

            if(quantitiesDispensed.size() > i) {
                Object[] qtyDis = quantitiesDispensed.get(i);
                id = String.valueOf(qtyDis[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityDispensed", qtyDis[0]);
                if(obj.has("unitPackSize")) {
                    Integer packSize = obj.getInt("unitPackSize");
                    Integer packs = ((BigDecimal)qtyDis[0]).intValue() / packSize;
                    obj.put("dispensedPacks", packs);
                } else {
                    obj.put("dispensedPacks", 0);
                }
                reportItems.put(id, obj);
            }

            if(losses.size() > i) {
                Object[] loss = losses.get(i);
                id = String.valueOf(loss[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                if(obj.has("unitPackSize")) {
                    Integer packSize = obj.getInt("unitPackSize");
                    Integer packs = ((BigDecimal) loss[0]).intValue() / packSize;
                    obj.put("losses", packs);
                } else {
                    obj.put("losses", loss[0]);
                }

                reportItems.put(id, obj);

            }

            if(adjustments.size() > i) {
                Object[] adj = adjustments.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                if(obj.has("unitPackSize")) {
                    Integer packSize = obj.getInt("unitPackSize");
                    Integer packs = ((BigDecimal) adj[0]).intValue() / packSize;
                    obj.put("adjustments", packs);
                } else {
                    obj.put("adjustments", adj[0]);
                }


                reportItems.put(id, obj);

            }

            if(adjustmentsNVe.size() > i) {
                Object[] adj = adjustmentsNVe.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                if(obj.has("unitPackSize")) {
                    Integer packSize = obj.getInt("unitPackSize");
                    Integer packs = ((BigDecimal) adj[0]).intValue() / packSize;
                    obj.put("adjustmentsNve", packs);
                } else {
                    obj.put("adjustmentsNve", adj[0]);
                }

                reportItems.put(id, obj);

            }

            if(shortDated.size() > i) {
                Object[] sDated = shortDated.get(i);
                id = String.valueOf(sDated[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                if(obj.has("unitPackSize") && sDated[1] != null) {
                    Integer packSize = obj.getInt("unitPackSize");
                    Integer packs = ((BigDecimal) sDated[1]).intValue() / packSize;
                    obj.put("shortDated", packs);
                } else {
                    obj.put("shortDated", String.valueOf(sDated[1]));
                }

                String exp = String.valueOf(sDated[2]);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date d = df.parse(exp);
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    obj.put("expiryDate", (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR));
                } catch (Exception e){}

                reportItems.put(id, obj);
            }
            if(outOfStock.size() > i) {
                Object[] out = outOfStock.get(i);
                id = String.valueOf(out[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                Integer stock = ((BigDecimal)out[1]).intValue();
                if(stock <= 0)
                    obj.put("outOfStock", String.valueOf(out[2]));
                else
                    obj.put("outOfStock", 0);
                reportItems.put(id, obj);
            }
            i--;
        }

        return reportItems;
    }

    public String getDispensingUnit(List<DispensingUnit> du, Integer dispensingUnitId) {
        for(int i = 0; i < du.size(); i++) {
            DispensingUnit u = du.get(i);
            if(dispensingUnitId == u.getId()) {
                return u.getName();
            }
        }
        return "";
    }

    @Override
    @Transactional
    public File createFacilityCDRRStandAloneReport(String month, String year, Integer accountId) {
        try {
            String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";

            String endDate = getLastDay(month, year);

            JSONObject reportItems = createARTCDRRData(month, year, accountId);
            String fileName = "ART-Facility-CDRR-StandAlone-Report.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(13);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{5, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setSize(10);
            headerStyle.setColor(0, 0, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);


            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph("MINISTRY OF HEALTH", headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("FACILITY CONSUMPTION DATA REPORT AND REQUEST (F-CDRR) FOR ANTIRETROVIRAL AND OPPORTUNISTIC INFECTION MEDICINES", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph t = new Paragraph("730B", headerStyle);
            t.setAlignment(Element.ALIGN_CENTER);
            t.setSpacingAfter(4);
            header.add(t);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(6);
            document.add(header);

            /**
             *  Top Content
             */
            PdfPTable topContent = new PdfPTable(3);

            PdfPCell cell = new PdfPCell(new Phrase("Programme Sponsor"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase(""));
            cell.setPadding(4);
            cell.setColspan(2);
            topContent.addCell(cell);



            cell = new PdfPCell(new Phrase("Facility Name : " + getFacilityName()));
            cell.setPadding(4);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Facility Code : " + getFacilityCode()));
            cell.setPadding(4);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("County :"));
            cell.setPadding(4);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("County : " + getFacilityDistrict()));
            cell.setPadding(4);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Type of service provided at facility :"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase(""));
            cell.setPadding(4);
            cell.setColspan(2);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Reporting period :"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Beginning :" + getDateFormat(startDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Ending :" + getDateFormat(endDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            topContent.addCell(cell);

            topContent.setSpacingAfter(5);
            document.add(topContent);


            /**
             *  Body Content
             */

            headerStyle = new Font();
            headerStyle.setSize(7);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            cell = new PdfPCell(new Phrase("Drug Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Unit Pack Size", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Beginning balance in packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity Received this period in packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           // cell.setColspan(2);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Total Quantity Dispensed this period", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setColspan(2);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Losses(Damages, Expires, Missing) in packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Positive Adjustments(Borrowed from or Issued out to Other facilities) In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Negative Adjustments(Borrowed from or Issued out to Other facilities) In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("End of month Physical Count In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Drugs with less than 6 months to expiry", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

//            cell = new PdfPCell(new Phrase("Expiry Date", headerStyle));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBorder(Rectangle.TOP);
//            table.addCell(cell);


            cell = new PdfPCell(new Phrase("Days out of Stock", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity needed for resupply", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);



            /**
             *  Begin second Row with empty values and packs
             */

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In units(tabs, caps, bottles for Liquids e.t.c)", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Expiry Date", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);


            /**
             *  Third Row
             */
            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("A", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("B", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("C", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("D", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("E", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("F", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("G", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("mm/yyyy", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("H", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("I", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(6);
            Iterator iterator = reportItems.keys();


            List<CdrrCategory> cdrrCats = referenceDAO.listCDRRCategoty();
            JSONArray finalItems = sortARTCDRRData(reportItems);

            for(int i = 0; i < finalItems.length(); i++) {
                if(finalItems.get(i) != null) {
                    if(!(finalItems.get(i) instanceof JSONObject)) {
                        continue;
                    }
                    JSONObject object = finalItems.getJSONObject(i);
                    JSONArray sorted = sortCategory(object);
                    iterator = object.keys();

                    CdrrCategory category = getCDRRCategory(cdrrCats, i);
                    if(category != null) {
                        cell = new PdfPCell(new Phrase(category.getName(), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setColspan(14);
                        table.addCell(cell);
                    } else {

                    }

                    int j = 0;
                    while(j < sorted.length()) {
                        if(sorted.get(j) != null) {
                            if(!(sorted.get(j) instanceof JSONObject)) {
                                j++;
                                continue;
                            }
                            JSONObject obj = sorted.getJSONObject(j);
                            if(obj == null) {
                                continue;
                            }

                            cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.optString("unit"), bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.has("balance") ? obj.get("balance").toString() : "0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.has("quantityReceived") ? obj.get("quantityReceived").toString() : "0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.has("quantityDispensed") ? obj.get("quantityDispensed").toString() : "0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

//                            cell = new PdfPCell(new Phrase(obj.has("dispensedPacks") ? obj.get("dispensedPacks").toString() : "0", bodyStyle));
//                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.has("losses") ? obj.get("losses").toString() :"0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.has("adjustments") ? obj.get("adjustments").toString() : "0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.has("adjustmentsNve") ? obj.get("adjustmentsNve").toString() : "0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.has("closingBalance") ? obj.get("closingBalance").toString() : "0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.has("shortDated") ? obj.get("shortDated").toString() : "0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.has("expiryDate") ? obj.get("expiryDate").toString() : "", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(obj.has("outOfStock") ? obj.get("outOfStock").toString() : "0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);

                            Integer reorder = 0;
                            Integer min = obj.optInt("min");
                            if(obj.has("closingBalance") && obj.has("unit") && min > 0) {
                                Integer bal = obj.optInt("closingBalance");
                                if(bal < min) {
                                    reorder = (min - bal) / obj.optInt("unit");
                                }
                            }

                            cell = new PdfPCell(new Phrase(String.valueOf(reorder), bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(cell);
                            j++;
                        }
                    }
                    /*
                    while(iterator.hasNext()) {
                        String key = (String)iterator.next();
                        JSONObject obj = reportItems.getJSONObject(key);

                        cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.optString("unit"), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.has("balance") ? obj.get("balance").toString() : "0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.has("quantityReceived") ? obj.get("quantityReceived").toString() : "0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.has("quantityDispensed") ? obj.get("quantityDispensed").toString() : "0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.has("dispensedPacks") ? obj.get("dispensedPacks").toString() : "0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.has("losses") ? obj.get("losses").toString() :"0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.has("adjustments") ? obj.get("adjustments").toString() : "0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.has("closingBalance") ? obj.get("closingBalance").toString() : "0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.has("shortDated") ? obj.get("shortDated").toString() : "0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.has("expiryDate") ? obj.get("expiryDate").toString() : "", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase(obj.has("outOfStock") ? obj.get("outOfStock").toString() : "0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);

                        Integer reorder = 0;
                        Integer min = obj.optInt("min");
                        if(obj.has("closingBalance") && obj.has("unit") && min > 0) {
                            Integer bal = obj.optInt("closingBalance");
                            if(bal < min) {
                                reorder = (min - bal) / obj.optInt("unit");
                            }
                        }

                        cell = new PdfPCell(new Phrase(String.valueOf(reorder), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table.addCell(cell);
                    }*/
                }
            }






            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Need to sort the report items according to the CDRR Category, we will do this here.
     * @param reportItems
     * @return
     */
    public JSONArray sortARTCDRRData(JSONObject reportItems) {
        JSONArray finalItems = new JSONArray();
        Iterator iterator = reportItems.keys();
        while(iterator.hasNext()) {
            String key = (String)iterator.next();
            JSONObject obj = reportItems.getJSONObject(key);
            int catId = obj.optInt("cdrrCategoryId");
            //if(catId != 0) {
            if(finalItems.isNull(catId)) {
                finalItems.put(catId, new JSONObject());
            }

            finalItems.getJSONObject(catId).put(key, obj);
        }
        return finalItems;
    }

    public JSONArray sortCategory(JSONObject object) {
        JSONArray array = new JSONArray();
        Iterator iterator = object.keys();
        while(iterator.hasNext()) {
            String key = (String)iterator.next();
            JSONObject obj = object.getJSONObject(key);
            if(obj.has("cdrr_index")) {
                array.put(obj.optInt("cdrr_index"), obj);
            }
        }
        return array;
    }

    public CdrrCategory getCDRRCategory(List<CdrrCategory> list, Integer id) {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == id) {
                return list.get(i);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public JSONObject createEMMSCDRRData(String month, String year) {
        String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";
        ServiceType st = referenceDAO.getServiceType("EMMS"); // TODO : To be replaces with UUID
        String endDate = getLastDay(month, year);
        List<Drug> drugs = stocksDAO.listDrugs(st.getId());
        List<Object[]> beginningBalance = reportsDAO.drugBalance(startDate, st.getId(), null);
        Integer received = stocksDAO.getTransactionTypeId("Received from");
        List<Object[]> quantitiesReceived = reportsDAO.cumulativeTransactions(new Integer[]{received}, startDate, endDate, st.getId());

        Integer dispensed = stocksDAO.getTransactionTypeId("Dispensed to Patients");
        List<Object[]> quantitiesDispensed = reportsDAO.cumulativeTransactions(new Integer[]{dispensed}, startDate, endDate, st.getId());

        Integer lost = stocksDAO.getTransactionTypeId("Losses(-)");
        Integer expired = stocksDAO.getTransactionTypeId("Expired(-)");
        List<Object[]> losses = reportsDAO.cumulativeTransactions(new Integer[]{lost, expired}, startDate, endDate, st.getId());

        Integer adjustment = stocksDAO.getTransactionTypeId("Ajustment (+)");
        List<Object[]> adjustments = reportsDAO.cumulativeTransactions(new Integer[]{adjustment}, startDate, endDate, st.getId());
        List<Object[]> shortDated = reportsDAO.cumulativeShortDatedDrugs(endDate, st.getId(), null);

        List<Object[]> outOfStock = reportsDAO.daysOutOfStock(endDate, st.getId());

        List<Object[]> closingBalance = reportsDAO.drugBalance(endDate, st.getId(), null);

        /**
         *  Creates the base JSON Object
         */
        JSONObject reportItems = new JSONObject();


        int i = drugs.size() - 1;
        while(i >= 0) {
            String id = null;
            JSONObject object = new JSONObject();
            reportItems.put(String.valueOf(drugs.get(i).getId()), object);
            if(beginningBalance.size() > i) {
                Object[] bal = beginningBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("balance", String.valueOf(bal[1]));
                reportItems.put(id, obj);
            }

            if(closingBalance.size() > i) {
                Object[] bal = closingBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("closingBalance", String.valueOf(bal[1]));
                reportItems.put(id, obj);
            }

            if(quantitiesReceived.size() > i) {
                Object[] qtyRec = quantitiesReceived.get(i);
                id = String.valueOf(qtyRec[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityReceived", qtyRec[0]);
                reportItems.put(id, obj);
            }

            if(quantitiesDispensed.size() > i) {
                Object[] qtyDis = quantitiesDispensed.get(i);
                id = String.valueOf(qtyDis[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityDispensed", qtyDis[0]);
                if(obj.has("unit")) {
                    Integer packSize = obj.getInt("unit");
                    Integer packs = ((BigDecimal)qtyDis[0]).intValue() / packSize;
                    obj.put("dispensedPacks", packs);
                } else {
                    obj.put("dispensedPacks", 0);
                }
                reportItems.put(id, obj);
            }

            if(losses.size() > i) {
                Object[] loss = losses.get(i);
                id = String.valueOf(loss[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("losses", loss[0]);
                reportItems.put(id, obj);

            }

            if(adjustments.size() > i) {
                Object[] adj = adjustments.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("adjustments", adj[0]);
                reportItems.put(id, obj);

            }

            if(shortDated.size() > i) {
                Object[] sDated = shortDated.get(i);
                id = String.valueOf(sDated[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("shortDated", String.valueOf(sDated[1]));
                String exp = String.valueOf(sDated[2]);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date d = df.parse(exp);
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    obj.put("expiryDate", (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR));
                } catch (Exception e){}

                reportItems.put(id, obj);
            }
            if(outOfStock.size() > i) {
                Object[] out = outOfStock.get(i);
                id = String.valueOf(out[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                Integer stock = ((BigDecimal)out[1]).intValue();
                if(stock <= 0)
                    obj.put("outOfStock", String.valueOf(out[2]));
                else
                    obj.put("outOfStock", 0);
                reportItems.put(id, obj);
            }
            i--;
        }

        Iterator iterator = drugs.iterator();
        while(iterator.hasNext()) {
            Drug d = (Drug)iterator.next();
            JSONObject obj = null;
            if (reportItems.has(String.valueOf(d.getId()))) {
                obj = reportItems.getJSONObject(String.valueOf(d.getId()));
                obj.put("name", d.getName());
                obj.put("dhisid", d.getDhisId());
                obj.put("unit", d.getPackSize());
                obj.put("min", d.getReorderPoint());
                reportItems.put(String.valueOf(d.getId()), obj);
            }

        }
        return reportItems;
    }

    @Override
    @Transactional
    public File createEMMSFacilityCDRRStandAloneReport(String month, String year) {

        try {
            String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";

            String endDate = getLastDay(month, year);

            JSONObject reportItems = createEMMSCDRRData(month, year);
            String fileName = "EMMS-Facility-CDRR-StandAlone-Report.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(13);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{5, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setSize(10);
            headerStyle.setColor(0, 0, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph("MINISTRY OF HEALTH", headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("FACILITY CONSUMPTION DATA REPORT AND REQUEST (F-CDRR) FOR ANTIRETROVIRAL AND OPPORTUNISTIC INFECTION MEDICINES", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph t = new Paragraph("730B", headerStyle);
            t.setAlignment(Element.ALIGN_CENTER);
            t.setSpacingAfter(4);
            header.add(t);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(6);
            document.add(header);

            /**
             *  Top Content
             */
            PdfPTable topContent = new PdfPTable(3);

            PdfPCell cell = new PdfPCell(new Phrase("Programme Sponsor"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase(""));
            cell.setPadding(4);
            cell.setColspan(2);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Facility Name : " + getFacilityName()));
            cell.setPadding(4);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Facility Code : " + getFacilityCode()));
            cell.setPadding(4);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("County :"));
            cell.setPadding(4);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("District : " + getFacilityDistrict()));
            cell.setPadding(4);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Type of service provided at facility :"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase(""));
            cell.setPadding(4);
            cell.setColspan(2);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Reporting period :"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Beginning :" + getDateFormat(startDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Ending :" + getDateFormat(endDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            topContent.addCell(cell);

            topContent.setSpacingAfter(5);
            document.add(topContent);


            /**
             *  Body Content
             */

            headerStyle = new Font();
            headerStyle.setSize(7);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            cell = new PdfPCell(new Phrase("Drug Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Unit Pack Size", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Beginning balance in packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity Received this period in packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Total Quantity Dispensed this period", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Losses(Damages, Expires, Missing) in packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adjustments(Borrowed from or Issued out to Other facilities) In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("End of month Physical Count In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Drugs with less than 6 months to expiry", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

//            cell = new PdfPCell(new Phrase("Expiry Date", headerStyle));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBorder(Rectangle.TOP);
//            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Days out of Stock", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity needed for resupply", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            /**
             *  Begin second Row with empty values and packs
             */

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In units(tabs, caps, bottles for Liquids e.t.c)", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Expiry Date", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);


            /**
             *  Third Row
             */
            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("A", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("B", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("C", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("D", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("E", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("F", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("In packs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("mm/yyyy", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("G", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("H", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(6);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            Iterator iterator = reportItems.keys();
            while(iterator.hasNext()) {
                String key = (String)iterator.next();
                JSONObject obj = reportItems.getJSONObject(key);

                cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.optString("unit"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("balance") ? obj.get("balance").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("quantityReceived") ? obj.get("quantityReceived").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("quantityDispensed") ? obj.get("quantityDispensed").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("dispensedPacks") ? obj.get("dispensedPacks").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("losses") ? obj.get("losses").toString() :"0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("adjustments") ? obj.get("adjustments").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("closingBalance") ? obj.get("closingBalance").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("shortDated") ? obj.get("shortDated").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("expiryDate") ? obj.get("expiryDate").toString() : "", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("outOfStock") ? obj.get("outOfStock").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                Integer reorder = 0;
                Integer min = obj.optInt("min");
                if(obj.has("closingBalance") && obj.has("unit") && min > 0) {
                    Integer bal = obj.optInt("closingBalance");
                    if(bal < min) {
                        reorder = (min - bal) / obj.optInt("unit");
                    }
                }

                cell = new PdfPCell(new Phrase(String.valueOf(reorder), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createPatientsByStatus(Integer patientStatus) {
        try {
            List<Object[]> patientByStatus = reportsDAO.listPatientsByStatus(patientStatus);
            String fileName = "patients-by-status.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);


            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            String patientStatusName = referenceDAO.getPatientStatusName(patientStatus);

            Paragraph reportType = new Paragraph("List of " + patientStatusName + " Patients", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            reportType.setSpacingAfter(5);
            header.add(reportType);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            BaseColor borderColor = new BaseColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("Type of service", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(2);
            cell.setBorderColor(borderColor);
            cell.setPaddingBottom(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Medical Record Number", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(2);
            cell.setBorderColor(borderColor);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(2);
            cell.setBorderColor(borderColor);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(2);
            cell.setBorderColor(borderColor);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Sex", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(2);
            cell.setBorderColor(borderColor);
            table.addCell(cell);



            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            Iterator iterator = patientByStatus.iterator();
            int i = 0;
            String currentService = "";

            int[] aggregate = new int[4]; //Aggregate Counts 0 Adult Male 1 Adult Female 2 Child Male 3 Child Female

            while (i < patientByStatus.size()) {
                Object[] row = patientByStatus.get(i);

                String service = String.valueOf(row[0]);
                if (!currentService.equalsIgnoreCase(service)) {
                    currentService = service;

                    cell = new PdfPCell(new Phrase(currentService, bodyStyle));
                    //cell.setBorder(Rectangle.NO_BORDER);
                    cell.setColspan(5);
                    table.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[8]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[1]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[2]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);



                cell = new PdfPCell(new Phrase(String.valueOf(row[4]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                if(String.valueOf(row[4]).equalsIgnoreCase("Male")) {
                    if(row[9] != null && Integer.parseInt(String.valueOf(row[9])) < 18) {
                        aggregate[2]++;
                    } else {
                        aggregate[0]++;
                    }
                } else if(String.valueOf(row[4]).equalsIgnoreCase("Female")) {
                    if(row[9] != null && Integer.parseInt(String.valueOf(row[9])) < 18) {
                        aggregate[3]++;
                    } else {
                        aggregate[1]++;
                    }
                }
                i++;
            }

            document.add(table);

            header = new Paragraph();
            header.setExtraParagraphSpace(4);
            document.add(header);

            table = new PdfPTable(4);
            cell = new PdfPCell(new Phrase("Adult", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Children", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Male", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Female", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Male", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Female", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            for( i = 0; i < aggregate.length; i++) {
                cell = new PdfPCell(new Phrase(String.valueOf(aggregate[i]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase("Total Adults", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(aggregate[0] + aggregate[1]), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Total Children", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(aggregate[2] + aggregate[3]), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Total", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(aggregate[0] + aggregate[1] + aggregate[2] + aggregate[3]), headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);
            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createPatientsByDrugUsedReport(Integer drugId) {
        try {
            List<Object[]> patientByDrugUsed = reportsDAO.listPatientsByDrugUsed(drugId);
            String fileName = "patients-by-drug-used.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            String drugName = referenceDAO.getDrugName(drugId);

            Paragraph reportType = new Paragraph("Patient List by drug used : " + drugName + "", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            reportType.setSpacingAfter(5);
            header.add(reportType);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);

            BaseColor borderColor = new BaseColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("Total", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(patientByDrugUsed.size() + "", headerStyle));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Medical Record Number", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(3);
            cell.setBorderColor(borderColor);
            cell.setPaddingBottom(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(3);
            cell.setBorderColor(borderColor);
            cell.setPaddingBottom(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(3);
            cell.setBorderColor(borderColor);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Age", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(3);
            cell.setBorderColor(borderColor);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            int i = 0;
            String currentService = "";
            while (i < patientByDrugUsed.size()) {
                Object[] row = patientByDrugUsed.get(i);

                cell = new PdfPCell(new Phrase(row[7] != null ? String.valueOf(row[7]) : "", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(row[0] != null ? String.valueOf(row[0]) : "", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(row[1] != null ? String.valueOf(row[1]) : "", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(row[2] != null ? String.valueOf(row[2]) : "", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                i++;
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createPatientsStartingByRegimen(String startDate, String endDate, Integer serviceId) {
        try {
            List<Object[]> patientByDrugUsed = reportsDAO.patientsStartingByRegimen(startDate, endDate, serviceId);
            String fileName = "patients-starting-by-regimen.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);


            Paragraph reportType = new Paragraph("Patient Starting By Regimen in the Period", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            reportType.setSpacingAfter(5);
            header.add(reportType);

            Paragraph period = new Paragraph("Between " + getDateFormat(startDate) + " and " + getDateFormat(endDate), headerStyle);
            period.setAlignment(Element.ALIGN_CENTER);
            header.add(period);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);

            BaseColor borderColor = new BaseColor(0, 0, 128);

            PdfPCell cell = new PdfPCell(new Phrase("Total", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(patientByDrugUsed.size() + "", headerStyle));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Regimen Started", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(3);
            cell.setBorderColor(borderColor);
            cell.setPaddingBottom(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(3);
            cell.setBorderColor(borderColor);
            cell.setPaddingBottom(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(3);
            cell.setBorderColor(borderColor);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Service Type", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            cell.setBorderWidthBottom(3);
            cell.setBorderColor(borderColor);
            table.addCell(cell);


            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            int i = 0;
            String currentService = "";
            while (i < patientByDrugUsed.size()) {
                Object[] row = patientByDrugUsed.get(i);



                cell = new PdfPCell(new Phrase(String.valueOf(row[0]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[1]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[2]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(row[3]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
                i++;
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public JSONObject createMalariaCDRRData(String month, String year) {
        String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";
        String endDate = getLastDay(month, year);
        ServiceType st = referenceDAO.getServiceType("MALARIA"); // TODO : To be replaces with UUID

        List<Drug> drugs = stocksDAO.listDrugs(st.getId());
        List<Object[]> beginningBalance = reportsDAO.drugBalance(startDate, st.getId(), null);
        Integer received = stocksDAO.getTransactionTypeId("Received from");
        List<Object[]> quantitiesReceived = reportsDAO.cumulativeTransactions(new Integer[]{received}, startDate, endDate, st.getId());

        Integer dispensed = stocksDAO.getTransactionTypeId("Dispensed to Patients");
        List<Object[]> quantitiesDispensed = reportsDAO.cumulativeTransactions(new Integer[]{dispensed}, startDate, endDate, st.getId());

        Integer lost = stocksDAO.getTransactionTypeId("Losses(-)");
        List<Object[]> losses = reportsDAO.cumulativeTransactions(new Integer[]{lost}, startDate, endDate, st.getId());

        Integer expire = stocksDAO.getTransactionTypeId("Expired(-)");
        List<Object[]> expired = reportsDAO.cumulativeTransactions(new Integer[]{expire}, startDate, endDate, st.getId());

        Integer adjustment = stocksDAO.getTransactionTypeId("Ajustment (+)");
        List<Object[]> adjustments = reportsDAO.cumulativeTransactions(new Integer[]{adjustment}, startDate, endDate, st.getId());

        Integer negativeAdjustment = stocksDAO.getTransactionTypeId("Ajustment (-)");
        List<Object[]> negativeAdjustments = reportsDAO.cumulativeTransactions(new Integer[]{negativeAdjustment}, startDate, endDate, st.getId());

        List<Object[]> shortDated = reportsDAO.cumulativeShortDatedDrugs(endDate, st.getId(), null);

        List<Object[]> outOfStock = reportsDAO.daysOutOfStock(endDate, st.getId());

        List<Object[]> closingBalance = reportsDAO.drugBalance(endDate, st.getId(), null);

        /**
         *  Creates the base JSON Object
         */
        JSONObject reportItems = new JSONObject();


        int i = drugs.size() - 1;
        while(i >= 0) {
            String id = null;
            JSONObject object = new JSONObject();
            reportItems.put(String.valueOf(drugs.get(i).getId()), object);
            if(beginningBalance.size() > i) {
                Object[] bal = beginningBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("balance", String.valueOf(bal[1]));
                reportItems.put(id, obj);
            }

            if(closingBalance.size() > i) {
                Object[] bal = closingBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("closingBalance", String.valueOf(bal[1]));
                reportItems.put(id, obj);
            }

            if(quantitiesReceived.size() > i) {
                Object[] qtyRec = quantitiesReceived.get(i);
                id = String.valueOf(qtyRec[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityReceived", qtyRec[0]);
                reportItems.put(id, obj);
            }

            if(quantitiesDispensed.size() > i) {
                Object[] qtyDis = quantitiesDispensed.get(i);
                id = String.valueOf(qtyDis[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityDispensed", qtyDis[0]);
                if(obj.has("unit")) {
                    Integer packSize = obj.getInt("unit");
                    Integer packs = ((BigDecimal)qtyDis[0]).intValue() / packSize;
                    obj.put("dispensedPacks", packs);
                } else {
                    obj.put("dispensedPacks", 0);
                }
                reportItems.put(id, obj);
            }

            if(losses.size() > i) {
                Object[] loss = losses.get(i);
                id = String.valueOf(loss[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("losses", loss[0]);
                reportItems.put(id, obj);

            }

            if(expired.size() > i) {
                Object[] expires = expired.get(i);
                id = String.valueOf(expires[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("expired", expires[0]);
                reportItems.put(id, obj);

            }

            if(adjustments.size() > i) {
                Object[] adj = adjustments.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("adjustments", adj[0]);
                reportItems.put(id, obj);
            }

            if(negativeAdjustments.size() > i) {
                Object[] adj = negativeAdjustments.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("negativeAdjustments", adj[0]);
                reportItems.put(id, obj);
            }

            if(shortDated.size() > i) {
                Object[] sDated = shortDated.get(i);
                id = String.valueOf(sDated[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("shortDated", String.valueOf(sDated[1]));
                obj.put("expiryDate", String.valueOf(sDated[2]));
                reportItems.put(id, obj);
            }
            if(outOfStock.size() > i) {
                Object[] out = outOfStock.get(i);
                id = String.valueOf(out[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                Integer stock = ((BigDecimal)out[1]).intValue();
                if(stock <= 0)
                    obj.put("outOfStock", String.valueOf(out[2]));
                else
                    obj.put("outOfStock", 0);
                reportItems.put(id, obj);
            }
            i--;
        }

        Iterator iterator = drugs.iterator();
        while(iterator.hasNext()) {
            Drug d = (Drug)iterator.next();
            JSONObject obj = null;
            if (reportItems.has(String.valueOf(d.getId()))) {
                obj = reportItems.getJSONObject(String.valueOf(d.getId()));
                obj.put("name", d.getName());
                obj.put("dhisid", d.getDhisId());
                obj.put("unit", d.getPackSize());
                obj.put("min", d.getReorderPoint());
                reportItems.put(String.valueOf(d.getId()), obj);
            }

        }
        return reportItems;
    }
    @Override
    @Transactional
    public File createMalariaCDRRReport(String month, String year) {
        try {
            String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";
            String endDate = getLastDay(month, year);

            JSONObject reportItems = createMalariaCDRRData(month, year);
            String fileName = "Malaria-Facility-CDRR-StandAlone-Report.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(13);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{5, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setSize(10);
            headerStyle.setColor(0, 0, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph("MINISTRY OF HEALTH", headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("HEALTH FACILITY MONTHLY SUMMARY REPORT FOR MALARIA COMMODITIES", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(6);
            header.setSpacingAfter(5);
            document.add(header);

            /**
             *  Top Content
             */
            PdfPTable topContent = new PdfPTable(2);

            PdfPCell cell = new PdfPCell(new Phrase("Province"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("District"));
            cell.setPadding(4);
            cell.setColspan(2);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Facility Name : " + getFacilityName()));
            cell.setPadding(4);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Facility Level : "));
            cell.setPadding(4);
            cell.setColspan(2);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Period of Reporting:Beginning :" + getDateFormat(startDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Ending :" + getDateFormat(endDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            topContent.setSpacingAfter(5);
            document.add(topContent);


            /**
             *  Body Content
             */

            headerStyle = new Font();
            headerStyle.setSize(7);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            cell = new PdfPCell(new Phrase("Drug Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Basic Units", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Beginning balance", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity Received this period", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Total Quantity Dispensed", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Losses(Excluding expires)", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Positive Adjustments", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Negative Adjustments", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Physical Count", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity of expired drugs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Medicines with 6 months to expiry", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Days out of Stock", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adjusted Consumption", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            /**
             *  Begin second Row with empty values and packs
             */

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("A", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("B", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("C", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("D", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("E", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("F", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("G", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("H", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("I", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("J", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("C x (Period covered)", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            /**
             *  Third Row
             */
            BaseColor bg = new BaseColor(192,192,192);
            cell = new PdfPCell(new Phrase("Malaria Commodities", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(6);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            Iterator iterator = reportItems.keys();
            while(iterator.hasNext()) {
                String key = (String)iterator.next();
                JSONObject obj = reportItems.getJSONObject(key);

                cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.optString("unit"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("balance") ? obj.get("balance").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("quantityReceived") ? obj.get("quantityReceived").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("quantityDispensed") ? obj.get("quantityDispensed").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("losses") ? obj.get("losses").toString() :"0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("adjustments") ? obj.get("adjustments").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("negativeAdjustments") ? obj.get("negativeAdjustments").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("closingBalance") ? obj.get("closingBalance").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("expired") ? obj.get("expired").toString() : "", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("shortDated") ? obj.get("shortDated").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("outOfStock") ? obj.get("outOfStock").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                Integer adjustedConsumption = 0;
                if(obj.has("quantityDispensed")) {
                    adjustedConsumption = obj.optInt("quantityDispensed") * 30;
                } else {

                }

                cell = new PdfPCell(new Phrase(String.valueOf(adjustedConsumption), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public JSONObject createLabCDRRData(String month, String year) {
        String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";
        String endDate = getLastDay(month, year);
        ServiceType st = referenceDAO.getServiceType("LAB");
        List<Drug> drugs = stocksDAO.listDrugs(st.getId());
        List<Object[]> beginningBalance = reportsDAO.drugBalance(startDate , st.getId(), null);
        Integer received = stocksDAO.getTransactionTypeId("Received from");
        List<Object[]> quantitiesReceived = reportsDAO.cumulativeTransactions(new Integer[]{received}, startDate, endDate, st.getId());

        Integer dispensed = stocksDAO.getTransactionTypeId("Dispensed to Patients");
        List<Object[]> quantitiesDispensed = reportsDAO.cumulativeTransactions(new Integer[]{dispensed}, startDate, endDate, st.getId());

        Integer lost = stocksDAO.getTransactionTypeId("Losses(-)");
        Integer expired = stocksDAO.getTransactionTypeId("Expired(-)");
        List<Object[]> losses = reportsDAO.cumulativeTransactions(new Integer[]{lost, expired}, startDate, endDate, st.getId());

        Integer adjustment = stocksDAO.getTransactionTypeId("Ajustment (+)");
        List<Object[]> adjustments = reportsDAO.cumulativeTransactions(new Integer[]{adjustment}, startDate, endDate, st.getId());

        Integer negativeAdjustment = stocksDAO.getTransactionTypeId("Ajustment (-)");
        List<Object[]> negativeAdjustments = reportsDAO.cumulativeTransactions(new Integer[]{negativeAdjustment}, startDate, endDate, st.getId());

        List<Object[]> shortDated = reportsDAO.cumulativeShortDatedDrugs(endDate, st.getId(), null);

        List<Object[]> outOfStock = reportsDAO.daysOutOfStock(endDate, st.getId());

        List<Object[]> closingBalance = reportsDAO.drugBalance(endDate, st.getId(), null);

        /**
         *  Creates the base JSON Object
         */
        JSONObject reportItems = new JSONObject();


        int i = drugs.size() - 1;
        while(i >= 0) {
            String id = null;
            JSONObject object = new JSONObject();
            reportItems.put(String.valueOf(drugs.get(i).getId()), object);
            if(beginningBalance.size() > i) {
                Object[] bal = beginningBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("balance", String.valueOf(bal[1]));
                reportItems.put(id, obj);
            }

            if(closingBalance.size() > i) {
                Object[] bal = closingBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("closingBalance", String.valueOf(bal[1]));
                reportItems.put(id, obj);
            }

            if(quantitiesReceived.size() > i) {
                Object[] qtyRec = quantitiesReceived.get(i);
                id = String.valueOf(qtyRec[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityReceived", qtyRec[0]);
                reportItems.put(id, obj);
            }

            if(quantitiesDispensed.size() > i) {
                Object[] qtyDis = quantitiesDispensed.get(i);
                id = String.valueOf(qtyDis[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityDispensed", qtyDis[0]);
                if(obj.has("unit")) {
                    Integer packSize = obj.getInt("unit");
                    Integer packs = ((BigDecimal)qtyDis[0]).intValue() / packSize;
                    obj.put("dispensedPacks", packs);
                } else {
                    obj.put("dispensedPacks", 0);
                }
                reportItems.put(id, obj);
            }

            if(losses.size() > i) {
                Object[] loss = losses.get(i);
                id = String.valueOf(loss[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("losses", loss[0]);
                reportItems.put(id, obj);

            }

            if(adjustments.size() > i) {
                Object[] adj = adjustments.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("adjustments", adj[0]);
                reportItems.put(id, obj);
            }

            if(negativeAdjustments.size() > i) {
                Object[] adj = negativeAdjustments.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("negativeAdjustments", adj[0]);
                reportItems.put(id, obj);
            }

            if(shortDated.size() > i) {
                Object[] sDated = shortDated.get(i);
                id = String.valueOf(sDated[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("shortDated", String.valueOf(sDated[1]));
                obj.put("expiryDate", String.valueOf(sDated[2]));
                reportItems.put(id, obj);
            }
            if(outOfStock.size() > i) {
                Object[] out = outOfStock.get(i);
                id = String.valueOf(out[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                Integer stock = ((BigDecimal)out[1]).intValue();
                if(stock <= 0)
                    obj.put("outOfStock", String.valueOf(out[2]));
                else
                    obj.put("outOfStock", 0);
                reportItems.put(id, obj);
            }
            i--;
        }

        Iterator iterator = drugs.iterator();
        while(iterator.hasNext()) {
            Drug d = (Drug)iterator.next();
            JSONObject obj = null;
            if (reportItems.has(String.valueOf(d.getId()))) {
                obj = reportItems.getJSONObject(String.valueOf(d.getId()));
                obj.put("name", d.getName());
                obj.put("dhisid", d.getDhisId());
                obj.put("unit", d.getPackSize());
                obj.put("min", d.getReorderPoint());
                reportItems.put(String.valueOf(d.getId()), obj);
            }

        }
        return reportItems;
    }
    @Override
    @Transactional
    public File createLabCDRRReport(String month, String year) {
        try {
            String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";
            String endDate = getLastDay(month, year);
            JSONObject reportItems = createLabCDRRData(month, year);
            String fileName = "Lab-Facility-CDRR-StandAlone-Report.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(13);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{5, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setSize(10);
            headerStyle.setColor(0, 0, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph("MINISTRY OF HEALTH", headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("FACILITY CONSUMPTION DATA REPORT AND REQUEST (F-CDRR) FOR LABORATORY COMMODITIES", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(6);
            header.setSpacingAfter(5);
            document.add(header);


            /**
             *  Top Content
             */
            PdfPTable topContent = new PdfPTable(2);

            PdfPCell cell = new PdfPCell(new Phrase("Name of facility :" + getFacilityName()));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Facility code : " + getFacilityCode()));
            cell.setPadding(4);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("District : " + getFacilityDistrict()));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Province/County : " ));
            cell.setPadding(4);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Affiliation :"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Affiliation :"));
            cell.setPadding(4);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Reporting for period Beginning :" + getDateFormat(startDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Ending :" + getDateFormat(endDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            topContent.addCell(cell);

            topContent.setSpacingAfter(5);
            document.add(topContent);


            /**
             *  Body Content
             */

            headerStyle = new Font();
            headerStyle.setSize(7);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            cell = new PdfPCell(new Phrase("Commodity Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Unit of Issue", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Beginning balance", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity Received", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity used", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Number of tests done", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Losses", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adjustments[indicate if (+) or (-)]", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("End of month Physical Count", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity expiring in less than 6 months", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

//            cell = new PdfPCell(new Phrase("Expiry Date", headerStyle));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setBorder(Rectangle.TOP);
//            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Days out of Stock", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity requested for resupply", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            /**
             *  Begin second Row with empty values and packs
             */

            cell = new PdfPCell(new Phrase("Positive", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Negative", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(6);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            Iterator iterator = reportItems.keys();
            while(iterator.hasNext()) {
                String key = (String)iterator.next();
                JSONObject obj = reportItems.getJSONObject(key);

                cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.optString("unit"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("balance") ? obj.get("balance").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("quantityReceived") ? obj.get("quantityReceived").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("quantityDispensed") ? obj.get("quantityDispensed").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                //TODO: Number of tests done
                cell = new PdfPCell(new Phrase("", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("losses") ? obj.get("losses").toString() :"0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("adjustments") ? obj.get("adjustments").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("negativeAdjustments") ? obj.get("adjustments").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("closingBalance") ? obj.get("closingBalance").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("shortDated") ? obj.get("shortDated").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("outOfStock") ? obj.get("outOfStock").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                Integer reorder = 0;
                Integer min = obj.optInt("min");
                if(obj.has("closingBalance") && obj.has("unit") && min > 0) {
                    Integer bal = obj.optInt("closingBalance");
                    if(bal < min) {
                        reorder = (min - bal) / obj.optInt("unit");
                    }
                }

                cell = new PdfPCell(new Phrase(String.valueOf(reorder), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public JSONObject createNutritionCDRRData(String month, String year) {
        String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";
        String endDate = getLastDay(month, year);
        ServiceType st = referenceDAO.getServiceType("HIV Nutrition");

        List<Drug> drugs = stocksDAO.listDrugs(st.getId());
        List<Object[]> beginningBalance = reportsDAO.drugBalance(startDate, st.getId(), null);
        Integer received = stocksDAO.getTransactionTypeId("Received from");
        List<Object[]> quantitiesReceived = reportsDAO.cumulativeTransactions(new Integer[]{received}, startDate, endDate, st.getId());

        Integer dispensed = stocksDAO.getTransactionTypeId("Dispensed to Patients");
        List<Object[]> quantitiesDispensed = reportsDAO.cumulativeTransactions(new Integer[]{dispensed}, startDate, endDate, st.getId());

        Integer lost = stocksDAO.getTransactionTypeId("Losses(-)");
        Integer expired = stocksDAO.getTransactionTypeId("Expired(-)");
        List<Object[]> losses = reportsDAO.cumulativeTransactions(new Integer[]{lost, expired}, startDate, endDate, st.getId());

        Integer adjustment = stocksDAO.getTransactionTypeId("Ajustment (+)");
        List<Object[]> adjustments = reportsDAO.cumulativeTransactions(new Integer[]{adjustment}, startDate, endDate, st.getId());

        Integer negativeAdjustment = stocksDAO.getTransactionTypeId("ajustment (-)");
        List<Object[]> negativeAdjustments = reportsDAO.cumulativeTransactions(new Integer[]{negativeAdjustment}, startDate, endDate, st.getId());

        List<Object[]> shortDated = reportsDAO.cumulativeShortDatedDrugs(endDate, st.getId(), null);

        List<Object[]> outOfStock = reportsDAO.daysOutOfStock(endDate, st.getId());

        List<Object[]> closingBalance = reportsDAO.drugBalance(endDate, st.getId(), null);

        /**
         *  Creates the base JSON Object
         */
        JSONObject reportItems = new JSONObject();


        int i = drugs.size() - 1;
        while(i >= 0) {
            String id = null;
            JSONObject object = new JSONObject();
            reportItems.put(String.valueOf(drugs.get(i).getId()), object);
            if(beginningBalance.size() > i) {
                Object[] bal = beginningBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("balance", String.valueOf(bal[1]));
                reportItems.put(id, obj);
            }

            if(closingBalance.size() > i) {
                Object[] bal = closingBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("closingBalance", String.valueOf(bal[1]));
                reportItems.put(id, obj);
            }

            if(quantitiesReceived.size() > i) {
                Object[] qtyRec = quantitiesReceived.get(i);
                id = String.valueOf(qtyRec[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityReceived", qtyRec[0]);
                reportItems.put(id, obj);
            }

            if(quantitiesDispensed.size() > i) {
                Object[] qtyDis = quantitiesDispensed.get(i);
                id = String.valueOf(qtyDis[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityDispensed", qtyDis[0]);
                if(obj.has("unit")) {
                    Integer packSize = obj.getInt("unit");
                    Integer packs = ((BigDecimal)qtyDis[0]).intValue() / packSize;
                    obj.put("dispensedPacks", packs);
                } else {
                    obj.put("dispensedPacks", 0);
                }
                reportItems.put(id, obj);
            }

            if(losses.size() > i) {
                Object[] loss = losses.get(i);
                id = String.valueOf(loss[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("losses", loss[0]);
                reportItems.put(id, obj);

            }

            if(adjustments.size() > i) {
                Object[] adj = adjustments.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("adjustments", adj[0]);
                reportItems.put(id, obj);
            }

            if(negativeAdjustments.size() > i) {
                Object[] adj = negativeAdjustments.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("negativeAdjustments", adj[0]);
                reportItems.put(id, obj);
            }

            if(shortDated.size() > i) {
                Object[] sDated = shortDated.get(i);
                id = String.valueOf(sDated[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("shortDated", String.valueOf(sDated[1]));
                obj.put("expiryDate", String.valueOf(sDated[2]));
                reportItems.put(id, obj);
            }
            if(outOfStock.size() > i) {
                Object[] out = outOfStock.get(i);
                id = String.valueOf(out[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                Integer stock = ((BigDecimal)out[1]).intValue();
                if(stock <= 0)
                    obj.put("outOfStock", String.valueOf(out[2]));
                else
                    obj.put("outOfStock", 0);
                reportItems.put(id, obj);
            }
            i--;
        }

        Iterator iterator = drugs.iterator();
        while(iterator.hasNext()) {
            Drug d = (Drug)iterator.next();
            JSONObject obj = null;
            if (reportItems.has(String.valueOf(d.getId()))) {
                obj = reportItems.getJSONObject(String.valueOf(d.getId()));
                obj.put("name", d.getName());
                obj.put("dhisid", d.getDhisId());
                obj.put("unit", d.getPackSize());
                obj.put("min", d.getReorderPoint());
                reportItems.put(String.valueOf(d.getId()), obj);
            }

        }
        return reportItems;
    }

    @Override
    @Transactional
    public File createNutritionCDRRReport(String month, String year) {
        try {
            String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";
            String endDate = getLastDay(month, year);

            JSONObject reportItems = createNutritionCDRRData(month, year);
            String fileName = "Nutrition-Facility-CDRR-StandAlone-Report.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(13);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{5, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setSize(10);
            headerStyle.setColor(0, 0, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph("MINISTRY OF HEALTH", headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("FACILITY CONSUMPTION DATA REPORT AND REQUEST (F-CDRR) FOR HIV NUTRITION COMMODITIES", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            reportType.setSpacingAfter(5);
            header.add(reportType);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(6);
            document.add(header);

            /**
             *  Top Content
             */
            PdfPTable topContent = new PdfPTable(3);

            PdfPCell cell = new PdfPCell(new Phrase("Facility name : " + getFacilityName()));
            cell.setPadding(4);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Facility's MFL code : " + getFacilityCode()));
            cell.setPadding(4);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Province :"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("County :"));
            cell.setPadding(4);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("District : " + getFacilityDistrict()));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Pipeline :"));
            cell.setPadding(4);
            cell.setColspan(3);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Point of service delivery :"));
            cell.setPadding(4);
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);


            cell = new PdfPCell(new Phrase("Period of reporting :"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Beginning :" + getDateFormat(startDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Ending :" + getDateFormat(endDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            topContent.setSpacingAfter(5);
            document.add(topContent);


            /**
             *  Body Content
             */

            headerStyle = new Font();
            headerStyle.setSize(7);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            BaseColor bg = new BaseColor(192,192,192);

            cell = new PdfPCell(new Phrase("Commodity Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Unit of Issue", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Beginning balance", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity Received this month", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity Dispensed this month", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Losses(Damages, Expires, Missing)", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Positive Adjustments(Borrowed from other facilities)", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Negative Adjustments(Issues out to other facilities)", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Physical Count", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Drugs with less than 6 months to expiry", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Days out of Stock this month", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity needed for resupply", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            /**
             *  Begin second Row with empty values and packs
             */

            cell = new PdfPCell(new Phrase("A", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("B", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("C", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("D", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("E", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("F", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("G", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Expiry Date", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("H", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(6);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            Iterator iterator = reportItems.keys();
            while(iterator.hasNext()) {
                String key = (String)iterator.next();
                JSONObject obj = reportItems.getJSONObject(key);

                cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.optString("unit"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("balance") ? obj.get("balance").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("quantityReceived") ? obj.get("quantityReceived").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("quantityDispensed") ? obj.get("quantityDispensed").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("losses") ? obj.get("losses").toString() :"0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("adjustments") ? obj.get("adjustments").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("negativeAdjustments") ? obj.get("adjustments").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("closingBalance") ? obj.get("closingBalance").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("shortDated") ? obj.get("shortDated").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("expiryDate") ? obj.get("expiryDate").toString() : "", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("outOfStock") ? obj.get("outOfStock").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                Integer reorder = 0;
                Integer min = obj.optInt("min");
                if(obj.has("closingBalance") && obj.has("unit") && min > 0) {
                    Integer bal = obj.optInt("closingBalance");
                    if(bal < min) {
                        reorder = (min - bal) / obj.optInt("unit");
                    }
                }

                cell = new PdfPCell(new Phrase(String.valueOf(reorder), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates FP CDRR Report data.
     *
     * @param month The month for which the data is required
     * @param year The year for which the data is required.
     * @return Returns a JSON Object with the data
     */
    @Override
    @Transactional
    public JSONObject createFPCDRRData(String month, String year) {
        String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";
        String endDate = getLastDay(month, year);
        ServiceType st = referenceDAO.getServiceType("FP/RH");

        List<Drug> drugs = stocksDAO.listDrugs(st.getId());
        List<Object[]> beginningBalance = reportsDAO.drugBalance(startDate, st.getId(), null);
        Integer received = stocksDAO.getTransactionTypeId("Received from");
        List<Object[]> quantitiesReceived = reportsDAO.cumulativeTransactions(new Integer[]{received}, startDate, endDate, st.getId());

        Integer dispensed = stocksDAO.getTransactionTypeId("Dispensed to Patients");
        List<Object[]> quantitiesDispensed = reportsDAO.cumulativeTransactions(new Integer[]{dispensed}, startDate, endDate, st.getId());

        Integer lost = stocksDAO.getTransactionTypeId("Losses(-)");
        Integer expired = stocksDAO.getTransactionTypeId("Expired(-)");
        List<Object[]> losses = reportsDAO.cumulativeTransactions(new Integer[]{lost, expired}, startDate, endDate, st.getId());

        Integer adjustment = stocksDAO.getTransactionTypeId("Ajustment (+)");
        List<Object[]> adjustments = reportsDAO.cumulativeTransactions(new Integer[]{adjustment}, startDate, endDate, st.getId());

        Integer negativeAdjustment = stocksDAO.getTransactionTypeId("ajustment (-)");
        List<Object[]> negativeAdjustments = reportsDAO.cumulativeTransactions(new Integer[]{negativeAdjustment}, startDate, endDate, st.getId());

        List<Object[]> closingBalance = reportsDAO.drugBalance(endDate, st.getId(), null);

        /**
         *  Creates the base JSON Object
         */
        JSONObject reportItems = new JSONObject();


        int i = drugs.size() - 1;
        while(i >= 0) {
            String id = null;
            JSONObject object = new JSONObject();
            reportItems.put(String.valueOf(drugs.get(i).getId()), object);
            if(beginningBalance.size() > i) {
                Object[] bal = beginningBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("balance", String.valueOf(bal[1]));
                reportItems.put(id, obj);
            }

            if(closingBalance.size() > i) {
                Object[] bal = closingBalance.get(i);
                id = String.valueOf(bal[0]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("closingBalance", String.valueOf(bal[1]));
                reportItems.put(id, obj);
            }

            if(quantitiesReceived.size() > i) {
                Object[] qtyRec = quantitiesReceived.get(i);
                id = String.valueOf(qtyRec[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityReceived", qtyRec[0]);
                reportItems.put(id, obj);
            }

            if(quantitiesDispensed.size() > i) {
                Object[] qtyDis = quantitiesDispensed.get(i);
                id = String.valueOf(qtyDis[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("quantityDispensed", qtyDis[0]);
                if(obj.has("unit")) {
                    Integer packSize = obj.getInt("unit");
                    Integer packs = ((BigDecimal)qtyDis[0]).intValue() / packSize;
                    obj.put("dispensedPacks", packs);
                } else {
                    obj.put("dispensedPacks", 0);
                }
                reportItems.put(id, obj);
            }

            if(losses.size() > i) {
                Object[] loss = losses.get(i);
                id = String.valueOf(loss[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("losses", loss[0]);
                reportItems.put(id, obj);

            }

            if(adjustments.size() > i) {
                Object[] adj = adjustments.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("adjustments", adj[0]);
                reportItems.put(id, obj);
            }

            if(negativeAdjustments.size() > i) {
                Object[] adj = negativeAdjustments.get(i);
                id = String.valueOf(adj[1]);
                JSONObject obj = null;
                if (reportItems.has(id)) {
                    obj = reportItems.getJSONObject(id);
                } else {
                    obj = new JSONObject();
                }
                obj.put("negativeAdjustments", adj[0]);
                reportItems.put(id, obj);
            }
            i--;
        }

        Iterator iterator = drugs.iterator();
        while(iterator.hasNext()) {
            Drug d = (Drug)iterator.next();
            JSONObject obj = null;
            if (reportItems.has(String.valueOf(d.getId()))) {
                obj = reportItems.getJSONObject(String.valueOf(d.getId()));
                obj.put("name", d.getName());
                obj.put("dhisid", d.getDhisId());
                obj.put("unit", d.getPackSize());
                obj.put("min", d.getReorderPoint());
                reportItems.put(String.valueOf(d.getId()), obj);
            }

        }
        return reportItems;
    }

    @Override
    @Transactional
    public File createFPCDRRReport(String month, String year) {

        try {
            String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";
            String endDate = getLastDay(month, year);

            JSONObject reportItems = createFPCDRRData(month, year);

            String fileName = "FP-Facility-CDRR-StandAlone-Report.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{5, 1, 2, 2, 2, 2, 2, 2, 2});

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setSize(10);
            headerStyle.setColor(0, 0, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph("MINISTRY OF HEALTH", headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("SDP CONTRACEPTIVES CONSUMPTION", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph dt = new Paragraph("DATA REPORT AND REQUEST FORM", headerStyle);
            dt.setAlignment(Element.ALIGN_CENTER);
            dt.setSpacingAfter(5);
            header.add(dt);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(6);
            document.add(header);

            /**
             *  Top Content
             */
            PdfPTable topContent = new PdfPTable(3);

            PdfPCell cell = new PdfPCell(new Phrase("Province :"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("County :"));
            cell.setPadding(4);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("District : " + getFacilityDistrict()));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Full SDP name"));
            cell.setPadding(4);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("MFL No : " + getFacilityCode() ));
            cell.setPadding(4);
            topContent.addCell(cell);



            cell = new PdfPCell(new Phrase("Facility Type :"));
            cell.setPadding(4);
            cell.setColspan(3);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Agency :"));
            cell.setPadding(4);
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);


            cell = new PdfPCell(new Phrase("Reporting Month :"));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Beginning :" + getDateFormat(startDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            cell = new PdfPCell(new Phrase("Ending :" + getDateFormat(endDate)));
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            topContent.addCell(cell);

            topContent.setSpacingAfter(5);
            document.add(topContent);


            /**
             *  Body Content
             */

            headerStyle = new Font();
            headerStyle.setSize(7);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            BaseColor bg = new BaseColor(192,192,192);

            cell = new PdfPCell(new Phrase("Contraceptive", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBackgroundColor(bg);
            table.addCell(cell);


            cell = new PdfPCell(new Phrase("Beginning balance", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Received this month", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Dispensed", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Losses", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adjustments", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Ending Balance", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Quantity Requested", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Positive", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Negative", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(6);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);

            Iterator iterator = reportItems.keys();
            while(iterator.hasNext()) {
                String key = (String)iterator.next();
                JSONObject obj = reportItems.getJSONObject(key);

                cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("balance") ? obj.get("balance").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("quantityReceived") ? obj.get("quantityReceived").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("quantityDispensed") ? obj.get("quantityDispensed").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("losses") ? obj.get("losses").toString() :"0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("adjustments") ? obj.get("adjustments").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("negativeAdjustments") ? obj.get("adjustments").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(obj.has("closingBalance") ? obj.get("closingBalance").toString() : "0", bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                Integer reorder = 0;
                Integer min = obj.optInt("min");
                if(obj.has("closingBalance") && obj.has("unit") && min > 0) {
                    Integer bal = obj.optInt("closingBalance");
                    if(bal < min) {
                        reorder = (min - bal) / obj.optInt("unit");
                    }
                }

                cell = new PdfPCell(new Phrase(String.valueOf(reorder), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase("SERVICE STATISTICS (Indicate only the number of Clients issued with Contraceptives", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(5);
            cell.setBackgroundColor(bg);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("New Clients", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Natural FP Counselling", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            /**
             *
             */
            cell = new PdfPCell(new Phrase("", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("New Clients", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Revisits", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Change of method", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);


            cell = new PdfPCell(new Phrase("Revisits", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Natural FP referrals", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("From", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("To", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);


            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createCumNumberOfPatientsReport(Integer year, Integer month, Integer serviceTypeId) {
        try {
            Integer grandTotal = 0;
            String serviceName = referenceDAO.getServiceName(serviceTypeId);
            List<ServiceType> subServices = null;
            String lastDate = getLastDay(month.toString(), year.toString());
            if(serviceName.equalsIgnoreCase("ART")) {
                subServices = referenceDAO.getSubServices(serviceTypeId);
            }
            if(subServices == null) {
                subServices = new ArrayList<ServiceType>();
                subServices.add(referenceDAO.getServiceType(serviceTypeId));
            }
            Vector reportData = new Vector();

            Set<Integer> statusIds = new HashSet<Integer>();
            for(int i = 0; i < subServices.size(); i++) {
                Map<Integer, JSONObject> data = reportsDAO.createCumNumberOfPatientsReport(lastDate, subServices.get(i).getId());
                reportData.add(data);
                grandTotal = grandTotal + reportsDAO.getGrandTotal();
                statusIds.addAll(data.keySet());
            }
            Iterator setIterator = statusIds.iterator();
            while(setIterator.hasNext()) {
                System.out.println("Found at  " + setIterator.next());
            }

            String fileName = "cumulative-number-of-patients.pdf";
            File file = new File(fileName);
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            int genSerMatrix = subServices.size() * ReportsDAOImpl.GENDER_AGE_FILTER.values().length; //Calculate the number of columns required for the services
            PdfPTable table = new PdfPTable(genSerMatrix + 3);
            table.setWidthPercentage(100);
            //table.setWidths(new int[]{2, 1, 1, 1, 1});

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("Cumulative Number of " + serviceName + " Patient by Current Status", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph duration = new Paragraph( full_months[month] + ", " + year, headerStyle);
            duration.setAlignment(Element.ALIGN_CENTER);
            duration.setSpacingAfter(5);
            header.add(duration);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(10);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);
            /*
                First Row
             */
            PdfPCell cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adult", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setColspan(genSerMatrix / 2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Children", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setColspan(genSerMatrix / 2);
            table.addCell(cell);
            //End First Row

            /*
                Second Row
             */
            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            cell.setColspan(3);
            table.addCell(cell);

            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                cell = new PdfPCell(new Phrase(gaf.name(), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.TOP);
                cell.setColspan(subServices.size());
                table.addCell(cell);
            }
            //End Second Row

            /*
                Third Row
             */
            cell = new PdfPCell(new Phrase("Current Status", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("N", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("%", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                for(int i = 0; i < subServices.size(); i++) {
                    cell = new PdfPCell(new Phrase(subServices.get(i).getName(), headerStyle));
                    cell.setBorder(Rectangle.TOP);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }
            //End Third Row

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            setIterator = statusIds.iterator();

            int[] totals = new int[genSerMatrix];
            while(setIterator.hasNext()) {
                Integer total = 0;
                Integer id = (Integer)setIterator.next();
                boolean nameAdded = false;
                for(int j = 0; j < reportData.size(); j++) {
                    Map<Integer, JSONObject> dt = (Map<Integer, JSONObject>)reportData.get(j);
                    if(dt.get(id) != null) {
                        JSONObject obj = dt.get(id);
                        if(obj.has("total")) {
                            Integer t = obj.optInt("total");
                            total = total + t;
                        }
                        if (obj.has("name") && !nameAdded) {
                            cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            //cell.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell);
                            nameAdded = true;
                        }
                    }
                }
                if(!nameAdded) {
                    cell = new PdfPCell(new Phrase("No name", bodyStyle));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    //cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }



                cell = new PdfPCell(new Phrase(String.valueOf(total), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                float per = (((float)total / (float)grandTotal) * 100);
                cell = new PdfPCell(new Phrase(roundValue(per), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                int t = 0;
                for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                    for(int j = 0; j < reportData.size(); j++) {
                        Map<Integer, JSONObject> dt = (Map<Integer, JSONObject>)reportData.get(j);
                        JSONObject obj = null;
                        JSONArray unitCounts;
                        if(dt.get(id) != null) {
                            obj = dt.get(id);
                        }
                        if(obj == null || !obj.has("data")) {
                            unitCounts = new JSONArray();
                        } else {
                            unitCounts = obj.getJSONArray("data");
                        }
                        if(gaf.getIndex() < unitCounts.length()) {
                            int count = unitCounts.isNull(gaf.getIndex()) ? 0 : unitCounts.optInt(gaf.getIndex());
                            totals[t] = totals[t] + count;
                            cell = new PdfPCell(new Phrase(count + "", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            //cell.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell);
                        } else {
                            cell = new PdfPCell(new Phrase("0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            //cell.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell);
                        }
                        t++;
                    }
                }
            }

            bodyStyle.setStyle("bold");
            cell = new PdfPCell(new Phrase("Total", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(grandTotal + "", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("100", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            for(int i = 0; i < totals.length; i++) {
                cell = new PdfPCell(new Phrase("" + totals[i], bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(Rectangle.TOP);
                //cell.setBorderWidthTop(2);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createActivePatientsByRegimenReport(Integer month, Integer year, Integer serviceTypeId) {
        try {
            String serviceName = referenceDAO.getServiceName(serviceTypeId);
            List<ServiceType> subServices = null;
            if(serviceName.equalsIgnoreCase("ART")) {
                subServices = referenceDAO.getSubServices(serviceTypeId);
            }
            if(subServices == null) {
                subServices = new ArrayList<ServiceType>();
                subServices.add(referenceDAO.getServiceType(serviceTypeId));
            }
            PatientStatus ps = referenceDAO.getPatientStatus("Active");
            String lastDate = getLastDay(month.toString(), year.toString());
            Vector reportData = new Vector();
            Set<Integer> regimenIds = new HashSet<Integer>();
            Vector<Integer> statusIds = new Vector<Integer>();
            for(int i = 0; i < subServices.size(); i++) {
                Map<Integer, JSONObject> data = reportsDAO.createActivePatientsByRegimenReport(lastDate, subServices.get(i).getId(), ps.getId());
                reportData.add(data);
                //statusIds.addAll(data.keySet());
                regimenIds.addAll(data.keySet());
            }
            Iterator setIterator = regimenIds.iterator();
            while(setIterator.hasNext()) {
                System.out.println("Found at  " + (Integer)setIterator.next());
            }

            String fileName = "active-patients-by-regimen.pdf";
            File file = new File(fileName);
            int genSerMatrix = subServices.size() * ReportsDAOImpl.GENDER_AGE_FILTER.values().length; //Calculate the number of columns required for the services
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph(serviceName + " Active patients by Regimen", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph duration = new Paragraph( full_months[month] + ", " + year, headerStyle);
            duration.setAlignment(Element.ALIGN_CENTER);
            header.add(duration);

            Paragraph serviceType = new Paragraph( "Service Type : " + referenceDAO.getServiceName(serviceTypeId), headerStyle);
            serviceType.setAlignment(Element.ALIGN_CENTER);
            serviceType.setSpacingAfter(5);
            header.add(serviceType);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            PdfPTable table = new PdfPTable(genSerMatrix + 1);
            table.setWidthPercentage(100);


            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            PdfPCell cell = new PdfPCell(new Phrase("Current Regimen", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);

            table.addCell(cell);

            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                for(int i = 0; i < subServices.size(); i++) {
                    cell = new PdfPCell(new Phrase(gaf.name() + " " + subServices.get(i).getName(), headerStyle));
                    cell.setBorder(Rectangle.TOP);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            setIterator = regimenIds.iterator();
            while(setIterator.hasNext()) {
                Integer id = (Integer)setIterator.next();

                boolean nameAdded = false;
                for(int j = 0; j < reportData.size(); j++) {
                    Map<Integer, JSONObject> dt = (Map<Integer, JSONObject>)reportData.get(j);
                    if(dt.get(id) != null) {
                        JSONObject obj = dt.get(id);
                        if (obj.has("name")) {
                            cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            //cell.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell);
                            nameAdded = true;
                            break;
                        }
                    }
                }
                if(!nameAdded) {
                    cell = new PdfPCell(new Phrase("No name", bodyStyle));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    //cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }


                for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                    for(int j = 0; j < reportData.size(); j++) {
                        Map<Integer, JSONObject> dt = (Map<Integer, JSONObject>)reportData.get(j);
                        JSONObject obj = null;
                        if(dt.get(id) != null) {
                            obj = dt.get(id);
                        }
                        JSONArray unitCounts = null;
                        if(obj == null || !obj.has("data")) {
                            unitCounts = new JSONArray();
                        } else {
                            unitCounts = obj.getJSONArray("data");
                        }
                        if(gaf.getIndex() < unitCounts.length()) {
                            cell = new PdfPCell(new Phrase(unitCounts.isNull(gaf.getIndex()) ? "0" : unitCounts.get(gaf.getIndex()).toString(), bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            //cell.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell);
                        } else {
                            cell = new PdfPCell(new Phrase("0", bodyStyle));
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            //cell.setBorder(Rectangle.NO_BORDER);
                            table.addCell(cell);
                        }
                    }
                }
            }
            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    @Transactional
    public File createEnrolledPatientsReport(String startDate, String endDate, Integer serviceId) {
        try {
            String serviceName = referenceDAO.getServiceName(serviceId);
            List<ServiceType> subServices = null;
            if(serviceName.equalsIgnoreCase("ART")) {
                subServices = referenceDAO.getSubServices(serviceId);
            }
            if(subServices == null) {
                subServices = new ArrayList<ServiceType>();
                subServices.add(referenceDAO.getServiceType(serviceId));
            }
            Vector reportData = new Vector();
            Set<Integer> regimenIds = new HashSet<Integer>();
            for(int i = 0; i < subServices.size(); i++) {
                JSONObject enrolled = reportsDAO.getPatientsEnrolled(startDate, endDate, subServices.get(i).getId());
                reportData.add(enrolled);
            }

            String fileName = "enrolled-patients.pdf";
            File file = new File(fileName);
            int genSerMatrix = subServices.size() * ReportsDAOImpl.GENDER_AGE_FILTER.values().length; //Calculate the number of columns required for the services
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("patients enrolled in the period from : " + getDateFormat(startDate) + " To : " + getDateFormat(endDate) + " for " + serviceName, headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            reportType.setSpacingAfter(5);
            header.add(reportType);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            PdfPTable table = new PdfPTable(genSerMatrix + 1);
            table.setWidthPercentage(100);


            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(8);


            /*
                First Row
             */
            PdfPCell cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adult", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.TOP);

            cell.setColspan(genSerMatrix / 2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Children", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM | Rectangle.TOP | Rectangle.RIGHT);
            cell.setColspan(genSerMatrix / 2);
            table.addCell(cell);
            //End First Row

            /*
                Second Row
             */
            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                cell = new PdfPCell(new Phrase(gaf.name(), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
                cell.setColspan(subServices.size());
                table.addCell(cell);
            }
            //End Second Row

            /*
                Third Row
             */
            cell = new PdfPCell(new Phrase("Description", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
            table.addCell(cell);


            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                for(int i = 0; i < subServices.size(); i++) {
                    cell = new PdfPCell(new Phrase(subServices.get(i).getName(), headerStyle));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    if(i == (subServices.size() - 1)) {
                        cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
                    } else {
                        cell.setBorder(Rectangle.TOP);
                    }
                    table.addCell(cell);
                }
            }

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            //cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            Integer total = 0;

            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                for(int j = 0; j < reportData.size(); j++) {
                    JSONObject obj = (JSONObject)reportData.get(j);

                    JSONArray unitCounts = null;
                    if(obj == null || !obj.has("data")) {
                        unitCounts = new JSONArray();
                    } else {
                        unitCounts = obj.getJSONArray("data");
                    }
                    if(gaf.getIndex() < unitCounts.length()) {
                        cell = new PdfPCell(new Phrase(unitCounts.isNull(gaf.getIndex()) ? "0" : unitCounts.get(gaf.getIndex()).toString(), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                        total = total + unitCounts.optInt(gaf.getIndex());
                    } else {
                        cell = new PdfPCell(new Phrase("0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    }
                }
            }

            bodyStyle.setStyle("bold");
            cell = new PdfPCell(new Phrase("Total", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("" + total, bodyStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(genSerMatrix);
            table.addCell(cell);

            document.add(table);

            table = new PdfPTable(11);
            table.setWidthPercentage(100);

            Integer[] arr = new Integer[subServices.size()];
            for(int i = 0; i < subServices.size(); i++)
                arr[i] = subServices.get(i).getId();

            Map<Integer, JSONObject> data = reportsDAO.getEnrolledRegimen(startDate, endDate, arr);

            /*
                Top Row
             */
            cell = new PdfPCell(new Phrase(""));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adult", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Children", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setColspan(4);
            table.addCell(cell);

            /*
                Second Row
             */
            cell = new PdfPCell(new Phrase(""));
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Total", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Male", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Female", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Male", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Female", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Regimen", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            for(int i = 0; i < 10; i++) {
                if(i%2 == 0) {
                    cell = new PdfPCell(new Phrase("N", headerStyle));
                    //cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                } else {
                    cell = new PdfPCell(new Phrase("%", headerStyle));
                    //cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }
            Integer grandTotal = reportsDAO.getGrandTotal();

            Iterator iterator = data.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry pairs = (Map.Entry) iterator.next();
                JSONObject obj = data.get(pairs.getKey());

                JSONArray unitCounts = null;
                if(obj.has("data")) {
                    unitCounts = obj.getJSONArray("data");
                } else {
                    unitCounts = new JSONArray();
                }
                if(obj.has("name")) {
                    cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                } else {
                    cell = new PdfPCell(new Phrase("No name", bodyStyle));
                }
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("" + obj.optInt("total"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                float per = (((float)obj.optInt("total") / (float)grandTotal) * 100);
                cell = new PdfPCell(new Phrase(roundValue(per), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
                for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                    if(gaf.getIndex() < unitCounts.length()) {
                        cell = new PdfPCell(new Phrase(unitCounts.isNull(gaf.getIndex()) ? "0" : unitCounts.get(gaf.getIndex()).toString(), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    } else {
                        cell = new PdfPCell(new Phrase("0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    }
                    Integer count = unitCounts.isNull(gaf.getIndex()) ? 0 : unitCounts.optInt(gaf.getIndex());
                    if(count != 0) {
                        float p = ((float)count / (float)grandTotal) * 100;
                        cell = new PdfPCell(new Phrase(roundValue(p), bodyStyle));
                    } else {
                        cell = new PdfPCell(new Phrase("0" , bodyStyle));
                    }
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }
            }
            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createStartedPatientsReport(String startDate, String endDate, Integer serviceId) {
        try {
            String serviceName = referenceDAO.getServiceName(serviceId);
            List<ServiceType> subServices = null;
            if(serviceName.equalsIgnoreCase("ART")) {
                subServices = referenceDAO.getSubServices(serviceId);
            }
            if(subServices == null) {
                subServices = new ArrayList<ServiceType>();
                subServices.add(referenceDAO.getServiceType(serviceId));
            }
            Vector reportData = new Vector();
            for(int i = 0; i < subServices.size(); i++) {
                JSONObject enrolled = reportsDAO.getPatientsStarted(startDate, endDate, subServices.get(i).getId());
                reportData.add(enrolled);
            }

            String fileName = "started-patients.pdf";
            File file = new File(fileName);
            int genSerMatrix = subServices.size() * ReportsDAOImpl.GENDER_AGE_FILTER.values().length; //Calculate the number of columns required for the services
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("Patients Started in the period from : " + getDateFormat(startDate) + " To : " + getDateFormat(endDate) + " for " + serviceName, headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            reportType.setSpacingAfter(5);
            header.add(reportType);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            PdfPTable table = new PdfPTable(genSerMatrix + 1);
            table.setWidthPercentage(100);


            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            /*
                First Row
             */
            PdfPCell cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adult", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setColspan(genSerMatrix / 2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Children", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setColspan(genSerMatrix / 2);
            table.addCell(cell);
            //End First Row

            /*
                Second Row
             */
            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                cell = new PdfPCell(new Phrase(gaf.name(), headerStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.TOP);
                cell.setColspan(subServices.size());
                table.addCell(cell);
            }
            //End Second Row

            /*
                Third Row
             */
            cell = new PdfPCell(new Phrase("Description", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);


            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                for(int i = 0; i < subServices.size(); i++) {
                    cell = new PdfPCell(new Phrase(subServices.get(i).getName(), headerStyle));
                    cell.setBorder(Rectangle.TOP);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);

            cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.TOP);
            table.addCell(cell);

            Integer total = 0;

            for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                for(int j = 0; j < reportData.size(); j++) {
                    JSONObject obj = (JSONObject)reportData.get(j);

                    JSONArray unitCounts = null;
                    if(obj == null || !obj.has("data")) {
                        unitCounts = new JSONArray();
                    } else {
                        unitCounts = obj.getJSONArray("data");
                    }
                    if(gaf.getIndex() < unitCounts.length()) {
                        cell = new PdfPCell(new Phrase(unitCounts.isNull(gaf.getIndex()) ? "0" : unitCounts.get(gaf.getIndex()).toString(), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                        total = total + unitCounts.optInt(gaf.getIndex());
                    } else {
                        cell = new PdfPCell(new Phrase("0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    }
                }
            }

            bodyStyle.setStyle("bold");
            cell = new PdfPCell(new Phrase("Total", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("" + total, bodyStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(genSerMatrix);
            table.addCell(cell);

            document.add(table);

            table = new PdfPTable(11);
            table.setWidthPercentage(100);

            Integer[] arr = new Integer[subServices.size()];
            for(int i = 0; i < subServices.size(); i++)
                arr[i] = subServices.get(i).getId();
            Map<Integer, JSONObject> data = reportsDAO.getStartedRegimen(startDate, endDate, arr);

            /*
                Top Row
             */
            cell = new PdfPCell(new Phrase(""));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Adult", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setColspan(4);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Children", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            cell.setColspan(4);
            table.addCell(cell);

            /*
                Second Row
             */
            cell = new PdfPCell(new Phrase(""));
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Total", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Male", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Female", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Male", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Female", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Regimen", headerStyle));
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            for(int i = 0; i < 10; i++) {
                if(i%2 == 0) {
                    cell = new PdfPCell(new Phrase("N", headerStyle));
                    //cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                } else {
                    cell = new PdfPCell(new Phrase("%", headerStyle));
                    //cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }

            Integer grandTotal = reportsDAO.getGrandTotal();
            Iterator iterator = data.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry pairs = (Map.Entry) iterator.next();
                JSONObject obj = data.get(pairs.getKey());

                JSONArray unitCounts = null;
                if(obj.has("data")) {
                    unitCounts = obj.getJSONArray("data");
                } else {
                    unitCounts = new JSONArray();
                }
                if(obj.has("name")) {
                    cell = new PdfPCell(new Phrase(obj.getString("name"), bodyStyle));
                } else {
                    cell = new PdfPCell(new Phrase("No name", bodyStyle));
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("" + obj.optInt("total"), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                float per = (((float)obj.optInt("total") / (float)grandTotal) * 100);
                cell = new PdfPCell(new Phrase(roundValue(per), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
                for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                    if(gaf.getIndex() < unitCounts.length()) {
                        cell = new PdfPCell(new Phrase(unitCounts.isNull(gaf.getIndex()) ? "0" : unitCounts.get(gaf.getIndex()).toString(), bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    } else {
                        cell = new PdfPCell(new Phrase("0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    }
                    Integer count = unitCounts.isNull(gaf.getIndex()) ? 0 : unitCounts.optInt(gaf.getIndex());
                    if(count != 0) {
                        float p = ((float)count / (float)grandTotal) * 100;
                        cell = new PdfPCell(new Phrase( roundValue(p), bodyStyle));
                    } else {
                        cell = new PdfPCell(new Phrase("0" , bodyStyle));
                    }
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }
            }
            document.add(table);

            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public File createBMIReport(Integer serviceId, boolean adult) {
        try {
            String serviceName = referenceDAO.getServiceName(serviceId);
            List<ServiceType> subServices = null;
            if(serviceName.equalsIgnoreCase("ART")) {
                subServices = referenceDAO.getSubServices(serviceId);
            }
            if(subServices == null) {
                subServices = new ArrayList<ServiceType>();
                subServices.add(referenceDAO.getServiceType(serviceId));
            }
            Vector reportData = new Vector();

            for(int i = 0; i < subServices.size(); i++) {
                JSONObject data = reportsDAO.getBMIReport(subServices.get(i).getId(), adult);
                reportData.add(data);
            }


            String fileName = "patient-bmi.pdf";
            File file = new File(fileName);
            int genSerMatrix = subServices.size() * ReportsDAOImpl.GENDER_AGE_FILTER.values().length; //Calculate the number of columns required for the services
            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph(" BMI Summary for active patients", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph serviceType = new Paragraph( "Service Type : " + serviceName, headerStyle);
            serviceType.setAlignment(Element.ALIGN_CENTER);
            serviceType.setSpacingAfter(5);
            header.add(serviceType);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            PdfPTable table = new PdfPTable(13);
            table.setWidthPercentage(100);


            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            PdfPCell cell = new PdfPCell(new Phrase("", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            headerStyle.setColor(BaseColor.RED);

            for(ReportsDAOImpl.BMI bmi : ReportsDAOImpl.BMI.values()) {
                cell = new PdfPCell(new Phrase(bmi.name(), headerStyle));
                cell.setBorder(Rectangle.BOTTOM);
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            headerStyle.setColor(BaseColor.BLUE);
            cell = new PdfPCell(new Phrase("Type of Service", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            for (int i = 0; i < 12; i++) {
                if(i % 2 == 0) {
                    cell = new PdfPCell(new Phrase("Female", headerStyle));
                } else {
                    cell = new PdfPCell(new Phrase("Male", headerStyle));
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);
            }

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);
            int[] totals = new int[12];

            for(int i = 0; i < subServices.size(); i++) {
                cell = new PdfPCell(new Phrase(subServices.get(i).getName(), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                JSONObject obj = (JSONObject)reportData.get(i);
                int j = 0;
                for(ReportsDAOImpl.BMI bmi : ReportsDAOImpl.BMI.values()) {
                    JSONArray arr;
                    if(obj.has(bmi.name())) {
                        arr = obj.getJSONArray(bmi.name());

                        for(ReportsDAOImpl.GENDER_AGE_FILTER gaf : ReportsDAOImpl.GENDER_AGE_FILTER.values()) {
                            if(gaf.getAdult() == adult) {
                                int count = arr.optInt(gaf.getIndex());
                                totals[j] = totals[j] + count;
                                j++;
                                cell = new PdfPCell(new Phrase(arr.optString(gaf.getIndex()), bodyStyle));
                                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                                //cell.setBorder(Rectangle.NO_BORDER);
                                table.addCell(cell);
                            }
                        }
                    } else {
                        cell = new PdfPCell(new Phrase("0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("0", bodyStyle));
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        //cell.setBorder(Rectangle.NO_BORDER);
                        table.addCell(cell);
                    }
                }
            }

            bodyStyle.setStyle("bold");
            cell = new PdfPCell(new Phrase("Grand total", bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            //cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            for(int i = 0; i < totals.length; i++) {
                cell = new PdfPCell(new Phrase("" + totals[i], bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);
            }

            document.add(table);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    @Transactional
    public File createEnrollmentGraph(Integer year) {
        File image = null;
        try {
            Integer[] data = reportsDAO.getEnrollmentByMonth(year);

            String fileName = "enrollment-graph-by-month.pdf";
            File file = new File(fileName);

            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph(  " Patient enrollment graph", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph duration = new Paragraph( "Year : " + year, headerStyle);
            duration.setAlignment(Element.ALIGN_CENTER);
            header.add(duration);



            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);

            document.add(header);

            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for(int i = 0; i < data.length; i++) {
                dataset.setValue(data[i], "Number of patients", months[i]);
            }
            JFreeChart chart = ChartFactory.createLineChart("Patients Enrollment", "Months", "Number of patients", dataset);

            final CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Color.lightGray);
            plot.setDomainGridlinePaint(Color.white);
            plot.setRangeGridlinePaint(Color.white);

            image = new File("reportImage.png");
            ChartUtilities.saveChartAsPNG(image,chart, 600, 300);
            Image chartImage = Image.getInstance(image.getName());
            document.add(chartImage);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(image != null)
                try {
                    image.delete();
                } catch (Exception e){}
        }

        return null;
    }

    @Override
    @Transactional
    public File createEWI2Report(String startDate, String endDate) { // Replaces EWI 2
        try {
            ServiceType st = referenceDAO.getServiceType("ART");
            List<Object[]> personList = reportsDAO.execute("SELECT ps.surname, ps.first_name, ps.other_names FROM person ps JOIN patient p ON ps.id = p.person_id JOIN patient_service_type pst ON p.id = pst.patient_id JOIN visit v ON p.id = v.patient_id WHERE (date(pst.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') AND pst.service_type_id = " + st.getId() + " AND v.id IN (SELECT min(v.id) FROM visit v JOIN regimen r ON v.regimen_id = r.id WHERE r.line = 1 group by patient_id) AND v.id IN (SELECT max(v.id) FROM visit v JOIN regimen r ON v.regimen_id = r.id WHERE r.line = 1 group by patient_id)");
            Integer denominator = reportsDAO.getDenominator("SELECT count(pst.service_type_id), st.name, st.id FROM patient_service_type pst JOIN service_type st ON pst.service_type_id = st.id  WHERE  (date(pst.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') AND st.id = " + st.getId() + " group by service_type_id ");
            String fileName = "ewi-2.pdf";
            File file = new File(fileName);

            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("HIV Drugs Resistance Early warning Indicators", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph duration = new Paragraph( "Period : from " + getDateFormat(startDate) + " To : " + getDateFormat(endDate) , headerStyle);
            duration.setAlignment(Element.ALIGN_CENTER);
            header.add(duration);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            header.setSpacingAfter(5);
            document.add(header);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            headerStyle.setStyle("bold");
            PdfPCell cell = new PdfPCell(new Phrase("Patients initiating ART first line and still in first line 12 months later", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Patients initiating ART", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Percentage", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);

            cell = new PdfPCell(new Phrase("" + personList.size(), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("" + denominator, bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            float per = (((float)personList.size() / (float)denominator) * 100);

            cell = new PdfPCell(new Phrase(roundValue(per), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            document.add(table);

            header = new Paragraph();
            header.setExtraParagraphSpace(4);
            header.setSpacingAfter(5);
            document.add(header);

            PdfPTable bodyTable = new PdfPTable(3);
            bodyTable.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Other Names", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            Iterator iterator = personList.iterator();
            while(iterator.hasNext()) {
                Object[] p = (Object[])iterator.next();

                cell = new PdfPCell(new Phrase(String.valueOf(p[0]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(p[1]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(p[2]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);
            }
            document.add(bodyTable);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    @Transactional
    public File createEWI3BReport(String startDate, String endDate) {
        try {
            ServiceType st = referenceDAO.getServiceType("ART");
            PatientStatus ps = referenceDAO.getPatientStatus("Lost to follow-up");
            List<Object[]> personList = reportsDAO.execute("SELECT ps.surname, ps.first_name, ps.other_names, v.patient_id, v.regimen_id as `reg_1`, v2.regimen_id as `reg2` FROM person ps JOIN patient p ON ps.id = p.person_id JOIN visit v ON p.id = v.patient_id INNER JOIN visit v2 on v.patient_id = v2.patient_id WHERE (date(p.service_start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') AND p.service_type_id = " + st.getId() + " AND v.regimen_id != v2.regimen_id and v2.id IN (SELECT min(id) FROM visit WHERE id > v.id AND patient_id = v.patient_id) group by patient_id");
            Integer denominator = reportsDAO.getDenominator("SELECT count(p.service_type_id), st.name, st.id FROM patient p JOIN service_type st ON p.service_type_id = st.id JOIN person ps ON p.person_id = ps.id WHERE  (date(p.service_start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') AND st.id = " + st.getId() + " group by service_type_id ");
            String fileName = "ewi-3-B.pdf";
            File file = new File(fileName);

            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("HIV Drugs Resistance Early warning Indicators", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph duration = new Paragraph( "Period : from " + getDateFormat(startDate) + " To : " + getDateFormat(endDate) , headerStyle);
            duration.setAlignment(Element.ALIGN_CENTER);
            header.add(duration);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            header.setSpacingAfter(5);
            document.add(header);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(8);

            headerStyle.setStyle("bold");
            PdfPCell cell = new PdfPCell(new Phrase("Patients initiating ART first line and Changed regimen", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Patients initiating ART", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Percentage", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);

            cell = new PdfPCell(new Phrase("" + personList.size(), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("" + denominator, bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            float per = (((float)personList.size() / (float)denominator) * 100);

            cell = new PdfPCell(new Phrase(roundValue(per), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            document.add(table);

            header = new Paragraph();
            header.setExtraParagraphSpace(4);
            header.setSpacingAfter(5);
            document.add(header);

            PdfPTable bodyTable = new PdfPTable(3);
            bodyTable.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Other Names", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            Iterator iterator = personList.iterator();
            while(iterator.hasNext()) {
                Object[] p = (Object[])iterator.next();

                cell = new PdfPCell(new Phrase(String.valueOf(p[0]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(p[1]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(p[2]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);
            }
            document.add(bodyTable);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    @Override
    @Transactional
    public File createEWI1Report(String startDate, String endDate) {  //Becomes EWI1
        try {
            ServiceType st = referenceDAO.getServiceType("ART");
            List<Object[]> personList = reportsDAO.execute("SELECT ps.surname, ps.first_name, ps.other_names, v.patient_id, v2.next_appointment_date, v.start_date, DATEDIFF(date(v.start_date) , date(v2.next_appointment_date)) as diff FROM person ps JOIN patient p ON ps.id = p.person_id JOIN patient_service_type pst ON p.id = pst.patient_id JOIN visit v ON p.id = v.patient_id INNER JOIN visit v2 on v.patient_id = v2.patient_id WHERE (date(v2.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') AND pst.service_type_id = " + st.getId() + " and v.id > v2.id AND v2.id IN (SELECT min(id) FROM visit WHERE (date(visit.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') group by patient_id) HAVING diff >= -4");
            List den = reportsDAO.execute("SELECT p.patient_id FROM patient_service_type p JOIN visit v ON p.patient_id = v.patient_id WHERE p.service_type_id = " + st.getId() + " AND (date(v.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "')  group by p.patient_id");


            Integer denominator = den.size();
            String fileName = "ewi-1.pdf";
            File file = new File(fileName);

            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("HIV Drugs Resistance Early warning Indicators", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph duration = new Paragraph( "Period : from " + getDateFormat(startDate) + " To : " + getDateFormat(endDate) , headerStyle);
            duration.setAlignment(Element.ALIGN_CENTER);
            header.add(duration);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            header.setSpacingAfter(5);
            document.add(header);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            headerStyle.setStyle("bold");
            PdfPCell cell = new PdfPCell(new Phrase("Number of patients who picked up on time", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Patients who picked ARV drugs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Percentage", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);

            cell = new PdfPCell(new Phrase("" + personList.size(), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("" + denominator, bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            float per = (((float)personList.size() / (float)denominator) * 100);

            cell = new PdfPCell(new Phrase(roundValue(per), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            document.add(table);

            header = new Paragraph();
            header.setExtraParagraphSpace(4);
            header.setSpacingAfter(5);
            document.add(header);

            PdfPTable bodyTable = new PdfPTable(3);
            bodyTable.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Other Names", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            Iterator iterator = personList.iterator();
            while(iterator.hasNext()) {
                Object[] p = (Object[])iterator.next();

                cell = new PdfPCell(new Phrase(String.valueOf(p[0]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(p[1]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(p[2]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);
            }
            document.add(bodyTable);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    @Override
    @Transactional
    public File createEWI4Report(String startDate, String endDate) {
        try {
            ServiceType st = referenceDAO.getServiceType("ART");

            List<Object[]> personList = reportsDAO.execute("SELECT p.surname, p.first_name, p.other_names, p.surname, p.sex, p.date_of_birth FROM (SELECT ps.first_name, ps.other_names, ps.surname, ps.sex, ps.date_of_birth, v.patient_id,v.id as `visitid`, date(v.start_date), t.id, transaction_id, sum(d.combination_type) as comb FROM visit v JOIN `transaction` t ON v.id = t.visit_id JOIN transaction_item ti ON t.id = ti.transaction_id JOIN drug d on ti.drug_id = d.id JOIN patient p ON v.patient_id = p.id JOIN person ps ON ps.id = p.person_id JOIN patient_service_type pst ON p.id = pst.patient_id WHERE pst.service_type_id = " + st.getId() + " AND (date(v.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') group by ti.transaction_id HAVING comb < 3 or comb is null) p GROUP by patient_id");
            List den = reportsDAO.execute("SELECT p.patient_id FROM patient_service_type p JOIN visit v ON p.patient_id = v.patient_id WHERE p.service_type_id = " + st.getId() + " AND (date(v.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "')  group by p.patient_id");
            Integer denominator = den.size();
            String fileName = "ewi-4.pdf";
            File file = new File(fileName);

            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("HIV Drugs Resistance Early warning Indicators", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph subTitle = new Paragraph("Dispensing practices");
            subTitle.setAlignment(Element.ALIGN_CENTER);
            header.add(subTitle);

            Paragraph duration = new Paragraph( "Period : from " + getDateFormat(startDate) + " To : " + getDateFormat(endDate) , headerStyle);
            duration.setAlignment(Element.ALIGN_CENTER);
            header.add(duration);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            header.setSpacingAfter(5);
            document.add(header);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            headerStyle.setStyle("bold");
            PdfPCell cell = new PdfPCell(new Phrase("Dispensing practices", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Patient receiving mono or dual drugs", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Percentage", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);

            cell = new PdfPCell(new Phrase("" + personList.size(), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("" + denominator, bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            float per = (((float)personList.size() / (float)denominator) * 100);

            cell = new PdfPCell(new Phrase(roundValue(per), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            document.add(table);

            header = new Paragraph();
            header.setExtraParagraphSpace(4);
            header.setSpacingAfter(5);
            document.add(header);

            PdfPTable bodyTable = new PdfPTable(3);
            bodyTable.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("Surname", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Phrase("First Name", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Other Names", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);

            Iterator iterator = personList.iterator();
            while(iterator.hasNext()) {
                Object[] p = (Object[])iterator.next();

                cell = new PdfPCell(new Phrase(String.valueOf(p[0]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(p[1]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(p[2]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);
            }
            document.add(bodyTable);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    @Override
    @Transactional
    public File createEWI3Report(String startDate, String endDate) { //Becomes EWI 3
        try {
            ServiceType st = referenceDAO.getServiceType("ART");

            int months = getEWI3(startDate, endDate);
            Integer denominator = 12;
            String fileName = "ewi-3.pdf";
            File file = new File(fileName);

            Document document = new Document(PageSize.A3);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font headerStyle = new Font();
            headerStyle.setStyle("bold");
            headerStyle.setColor(153, 51, 0);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            Paragraph header = new Paragraph();
            Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
            hf.setAlignment(Element.ALIGN_CENTER);
            header.add(hf);

            Paragraph reportType = new Paragraph("HIV Drugs Resistance Early warning Indicators", headerStyle);
            reportType.setAlignment(Element.ALIGN_CENTER);
            header.add(reportType);

            Paragraph duration = new Paragraph( "Period : from " + getDateFormat(startDate) + " To : " + getDateFormat(endDate) , headerStyle);
            duration.setAlignment(Element.ALIGN_CENTER);
            header.add(duration);

            header.setAlignment(Element.ALIGN_CENTER);
            header.setExtraParagraphSpace(4);
            header.setSpacingAfter(5);
            document.add(header);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            headerStyle = new Font();
            headerStyle.setColor(0, 0, 128);
            headerStyle.setSize(8);
            headerStyle.setFamily(BaseFont.TIMES_ROMAN);

            headerStyle.setStyle("bold");
            PdfPCell cell = new PdfPCell(new Phrase("Months with no Stock Out in a year", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("12 months", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Percentage", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            Font bodyStyle = new Font();
            bodyStyle.setSize(8);
            bodyStyle.setFamily(BaseFont.TIMES_ROMAN);

            cell = new PdfPCell(new Phrase("" + (12 - months), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("" + denominator, bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            float per = (((float)(12 - months) / (float)denominator) * 100);

            cell = new PdfPCell(new Phrase(roundValue(per), bodyStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            document.add(table);

            header = new Paragraph();
            header.setExtraParagraphSpace(4);
            header.setSpacingAfter(5);
            document.add(header);

            PdfPTable bodyTable = new PdfPTable(3);
            bodyTable.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("Month", headerStyle));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOTTOM);
            bodyTable.addCell(cell);


            /*Iterator iterator = monthList.iterator();
            while(iterator.hasNext()) {
                Object[] p = (Object[])iterator.next();

                cell = new PdfPCell(new Phrase(String.valueOf(p[0]), bodyStyle));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                //cell.setBorder(Rectangle.NO_BORDER);
                bodyTable.addCell(cell);
            } */
            document.add(bodyTable);
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    @Override
    @Transactional
    public List<Double> getEWI(String startDate, String endDate) throws  Exception{
        ServiceType st = referenceDAO.getServiceType("ART");
        List<Object[]> personList = reportsDAO.execute("SELECT ps.surname, ps.first_name, ps.other_names, v.patient_id, v2.next_appointment_date, v.start_date, DATEDIFF(date(v.start_date) , date(v2.next_appointment_date)) as diff FROM person ps JOIN patient p ON ps.id = p.person_id JOIN patient_service_type pst ON p.id = pst.patient_id JOIN visit v ON p.id = v.patient_id INNER JOIN visit v2 on v.patient_id = v2.patient_id WHERE (date(v2.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') AND pst.service_type_id = " + st.getId() + " and v.id > v2.id AND v2.id IN (SELECT min(id) FROM visit WHERE (date(visit.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') group by patient_id) HAVING diff >= -4");
        List den = reportsDAO.execute("SELECT p.patient_id FROM patient_service_type p JOIN visit v ON p.patient_id = v.patient_id WHERE p.service_type_id = " + st.getId() + " AND (date(v.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "')  group by p.patient_id");
        float ewi1 = 0;
        Integer denominator = den.size();
        if(personList.size() > 0)
            ewi1 = (((float)personList.size() / (float)denominator) * 100);

        personList = reportsDAO.execute("SELECT ps.surname, ps.first_name, ps.other_names FROM person ps JOIN patient p ON ps.id = p.person_id JOIN patient_service_type pst ON p.id = pst.patient_id JOIN visit v ON p.id = v.patient_id WHERE (date(pst.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') AND pst.service_type_id = " + st.getId() + " AND v.id IN (SELECT min(v.id) FROM visit v JOIN regimen r ON v.regimen_id = r.id WHERE r.line = 1 group by patient_id) AND v.id IN (SELECT max(v.id) FROM visit v JOIN regimen r ON v.regimen_id = r.id WHERE r.line = 1 group by patient_id)");
        denominator = reportsDAO.getDenominator("SELECT count(pst.service_type_id), st.name, st.id FROM patient_service_type pst JOIN service_type st ON pst.service_type_id = st.id  WHERE  (date(pst.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') AND st.id = " + st.getId() + " group by service_type_id ");
        float ewi2 = 0;
        if(personList.size() > 0)
            ewi2 = (((float)personList.size() / (float)denominator) * 100);


        denominator = 12;
        int months = getEWI3(startDate, endDate);
        float ewi3 = (((float)(12 - months) / (float)denominator) * 100);

        PatientStatus ps = referenceDAO.getPatientStatus("Lost to follow-up");
        personList = reportsDAO.execute("SELECT p.patient_id, p.first_name, p.other_names, p.surname, p.sex, p.date_of_birth FROM (SELECT ps.first_name, ps.other_names, ps.surname, ps.sex, ps.date_of_birth,v.patient_id, v.id as `visitid`, date(v.start_date), t.id, transaction_id, sum(d.combination_type) as comb FROM visit v JOIN `transaction` t ON v.id = t.visit_id JOIN transaction_item ti ON t.id = ti.transaction_id JOIN drug d on ti.drug_id = d.id JOIN patient p ON v.patient_id = p.id JOIN person ps ON ps.id = p.person_id JOIN patient_service_type pst ON p.id = pst.patient_id WHERE pst.service_type_id = " + st.getId() + " AND (date(v.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "') group by ti.transaction_id HAVING comb < 3 or comb is null) p GROUP by patient_id");
        den = reportsDAO.execute("SELECT p.patient_id FROM patient_service_type p JOIN visit v ON p.patient_id = v.patient_id WHERE p.service_type_id = " + st.getId() + " AND (date(v.start_date) BETWEEN '" + startDate + "' AND '" + endDate + "')  group by p.patient_id");
        float ewi4 = 0;
        if(personList.size() > 0)
            ewi4 = (((float)personList.size() / (float)den.size()) * 100);

        List<Double> report = new ArrayList<Double>();
        report.add(Double.valueOf(String.valueOf(roundValue(ewi1))));
        report.add(Double.valueOf(String.valueOf(roundValue(ewi2))));
        report.add(Double.valueOf(String.valueOf(roundValue(ewi3))));
        report.add(Double.valueOf(String.valueOf(roundValue(ewi4))));
        return report;
    }

    public int getEWI3(String startDate, String endDate) throws Exception{

        String[] startParts = startDate.split("/");
        String[] endParts = endDate.split("/");
        if(startParts[1].equalsIgnoreCase(endParts[1])) { //Start Date and End Date are same months
            int month = Integer.parseInt(startParts[1]);
            month += 1;
            int year = Integer.parseInt(startParts[0]);
            if(month > 12) {
                month = 1;
                year += 1;
            }
            startDate = year + "/" + month + "/" + "01";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        Calendar c = Calendar.getInstance();
        Date d = df.parse(startDate);
        c.setTime(d);

        int stockOutMonths = 0;

        List months = new ArrayList();

        for(int i = 0; i < 365; i++) {
            Date end = df.parse(endDate);
            Calendar cc = Calendar.getInstance();
            cc.setTime(end);
            if(c.getTimeInMillis() > cc.getTimeInMillis()) {
                System.out.println("Time " + c.getTimeInMillis());
                System.out.println("END " + cc.getTimeInMillis());
                return stockOutMonths;
            }
            String query = "SELECT d.id, (coalesce(sum(`ti`.`units_in`), 0) - coalesce(sum(`ti`.`units_out`), 0)) AS `difference`, DATEDIFF(date(ti.created_on), '" + df.format(c.getTime()) + "') as date_diff FROM drug d LEFT JOIN transaction_item ti ON d.id = ti.drug_id WHERE d.tracer = 1  group by d.id HAVING difference >= 0.0 AND (date_diff IS NULL OR date_diff <= 0)";
            //String query = "SELECT (coalesce(sum(`ti`.`units_in`), 0) - coalesce(sum(`ti`.`units_out`), 0)) AS `difference` FROM  transaction_item ti WHERE DATEDIFF( date(ti.created_on), '" + df.format(c.getTime()) + "') <= 0 group by ti.drug_id HAVING difference = 0";
            List resp = reportsDAO.execute(query);
            if(resp.size() > 0) { // The drug had zero stock so we record the month
                stockOutMonths = stockOutMonths + 1;
                String date = df.format(c.getTime());
                String[] parts = date.split("/");
                int newMonth = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[0]);
                newMonth = newMonth + 1;
                if(newMonth > 12) {
                    newMonth = 1;
                    year += 1;
                }
                String newDate = year + "/";
                //if(newMonth < 10) {
                newDate += newMonth;
                //} else {

                //}
                newDate += "/01";
                c.setTime(df.parse(newDate)); // Move Calendar to new month.
            } else {
                c.add(Calendar.DAY_OF_YEAR, 1); // Increment by 1 day
            }
        }
        return stockOutMonths;
    }

    @Override
    @Transactional
    public File createFacilityFmapsReport(String month, String year,Integer accountId){
        String date1 = year + "/" + (Integer.parseInt(month) + 1) + "01";
        String date2 = getLastDay(month, year);
        PatientStatus ps = referenceDAO.getPatientStatus("Active");
        String sql = "SELECT r.code, count(p.id), r.name, r.line, st.name as `service type`, st.id as `serviceId`, v.regimen_id,  floor(datediff(date(now()), date(p.date_of_birth)) / 365) as age FROM person p JOIN patient pt ON p.id = pt.person_id JOIN patient_service_type pst ON pt.id = pst.patient_id JOIN visit v ON pt.id = v.patient_id JOIN regimen r ON r.id = v.regimen_id JOIN service_type st ON st.id = pst.service_type_id WHERE v.id IN (SELECT max(id) FROM visit WHERE regimen_id IS NOT NULL GROUP BY patient_id) AND pt.patient_status_id = " + ps.getId() + " AND (date(v.start_date) BETWEEN '" + date1 + "' AND '" + date2 + "')  group by code ORDER BY pst.service_type_id";


        String fileName = "fmaps.pdf";
        File file = new File(fileName);
        Document document = new Document(PageSize.A3);
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();


        Font headerStyle = new Font();
        headerStyle.setStyle("bold");
        headerStyle.setColor(153, 51, 0);
        headerStyle.setFamily(BaseFont.TIMES_ROMAN);

        Paragraph Header = new Paragraph();
        Paragraph hf = new Paragraph(getFacilityName(), headerStyle);
        hf.setAlignment(Element.ALIGN_CENTER);
        Header.add(hf);

        Paragraph reportType = new Paragraph("", headerStyle);
        reportType.setAlignment(Element.ALIGN_CENTER);
        Header.add(reportType);

        Paragraph duration = new Paragraph( "Period : from " + getDateFormat(date1) + " To : " + getDateFormat(date2) , headerStyle);
        duration.setAlignment(Element.ALIGN_CENTER);
        Header.add(duration);

        Header.setAlignment(Element.ALIGN_CENTER);
        Header.setExtraParagraphSpace(4);
        Header.setSpacingAfter(5);
//        document.add(Header);


        document.close();


//        return file;
//    } catch (Exception e) {
//        e.printStackTrace();
//    } finally {
//
//    }
   return null;
    }
    @Override
    @Transactional
    public HSSFWorkbook createActivePatientsByRegimen(ServletContext context, String year, String month) throws Exception {
        String date1 = year + "/" + (Integer.parseInt(month) + 1) + "01";
        String date2 = getLastDay(month, year);
        PatientStatus ps = referenceDAO.getPatientStatus("Active");
       String sql = "SELECT r.code, count(p.id), r.name, r.line, st.name as `service type`, st.id as `serviceId`, v.regimen_id,  floor(datediff(date(now()), date(p.date_of_birth)) / 365) as age FROM person p JOIN patient pt ON p.id = pt.person_id JOIN patient_service_type pst ON pt.id = pst.patient_id JOIN visit v ON pt.id = v.patient_id JOIN regimen r ON r.id = v.regimen_id JOIN service_type st ON st.id = pst.service_type_id WHERE v.id IN (SELECT max(id) FROM visit WHERE regimen_id IS NOT NULL GROUP BY patient_id) AND pt.patient_status_id = " + ps.getId() + " AND (date(v.start_date) BETWEEN '" + date1 + "' AND '" + date2 + "')  group by code ORDER BY pst.service_type_id";
        //Editted by Davie

        String sql1 = "select code, count(distinct patient_id) as NumofPatients from (select R2.CODE PCode, R2.name as PatientRegimen, regimen.id as RegID, regimen.code, regimen.name,\n" +
                " drug.id as DrugID, drug.name as DrugName,visit.patient_id , person.id, person.date_of_birth ,\n" +
                "curdate(), datediff(curdate(),person.date_of_birth) as Age from regimen \n" +
                "inner join regimen_drug on regimen_drug.regimen_id=regimen.id \n" +
                "inner join drug on drug.id=regimen_drug.drug_id  inner join \n" +
                "transaction_item on drug.id=transaction_item.drug_id inner join \n" +
                "transaction on transaction.id=transaction_item.transaction_id\n" +
                "inner join visit on visit.id=transaction.visit_id\n" +
                "inner join patient on patient.id=visit.patient_id inner join \n" +
                "person on person.id=patient.person_id\n" +
                "inner join (select * from regimen) as R2 on R2.id=visit.regimen_id\n" +
                "where regimen.code in ('OI1A','OI1C','OI2A','OI2C','OI4A','OI4C','OI3A','OI3C',\n" +
                "'CM3N','CM3R','OC3N','OC3R') and visit.start_date between '"+date1+"'  and '"+date2+"'\n" +
                "and person.date_of_birth is not null) x group by code";

        String file_path = context.getRealPath("") + File.separator + "FMAPS-Template.xls";

        FileInputStream fsIP= new FileInputStream(new File(file_path)); //Read the spreadsheet that needs to be updated

        HSSFWorkbook myWorkBook = new HSSFWorkbook(fsIP); //Access the workbook

        HSSFSheet mySheet = myWorkBook.getSheetAt(0);

        mySheet.getRow(3).getCell(2).setCellValue(getFacilityName());
        mySheet.getRow(4).getCell(2).setCellValue(getFacilityDistrict());

        mySheet.getRow(6).getCell(3).setCellValue("01/" + (Integer.parseInt(month) + 1) + "/" + year);
        String[] lastDays = date2.split("/");
        mySheet.getRow(6).getCell(6).setCellValue(lastDays[2] + "/" + lastDays[1] + "/" + lastDays[0]);



        String code1;
        String count1;
        String count2 = null;
        String count3 = null;
        String count4 = null;
        List<Object[]> activePatients1 = reportsDAO.execute(sql1);
        JSONObject regimens1 = new JSONObject();
        for (Object[] o1 : activePatients1) {
            code1 = String.valueOf(o1[0]);
            count1 = String.valueOf(o1[1]);
            if (o1[0].equals("OI1A")) {
                count2 = String.valueOf(count1);
                //count.equals(count1);
            }else if(o1[0].equals("OI2A")){
                count3 = String.valueOf(count1);
            }
            else if(o1[0].equals("OI1C")){
                count4 = String.valueOf(count1);
            }
             else{

                regimens1.put(code1, count1);
            }
        }

            List<Object[]> activePatients = reportsDAO.execute(sql);
            JSONObject regimens = new JSONObject();
            for (Object[] o : activePatients) {
               String code = String.valueOf(o[0]);
              String count = String.valueOf(o[1]);
                if (o[0].equals("OI1A")) {
                   int Value1 = Integer.valueOf(count);
                   int value2 = Integer.parseInt(count2);
                    int value3  = Value1+value2;
                    String value4 = Integer.toString(value3);
                    //count.equals(count1);
                    regimens.put(code,value4);

                } else if(o[0].equals("OI2A")) {
                    int Val1 = Integer.valueOf(count);
                    int val2 = Integer.parseInt(count3);
                    int val3  = Val1+val2;
                    String val4 = Integer.toString(val3);
                    regimens.put(code, val4);

                }else if(o[0].equals("OI1C")) {
                    int V1 = Integer.valueOf(count);
                    int v2 = Integer.parseInt(count4);
                    int v3  = V1+v2;
                    String v4 = Integer.toString(v3);
                    regimens.put(code, v4);
                }else{
                    regimens.put(code,count);
                }
            }
        //int sum = Integer.valueOf(regimens1.getString("OI1A")) + Integer.valueOf(count);
         //The new list of objects should look something like this(below). notice where there is the if statement
        //if the code loops  and the regimen code matches with the one specified  it should read values from "sql1" if it does not match then
        //we should use the count of "sql" and not "sql1"
//        List<Object[]> activePatients = reportsDAO.execute(sql);
//        JSONObject regimens = new JSONObject();
//        for (Object[] o : activePatients) {
//            String code = String.valueOf(o[0]);
//            String count = String.valueOf(o[1]);
//            if(code.equals("OI1A"))
//            regimens1.put(code1,count1);
//            else
//            {
//                regimens.put(code,count);
//            }
//
//        }






            int[] groupA = {22, 30, 36, 37, 48, 54, 60}; // Rows which must be skipped in the first columns
            int[] groupB = {22, 24, 31, 32, 38, 42, 43, 44, 45, 50, 53, 60, 61, 62, 63, 64, 65}; // Rows which must be skipped in the second columns
            for (int i = 13; i < 66; i++) {
                if (!existInArray(i, groupA)) {
                    String code = mySheet.getRow(i).getCell(1).getStringCellValue();
                    if (regimens.has(code.trim())) {
                        mySheet.getRow(i).getCell(3).setCellValue(Integer.parseInt(regimens.optString(code.trim())));
                    }
                }
                if (!existInArray(i, groupB)) {
                    String code = mySheet.getRow(i).getCell(5).getStringCellValue();
                    if (regimens.has(code.trim())) {
                        mySheet.getRow(i).getCell(7).setCellValue(Integer.parseInt(regimens.optString(code.trim())));
                    }
                }
            }
            return myWorkBook;
        }
    public boolean existInArray(int value, int[] array) {
        for(int i = 0; i < array.length; i++) {
            if(array[i] == value) {
                return true;
            }
        }
        return false;
    }

    public void createRow(JSONObject obj, HSSFRow myRow) {

        HSSFCell myCell = myRow.createCell(0);
        myCell.setCellValue(obj.getString("code"));

        myCell = myRow.createCell(1);
        myCell.setCellValue(obj.getString("name"));

        myCell = myRow.createCell(2);
        myCell.setCellValue(obj.getInt("count"));
    }

    public String getAccountName(List<Account> accounts, Integer id) {
        Iterator iterator = accounts.iterator();
        while(iterator.hasNext()) {
            Account account = (Account)iterator.next();
            if(account.getId() == id) {
                return account.getName();
            }
        }
        return "";
    }

    /**
     *  Formats the given date to dd/mm/yyyy
     * @param date
     * @return
     */
    public String getDateFormat(String date) {
        String[] dates = date.split("-");
        //return dates[0] + "/" + dates[1] + "/" + dates[2];
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date d = df.parse(date);
            df = new SimpleDateFormat("dd/MM/yyyy");
            String dt = df.format(d);
            return dt;
        } catch (Exception e) {
            return date;
        }
    }

    public String roundValue(float value) {
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(value);
    }

    /**
     *  Given a month and year, it returns the last day of that month.
     * @param month
     * @param year
     * @return last date of the specified month
     */
    public String getLastDay(String month, String year) {
        Integer y = Integer.parseInt(year);
        Integer m = Integer.parseInt(month);
        m = m + 1;

        String last = "";
        if( y % 4 == 0 && m == 2) {
            last = "29";
        } else if(y % 4 != 0 && m == 2) {
            last = "28";
        } else if((m < 9 && m % 2 == 1) || m == 8 || m == 10 || m == 12) {
            last = "31";
        } else if(m % 2 == 0 || m == 9 || m == 11) {
            last = "30";
        }
        return year + "/" + (m) + "/" + last;
    }

    @Override
    @Transactional
    public HSSFWorkbook createFacilityCDRR(String month, String year, ServletContext context, Integer accountId) throws Exception {
        JSONObject reportData = createARTCDRRData(month, year, accountId);

        String startDate = year + "/" + (Integer.parseInt(month) + 1) + "/01";

        String endDate = getLastDay(month, year);

        String file_path = context.getRealPath("") + File.separator + "FCDRR-Template.xls";

        FileInputStream fsIP= new FileInputStream(new File(file_path)); //Read the spreadsheet that needs to be updated

        HSSFWorkbook myWorkBook = new HSSFWorkbook(fsIP); //Access the workbook

        HSSFSheet mySheet = myWorkBook.getSheetAt(0);

        mySheet.getRow(3).getCell(2).setCellValue(getFacilityName());
        mySheet.getRow(4).getCell(10).setCellValue( getFacilityDistrict());

        mySheet.getRow(6).getCell(4).setCellValue(getDateFormat(startDate));

        mySheet.getRow(6).getCell(10).setCellValue(getDateFormat(endDate));

        mySheet.getRow(3).getCell(10).setCellValue(getFacilityCode());

        Iterator iterator = reportData.keys();

        int rows = 5;

        HSSFRow myRow = null;
        rows = 14;

        List<CdrrCategory> cdrrCats = referenceDAO.listCDRRCategoty();

        JSONArray finalItems = sortARTCDRRData(reportData);

        for(int i = 0; i < finalItems.length(); i++) {
            if (finalItems.get(i) != null) {
                if (!(finalItems.get(i) instanceof JSONObject)) {
                    continue;
                }
                JSONObject object = finalItems.getJSONObject(i);
                iterator = object.keys();

                JSONArray sorted = sortCategory(object);

                CdrrCategory category = getCDRRCategory(cdrrCats, i);
                if (category != null) {
                    myRow = mySheet.getRow(rows);
                    //myRow.getCell(1).setCellValue(category.getName());

                    mySheet.addMergedRegion(new CellRangeAddress(rows, rows, 1, 12));

                    HSSFCell mergedCell = myRow.createCell(1);
                    mergedCell.setCellValue(category.getName());
                    HSSFCellStyle mergedStyle = myWorkBook.createCellStyle();

                    HSSFFont font = myWorkBook.createFont();
                    font.setFontName(HSSFFont.FONT_ARIAL);
                    font.setFontHeightInPoints((short) 22);
                    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                    //font.setBoldweight((short)12);
                    mergedStyle.setFont(font);
                    mergedStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    mergedStyle.setFillBackgroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
                    //mergedStyle.setFillPattern(CellStyle.FINE_DOTS);

                    mergedCell.setCellStyle(mergedStyle);
                    rows++;
                } else {
                    rows++;
                }

                int j = 0;
                while(j < sorted.length()) {
                    if(sorted.get(j) != null) {
                        if(!(sorted.get(j) instanceof JSONObject)) {
                            j++;
                            continue;
                        }
                        JSONObject obj = sorted.getJSONObject(j);
                        if(obj == null) {
                            continue;
                        }


                        myRow = mySheet.getRow(rows);
                        if(myRow == null) {
                            System.out.println(obj.toString());
                            continue;
                        }
                        myRow.getCell(1).setCellValue(obj.getString("name"));

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(2).setCellValue(obj.optString("unit"));

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(3).setCellValue(obj.has("balance") ? obj.get("balance").toString() : "0");

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(4).setCellValue(obj.has("quantityReceived") ? obj.get("quantityReceived").toString() : "0");

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(5).setCellValue(obj.has("quantityDispensed") ? obj.get("quantityDispensed").toString() : "0");

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(6).setCellValue(obj.has("losses") ? obj.get("losses").toString() : "0");

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(7).setCellValue(obj.has("adjustments") ? obj.get("adjustments").toString() : "0");

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(8).setCellValue(obj.has("adjustmentsNve") ? obj.get("adjustmentsNve").toString() : "0");

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(9).setCellValue(obj.has("closingBalance") ? obj.get("closingBalance").toString() : "0");

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(10).setCellValue(obj.has("shortDated") ? obj.get("shortDated").toString() : "0");

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(11).setCellValue(obj.has("expiryDate") ? obj.get("expiryDate").toString() : "");

                        myRow = mySheet.getRow(rows);
                        myRow.getCell(12).setCellValue(obj.has("outOfStock") ? obj.get("outOfStock").toString() : "0");
                        rows++;
                    }

                    j++;
                }
            }
        }
        return myWorkBook;
    }
}
