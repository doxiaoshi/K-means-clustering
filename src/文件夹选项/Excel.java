package �ļ���ѡ��;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
public class Excel extends JFrame{
	public static String s;
	public static void main(String[] args) {
		try {
			s=����.s;
			//��ȡָ���е�ֵ
			//readSpecifyColumns(new File(s));
			
			//��ȡָ���е�ֵ
			//readSpecifyRows(new File(s));
			
			//��ȡ���е�ֵ
			readRowsAndColums(new File(s));
			
			//����ȡ����ֵд�뵽TXT����xls��
			copy_excel(new File(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private static void readRowsAndColums(File file) throws BiffException, IOException {
		//1:����workbook
        Workbook workbook=Workbook.getWorkbook(new File(s)); 
        //2:��ȡ��һ��������sheet
        Sheet sheet=workbook.getSheet(0);
        //3:��ȡ����
        System.out.println("�У�"+sheet.getRows());
        System.out.println("�У�"+sheet.getColumns());
        for (int j = 1; j < sheet.getRows(); j++) {
			if(sheet.getCell(0, j).getContents()!="")
			{
			String cellinfo = (sheet.getCell(0, j).getContents())+","+(sheet.getCell(1, j).getContents())+","+(sheet.getCell(2, j).getContents());//��ȡ���ǵڶ������ݣ�û�б��⣬������ʼλ����forѭ���ж���
			System.out.println(cellinfo);
			}
			else
				break;
        }
        
        //���һ�����ر���Դ
        workbook.close();
	}
	
	/**
	 * 	����ȡ����ֵд�뵽TXT����xls��
	 * @param file
	 * @throws Exception
	 */
	public static void copy_excel(File file) throws Exception {
		FileWriter fWriter = null;
		PrintWriter out = null;
		String fliename = file.getName().replace(".xls", "");
		//fWriter = new FileWriter(file.getParent()+ "/agetwo.xls");//�����ʽΪ.xls
		fWriter = new FileWriter(file.getParent() + "/" + fliename + ".txt");//�����ʽΪ.txt
		out = new PrintWriter(fWriter);
		InputStream is = new FileInputStream(file.getAbsoluteFile());
		Workbook wb = null;
		wb = Workbook.getWorkbook(is);
		int sheet_size = wb.getNumberOfSheets();
		Sheet sheet = wb.getSheet(0);
		for (int j = 1; j < sheet.getRows(); j++) {
			String cellinfo = (sheet.getCell(0, j).getContents())+","+(sheet.getCell(1, j).getContents())+","+ (sheet.getCell(2, j).getContents());//��ȡ���ǵڶ������ݣ�û�б��⣬������ʼλ����forѭ���ж���
			out.println(cellinfo);
		}
		out.close();//�ر���
		fWriter.close();
		out.flush();//ˢ�»���
		System.out.println("�����ɣ�");
	}
}
