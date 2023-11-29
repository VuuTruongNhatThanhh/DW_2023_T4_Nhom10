package com.dw.nhom10.dw_nhom10;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class ExtractData {
    public static void main(String[] args) {
    	DatabaseConfig config1 = new DatabaseConfig("db1");
    	String jdbcUrl = config1.getJdbcUrl();
    	String username = config1.getUsername();
    	String password = config1.getPassword();
        
        String link_save_toFile = null; 
        String source_link = null; 
        String element1 = null;
        String element2 = null;
        String element3 = null;
        String element4 = null;
        String element5 = null;
        String element6 = null;
        String element7 = null;
        String element8 = null;
        String element9 = null;
        String element10 = null;
        String element11 = null;
        String element12 = null;
        String element13 = null;
        String element14 = null;
        String element15 = null;
        String element16 = null;
        
        
   /**
    * 2. Connect DB   
    */
      try {
          Connection connection = DriverManager.getConnection(jdbcUrl, username, password);


	/**
	 * 3. Insert into log status SE (start extract)	 
	 */
          String sqlQuery1 = "INSERT INTO log (id_config, time_extract, id_date, status_extract) VALUES (1, TIME(NOW()), CURDATE(), 'SE');";

    /**
     * 4. Select source_link, element(1-16), link_save_excel from config  
     */
          String sqlQuery2 = "SELECT source_link FROM config";
          String sqlQuery3 = "SELECT element1 FROM config";
          String sqlQuery4 = "SELECT element2 FROM config";
          String sqlQuery5 = "SELECT element3 FROM config";
          String sqlQuery6 = "SELECT element4 FROM config";
          String sqlQuery7 = "SELECT element5 FROM config";
          String sqlQuery8 = "SELECT element6 FROM config";
          String sqlQuery9 = "SELECT element7 FROM config";
          String sqlQuery10 = "SELECT element8 FROM config";
          String sqlQuery11 = "SELECT element9 FROM config";
          String sqlQuery12 = "SELECT element10 FROM config";
          String sqlQuery13 = "SELECT element11 FROM config";
          String sqlQuery14 = "SELECT element12 FROM config";
          String sqlQuery15 = "SELECT element13 FROM config";
          String sqlQuery16 = "SELECT element14 FROM config";
          String sqlQuery17 = "SELECT element15 FROM config";
          String sqlQuery18 = "SELECT element16 FROM config";
          String sqlQuery19 = "SELECT link_save_excel FROM config";
     
          PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery1);
          PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2);
          PreparedStatement preparedStatement3 = connection.prepareStatement(sqlQuery3);
          PreparedStatement preparedStatement4 = connection.prepareStatement(sqlQuery4);
          PreparedStatement preparedStatement5 = connection.prepareStatement(sqlQuery5);
          PreparedStatement preparedStatement6 = connection.prepareStatement(sqlQuery6);
          PreparedStatement preparedStatement7 = connection.prepareStatement(sqlQuery7);
          PreparedStatement preparedStatement8 = connection.prepareStatement(sqlQuery8);
          PreparedStatement preparedStatement9 = connection.prepareStatement(sqlQuery9);
          PreparedStatement preparedStatement10 = connection.prepareStatement(sqlQuery10);
          PreparedStatement preparedStatement11 = connection.prepareStatement(sqlQuery11);
          PreparedStatement preparedStatement12 = connection.prepareStatement(sqlQuery12);
          PreparedStatement preparedStatement13 = connection.prepareStatement(sqlQuery13);
          PreparedStatement preparedStatement14 = connection.prepareStatement(sqlQuery14);
          PreparedStatement preparedStatement15 = connection.prepareStatement(sqlQuery15);
          PreparedStatement preparedStatement16 = connection.prepareStatement(sqlQuery16);
          PreparedStatement preparedStatement17 = connection.prepareStatement(sqlQuery17);
          PreparedStatement preparedStatement18 = connection.prepareStatement(sqlQuery18);
          PreparedStatement preparedStatement19 = connection.prepareStatement(sqlQuery19);
          
          int rowsAffected = preparedStatement1.executeUpdate();
          System.out.println("status SE đã được thêm vào bảng log");
          
          ResultSet resultSet2 = preparedStatement2.executeQuery();
          ResultSet resultSet3 = preparedStatement3.executeQuery();
          ResultSet resultSet4 = preparedStatement4.executeQuery();
          ResultSet resultSet5 = preparedStatement5.executeQuery();
          ResultSet resultSet6 = preparedStatement6.executeQuery();
          ResultSet resultSet7 = preparedStatement7.executeQuery();
          ResultSet resultSet8 = preparedStatement8.executeQuery();
          ResultSet resultSet9 = preparedStatement9.executeQuery();
          ResultSet resultSet10 = preparedStatement10.executeQuery();
          ResultSet resultSet11 = preparedStatement11.executeQuery();
          ResultSet resultSet12 = preparedStatement12.executeQuery();
          ResultSet resultSet13 = preparedStatement13.executeQuery();
          ResultSet resultSet14 = preparedStatement14.executeQuery();
          ResultSet resultSet15 = preparedStatement15.executeQuery();
          ResultSet resultSet16 = preparedStatement16.executeQuery();
          ResultSet resultSet17 = preparedStatement17.executeQuery();
          ResultSet resultSet18 = preparedStatement18.executeQuery();
          ResultSet resultSet19 = preparedStatement19.executeQuery();
          
          if (resultSet2.next()) {
              source_link = resultSet2.getString("source_link");
          }
          if (resultSet3.next()) {
              element1 = resultSet3.getString("element1");
          }
          if (resultSet4.next()) {
              element2 = resultSet4.getString("element2");
          }
          if (resultSet5.next()) {
              element3 = resultSet5.getString("element3");
          }
          if (resultSet6.next()) {
              element4 = resultSet6.getString("element4");
          }
          if (resultSet7.next()) {
              element5 = resultSet7.getString("element5");
          }
          if (resultSet8.next()) {
              element6 = resultSet8.getString("element6");
          }
          if (resultSet9.next()) {
              element7 = resultSet9.getString("element7");
          }
          if (resultSet10.next()) {
              element8 = resultSet10.getString("element8");
          }
          if (resultSet11.next()) {
              element9 = resultSet11.getString("element9");
          }
          if (resultSet12.next()) {
              element10 = resultSet12.getString("element10");
          }
          if (resultSet13.next()) {
              element11 = resultSet13.getString("element11");
          }
          if (resultSet14.next()) {
              element12 = resultSet14.getString("element12");
          }
          if (resultSet15.next()) {
              element13 = resultSet15.getString("element13");
          }
          if (resultSet16.next()) {
              element14 = resultSet16.getString("element14");
          }
          if (resultSet17.next()) {
              element15 = resultSet17.getString("element15");
          }
          if (resultSet18.next()) {
              element16 = resultSet18.getString("element16");
          }
          if (resultSet19.next()) {
             link_save_toFile = resultSet19.getString("link_save_excel");
          }
          
          
          
         
          resultSet2.close();
          resultSet3.close();
          resultSet4.close();
          resultSet5.close();
          resultSet6.close();
          resultSet7.close();
          resultSet8.close();
          resultSet9.close();
          resultSet10.close();
          resultSet11.close();
          resultSet12.close();
          resultSet13.close();
          resultSet14.close();
          resultSet15.close();
          resultSet16.close();
          resultSet17.close();
          resultSet18.close();
          resultSet19.close();
          
       
          
          preparedStatement1.close();
          preparedStatement2.close();
          preparedStatement3.close();
          preparedStatement4.close();
          preparedStatement5.close();
          preparedStatement6.close();
          preparedStatement7.close();
          preparedStatement8.close();
          preparedStatement9.close();
          preparedStatement10.close();
          preparedStatement11.close();
          preparedStatement12.close();
          preparedStatement13.close();
          preparedStatement14.close();
          preparedStatement15.close();
          preparedStatement16.close();
          preparedStatement17.close();
          preparedStatement18.close();
          preparedStatement19.close();
          

          

        try {
	/**
	 * 5. Connect to website from source_link 
	 */
            String url = source_link;
            Document document = Jsoup.connect(url).get();

 	/**
 	 *  6.	Use jsoup to choose the document of html (element)	
 	 */
            Elements ngayElements = document.select(element1);
            Elements kyVeElements = document.select(element2);
            Elements So1Elements = document.select(element3);
            Elements So2Elements = document.select(element4);
            Elements So3Elements = document.select(element5);
            Elements So4Elements = document.select(element6);
            Elements So5Elements = document.select(element7);
            Elements So6Elements = document.select(element8);
            Elements So7Elements = document.select(element9);
            Elements jp1Elements = document.select(element10);
            Elements jp2Elements = document.select(element11);
            Elements amountjp1Elements = document.select(element12);
            Elements amountjp2Elements = document.select(element13);
            Elements amount1Elements = document.select(element14);
            Elements amount2Elements = document.select(element15);
            Elements amount3Elements = document.select(element16);
           
            File excelFile = new File(link_save_toFile);
            Workbook workbook;
      /**
       *  7. Check file excel exist or not exist
       *     - if exist, read file excel and open it
       *     - if not exists, create new file excel     
       */
            if (excelFile.exists()) {
                FileInputStream fis = new FileInputStream(excelFile);
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new XSSFWorkbook();
            }

        /**
         * Check sheet in file excel is null or not null
         *  - null: create sheet vietlot
         *  - not null: remove all rows in this sheet
         */
            Sheet sheet = workbook.getSheet("vietlot");
            if (sheet == null) {
                sheet = workbook.createSheet("vietlot");
            } else {
            
                for (int i = sheet.getLastRowNum(); i >= 0; i--) {
                    sheet.removeRow(sheet.getRow(i));
                }
            }
            
            
            

         /**
          * 8. Write data to file excel
          */
            Iterator<Element> ngayIterator = ngayElements.iterator();
            Iterator<Element> kyVeIterator = kyVeElements.iterator();
            Iterator<Element> so1Iterator = So1Elements.iterator();
            Iterator<Element> so2Iterator = So2Elements.iterator();
            Iterator<Element> so3Iterator = So3Elements.iterator();
            Iterator<Element> so4Iterator = So4Elements.iterator();
            Iterator<Element> so5Iterator = So5Elements.iterator();
            Iterator<Element> so6Iterator = So6Elements.iterator();
            Iterator<Element> so7Iterator = So7Elements.iterator();
            Iterator<Element> jp1Iterator = jp1Elements.iterator();
            Iterator<Element> jp2Iterator = jp2Elements.iterator();
            Iterator<Element> amountjp1Iterator = amountjp1Elements.iterator();
            Iterator<Element> amountjp2Iterator = amountjp2Elements.iterator();
            Iterator<Element> amount1Iterator = amount1Elements.iterator();
            Iterator<Element> amount2Iterator = amount2Elements.iterator();
            Iterator<Element> amount3Iterator = amount3Elements.iterator();
            int rowIndex = 0;

            while (ngayIterator.hasNext() || kyVeIterator.hasNext() || so1Iterator.hasNext()
            		|| so2Iterator.hasNext() || so3Iterator.hasNext() || so4Iterator.hasNext()
            		|| so5Iterator.hasNext() || so6Iterator.hasNext() || so7Iterator.hasNext()
            		|| jp1Iterator.hasNext() || jp2Iterator.hasNext() || amountjp1Iterator.hasNext()
            		|| amountjp2Iterator.hasNext() || amount1Iterator.hasNext() || amount2Iterator.hasNext()
            		|| amount3Iterator.hasNext())  {
                Row row = sheet.createRow(rowIndex++);

                
                if (ngayIterator.hasNext()) {
                    Element ngayElement = ngayIterator.next();
                    Cell cell1 = row.createCell(0);
                    cell1.setCellValue(ngayElement.text());
                }

                
                if (kyVeIterator.hasNext()) {
                    Element kyVeElement = kyVeIterator.next();
                    Cell cell2 = row.createCell(1);
                    cell2.setCellValue(kyVeElement.text());
                }
                
             
                if (so1Iterator.hasNext()) {
                    Element so1Element = so1Iterator.next();
                    Cell cell3 = row.createCell(2);
                    cell3.setCellValue(so1Element.text());
                }
                
             
                if (so2Iterator.hasNext()) {
                    Element so2Element = so2Iterator.next();
                    Cell cell4 = row.createCell(3);
                    cell4.setCellValue(so2Element.text());
                }
                
             
                if (so3Iterator.hasNext()) {
                    Element so3Element = so3Iterator.next();
                    Cell cell5 = row.createCell(4);
                    cell5.setCellValue(so3Element.text());
                }
                
               
                if (so4Iterator.hasNext()) {
                    Element so4Element = so4Iterator.next();
                    Cell cell6 = row.createCell(5);
                    cell6.setCellValue(so4Element.text());
                }
                
                
                if (so5Iterator.hasNext()) {
                    Element so5Element = so5Iterator.next();
                    Cell cell7 = row.createCell(6);
                    cell7.setCellValue(so5Element.text());
                }
                
             
                if (so6Iterator.hasNext()) {
                    Element so6Element = so6Iterator.next();
                    Cell cell8 = row.createCell(7);
                    cell8.setCellValue(so6Element.text());
                }
             
                if (so7Iterator.hasNext()) {
                    Element so7Element = so7Iterator.next();
                    Cell cell9 = row.createCell(8);
                    cell9.setCellValue(so7Element.text());
                }
                
            
                if (jp1Iterator.hasNext()) {
                    Element jp1Element = jp1Iterator.next();
                    Cell cell10 = row.createCell(9);
                    cell10.setCellValue(jp1Element.text());
                }
                
               
                if (jp2Iterator.hasNext()) {
                    Element jp2Element = jp2Iterator.next();
                    Cell cell11 = row.createCell(10);
                    cell11.setCellValue(jp2Element.text());
                }
                
              
                if (amountjp1Iterator.hasNext()) {
                    Element amountjp1Element = amountjp1Iterator.next();
                    Cell cell12 = row.createCell(11);
                    cell12.setCellValue(amountjp1Element.text());
                }
                
               
                if (amountjp2Iterator.hasNext()) {
                    Element amountjp2Element = amountjp2Iterator.next();
                    Cell cell13 = row.createCell(12);
                    cell13.setCellValue(amountjp2Element.text());
                }
                
               
                if (amount1Iterator.hasNext()) {
                    Element amount1Element = amount1Iterator.next();
                    Cell cell14 = row.createCell(13);
                    cell14.setCellValue(amount1Element.text());
                }
                
            
                if (amount2Iterator.hasNext()) {
                    Element amount2Element = amount2Iterator.next();
                    Cell cell15 = row.createCell(14);
                    cell15.setCellValue(amount2Element.text());
                }
                
               
                if (amount3Iterator.hasNext()) {
                    Element amount3Element = amount3Iterator.next();
                    Cell cell16 = row.createCell(15);
                    cell16.setCellValue(amount3Element.text());
                }
            }

           
          /**
           * 9. Check data write to file excel
           */
            try (FileOutputStream fos = new FileOutputStream(link_save_toFile)) {
                workbook.write(fos);
                System.out.println("Dữ liệu đã được cập nhật vào file Excel thành công.");
          /**
           * If success, insert into log status CE      
           */
                String sqlQuery20 = "INSERT INTO log (id_config, time_extract, id_date, status_extract) VALUES (1, TIME(NOW()), CURDATE(), 'CE');";
                PreparedStatement preparedStatement20 = connection.prepareStatement(sqlQuery20);
                int rowsAffected2 = preparedStatement20.executeUpdate();
                System.out.println("status CE đã được thêm vào bảng log");
            } catch (Exception e) {
           /**
            * If not success, insert into log status FE
            */
            	String sqlQuery21 = "INSERT INTO log (id_config, time_extract, id_date, status_extract) VALUES (1, TIME(NOW()), CURDATE(), 'FE');";
                PreparedStatement preparedStatement21 = connection.prepareStatement(sqlQuery21);
                int rowsAffected3 = preparedStatement21.executeUpdate();
                System.out.println("status FE đã được thêm vào bảng log");
			}

            

        } catch (IOException e) {
            e.printStackTrace();
        }
       
      
        connection.close();

      
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
}
