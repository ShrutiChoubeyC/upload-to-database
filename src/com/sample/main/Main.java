package com.sample.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sample.service.ServiceImpl;

public class Main {

	private static Scanner input;

	public static void main(String[] args) {

		ServiceImpl serviceImpl = new ServiceImpl();
		String excelPath;
		input = new Scanner(System.in);
		Workbook wb;
		List<File> generatedCSV_Files;

		System.out.println("Please enter filename (only .xlsx file): \n");
		excelPath = input.nextLine();

		try {
			if (excelPath.contains(".xlsx") && !excelPath.isEmpty()) {
				wb = new XSSFWorkbook(excelPath);

			} else if (excelPath.isEmpty()) {
				excelPath = "multiple_Sheets.xlsx";
				wb = new XSSFWorkbook(excelPath);
			} else {
				wb = new XSSFWorkbook(excelPath + ".xlsx");
			}

			generatedCSV_Files = serviceImpl.excelToCSVConvert(wb);
			serviceImpl.insertToDb(generatedCSV_Files);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
