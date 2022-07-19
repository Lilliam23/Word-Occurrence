package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



/**
 * 
 * @author lilliamcastro
 *
 */



public class Word_OccurenceDB extends Application
{

  
	/**
	 * 
	 * @override 
	 * @param Stage
	 * @param stage
	 * 
	 */
	


   @Override
   public void start(Stage stage)
   {
   Scene scene = new Scene(new Group());
   TextArea ta = new TextArea();

   
   

   // Prepare scene
   

   scene.setFill(Color.web("#8B008B"));
   scene.getWindow();

   
   

   // Label

           Label lbl = new Label();
           lbl.setLayoutX(300);
           lbl.setLayoutY(300);


 
      		
    // Button 				
  
   Button btnStart = new Button("Start Text Analyzer");
           btnStart.setLayoutX(600);
           btnStart.setOnAction(e -> {
               try {
            	   Word_OccurenceDB();
            	   
               } catch (Exception e1) {
                   e1.printStackTrace();
               }
         
              
           });
           

   // Prepare the Vertical Box

   VBox vbox = new VBox();
   vbox.setMaxWidth(700);
   vbox.setSpacing(5);
   vbox.layout();
   vbox.setPadding(new Insets(10, 10,10, 10));
  
           vbox.getChildren().addAll(lbl, ta,btnStart);
     
   ((Group) scene.getRoot()).getChildren().addAll(vbox);


   // Prepare the Stage


   stage.setScene(scene);
   stage.setTitle("Word Occurrences");
   stage.setWidth(700);
   stage.setHeight(550);
   stage.show();
   
  
   
   }

private void Word_OccurenceDB () throws Exception
{

int choice;
do{
//A scanner object to take input word from user.
Scanner in = new Scanner(System.in);
try
{
//Asking user to enter a word.
	System.out.println("Search for word:");
	String word = in.next();

//Loading the jdbc driver.
	Class.forName("com.mysql.jdbc.Driver");
	
//Establishing Connection.
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost/wordOccurences","root","8528");
	PreparedStatement ps = con.prepareStatement("insert into word (words) values(?)");
		ps.setString(1,word);
		ps.executeUpdate();
		System.out.println("Database after addition of your word");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from word");
			while(rs.next()){
				System.out.println(rs.getString(1));
			}

			con.close();
	}
	catch(ClassNotFoundException |SQLException c){
		System.out.println(c.getMessage());
	}
		System.out.println("Do you want to enter more word(1 for yes 0 for no)");
			choice = in.nextInt();
			}while(choice==1);
//Calculating Word Frequency Section.
		System.out.println("Frequency of each word present in the database is:");
//Creating a map to store the word and corresponding frequency.
		Map<String,Integer> frequency = new LinkedHashMap<>();
//Same as word storing section accessing the database and get the resultset.
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/wordOccurences","root","8528");
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("Select * from word");
				while(rs.next()){
					String s = rs.getString(1);
					if(frequency.get(s)==null){
						frequency.put(s,1);
					}
					else{
						frequency.put(s,frequency.get(s)+1);
					}
				}
//Closing the database connection.
				con.close();
			}
			catch(ClassNotFoundException | SQLException s){
				System.out.println(s.getMessage());
			}
//Printing the words with it's frequency.
			Set<String> key = frequency.keySet();
			for(String k:key){
				System.out.println("Word: "+k+" frequency: "+frequency.get(k));
			}
}
}