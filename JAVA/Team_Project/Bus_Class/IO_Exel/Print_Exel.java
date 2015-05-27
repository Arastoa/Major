package IO_Exel;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Print_Exel {
	public static void print_timeTable(String file_name,final int sheet_no){
		Workbook wb=null;
		Sheet sheet=null;
		
		try{
			wb=Workbook.getWorkbook(new File(file_name));
			
			//file_name인 xls문서를 읽어서 화면에 출력하는 메소드
			sheet=wb.getSheet(sheet_no);
			for(int i=0 ; i< sheet.getColumn(1).length  ; i++){
					
				for(int j=0 ; j<sheet.getRow(0).length; j++){
	                   System.out.print(sheet.getCell( j, i).getContents()+"\t");
				}
				System.out.println("");
			}

		}catch(Exception e){
			e.getMessage(); 
		}
	}
	
	//book에서 사용할 행선지 입력받을 때 확인용
	public static String destination_list(final int sheet_no) {
		Workbook wb=null;
		Sheet sheet=null;
			
		try{
			wb=Workbook.getWorkbook(new File("전체터미널.xls"));
			sheet=wb.getSheet(sheet_no);
			String destination = new String();
				
				//4번째줄의 i열을 읽어들임 시트의 모든 행선지를 concat으로 연결해서 나중에 contain으로 활용
				for(int i=1 ; i< sheet.getRow(4).length  ; i++){
						destination=destination.concat(sheet.getCell(i,4).getContents()+" ");
				}
					return destination;
		}catch(Exception e){
			e.getMessage(); 
			return null;
		}
	}
	
	public static int get_price(final int sheet_no,String dest){
		Workbook wb=null;
		Sheet sheet=null;
		int price=1;
		
			try {
				wb=Workbook.getWorkbook(new File("전체터미널.xls"));
				sheet=wb.getSheet(sheet_no);
				
				//6번째 줄의 i칸을 읽어들임 맞는것 찾으면 설정하고 return
				for(int i=1 ; i< sheet.getRow(4).length  ; i++)
					if(sheet.getCell(i,4).getContents().equals(dest)){
						price=Integer.parseInt(sheet.getCell(i,6).getContents());
						break;
					}
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return price;
	}
	
	public static int get_drivingTime(final int sheet_no,String dest){
		Workbook wb=null;
		Sheet sheet=null;
		int time=0;
			try {
				wb=Workbook.getWorkbook(new File("전체터미널.xls"));
				sheet=wb.getSheet(sheet_no);
				//소요시간 설정
				for(int i=1 ; i< sheet.getRow(4).length  ; i++)
					if(sheet.getCell(i,4).getContents().equals(dest)){
						time=Integer.parseInt(sheet.getCell(i,5).getContents());
						break;
					}
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return time;
	}
	
	public static String get_timeTable(final int sheet_no,String dest){
		Workbook wb=null;
		Sheet sheet=null;
			
		try{
			wb=Workbook.getWorkbook(new File("전체터미널.xls"));
			sheet=wb.getSheet(sheet_no);
			String timeTable = new String();
			
			int dest_po=0;
			//행선지의 시간표를 concat으로 연결 나중에 contain으로 활용
			for( ; dest_po< sheet.getRow(4).length  && !sheet.getCell(dest_po,4).getContents().equals(dest) ; dest_po++){}
			
			for(int i=0 ; i<sheet.getColumn(dest_po).length ; i++){
				timeTable=timeTable.concat(sheet.getCell(dest_po,i).getContents());
			}
			return timeTable;
		}catch(Exception e){
			e.getMessage(); 
			return null;
		}
	}
}
