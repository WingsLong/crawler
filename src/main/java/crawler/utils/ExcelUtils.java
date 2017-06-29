package crawler.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

/**
 * @author Handsomeli,yuanln,wangsk
 *
 */
@SuppressWarnings(value={"unchecked","deprecation"})
public class ExcelUtils {
	private String sheetTitle = "";
	private String sheetName = "data";

	private HSSFWorkbook wb;

	private HSSFSheet sheet;

	private HSSFRow row;

	private HSSFCell cell;

	private HSSFFont font;

	private HSSFCellStyle cellStyle;

	private FileOutputStream fileOut;
	
	private int titleStartrow = 0;
	private int dataStartrow = 0;
	
	private String dirPath="";

	public ExcelUtils() {
		wb = new HSSFWorkbook();
	}

	public static void main(String[] args)
	{
		ArrayList<String> row1 = new ArrayList<String>();
		row1.add("2009-01-24");
		row1.add("广州");
		row1.add("24%");
		row1.add("35.3999");
		ArrayList<String> row2 = new ArrayList<String>();
		row2.add("20090124");
		row2.add("23.esfi9er");
		row2.add("6606,489");
		row2.add("333333333.3333");
		ArrayList<String> row3 = new ArrayList<String>();
		ArrayList resultList = new ArrayList();
		resultList.add(row1);
		resultList.add(row2);
		
		List data = new ArrayList();
		
		Title title1 = new Title("分公司",2,1);
		Title title2 = new Title("时间",2,1);
		
		Title title3 = new Title("wap1x",1,3);
		Title title4 = new Title("吞吐量",1,1);
		Title title5 = new Title("时长",1,1);
		Title title6 = new Title("活跃用户数",1,1);

		Title title7 = new Title("evdo",1,3);
		Title title8 = new Title("吞吐量",1,1);
		Title title9 = new Title("时长",1,1);
		Title title10 = new Title("活跃用户数",1,1);
		ArrayList headRow1 = new ArrayList();
		headRow1.add(title1);
		headRow1.add(title2);
		headRow1.add(title3);
		headRow1.add("");
		headRow1.add("");
		headRow1.add(title7);
		headRow1.add("");
		headRow1.add("");
		ArrayList headRow2 = new ArrayList();
		headRow2.add("");
		headRow2.add("");
		headRow2.add(title4);
		headRow2.add(title5);
		headRow2.add(title6);
		headRow2.add(title8);
		headRow2.add(title9);
		headRow2.add(title10);

		ArrayList headRows = new ArrayList();
		headRows.add(headRow1);
		headRows.add(headRow2);
		
		for (ArrayList item : (List<ArrayList>)resultList) {
			
			data.add(item.toArray());
		}
//		log.debug(headRow);
		ExcelUtils excelUtils = new ExcelUtils();
		excelUtils.setTitleStartrow(1);
		excelUtils.setDataStartrow(4);
		excelUtils.createSheet(headRows,null);
		excelUtils.setValueToRow("c:\\a.xls", data);
	}
	public void setValueToRow(String excelName, List list) {
		
		excelName = dirPath + excelName;
		System.out.println("生成文件的路径是：" + excelName);
		Object[] obj;
		try {
			for (int i = this.dataStartrow; i < list.size()+this.dataStartrow; i++) {
				row = sheet.createRow(i + 1);
				obj = (Object[]) list.get(i-this.dataStartrow);
				this.createCell(row, obj);
			}
			fileOut = new FileOutputStream(excelName);
			wb.write(fileOut);

		} catch (Exception ex) {
			System.out.print("生成报表有误:" + ex);
			ex.printStackTrace();
		} finally {
			try {
				fileOut.flush();
				fileOut.close();
			} catch (Exception e) {
				System.out.println("ExcelUtil.setValueToRow()");
			}
		}
	}

	//write by wsk 2009-7-1 for export multi-sheet excel
	public void outputExcel(String excelName, List<String> sheetnames, List<String> header, List<List<List<String>>> titles, List<List<List>> data)
	{
		for(int i=0; i<sheetnames.size(); i++)
		{
			sheetName=sheetnames.get(i);
			this.createSheet(header.get(i), titles, i);
			this.setValueToRow(data.get(i));
		}
		try {
			fileOut = new FileOutputStream(excelName);
			wb.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//write by wsk for multi-sheet
	private void setValueToRow(List<List> data) {		
		
		List obj;
		try {
			int k=0, max=0;
			for (int i = 0; i < data.size(); i++) {
				row = sheet.createRow(i + 2);
				obj = data.get(i);
				k = obj.size();
				if(k>max)max=k;
				this.createCell(row, obj.toArray());
			}
			for (int i = 0; i < max; i++) {
//				System.out.println("k=" + i);
				sheet.autoSizeColumn(( short ) i );
			}
			
		} catch (Exception ex) {
			//System.out.print("生成报表有误:" + ex);
			ex.printStackTrace();
		}
	}
	
//	write by wsk for multi-sheet
	private void createSheet(String header, List<List<List<String>>> titles, int no) {
		try {
			sheet = wb.createSheet(sheetName);
			sheet.setDisplayGridlines(false);
			if(titles!=null){
				
				font = wb.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				
				cellStyle = wb.createCellStyle();
				cellStyle.setFont(font);
				
				row = sheet.createRow(0);//标题
				sheet.addMergedRegion(new Region(0, (short)0, 0, (short)(titles.get(0).get(0).size()-1)));
				cell = row.createCell((short)0);
				cell.setCellStyle(cellStyle);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(header);
				
				font.setColor(HSSFColor.DARK_BLUE.index);
				
				cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); // 设置底线和颜色
				cellStyle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM); // 设置左边线和颜色
				cellStyle.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM); // 设置右边线和颜色
				cellStyle.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM); // 设置上面线和颜色
				cellStyle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
				

				
				List<List<String>> titletype=null;
				List<String> titlerow=null;
				for(int i=1; i<=titles.size(); i++)
				{
					titletype=titles.get(i-1);			//title or kpi
					titlerow=titletype.get(no);			//sheet type
					row = sheet.createRow(i);
					cellStyle.setWrapText(true);
					
					for (int j = 0; j < titlerow.size(); j++) {
						cell = row.createCell((short) j);
						cell.setCellStyle(cellStyle);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						cell.setCellValue(titlerow.get(j));
					}
				}
			}
		} catch (Exception ex) {
			//System.out.print(ex);
			ex.printStackTrace();
		}
	}
	
	public void createSheet(List list,HSSFCellStyle cellStyle) //add by yuan
	{
		try {
			sheet = wb.createSheet(sheetName);
			sheet.setDisplayGridlines(false);
			if (cellStyle == null) {
				font = wb.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				font.setColor(HSSFColor.DARK_BLUE.index);
				
				cellStyle = wb.createCellStyle();
				cellStyle.setFont(font);
				cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); // 设置底线和颜色
				cellStyle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM); // 设置左边线和颜色
				cellStyle.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM); // 设置右边线和颜色
				cellStyle.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM); // 设置上面线和颜色
				cellStyle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
			}
			
			row = sheet.createRow(0);//标题
			sheet.addMergedRegion(new Region(0, (short)0, 0, (short)(((List)list.get(0)).size()-1)));
			
			cell = row.createCell((short)0);
			cell.setCellStyle(cellStyle);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(sheetTitle);
			
			int s = this.titleStartrow;
			int cols = ((List)list.get(0)).size();
//			List<Title> titles = new ArrayList();
			for(int j = s; j < list.size()+this.titleStartrow; j++) {
				HSSFRow headerRow = sheet.createRow((short)j );
				for(int i = 0;i < cols;i++)
				{	
					HSSFCell headerCell = headerRow.createCell((short) i);
					Object object = ((List)list.get(j-this.titleStartrow)).get(i);
					if (object instanceof Title) {
						Title title = (Title)object;
						sheet.addMergedRegion(new Region(j,(short)i,j+title.rowSpan-1,(short)(i+title.colSpan-1)));
					}
//					headerCell.setEncoding(HSSFCell.ENCODING_UTF_16);
//					System.out.println(object.toString()+"  "+StringOperator.getEncoding(object.toString()));
					if(StringOperator.getEncoding(object.toString()).equals("UTF-8"))
					{
						headerCell.setCellValue(StringOperator.UTF2GB(object.toString()));
//						System.out.println(object.toString()+"  "+new String(object.toString().getBytes(""),""));
					}
					else if(StringOperator.getEncoding(object.toString()).equals("ISO8859_1"))
						headerCell.setCellValue(StringOperator.ISO2GB(object.toString()));
					else
						headerCell.setCellValue(object.toString());
					headerCell.setCellStyle(cellStyle);
				}
				s++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void createSheet(Object[] firstRowValue) {
		try {
			sheet = wb.createSheet(sheetName);
			sheet.setDisplayGridlines(false);
			if(firstRowValue!=null){
						
				font = wb.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				font.setColor(HSSFColor.DARK_BLUE.index);
				
				cellStyle = wb.createCellStyle();
				cellStyle.setFont(font);
				cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); // 设置底线和颜色
				cellStyle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM); // 设置左边线和颜色
				cellStyle.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM); // 设置右边线和颜色
				cellStyle.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM); // 设置上面线和颜色
				cellStyle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
				
				row = sheet.createRow(0);//标题
				sheet.addMergedRegion(new Region(0, (short)0, 0, (short)(firstRowValue.length-1)));
				
				cell = row.createCell((short)0);
				cell.setCellStyle(cellStyle);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(sheetTitle);
				
				
				row = sheet.createRow(1);
				cellStyle.setWrapText(true);
				for (int i = 0; i < firstRowValue.length; i++) {
					cell = row.createCell((short) i);
					cell.setCellStyle(cellStyle);
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					cell.setCellValue((String)firstRowValue[i]);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void createCell(HSSFRow row, Object[] obj) {
		try {
			cellStyle = wb.createCellStyle();
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); // 设置底线和颜色
			cellStyle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM); // 设置左边线和颜色
			cellStyle.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM); // 设置右边线和颜色
			cellStyle.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM); // 设置上面线和颜色
			cellStyle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
			
			HSSFCellStyle percent_CellStyle = null;
			HSSFCellStyle number_CellStyle = null;
			HSSFCellStyle float_CellStyle = null;
			for (int i = 0; i < obj.length; i++) {
				cell = row.createCell((short) i);
				cell.setCellStyle(cellStyle);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//				cell.setCellValue(obj[i].toString());
				if(ValidateOperator.isFloat(obj[i].toString())==true)
				{
					Number n = NumberFormat.getInstance().parse(obj[i].toString());
					if(float_CellStyle==null)
					{
						float_CellStyle = wb.createCellStyle();
						float_CellStyle.setAlignment(cellStyle.ALIGN_CENTER);
						float_CellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); // 设置底线和颜色
						float_CellStyle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
						float_CellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM); // 设置左边线和颜色
						float_CellStyle.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
						float_CellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM); // 设置右边线和颜色
						float_CellStyle.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
						float_CellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM); // 设置上面线和颜色
						float_CellStyle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
						float_CellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
					}
					cell.setCellStyle(float_CellStyle);
					
					cell.setCellValue(n.doubleValue());
//					System.out.println("float="+n.doubleValue());
				}
				else if(ValidateOperator.isNumber(obj[i].toString())==true)
				{
					Number n = NumberFormat.getInstance().parse(obj[i].toString());
					if(number_CellStyle==null)
					{
						number_CellStyle = wb.createCellStyle();
						number_CellStyle.setAlignment(cellStyle.ALIGN_CENTER);
						number_CellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); // 设置底线和颜色
						number_CellStyle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
						number_CellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM); // 设置左边线和颜色
						number_CellStyle.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
						number_CellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM); // 设置右边线和颜色
						number_CellStyle.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
						number_CellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM); // 设置上面线和颜色
						number_CellStyle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
						number_CellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
					}
					cell.setCellStyle(number_CellStyle);
					
					cell.setCellValue(n.longValue());
//					System.out.println("number="+obj[i].toString());
				}
				else if(ValidateOperator.isPercent(obj[i].toString())==true)
				{
					NumberFormat nf = NumberFormat.getInstance();
					Number n = nf.parse(obj[i].toString());
					if(percent_CellStyle==null)
					{
						percent_CellStyle = wb.createCellStyle();
						percent_CellStyle.setAlignment(cellStyle.ALIGN_CENTER);
						percent_CellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM); // 设置底线和颜色
						percent_CellStyle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
						percent_CellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM); // 设置左边线和颜色
						percent_CellStyle.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
						percent_CellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM); // 设置右边线和颜色
						percent_CellStyle.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
						percent_CellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM); // 设置上面线和颜色
						percent_CellStyle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
						percent_CellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
					}
					cell.setCellStyle(percent_CellStyle);
					
//					System.out.println(HSSFDataFormat.getBuiltinFormats());
					cell.setCellValue(n.doubleValue()/100.00);
				}
				else
					cell.setCellValue(obj[i].toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public int getDataStartrow() {
		return dataStartrow;
	}

	public void setDataStartrow(int dataStartrow) {
		this.dataStartrow = dataStartrow-1;
	}

	public int getTitleStartrow() {
		return titleStartrow;
	}

	public void setTitleStartrow(int titleStartrow) {
		this.titleStartrow = titleStartrow;
	}
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetTitle() {
		return sheetTitle;
	}

	public void setSheetTitle(String sheetTitle) {
		this.sheetTitle = sheetTitle;
	}
}
