package crawler.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelImport {
	public static void main(String[] args) throws FileNotFoundException {
		HashMap<String,ArrayList<ArrayList>> result = ExcelImport.read("c:\\a.xls");
		for(Entry<String,ArrayList<ArrayList>> et:result.entrySet())
		{
			System.out.println(et.getKey()+"******************");
			System.out.println(StringOperator.list2String(et.getValue()));
		}
	}
	public static HashMap<String,ArrayList<ArrayList>> read(FileInputStream fis) {
		HashMap<String,ArrayList<ArrayList>> result = new HashMap<String,ArrayList<ArrayList>>();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			// 创建对工作表的引用。
			// 也可用getSheetAt(int index)按索引引用，
			// 在Excel文档中，第一张工作表的缺省索引是0，
			// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			// 读取左上端单元
//			System.out.println("sheet数目： " + workbook.getNumberOfSheets());
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 循环sheet
//				System.out.println("==========开始第 " + i + " 个sheet============");
				HSSFSheet childSheet = workbook.getSheetAt(i);
				String sheetName = workbook.getSheetName(i);
				ArrayList<ArrayList> data = new ArrayList<ArrayList>();
				result.put(sheetName, data);
				for (int r = 0; r < childSheet.getPhysicalNumberOfRows(); r++) {// 循环该子sheet row
//					System.out.println("childSheet " + (r + 1) + "行数:: "+ childSheet.getPhysicalNumberOfRows());
//					System.out.println("childSheet 单元格的数目:: "+ childSheet.getRow(r).getPhysicalNumberOfCells());
					ArrayList<String> row = new ArrayList<String>();
					int null_count = 0;
					for (short c = 0; c < childSheet.getRow(r).getPhysicalNumberOfCells()+null_count; c++) {// 循环该子sheet行对应的单元格项
						HSSFCell cell = childSheet.getRow(r).getCell(c);
//						System.out.println("cell:: " + cell);
						String value = null;

						if (cell == null)
						{
							System.out.println("cell == null");
							null_count++;
							row.add("");
							continue;
						}
//						System.out.println("cell.getCellType():: "+ cell.getCellType());
						switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC:
//								 System.out.println("v="+cell.getNumericCellValue());
								if (HSSFDateUtil.isValidExcelDate(cell.getNumericCellValue()) && (cell.getNumericCellValue()+"").indexOf("-")!=-1) {
									Date d = (Date) cell.getDateCellValue();
									DateFormat format = new SimpleDateFormat();
									value =format.format(d);
//									System.out.println("time="+value);
								} else
									value = ""+cell.getNumericCellValue();
								break;
							case HSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								value = "";
								break;
							default:
						}
						row.add(value);
//						System.out.println("value :: " + value);
					}
					data.add(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static HashMap<String,ArrayList<ArrayList>> read(String filename) throws FileNotFoundException {
		if(new File(filename).exists())
			return read(new FileInputStream(filename));
		else
			return new HashMap<String,ArrayList<ArrayList>>();
	}
}
