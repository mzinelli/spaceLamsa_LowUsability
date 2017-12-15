package com.mpu.spinv;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Excel {
	int rownum = 1;
	int cellnum = 0;
	
	HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet("DadosFisiologicos");
	
	Cell cell;
	Row row;
	

	public void plan(){
		
		row = sheet.createRow(1);
		
		cell = row.createCell(cellnum++);	
		cell.setCellValue(Arduino.bpmtest);
		cell = row.createCell(cellnum++);	
		cell.setCellValue(Arduino.tempTest);
		cell = row.createCell(cellnum++);	
		cell.setCellValue(Arduino.grooveTest);
		cell = row.createCell(cellnum++);	
	}
	
	public void escrever(){
		cellnum = 0;
		
		row = sheet.createRow(rownum++);
		
		cell = row.createCell(cellnum++);	
		cell.setCellValue("BPM: "+Arduino.bpmtest);
		cell = row.createCell(cellnum++);	
		cell.setCellValue("Temperature: "+Arduino.tempTest);
		cell = row.createCell(cellnum++);	
		cell.setCellValue("Groove: "+Arduino.grooveTest);
		cell = row.createCell(cellnum++);	
		
		try{
			FileOutputStream out = new FileOutputStream("C:/Users/Miguel/Desktop/ResultadosJogoBaixaUsabilidade.xls");
			workbook.write(out);
			out.close();
			workbook.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
