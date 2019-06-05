package com.mayikt.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ExcelUtil {

	public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
		HSSFWorkbook wkb = null;
		OutputStream outputStream = null;
		try {
			// 创建HSSFWorkbook对象(excel的文档对象)
			wkb = new HSSFWorkbook();
			// 建立新的sheet对象（excel的表单）
			HSSFSheet sheet = wkb.createSheet("成绩表");

			// 获取样式对象
			HSSFCellStyle cellStyle = wkb.createCellStyle();
			// 设置样式对象，这里仅设置了边框属性
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			// 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
			HSSFRow row = sheet.createRow(0);
			// 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(cellStyle);
			// 设置单元格内容
			cell.setCellValue("学员考试成绩一览表");
			// 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

			// 在sheet里创建第二行
			row = sheet.createRow(1);
			// 创建单元格并设置单元格内容
			row.createCell(0).setCellValue("姓名");
			row.createCell(1).setCellValue("班级");
			row.createCell(2).setCellValue("笔试成绩");
			row.createCell(3).setCellValue("机试成绩");

			// 在sheet里创建第三行
			row = sheet.createRow(2);
			row.createCell(0).setCellValue("李明");
			row.createCell(1).setCellValue("As178");
			row.createCell(2).setCellValue(87);
			row.createCell(3).setCellValue(78);
			// .....省略部分代码

			// 输出Excel文件
			outputStream = response.getOutputStream();
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				fileName = new String(fileName.getBytes("gbk"), "ISO8859-1");
			}
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			wkb.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
				if (wkb != null) {
					wkb.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void upload(HttpServletRequest request, HttpServletResponse response, File file) {
		String suffix = getSuffix(file.getName());
		if ("xls".equals(suffix)) {
            readXls(file); // 解析文件
        } else if ("xlsx".equals(suffix)) {
            readXlsx(file); // 解析文件
        } else {
            System.err.println("文件格式错误");
        }
		
	}

	private void readXls(File file) {
		HSSFWorkbook hssfWorkbook = null;
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			hssfWorkbook = new HSSFWorkbook(inputStream);
			
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				for (int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow != null) {
						toObject(hssfRow);
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != hssfWorkbook) {
					hssfWorkbook.close();
				}
				if (null != inputStream) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void readXlsx(File file) {
        XSSFWorkbook xssfWorkbook = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            xssfWorkbook = new XSSFWorkbook(inputStream);
            for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xssfSheet == null) {
                    continue;
                }
                for (int rowNum = 2; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                    XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                    if (xssfRow != null) {
                    	toObject(xssfRow);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
            try {
                if (null != xssfWorkbook) {
                    xssfWorkbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
	}

	private void toObject(Row row) {
		boolean isDecimal = false;
		if (row instanceof HSSFRow) {
			HSSFRow hssfRow = (HSSFRow) row;
			System.out.println(changeCellToString(hssfRow.getCell(0), isDecimal));
			System.out.println(changeCellToString(hssfRow.getCell(1), isDecimal));
			System.out.println(changeCellToString(hssfRow.getCell(2), true));
			System.out.println(changeCellToString(hssfRow.getCell(3), true));
		}
		if (row instanceof XSSFRow) {
			XSSFRow xssfRow = (XSSFRow) row;
			System.out.println(changeCellToString(xssfRow.getCell(0), isDecimal));
			System.out.println(changeCellToString(xssfRow.getCell(1), isDecimal));
			System.out.println(changeCellToString(xssfRow.getCell(2), true));
			System.out.println(changeCellToString(xssfRow.getCell(3), true));
		}
	}
	
	private String changeCellToString(XSSFCell cell, Boolean isDecimal) {
        String returnValue = "";
        if (null != cell) {
            switch (cell.getCellType()) {
                case XSSFCell.CELL_TYPE_NUMERIC: // 数字
                    if (isDecimal) {
                        returnValue = String.valueOf(cell.getNumericCellValue());
                    } else {
                        DecimalFormat df = new DecimalFormat("0");
                        String whatYourWant = df.format(cell.getNumericCellValue());
                        returnValue = whatYourWant;
                    }

                    break;

                case XSSFCell.CELL_TYPE_STRING: // 字符串

                    returnValue = cell.getStringCellValue();

                    break;

                case XSSFCell.CELL_TYPE_BOOLEAN: // 布尔

                    Boolean booleanValue = cell.getBooleanCellValue();

                    returnValue = booleanValue.toString();

                    break;

                case XSSFCell.CELL_TYPE_BLANK: // 空值

                    returnValue = "";

                    break;

                case XSSFCell.CELL_TYPE_FORMULA: // 公式

                    returnValue = cell.getCellFormula();

                    break;

                case XSSFCell.CELL_TYPE_ERROR: // 故障

                    returnValue = "";

                    break;

                default:
                    System.err.println("未知类型");
                    break;
            }
        }
        return returnValue;
    }
	
	private String changeCellToString(HSSFCell cell, Boolean isDecimal) {
        String returnValue = "";
        if (null != cell) {
            switch (cell.getCellType()) {
                case XSSFCell.CELL_TYPE_NUMERIC: // 数字
                    if (isDecimal) {
                        returnValue = String.valueOf(cell.getNumericCellValue());
                    } else {
                        DecimalFormat df = new DecimalFormat("0");
                        String whatYourWant = df.format(cell.getNumericCellValue());
                        returnValue = whatYourWant;
                    }
                    break;
                case XSSFCell.CELL_TYPE_STRING: // 字符串
                    returnValue = cell.getStringCellValue();
                    break;
                case XSSFCell.CELL_TYPE_BOOLEAN: // 布尔
                    Boolean booleanValue = cell.getBooleanCellValue();
                    returnValue = booleanValue.toString();
                    break;
                case XSSFCell.CELL_TYPE_BLANK: // 空值
                    returnValue = "";
                    break;
                case XSSFCell.CELL_TYPE_FORMULA: // 公式
                    returnValue = cell.getCellFormula();
                    break;
                case XSSFCell.CELL_TYPE_ERROR: // 故障
                    returnValue = "";
                    break;
                default:
                    System.err.println("未知类型");
                    break;
            }
        }
        return returnValue;
    }
	
    private String getSuffix(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        if (path.contains(".")) {
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
        return null;
    }
    
    public static void main(String[] args) {
		System.out.println(System.getProperty("java.io.tmpdir"));
	}

}
