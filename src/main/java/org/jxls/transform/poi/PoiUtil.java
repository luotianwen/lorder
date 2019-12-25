//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jxls.transform.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jxls.builder.xls.XlsCommentAreaBuilder;

public class PoiUtil {
    public PoiUtil() {
    }

    public static void setCellComment(Cell cell, String commentText, String commentAuthor, ClientAnchor anchor) {
        Sheet sheet = cell.getSheet();
        Workbook wb = sheet.getWorkbook();
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        CreationHelper factory = wb.getCreationHelper();
        if (anchor == null) {
            anchor = factory.createClientAnchor();
            anchor.setCol1(cell.getColumnIndex() + 1);
            anchor.setCol2(cell.getColumnIndex() + 3);
            anchor.setRow1(cell.getRowIndex());
            anchor.setRow2(cell.getRowIndex() + 2);
        }

        Comment comment = drawing.createCellComment(anchor);
        comment.setString(factory.createRichTextString(commentText));
        comment.setAuthor(commentAuthor != null ? commentAuthor : "");
        cell.setCellComment(comment);
    }

    public WritableCellValue hyperlink(String address, String link, String linkTypeString) {
        return new WritableHyperlink(address, link, linkTypeString);
    }

    public WritableCellValue hyperlink(String address, String title) {
        return new WritableHyperlink(address, title);
    }

    public static void copySheetProperties(Sheet src, Sheet dest) {
        dest.setAutobreaks(src.getAutobreaks());
        dest.setDisplayGridlines(src.isDisplayGridlines());
        dest.setVerticallyCenter(src.getVerticallyCenter());
        dest.setFitToPage(src.getFitToPage());
        dest.setForceFormulaRecalculation(src.getForceFormulaRecalculation());
        dest.setRowSumsRight(src.getRowSumsRight());
        dest.setRowSumsBelow(src.getRowSumsBelow());
        //增加页边距保存
        dest.setMargin(Sheet.TopMargin, src.getMargin(Sheet.TopMargin));
        dest.setMargin(Sheet.LeftMargin, src.getMargin(Sheet.LeftMargin));
        dest.setMargin(Sheet.RightMargin, src.getMargin(Sheet.RightMargin));
        dest.setMargin(Sheet.BottomMargin, src.getMargin(Sheet.BottomMargin));
    //设置顶端标题行
/*
        Workbook wb = src.getWorkbook();
        wb.setPrintArea(
                0, //sheet index
                0, //start column
                2, //end column
                0, //start row
                14  //end row
        );
        PrintSetup ps=dest.getPrintSetup();
        ps.setPaperSize(PrintSetup.TABLOID_PAPERSIZE);
        Name name = wb.getName("Print_Titles");*/

    /*AreaReference[] generateContiguous = AreaReference.generateContiguous(name.getRefersToFormula());

    int firstTitleRow =generateContiguous[0].getFirstCell().getRow();

    int lastTitleRow =generateContiguous[0].getLastCell().getRow();

    short firstCol = generateContiguous[0].getFirstCell().getCol();

    short lastCol = generateContiguous[0].getLastCell().getCol();

    dest.setRepeatingRows(new CellRangeAddress(firstTitleRow, lastTitleRow, firstCol, lastCol));
*/
    //设置页眉页脚

    Header destHeader = dest.getHeader();

    destHeader.setCenter(src.getHeader().getCenter());

    destHeader.setLeft(src.getHeader().getLeft());

    destHeader.setRight(src.getHeader().getRight());

    Footer destFooter = dest.getFooter();

    destFooter.setCenter(src.getFooter().getCenter());

    destFooter.setLeft(src.getFooter().getLeft());

    destFooter.setRight(src.getFooter().getRight());
 
        copyPrintSetup(src, dest);
    }

    private static void copyPrintSetup(Sheet src, Sheet dest) {
        PrintSetup srcPrintSetup = src.getPrintSetup();
        PrintSetup destPrintSetup = dest.getPrintSetup();
        destPrintSetup.setCopies(srcPrintSetup.getCopies());
        destPrintSetup.setDraft(srcPrintSetup.getDraft());
        destPrintSetup.setFitHeight(srcPrintSetup.getFitHeight());
        destPrintSetup.setFitWidth(srcPrintSetup.getFitWidth());
        destPrintSetup.setFooterMargin(srcPrintSetup.getFooterMargin());
        destPrintSetup.setHeaderMargin(srcPrintSetup.getHeaderMargin());
        destPrintSetup.setHResolution(srcPrintSetup.getHResolution());
        destPrintSetup.setLandscape(srcPrintSetup.getLandscape());
        destPrintSetup.setLeftToRight(srcPrintSetup.getLeftToRight());
        destPrintSetup.setNoColor(srcPrintSetup.getNoColor());
        destPrintSetup.setNoOrientation(srcPrintSetup.getNoOrientation());
        destPrintSetup.setNotes(srcPrintSetup.getNotes());
        destPrintSetup.setPageStart(srcPrintSetup.getPageStart());
        destPrintSetup.setPaperSize(srcPrintSetup.getPaperSize());
        destPrintSetup.setScale(srcPrintSetup.getScale());
        destPrintSetup.setUsePage(srcPrintSetup.getUsePage());
        destPrintSetup.setValidSettings(srcPrintSetup.getValidSettings());
        destPrintSetup.setVResolution(srcPrintSetup.getVResolution());
    }

    public static boolean isJxComment(String cellComment) {
        if (cellComment == null) {
            return false;
        } else {
            String[] commentLines = cellComment.split("\\n");
            String[] var2 = commentLines;
            int var3 = commentLines.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String commentLine = var2[var4];
                if (commentLine != null && XlsCommentAreaBuilder.isCommandString(commentLine.trim())) {
                    return true;
                }
            }

            return false;
        }
    }
}
