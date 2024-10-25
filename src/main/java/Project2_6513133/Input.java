/*1.Panithan Kunsuntrontham 6513133 
  2. Thunyaphat Permsup 6513167 
  3.Mattana Olarikded 6513173 
  4.Suphanai chalood 6513176 */
package Project2_6513133;

import java.util.Scanner;
import java.io.File;
import java.util.LinkedHashSet;
class Input {
    private String path  ;
    private String fname ;
    private Scanner scan;
    public Input(String p,String n){
        this.fname=n;
        this.path=p;
        this.scan= new Scanner(System.in);
    }
    public void Newtry(LinkedHashSet<String> temp){
      boolean opensuc = false;
      
      while(!opensuc){
          try{
            Scanner filescan = new Scanner(new File(path+fname));  
              opensuc = true;
              
            while(filescan.hasNext()){
                ReadFile(filescan.nextLine(),temp);
             
            }
             
          }catch(Exception e){
              System.out.println(e);
              System.out.print("Enter new Filename -->");
              fname = scan.next();
          }
          
        
      }
      
  }
   public void ReadFile(String line,LinkedHashSet<String>  temp){
       
        String word = line.trim();
        temp.add(word);
   }
   
}

