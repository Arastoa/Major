package File;

import java.io.IOException; // IOException를 선언하기 위해 선언
import java.io.File; // File을 불러오기 위해 선언
import jxl.read.biff.BiffException; // BiffException를 선언하기 위해 선언

import jxl.*; // 외부라이브러리인 엑셀 라이브러리의 사용위해 jxl패키지 선언
import java.util.Scanner; // Scanner 클래스의 사용을 위해 util패키지 선언

public class Excel { // Excel파일의 파일입출력을 담당하는 Excel 클래스
	public void Read(int a) throws BiffException, IOException {
		// Read 메소드 -> 각 조건에 맞는 미리 만들어 놓은 엑셀의 시트를 가져오는 메소드
		// BiffException:엑셀파일 호환안될때 발생(XLSX 파일 읽어오기 불가) , IOException:입출력 동작실패 또는 인터럽트시 발생
		
		Scanner scan = new Scanner(System.in); // Scanner 클래스의 사용을 위해 scan 객체 생성

		int check; // 터미널 내에 지역을 체크하기위한 변수 check
		Workbook workbook = null; // 엑셀 패키지 내에 있는 WorkBook클래스와 Sheet클래스의 사용을 위해  객체 생성			
		Sheet sheet = null;
		workbook = Workbook.getWorkbook(new File("전체터미널.xls"));
		// getWorkbook 메소드를 이용하여 미리 정의해 놓은 전체터미널.xls 엑셀파일을 workbook에 저장.

		if (workbook != null) {
			// 조건 : 엑셀파일에서 workbook을 인식했을 때

			if (a == 1) { // Read 메소드 호출시 매게변수에 값에 따라 터미널이 바뀐다.
				System.out.println("지역을 선택하세요");
				System.out.println("( 1.경기/경북/강원 2.전라남도 3.전라북도 4.충북/충남 )");
				// 각터미널에 따른 지역을 선택
				check = scan.nextInt(); // 지역선택하기 위해 값을 다시 입력받음
				if (check == 1)  // 입력한 값에 따라 고유지역으로 출력
					sheet = workbook.getSheet("센트럴경기"); // sheet에 getSheet 메소드를 	통해 sheet를 가져옴								
					// 엑셀파일은 모두 전체터미널.xls이고, 입력받은 값에 따라 설정해 놓은 지역으로 sheet 단위가 출력된다.
				 else if (check == 2) 
					sheet = workbook.getSheet("센트럴전남");
				 else if (check == 3) 
					sheet = workbook.getSheet("센트럴전북");
				 else if (check == 4) 
					sheet = workbook.getSheet("센트럴충북");
			}

			else if (a == 2) {
				System.out.println("지역을 선택하세요");
				System.out.println("( 1.경기도 2. 경상/강원도 3. 전라도 4.충청도 )");
				check = scan.nextInt();
				if (check == 1) 
					sheet = workbook.getSheet("남부경기");
				 else if (check == 2) 
					sheet = workbook.getSheet("남부경상");
				 else if (check == 3) 
					sheet = workbook.getSheet("남부전라");
				else if (check == 4) 
					sheet = workbook.getSheet("남부충청");
			} 
			
			else if (a == 3) {
				System.out.println("지역을 선택하세요");
				System.out.println("( 1.경부 2.영동 3.호남 )");
				check = scan.nextInt();
				if (check == 1) 
					sheet = workbook.getSheet("동서울경부");
				 else if (check == 2) 
					sheet = workbook.getSheet("동서울영동");
				 else if (check == 3) 
					sheet = workbook.getSheet("동서울호남");	
			}

			else if (a == 4) {
				System.out.println("지역을 선택하세요");
				System.out.println("( 1.고속버스지역 2.시외버스지역 )");
				check = scan.nextInt();
				if (check == 1) 
					sheet = workbook.getSheet("상봉고속");
				 else if (check == 2) 
					sheet = workbook.getSheet("상봉시외");
			} // 여기까지가 각 터미널별,지역별 전체시간표 출력!

			else if (a == 5) {
				// Read 메소드로 넘어온 매게변수가 5일때는 데이터 베이스에 있는 회원정보를 출력하는 경우!
				workbook = Workbook.getWorkbook(new File("회원현황.xls")); // workbook에 회원현황.xls을  저장한다.			 
				sheet = workbook.getSheet("회원현황"); // sheet에 회원현황 sheet를 저장한다.

				// 시트내에서 데이터가 시작되는 Row지정
				int nRowStartIndex = 0;
				// 시트내에서가 데이터가 끝나는 Row지정
				int nRowEndIndex = sheet.getColumn(0).length - 1;
				// getColumn 메소드를 사용하여 0번열의 행의 갯수를 반환한다. (최대 출력을결정) -> 행이 작은 열로
				// 선정시, 더 긴행을가진 값들은 안나옴을방지!

				// 시트내에서 데이터가 시작되는 Column지정
				int nColumnStartIndex = 0;
				// 시트내에서 데이터가 끝나는 Column지정
				int nColumnEndIndex = sheet.getRow(0).length - 1;
				// getRow 메소드를 사용하여 0번행의 열의 갯수를 반환한다. (최대 출력을결정) -> 열이 더 작은 행으로 선정시, 더 긴 열을가진 값들은 안나옴을방지!
				String cellprint = ""; // 출력을 담당하는 변수 선언 및 초기화

				for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
					for (int nColumn = nColumnStartIndex; nColumn <= nColumnEndIndex; nColumn++) {
						// 이중 for문으로 각 행과 열을 순서대로 설정
						cellprint = sheet.getCell(nColumn, nRow).getContents();
						// 출력변수에 getCell메소드를 사용해 각 셀의 위치를 선정 후 getcontents 메소드를 사용해 값을 불러옴!
						
						if (nColumn == 1) // 1번열의 내용을 만날시 출력을 제어하기 위해 Continue 사용!
							continue; // 1번열의 경우, 비밀번호를 담당하는 부분이기 때문에, 출력은 제어하고 엑셀파일에는 저장!
									
						System.out.print(cellprint);  // shellprint에 있는 값을 출력
						System.out.print("\t");   // 텝만큼 공간할애
					}
					System.out.println(); // 한칸띄움
				}

				return;
			}

			if (sheet != null) {

				// 시트내에서 데이터가 시작되는 Row지정
				int nRowStartIndex = 0;
				// 시트내에서 데이터가 끝나는 Row지정
				int nRowEndIndex = sheet.getColumn(1).length - 1;
				// getColumn 메소드를 사용하여 1번열의 행의 갯수를 반환한다. (최대 출력을결정) -> 행이 작은 열로 선정시, 더 긴행을가진 값들은 안나옴을방지!
				// 	엑셀파일에서 1번열의 행의갯수를 제일 길게 저장
		
				// 시트내에서 데이터가 시작되는 Column지정
				int nColumnStartIndex = 0;
				// 시트내에서 데이터가 끝나는 Column지정
				int nColumnEndIndex = sheet.getRow(4).length - 1;
				// getRow 메소드를 사용하여 4번행의 열의 갯수를 반환한다. (최대 출력을결정) -> 열이 더 작은 행으로 	선정시, 더 긴 열을가진 값들은 안나옴을방지!
				// 엑셀파일에서 4번행의 열의 갯수를 제일 길게 저장

				String cellprint = ""; // 출력할 변수 선언 및 초기화

				for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
					for (int nColumn = nColumnStartIndex; nColumn <= nColumnEndIndex; nColumn++) {
						// 이중 for문으로 각 행과 열을 순서대로 설정
						cellprint = sheet.getCell(nColumn, nRow).getContents();
						// 출력변수에 getCell메소드를 사용해 각 셀의 위치를 선정 후 getcontents 메소드를 사용해 값을 불러옴!
						System.out.print(cellprint); // cellprint변수의 값을 출력
						System.out.print("\t"); // 텝만큼 공간을 할애
					}
					System.out.println(); // 한칸 띄움
				}

			} else {
				System.out.println("시트불러오기에 실패하였습니다."); // 시트불러오기에 실패하였을 시 출력!
			}
		} else {
			System.out.println("워크북 불러오기에 실패하였습니다."); // 워크북(엑셀) 불러오기 실패하였을시 출력!
		}

	}

}
