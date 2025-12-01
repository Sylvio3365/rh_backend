package com.rh.service;

import com.rh.model.FichePaie;
import com.rh.model.Irsa;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ExcelGeneratorService {

    public byte[] generateFichePaieExcel(FichePaie fichePaie) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("FICHE DE PAIE");

            // Styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle boldStyle = createBoldStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);

            // Contenu de la fiche de paie
            int rowNum = 0;

            // En-tête avec période
            rowNum = createHeader(sheet, rowNum, headerStyle, fichePaie);
            rowNum++; // Ligne vide

            // Informations du personnel
            rowNum = createPersonnelInfo(sheet, rowNum, boldStyle, fichePaie);
            rowNum++; // Ligne vide

            // Gains
            rowNum = createGainsSection(sheet, rowNum, boldStyle, currencyStyle, fichePaie);
            rowNum++; // Ligne vide

            // Retenues
            rowNum = createRetenuesSection(sheet, rowNum, boldStyle, currencyStyle, fichePaie);
            rowNum++; // Ligne vide

            // Synthèse
            createSyntheseSection(sheet, rowNum, boldStyle, currencyStyle, fichePaie);

            // Ajuster la largeur des colonnes
            for (int i = 0; i < 10; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private int createHeader(Sheet sheet, int rowNum, CellStyle headerStyle, FichePaie fichePaie) {
        // Titre principal
        Row headerRow = sheet.createRow(rowNum++);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("FICHE DE PAIE");
        headerCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

        // Période (mois et année)
        Row periodRow = sheet.createRow(rowNum++);
        periodRow.createCell(0).setCellValue("Période : " + fichePaie.getPeriode());

        // Dates exactes (du 1er au dernier jour du mois)
        Row datesRow = sheet.createRow(rowNum++);
        datesRow.createCell(0).setCellValue("Du 01/" + String.format("%02d", fichePaie.getMois()) +
                "/" + fichePaie.getAnnee() + " au " +
                getLastDayOfMonth(fichePaie.getMois(), fichePaie.getAnnee()) +
                "/" + String.format("%02d", fichePaie.getMois()) +
                "/" + fichePaie.getAnnee());

        return rowNum;
    }

    private String getLastDayOfMonth(int mois, int annee) {
        java.time.YearMonth yearMonth = java.time.YearMonth.of(annee, mois);
        return String.valueOf(yearMonth.lengthOfMonth());
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createBoldStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        return style;
    }

    private int createPersonnelInfo(Sheet sheet, int rowNum, CellStyle boldStyle, FichePaie fichePaie) {
        Row row1 = sheet.createRow(rowNum++);
        row1.createCell(0).setCellValue("Nom et Prénoms :");
        row1.createCell(1).setCellValue(fichePaie.getPersonnelDetail().getPersonnel().getNom() + " " +
                fichePaie.getPersonnelDetail().getPersonnel().getPrenom());

        Row row2 = sheet.createRow(rowNum++);
        row2.createCell(0).setCellValue("Matricule :");
        row2.createCell(1).setCellValue(fichePaie.getPersonnelDetail().getPersonnel().getMatricule());

        Row row3 = sheet.createRow(rowNum++);
        row3.createCell(0).setCellValue("Fonction :");
        row3.createCell(1).setCellValue(fichePaie.getPersonnelDetail().getPoste().getNom());

        Row row4 = sheet.createRow(rowNum++);
        row4.createCell(0).setCellValue("Salaire de base :");
        row4.createCell(1).setCellValue(fichePaie.getPersonnelDetail().getSalaire().doubleValue());

        return rowNum;
    }

    private int createGainsSection(Sheet sheet, int rowNum, CellStyle boldStyle, CellStyle currencyStyle, FichePaie fichePaie) {
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("GAINS");
        headerRow.getCell(0).setCellStyle(boldStyle);

        // Salaire de base
        Row salaireRow = sheet.createRow(rowNum++);
        salaireRow.createCell(0).setCellValue("Salaire de base");
        salaireRow.createCell(1).setCellValue(fichePaie.getPersonnelDetail().getSalaire().doubleValue());
        salaireRow.getCell(1).setCellStyle(currencyStyle);

        // Heures supplémentaires
        for (Map.Entry<Double, Double> entry : fichePaie.getHeureSup().entrySet()) {
            if (entry.getValue() > 0) {
                Row hsRow = sheet.createRow(rowNum++);
                String libelle = getLibelleHeureSup(entry.getKey());
                hsRow.createCell(0).setCellValue(libelle);
                hsRow.createCell(1).setCellValue(entry.getValue());
                hsRow.getCell(1).setCellStyle(currencyStyle);
            }
        }

        // Total gains
        Row totalRow = sheet.createRow(rowNum++);
        totalRow.createCell(0).setCellValue("TOTAL GAINS");
        totalRow.getCell(0).setCellStyle(boldStyle);
        totalRow.createCell(1).setCellValue(fichePaie.getSalaireBrut());
        totalRow.getCell(1).setCellStyle(currencyStyle);

        return rowNum;
    }

    private int createRetenuesSection(Sheet sheet, int rowNum, CellStyle boldStyle, CellStyle currencyStyle, FichePaie fichePaie) {
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("RETENUES");
        headerRow.getCell(0).setCellStyle(boldStyle);

        // CNAPS (récupéré du personnelDetail)
        Row cnapsRow = sheet.createRow(rowNum++);
        cnapsRow.createCell(0).setCellValue("Retenue CNAPS 1%");
        cnapsRow.createCell(1).setCellValue(fichePaie.getCnaps()); // ← Utilise le getter qui vient de personnelDetail
        cnapsRow.getCell(1).setCellStyle(currencyStyle);

        // Sanitaire
        Row sanitaireRow = sheet.createRow(rowNum++);
        sanitaireRow.createCell(0).setCellValue("Retenue sanitaire");
        sanitaireRow.createCell(1).setCellValue(fichePaie.getRetenueSanitaire());
        sanitaireRow.getCell(1).setCellStyle(currencyStyle);

        // IRSA
        for (Irsa irsa : fichePaie.getIrsaList()) {
            if (irsa.getMontant() > 0) {
                Row irsaRow = sheet.createRow(rowNum++);
                String libelle = getLibelleIrsa(irsa);
                irsaRow.createCell(0).setCellValue(libelle);
                irsaRow.createCell(1).setCellValue(irsa.getMontant());
                irsaRow.getCell(1).setCellStyle(currencyStyle);
            }
        }

        // Total retenues
        Row totalRow = sheet.createRow(rowNum++);
        totalRow.createCell(0).setCellValue("TOTAL RETENUES");
        totalRow.getCell(0).setCellStyle(boldStyle);
        totalRow.createCell(1).setCellValue(fichePaie.getCnaps() + fichePaie.getRetenueSanitaire() + fichePaie.getTotalIrsa());
        totalRow.getCell(1).setCellStyle(currencyStyle);

        return rowNum;
    }
    private void createSyntheseSection(Sheet sheet, int rowNum, CellStyle boldStyle, CellStyle currencyStyle, FichePaie fichePaie) {
        Row netRow = sheet.createRow(rowNum++);
        netRow.createCell(0).setCellValue("NET À PAYER");
        netRow.getCell(0).setCellStyle(boldStyle);
        netRow.createCell(1).setCellValue(fichePaie.getSalaireNet());
        netRow.getCell(1).setCellStyle(currencyStyle);
    }

    private String getLibelleHeureSup(Double pourcentage) {
        switch (pourcentage.intValue()) {
            case 30: return "Heures supplémentaires majorées de 30%";
            case 40: return "Heures supplémentaires majorées de 40%";
            case 50: return "Heures supplémentaires majorées de 50%";
            case 100: return "Heures supplémentaires majorées de 100%";
            case 130: return "Majoration pour heures de nuit";
            default: return "Heures supplémentaires " + pourcentage + "%";
        }
    }

    private String getLibelleIrsa(Irsa irsa) {
        if (irsa.getMax() == null) {
            return "Tranche IRSA PLUS DE " + String.format("%,.0f", irsa.getMin().doubleValue());
        }
        return "Tranche IRSA DE " + String.format("%,.0f", irsa.getMin().doubleValue()) +
                " À " + String.format("%,.0f", irsa.getMax().doubleValue());
    }
}