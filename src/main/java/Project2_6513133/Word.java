/*1.Panithan Kunsuntrontham 6513133 
  2. Thunyaphat Permsup 6513167 
  3.Mattana Olarikded 6513173 
  4.Suphanai chalood 6513176 */
package Project2_6513133;

import java.util.TreeSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
 class Word {
     private LinkedHashSet<String> Words;
     private Scanner scan1;
     private String word1,word2;
     private boolean running = true;
     private Graph<String, DefaultWeightedEdge> Word_graph ;
     public Word(LinkedHashSet<String> W){
        this.Words = W;
        this.scan1 = new Scanner(System.in);
        this.Word_graph=this.create_graph();
    }
    public void Search(){
        System.out.printf("Search = \n");
        String input = this.scan1.next();
        TreeSet<String> temp = new TreeSet<>();
        System.out.print("=".repeat(50)+"Available Words"+"=".repeat(50)+"\n");
        int i =0;
        for(String S: Words){
            if(S.toLowerCase().contains(input.toLowerCase())){
               temp.add(S);
            }
        }
       for(String S: temp){
            if(S.toLowerCase().contains(input.toLowerCase())){
               System.out.printf("%-9s",S);  
                if(i%10==0&&i!=0){
                    System.out.printf("\n");
                    i=0;
                }else{
                    i++;  
                }
            }
        }
    }
    public void menu(){
        System.out.printf("\nEnter Menu >> (S = search, L = ladder, Q = quit)\n");
        String input = this.scan1.next();
        switch(input.toLowerCase()){
            case "s":
                this.Search();
                break;
            case "l":
                this.Ladder();               
                break;
            case "q":
                this.running=false;
                break;
            default:
              System.out.printf("Invaild Try Again!");
              break;
        }
    }
    public void Ladder(){
        System.out.printf("Enter 5-letter word %d\n",1);
        this.word1 = this.input_word(this.scan1.next(),1);
        System.out.printf("Enter 5-letter word %d\n",2);
        this.word2 = this.input_word(this.scan1.next(),2);
        this.Transform();
    }
    public Graph<String, DefaultWeightedEdge> create_graph(){
        SimpleWeightedGraph<String, DefaultWeightedEdge> SWG =  new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        List<String> temp = new ArrayList<>(this.Words);
        for(String S:this.Words){
            SWG.addVertex(S);
        }
        //elevator
        for(int i=0; i<temp.size(); i++){
            for(int j=0 ; j<i; j++){
                if(this.ArePermutations(temp.get(i),temp.get(j) )&&!temp.get(i).equalsIgnoreCase(temp.get(j))){
                   Graphs.addEdgeWithVertices(SWG, temp.get(i), temp.get(j), 0.0);
                }
            }
        }
        //ladder
        for(String S:this.Words){//Each word in LinkedHashset
                char[] chars = S.toCharArray();
                for(int i=0; i<S.length(); i++){//Each character in words
                    char org = chars[i];
                    for(char ab='a';ab <='z'; ab++){//Each alphabet
                        if(ab!=org){
                            chars[i]=ab;
                            String new_word = new String(chars);
                            if(this.Words.contains(new_word)){
                               Graphs.addEdgeWithVertices(SWG, S, new_word, this.cost(S, new_word));
                            }
                        }
                        chars[i]=org;
                    }
                }
                
        }
           
      
        return SWG;
    }
    public boolean ArePermutations(String l1,String l2){
        if(l1.length()!=l2.length()){
            return false;
        }
      String[]W1= l1.split("");
      String[]W2= l2.split("");
      Arrays.sort(W1);
      Arrays.sort(W2);
      return Arrays.equals(W1, W2);
    }
    public void print_graph( GraphPath<String, DefaultWeightedEdge> gpath){
        List<String> Vertex = gpath.getVertexList();
         List<DefaultWeightedEdge> all_edges = gpath.getEdgeList();
         int transform_cost=0;
         System.out.printf("\n%s\n",Vertex.get(0));
        for(int i=1; i<Vertex.size(); i++){
            int cost = (int)this.Word_graph.getEdgeWeight(all_edges.get(i-1));
            if(cost==0){
                 System.out.printf("%-1s (%-8s +0)\n",Vertex.get(i),"elevator");
            }else{
                System.out.printf("%-1s (%-8s +%d)\n",Vertex.get(i),"Ladder",(int)this.Word_graph.getEdgeWeight(all_edges.get(i-1)));
                transform_cost +=((int)this.Word_graph.getEdgeWeight(all_edges.get(i-1)));
            }
        }
      System.out.printf("\nTranformation cost = %d\n",transform_cost);
    }
    public void Transform(){
        if (this.Word_graph.containsVertex(this.word1) && this.Word_graph.containsVertex(this.word2))
	{
            ShortestPathAlgorithm<String, DefaultWeightedEdge> shpath;
            
            shpath = new DijkstraShortestPath<>(this.Word_graph);
             GraphPath<String, DefaultWeightedEdge> gpath = shpath.getPath(this.word1, this.word2);
             if(gpath!=null){
                 this.print_graph(gpath); 
             }else{
                 System.out.printf("\n\nCannot Tranform %s to %s\n\n",this.word1,this.word2);
             }
            
    }
    }
    public double cost(String l1,String l2){
        if(l1.compareToIgnoreCase(l2)<=0){
         return -(l1.compareToIgnoreCase(l2));
        }
         return l1.compareToIgnoreCase(l2);  
    }
    public boolean get_running(){
        return this.running;
    }
    public String input_word(String l,int n){
        while(!this.check(l)){
            System.out.printf("Enter 5-letter word %d\n",n);
            l = this.scan1.next();
        }
        return l;
    }
    public boolean check(String l){
        if(l.length()!=5){
            System.out.printf("Length of word is not equal to 5. Try Again!\n");
            return false;
        }
        for(String S:this.Words){
            if(S.equalsIgnoreCase(l)){
                return true;
            }
        }
         System.out.printf("Invaild Input Try Again!\n");
        return false;
    }
}
