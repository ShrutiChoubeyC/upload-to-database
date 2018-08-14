package com.sample.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ServiceImpl {

	private String csvPaths[];
	List<File> csvFiles = new ArrayList<>();

	public List<File> excelToCSVConvert(Workbook wb) {
		try {
			csvPaths = new String[wb.getNumberOfSheets()];
			PrintStream out = null;

			int cellType;

			String csvFileName = null;
			File temp;
			FileOutputStream outFileStream;

			System.out.println("Number of sheet is: " + wb.getNumberOfSheets());
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				csvFileName = wb.getSheetName(i);
				csvPaths[i] = csvFileName;
				csvFileName = csvFileName + ".csv";
				Sheet sheet = wb.getSheetAt(i);
				temp = new File(csvFileName);
				outFileStream = new FileOutputStream(temp);

				if (i == 0) {

					out = new PrintStream(outFileStream, true, "UTF-8");

				}
				if (wb.getNumberOfSheets() > 0 && i > 0) {

					out = new PrintStream(outFileStream, true, "UTF-8");
				}

				for (Row row : sheet) {
					boolean firstCell = true;
					for (Cell cell : row) {
						cellType = cell.getCellType();
						Object value = handleCell(cell, cellType);
						if (!firstCell) {
							out.print(',');
						}

						String text = value.toString();
						out.print(text);
						firstCell = false;
					}

					out.println();
					csvFiles.add(i, temp);

				}
			}
			out.close();

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return csvFiles;

	}

	private Object handleCell(Cell cell, int cellType) {
		try {
			if (HSSFCell.CELL_TYPE_NUMERIC == cellType)
				return cell.getNumericCellValue();
			else if (HSSFCell.CELL_TYPE_STRING == cellType)
				return cell.getStringCellValue();
			else if (HSSFCell.CELL_TYPE_BOOLEAN == cellType)
				return cell.getBooleanCellValue();
			else if (HSSFCell.CELL_TYPE_BLANK == cellType)
				return "BLANK";
			else if (HSSFCell.CELL_TYPE_FORMULA == cellType)
				handleCell(cell, cell.getCachedFormulaResultType());
			else
				return ("Unknown cell type ");
		} catch (Exception e) {
			System.out.println("getMessage======" + e.getMessage());
			System.out.println("getStackTrace====" + e.getStackTrace());
		}

		return cellType;
	}

	public void insertToDb(List<File> csvFiles) {

		String[] csvPath = new String[csvFiles.size()];
		System.out.println(csvFiles.size()+" --csvFiles.size() ");
		List<String> statements = new ArrayList<>();
		statements.add(".mode csv");
		
		try {
			int i =0;
		File[] filesInDirectory = new File(Paths.get("").toAbsolutePath().toFile().getAbsolutePath().toString()).listFiles();
		for(File f : filesInDirectory){
		    String filePath = f.getAbsolutePath();
		    String fileExtenstion = filePath.substring(filePath.lastIndexOf(".") + 1,filePath.length());
		    if("csv".equals(fileExtenstion)){
		        System.out.println("CSV file found -> " + filePath);
		        csvPath[i] = csvFiles.get(i).getName().substring(0, csvFiles.get(i).getName().indexOf('.'));
				
		        String tableName = getOnlyStrings(csvPath[i]);
				statements.add(".import " + csvPath[i] + ".csv " + tableName);	
				System.out.println("Running index--"+ i);
				i++;
		    }
		    
		}  

	    Files.write(Paths.get("cmdFile.list"), statements);
		String[] cmd = { "sqlite3", "test.db", ".read cmdFile.list" };
		
		Runtime.getRuntime().exec(cmd);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	 public static String getOnlyStrings(String s) {
		    Pattern pattern = Pattern.compile("[^a-z A-Z 0-9]");
		    Matcher matcher = pattern.matcher(s);
		    String name = matcher.replaceAll("");
		    return name;
		 }
	

}
