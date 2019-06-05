package com.mayikt.core.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 *  
 * @项目名称:广西红包中心
 * @工程名称:lyhzq-commons
 * @类名称:ExcelReadUtils
 * @类描述:读取excel通用类
 * @作者:dww
 * @创建时间:2018年4月3日 下午2:39:55
 * @当前版本:1.0
 *
 */
public class ExcelReadUtils {

    private final static String OFFICE_EXCEL_XLS = "xls";

    private final static String OFFICE_EXCEL_XLSX = "xlsx";

    private final static String POINT = ".";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    private static Logger logger = LoggerFactory.getLogger(ExcelReadUtils.class);

    /**
     * 
     * @readExcel(读取excel数据)
     * @作者:dww
     * @创建时间:2018年4月3日 下午2:40:16
     * @param file 前端上传的excel文件
     * @return
     */
    public static List<ArrayList<String>> readExcel(MultipartFile file){
        if (file == null || StringUtils.isBlank(file.getOriginalFilename())) {
            return null;
        }
        String postfix = getPostfix(file.getOriginalFilename());
        if (StringUtils.isNotBlank(postfix)) {
            if (OFFICE_EXCEL_XLS.equals(postfix)) {
                return readXls(file);
            } else if (OFFICE_EXCEL_XLSX.equals(postfix)) {
                return readXlsx(file);
            }
        }
        return null;
    }

    /**
     * 
     * @readXlsx(读取xlsx excel数据,支持2010以后的)
     * @作者:dww
     * @创建时间:2018年4月3日 下午2:40:44
     * @param file
     * @return
     */
    private static List<ArrayList<String>> readXlsx(MultipartFile file) {
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        // IO流读取文件
        InputStream input = null;
        XSSFWorkbook wb = null;
        ArrayList<String> rowList = null;
        int totalRows = 0; // sheet中总行数
        int totalCells = 0; // 每一行总单元格数
        try {
            input = file.getInputStream();
            // 创建文档
            wb = new XSSFWorkbook(input);
            // 读取sheet(页)
            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = wb.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                totalRows = xssfSheet.getLastRowNum();
                // 读取Row,从第二行开始
                for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow != null) {
                        rowList = new ArrayList<String>();
                        totalCells = xssfRow.getLastCellNum();
                        // 读取列，从第一列开始
                        for (int c = 0; c <= totalCells + 1; c++) {
                            XSSFCell cell = xssfRow.getCell(c);
                            if (cell == null) {
                            	if(c < totalCells - 1){
                            		rowList.add("");
                            	}
                                continue;
                            }
                            rowList.add(getXValue(cell).trim());
                        }
                    }
                }
            }
            return list;
        } catch (IOException e) {
            logger.error("读取excel异常:{}", e.getMessage(), e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                logger.error("读取excel关闭流异常:{}", e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 
     * @readXls(读取xls excel数据支持2003-2007.xls)
     * @作者:dww
     * @创建时间:2018年4月3日 下午2:41:04
     * @param file文件流
     * @return
     */
    private static List<ArrayList<String>> readXls(MultipartFile file) {
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        // IO流读取文件
        InputStream input = null;
        HSSFWorkbook wb = null;
        ArrayList<String> rowList = null;
        int totalRows = 0; // sheet中总行数
        int totalCells = 0; // 每一行总单元格数
        try {
            input = file.getInputStream();
            // 创建文档
            wb = new HSSFWorkbook(input);
            // 读取sheet(页)
            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = wb.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                totalRows = hssfSheet.getLastRowNum();
                // 读取Row,从第二行开始
                for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        rowList = new ArrayList<String>();
                        totalCells = hssfRow.getLastCellNum();
                        // 读取列，从第一列开始
                        for (int c = 0; c <= totalCells + 1; c++) {
                            HSSFCell cell = hssfRow.getCell(c);
                            if (cell == null) {
                            	if(c < totalCells - 1){
                            		rowList.add("");
                            	}
                                continue;
                            }
                            rowList.add(getHValue(cell).trim());
                        }
                        list.add(rowList);
                    }
                }
            }
            return list;
        } catch (IOException e) {
            logger.error("读取excel异常:{}", e.getMessage(), e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                logger.error("读取excel关闭流异常:{}", e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 
     * @getPostfix(获取文件后缀名)
     * @作者:dww
     * @创建时间:2017年11月6日 下午4:39:56
     * @param path
     * @return
     */
    private static String getPostfix(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        if (filePath.contains(POINT)) {
            return filePath.substring(filePath.lastIndexOf(POINT) + 1, filePath.length());
        }
        return null;
    }

    /**
     * 
     * @getHValue(xls单元格数据类型处理)
     * @作者:dww
     * @创建时间:2017年11月6日 下午4:48:49
     * @param hssfCell
     * @return
     */
    private static String getHValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            String cellValue = "";
            if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                Date date = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
                cellValue = sdf.format(date);
            } else {
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(hssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(POINT) + 1, cellValue.length());
                if (strArr.equals("00")) {
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
                }
            }
            return cellValue;
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    /**
     * 
     * @getXValue(xlsx单元格数据类型处理)
     * @作者:dww
     * @创建时间:2017年11月6日 下午4:48:00
     * @param xssfCell
     * @return
     */
    private static String getXValue(XSSFCell xssfCell) {
        if (xssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            String cellValue = "";
            if (XSSFDateUtil.isCellDateFormatted(xssfCell)) {
                Date date = XSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue());
                cellValue = sdf.format(date);
            } else {
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(xssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(POINT) + 1, cellValue.length());
                if (strArr.equals("00")) {
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
                }
            }
            return cellValue;
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }

    /**
     * 
     *  
     * @项目名称:广西红包中心
     * @工程名称:lyhzq-commons
     * @类名称:XSSFDateUtil
     * @类描述:自定义xssf日期工具类
     * @作者:dww
     * @创建时间:2018年4月3日 下午2:41:47
     * @当前版本:1.0
     *
     */
    private static class XSSFDateUtil extends DateUtil {
        protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
            return DateUtil.absoluteDay(cal, use1904windowing);
        }
    }
}
