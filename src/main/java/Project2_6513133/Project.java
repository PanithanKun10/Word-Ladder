/*1.Panithan Kunsuntrontham 6513133 
  2. Thunyaphat Permsup 6513167 
  3.Mattana Olarikded 6513173 
  4.Suphanai chalood 6513176 */
package Project2_6513133;
import java.util.Scanner;
import java.util.LinkedHashSet;
public class Project {
    private Word W;
    private String index;
     public static void main(String Args[]){
         Project Proj = new Project();
         Proj.Init();
         Proj.MENU();
     }
     public void Init(){
        Scanner scan = new Scanner(System.in);
        String Path = "src/main/java/Project2_6513133/";
        String[] filename={"words_250.txt","words_5757.txt"};
        do{
           try{
        System.out.printf("Select Words File : 0 >> 250 words ,1 >> 5757 words\n");
          this.index = scan.next();
          if(Integer.parseInt(index)!=0 && Integer.parseInt(index)!=1){
               System.out.printf("Invalid Try Again!\n");
             continue; 
          }
          break;
            }catch(Exception e){
                System.out.println(e);    
            }
        }while(true);
        System.out.println("please wait...");  
       Input cal = new Input(Path,filename[Integer.parseInt(this.index)]);
      LinkedHashSet<String> Temp = new  LinkedHashSet<>();
        cal.Newtry(Temp);
        this.W = new Word(Temp);
     }
       public void MENU(){
         do{
           W.menu();  
         }
       while(W.get_running());
     }
      
}
