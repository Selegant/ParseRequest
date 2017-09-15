package com.example.http.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 操作Excel表格的功能类
 */
public class APIAutoHS {
    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;

    /**
     * 读取Excel表格表头的内容
     * @param
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
//        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            //title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(row.getCell((short) i));
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     * @param
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readExcelContent(InputStream is) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        String stemp = "1";
        // 正文内容应该从第二行开始,第一行为表头的标题
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            if("1".equals(stemp)){
            	sb.append("**简要描述：**"+"\n\n");
            	sb.append("- "+getCellFormatValue(row.getCell((short) 1)).trim()+"\n\n\n");
            	sb.append("**请求方式：**"+"\n");
            	sb.append("- POST"+"\n\n");
            	sb.append("**参数：**"+"\n\n");
            	sb.append("|参数名|必选|类型|说明|"+"\n");
            	sb.append("|:----    |:---|:----- |-----   |"+"\n");
            	stemp = "2";
            	continue;
            }
            if("2".equals(stemp) ){
            	if("输出".equals(getCellFormatValue(row.getCell((short) 0)).trim())){
            		sb.append("\n"+" **返回示例**"+"\n\n");
            		sb.append(" **返回参数说明**"+"\n\n");
            		sb.append("|参数名|类型|说明|"+"\n");
            		sb.append("|:-----  |:-----|-----"+"\n");
//            		sb.append("|"+getCellFormatValue(row.getCell((short) 2)).trim()
//                				+"|"+getCellFormatValue(row.getCell((short) 3)).trim()
//                				+"|"+getCellFormatValue(row.getCell((short) 1)).trim()+"|");
//                	sb.append("\n");
            		stemp = "3";
            		continue;
            	}
            	if("输入".equals(getCellFormatValue(row.getCell((short) 0)).trim())){
            		continue;
            	}else{
            		if("".equals(getCellFormatValue(row.getCell((short) 1)).trim())){
            			continue;
            		}
        			sb.append("|"+getCellFormatValue(row.getCell((short) 3)).trim());
            		if("否".equals(getCellFormatValue(row.getCell((short) 5)).trim())){
            			sb.append("|是");
            		}else{
            			sb.append("|否");
            		}
            		sb.append("|"+getCellFormatValue(row.getCell((short) 4)).trim()
            				+"|"+getCellFormatValue(row.getCell((short) 1)).trim()+"|");
            		sb.append("\n");
            		continue;
            	}
            	
            }
            if("3".equals(stemp)){
            	if("请求示范".equals(getCellFormatValue(row.getCell((short) 0)).trim())){
            		sb.append("\n"+sb2.toString());
            		sb.append("\n"+"**请求URL：**"+"\n");
            		sb.append(getCellFormatValue(row.getCell((short) 1)).trim()+"\n");
            		stemp = "5";
            		continue;
            	}else{
            		if(!"".equals(getCellFormatValue(row.getCell((short) 1)).trim())
            				&&"响应数据".equals(getCellFormatValue(row.getCell((short) 1)))){
            			if(!"".equals(getCellFormatValue(row.getCell((short) 1)).trim())){
            				sb.append("|"+getCellFormatValue(row.getCell((short) 3)).trim()
                    				+"|"+getCellFormatValue(row.getCell((short) 4)).trim()
                    				+"|"+getCellFormatValue(row.getCell((short) 6)).trim()+"|");
                    		sb.append("\n");
                    		stemp = "4";
                    		continue;
            			}else{
            				continue;
            			}
            		}else{
            			continue;
            		}
            		
            	}
            }
            if("4".equals(stemp)){
            	if("请求示范".equals(getCellFormatValue(row.getCell((short) 0)).trim())){
            		sb.append("\n"+sb2.toString());
            		sb.append("\n"+"**请求URL：**"+"\n");
            		sb.append(getCellFormatValue(row.getCell((short) 1)).trim()+"\n");
            		stemp = "5";
            		continue;
            	}else{
            		if("".equals(getCellFormatValue(row.getCell((short) 3)).trim())){
            			continue;
            		}
            		sb.append("|"+getCellFormatValue(row.getCell((short) 3)).trim()
            				+"|"+getCellFormatValue(row.getCell((short) 4)).trim()
            				+"|"+getCellFormatValue(row.getCell((short) 6)).trim()+"|");
            		sb.append("\n");
            		continue;
            	}
            }
//            if("5".equals(stemp)){
//            	if("请求示范".equals(getCellFormatValue(row.getCell((short) 0)).trim())){
//            		sb.append("\n"+sb2.toString());
//            		sb.append("\n"+"**请求URL：**"+"\n");
//            		sb.append(getCellFormatValue(row.getCell((short) 1)).trim()+"\n");
//            		stemp = "6";
//            		continue;
//            	}else{
//            		continue;
//            	}
//            }
            if("5".equals(stemp)){
            	sb.append("\n");
            	sb.append("**备注**"+"\n\n");
            	sb.append("- 更多返回错误代码请看首页的错误代码描述");
            	break;
            }
//          str += getCellFormatValue(row.getCell((short) j)).trim() + "    ";
            content.put(i, str);
            str = "";
        }
        System.out.println(sb.toString());
        return content;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    
                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    public static void main(String[] args) {
        try {
            // 对读取Excel表格标题测试
            InputStream is = new FileInputStream("C:\\Users\\WT\\Desktop\\APITest.xls");
            APIAutoHS excelReader = new APIAutoHS();
            String[] title = excelReader.readExcelTitle(is);
//            System.out.println("获得Excel表格的标题:");
            for (String s : title) {
//                System.out.print(s + " ");
            }

            // 对读取Excel表格内容测试
            InputStream is2 = new FileInputStream("C:\\Users\\WT\\Desktop\\APITest.xls");
            Map<Integer, String> map = excelReader.readExcelContent(is2);
//            System.out.println("\n获得Excel表格的内容:");
            for (int i = 1; i <= map.size(); i++) {
//                System.out.println(map.get(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
    }
}