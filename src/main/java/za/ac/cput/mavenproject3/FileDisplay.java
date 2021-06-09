/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.mavenproject3;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Archie Basika Siphondo
 * 216081076
*/


public class FileDisplay {
    
  private ObjectInputStream values;
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
    
    
    public FileDisplay(){
        
    }
    
    
    //Opening ser file
    public void openFile(){
        
        try{
            values = new ObjectInputStream( new FileInputStream ("stakeholder.ser"));
            System.out.println("--Ser File has been opened--");
        }
        catch(IOException ioe){
            System.out.println("Error opening file" + ioe.getMessage());
        }  
    }
    
   
    public void closeFile(){
        
       try{
            values.close();
        }
        catch(IOException ioe){
            System.out.println("Error closing file" + ioe.getMessage());
        }   
    }  
    
   
    public void readingObjects(){
        
        try{
            while(true){
                Object ans = (Stakeholder)values.readObject();
                if(ans instanceof Customer ){
                    customers.add((Customer) ans);
                } else {
                    suppliers.add((Supplier) ans);
                }
            }
            
        }
        catch(EOFException eofe){
            
        }
        catch(ClassNotFoundException ioe){
            System.out.println("Class error reading from file " + ioe);
        }
        catch(IOException ioe){
            System.out.println("Error reading from the file " + ioe);
        }   
        finally{
            closeFile();
            System.out.println("--Ser File has been close--");
        }
        
    }
    
    
    //Sorting ArralyLists
    public void sortArrays(){
        Collections.sort(customers, new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o1.getStHolderId().compareTo(o2.getStHolderId());
            }
        });
        
        Collections.sort(suppliers, new Comparator<Supplier>() {
            @Override
            public int compare(Supplier o1, Supplier o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
    
    
    
    public int ageOfCustomer(Customer o1){
        
        String str = o1.getDateOfBirth();
        String[] value = str.split("-");
        
        int year = Integer.parseInt(value[0]);
        int month = Integer.parseInt(value[1]);
        int day = Integer.parseInt(value[2]);
        
        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate currentDate = LocalDate.now();
        long years = ChronoUnit.YEARS.between(birthDate, currentDate);
        int ans = (int)years;
        
        return ans;
    }
    
    
    public String dateOfBirth(Customer o1){
        
        String str = o1.getDateOfBirth();
        String[] value = str.split("-");
        
        int year = Integer.parseInt(value[0]);
        int month = Integer.parseInt(value[1]);
        int day = Integer.parseInt(value[2]);
        
        String monthFormatted = "";
        
        switch(month){
            case 1: 
                monthFormatted = "Jan";
                break;
            case 2: 
                monthFormatted = "Feb";
                break;    
            case 3: 
                monthFormatted = "Mar";
                break;
            case 4: 
                monthFormatted = "Apr";
                break;
            case 5: 
                monthFormatted = "May";
                break;
            case 6: 
                monthFormatted = "Jun";
                break;
            case 7: 
                monthFormatted = "Jul";
                break;
            case 8: 
                monthFormatted = "Aug";
                break;
            case 9: 
                monthFormatted = "Sep";
                break;
            case 10: 
                monthFormatted = "Oct";
                break;
            case 11: 
                monthFormatted = "Nov";
                break;
            case 12: 
                monthFormatted = "Dec";
                break;
        }
        String answer = day + " " + monthFormatted + " " + year;
        return answer;
    }
    
  
    public int canRent(){
        
        int ans = 0;
        for(Customer value:customers){
            if(value.getCanRent()){
                ans = ans + 1;
            }
        }
        
        return ans;
    }
    
   
    public int cantRent(){
        
        int ans = 0;
        for(Customer value:customers){
            if(!value.getCanRent()){
                ans = ans + 1;
            }
        }
        
        return ans;
    }
    
    
    public void createTxtFiles(){
        try {
          File obj = new File("customerOutFile.txt");
          if (obj.createNewFile()){
              System.out.println("--File created: " + obj.getName()+ "--");
          } else {
              System.out.println("--TXT file " + obj.getName() +  " already exists--");
          }
        } catch (IOException e) {
          System.out.println("An error creating the file occured");  

        }
        
        try {
          File obj = new File("supplierOutFile.txt");
          if (obj.createNewFile()){
              System.out.println("--File created: " + obj.getName()+ "--");
          } else {
              System.out.println("--TXT file " + obj.getName() +  " already exists--");
          }
        } catch (IOException e) {
          System.out.println("An error creating the file occurred.");  

        }
    }
    
   
    public void WriteToFile() {
 
        
        String customerValues = "";
        for(Customer obj : customers){
            customerValues = customerValues + String.format("\n%-6s\t%-8s\t%-8s\t%-8s\t  %-8s", 
                    obj.getStHolderId(), obj.getFirstName(), obj.getSurName(), dateOfBirth(obj), ageOfCustomer(obj));
        }
        
        try {
            FileWriter objCustomer = new FileWriter("customerOutFile.txt");
            objCustomer.write("===========================CUSTOMERS===========================\n" +
                      "ID      Name            Surname         Date of birth     Age\n" +
                      "===============================================================\n"+
                      customerValues + "\n" + 
                      "\nNumber of customers who can rent:      " + canRent() +
                      "\nNumber of customers who cannot rent:   " + cantRent()
            );
            System.out.println("--customerOutFile.txt written to--");
           
            objCustomer.close();
        }
        catch (IOException e){
            System.out.println("An error writig to the file occurred.");    
        }
        
        
        String supplierValues = "";
        for(Supplier obj : suppliers){
            supplierValues = supplierValues + String.format("\n%-5s\t%-17s\t%-8s\t%-8s", 
                    obj.getStHolderId(), obj.getName(), obj.getProductType(), obj.getProductDescription());
        }
        
        try {
            FileWriter objSupplier = new FileWriter("supplierOutFile.txt");
            objSupplier.write("===========================SUPPLIERS===========================\n" +
                              "ID      Name                    Prod Type       Description\n" +
                              "================================================================\n"+
                               supplierValues 
            );
            System.out.println("--supplierOutFile.txt written to--");
            //Closing txt file
            objSupplier.close();
        }
        catch (IOException e){
            System.out.println("An error writig to the file occurred.");    
        }
    }
  
}
  
    

