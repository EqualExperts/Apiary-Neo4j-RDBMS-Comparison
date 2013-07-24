package com.ee.apiary.sql.core;

import com.ee.apiary.sql.hibernate.entities.DirectManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Created with IntelliJ IDEA.
 * User: anuj
 * Date: 26/6/13
 * Time: 1:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainClass {

	private static final int LEVEL_ONE = 1;
    private static final int LEVEL_TWO = 2;
    private static final int LEVEL_THREE = 3;
    private static final int LEVEL_FOUR = 4;
    private static final int LEVEL_FIVE = 5;
    private static final int LEVEL_SIX = 6;
    private static final int LEVEL_SEVEN = 7;
    private static final int LEVEL_EIGHT = 8;
    private static final int LEVEL_NINE = 9;
    private static final int LEVEL_TEN = 10;
    private static final int LEVEL_ELEVEN = 11;
	private static final int LEVEL_TWELVE = 12;
	private static final int LEVEL_THIRTEEN = 13;
	private static final int LEVEL_FOURTEEN = 14;
	private static final int LEVEL_FIFTEEN = 15;
	private static final int LEVEL_SIXTEEN = 16;
	private static final int LEVEL_SEVENTEEN = 17;
	private static final int LEVEL_EIGHTEEN = 18;
	private static final int LEVEL_NINETEEN = 19;
	private static final int LEVEL_TWENTY = 20;
    
    private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_ONE = 10;
    private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_TWO = 10;
    private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_THREE = 10;
    private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR = 10;
    private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_SIX = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_NINE = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_TEN = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_ELEVEN = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_TWELVE = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_THIRTEEN = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_FOURTEEN = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_FIFTEEN = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_SIXTEEN = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_SEVENTEEN = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHTEEN = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_NINETEEN = 10;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_TWENTY = 10;
	
	
	public static void main(String[] args) {

        SortedMap<Integer, OrgLevelData> orgDataAtLevel = null;
        try {
            orgDataAtLevel = compileOrgDataFromResources();
        System.out.println("Organisation data compiled");
        } catch (IOException e) {
            System.err.println(" IO Exception : " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        SqlOrgHierarchy sqlOrgHierarchy = new SqlOrgHierarchy.Builder(orgDataAtLevel).build();
        //sqlOrgHierarchy.checkForInConsistentData();
        System.out.println("Creating person objects....");
        SortedMap<Integer, OrgLevelData> levelWithPeople = sqlOrgHierarchy.toLevelWithPersonObjs(orgDataAtLevel);
        System.out.println("Creating direct managers....");
        List<DirectManager> directManagers = sqlOrgHierarchy.createDirectManagerRelationships(levelWithPeople);
        System.out.println("Saving data....");
        SqlOrgHierarchyDAO.saveOrgData(levelWithPeople, directManagers);
        System.out.println("Organisation data populated in DB");
    }

    private static SortedMap<Integer, OrgLevelData> compileOrgDataFromResources() throws IOException {

       // InputStream firstNamesStream = MainClass.class.getResourceAsStream("/firstNames.txt");
       // InputStream lastNamesStream = MainClass.class.getResourceAsStream("/lastNames.txt");
       //
       // BufferedReader firstNameReader = new BufferedReader(new InputStreamReader(firstNamesStream));
       // BufferedReader lastNameReader = new BufferedReader(new InputStreamReader(lastNamesStream));

        List<String> firstNames = new ArrayList<>();
        List<String> lastNames = new ArrayList<>();
        List<String> fullNames = new ArrayList<>();
        SortedMap<Integer, OrgLevelData> peopleAtLevel = new TreeMap<>();
     //   String name = null;
     //   while ((name = firstNameReader.readLine()) != null) {
     //       firstNames.add(name);
     //   }
     //   while ((name = lastNameReader.readLine()) != null) {
     //       lastNames.add(name);
     //   }
     //   for (String firstName : firstNames) {
     //       for (String lastName : lastNames) {
     //           fullNames.add(firstName + " " + lastName);
     //       }
     //   }
       
        for(int i = 1; i<=1500; i++){
        	firstNames.add("first"+i);
        }
        for(int i = 1; i<=1500; i++){
        	lastNames.add("last"+i);
        }
        for (String first : firstNames) {
			for (String last : lastNames) {
				fullNames.add(first + " " + last);
			}
		}
        firstNames.clear();
        lastNames.clear();
        System.out.println("Person name list created with size : " + fullNames.size());
        
/*        Case 1 : (Levels = 3, Manages Limit = 5)
 * 
 *  At Level 1 => 1
        At Level 2 => 1
        At Level 3 => 1
        At Level 4 => 1
        At Level 5 => 1
        At Level 6 => 1
        At Level 7 => 1
        At Level 8 => 1
        At Level 9 => 1
        At Level 10 => 1
        At Level 11 => 1
        At Level 12 => 1
        At Level 13 => 28
        At Level 14 => 160
        At Level 15 => 800
        
        Total => 1000
 * 
 * 
 * 
 * 
 * 
 * 
        At Level 1 => 1
        At Level 2 => 1
        At Level 3 => 1
        At Level 4 => 1
        At Level 5 => 1
        At Level 6 => 1
        At Level 7 => 1
        At Level 8 => 1
        At Level 9 => 1
        At Level 10 => 1
        At Level 11 => 30
        At Level 12 => 80
        At Level 13 => 280
        At Level 14 => 1600
        At Level 15 => 8000
        
        Total => 10000
*/
       // peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
       // peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 2), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
       // peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(2, 3), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));
       // peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(3, 4), DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));
       // peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(4, 5), DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
       // peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(5, 6),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));	
       // peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(6, 7),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));	
       // peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(7, 8),DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT));									
       // peopleAtLevel.put(LEVEL_NINE, new OrgLevelData(fullNames.subList(8, 9),DIRECTLY_MANAGES_LIMIT_LEVEL_NINE));	
       // peopleAtLevel.put(LEVEL_TEN, new OrgLevelData(fullNames.subList(9, 10),DIRECTLY_MANAGES_LIMIT_LEVEL_TEN));
       // peopleAtLevel.put(LEVEL_ELEVEN, new OrgLevelData(fullNames.subList(10, 40),DIRECTLY_MANAGES_LIMIT_LEVEL_ELEVEN));
       // peopleAtLevel.put(LEVEL_TWELVE, new OrgLevelData(fullNames.subList(40, 120),DIRECTLY_MANAGES_LIMIT_LEVEL_TWELVE));
       // peopleAtLevel.put(LEVEL_THIRTEEN, new OrgLevelData(fullNames.subList(120, 400),DIRECTLY_MANAGES_LIMIT_LEVEL_THIRTEEN));
       // peopleAtLevel.put(LEVEL_FOURTEEN, new OrgLevelData(fullNames.subList(400, 2000),DIRECTLY_MANAGES_LIMIT_LEVEL_FOURTEEN));
       // peopleAtLevel.put(LEVEL_FIFTEEN, new OrgLevelData(fullNames.subList(2000, 10000),DIRECTLY_MANAGES_LIMIT_LEVEL_FIFTEEN));
        
        
/*        Case 1 : (Levels = 3, Manages Limit = 5)
			        At Level 1 => 4000
			        At Level 2 => 16000
			        At Level 3 => 80000
			        
			        Total => 100000
*/
                peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 4000), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(4000, 20000), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(20000, 100000), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
       
         
        
        
/*        Case 2 : (Levels = 4, Manages Limit = 5)
			        At Level 1 => 1000
			        At Level 2 => 5000    
			        At Level 3 => 20000 
			        At Level 4 => 74000
			        
			        Total => 100000

                peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1000), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1000, 6000), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(6000, 26000), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(26000, 100000),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
        
        
 */       

/*		Case 3 : (Levels = 5, Manages Limit = 5)
			      At Level 1 => 300      
			      At Level 2 => 1500   
			      At Level 3 => 7500     
			      At Level 4 => 30000 
			      At Level 5 => 60700
			      
			      Total => 100000
 
                peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 300), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(300, 1800), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(1800, 9300), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(9300, 39300),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(39300, 100000),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));
 */      
        
        
/*        Case 4 : (Levels = 6, Manages Limit = 5)
					At Level 1 => 300      
					At Level 2 => 1000    
					At Level 3 => 5000    
					At Level 4 => 10000  
					At Level 5 => 30000
					At Level 6 => 53700
					Total => 100000
					
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 300), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
                peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(300, 1300), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
                peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(1300, 6300), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
                peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(6300, 16300),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(16300, 46300),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(46300, 100000),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));
*/					
/*			
					
		 * At Level 1 => 1
	     * At Level 2 => 5
	     * At Level 3 => 94
	     * At Level 4 => 200
	     * At Level 5 => 300
	     * At Level 6 => 400
	     * Total => 1000
                
                peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
                peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 6), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
                peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(6, 100), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
                peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(100, 300),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(300, 600),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(600, 1000),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));		
                
         * At Level 1 => 10
	     * At Level 2 => 100
	     * At Level 3 => 1000
	     * At Level 4 => 10000
	     * At Level 5 => 100000
	     * At Level 6 => 1000000
	     * Total => 1111110
                
                peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 10), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
                peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(10, 110), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
                peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(110, 1110), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
                peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(1110, 11110),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(11110, 111110),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(111110, 1111110),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));		       							
*/		    
              
/*        Case 5 : (Levels = 7, Manages Limit = 5)
					At Level 1 => 200
					At Level 2 => 500    
					At Level 3 => 2000    
					At Level 4 => 7000
					At Level 5 => 14000
					At Level 6 => 28000
					At Level 7 => 48300
					
					Total => 10000
					
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 200), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(200, 700), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(700, 2700), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(2700, 9700),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(9700, 23700),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(23700, 51700),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));									
		        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(51700, 100000),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));
*/					
	/*				
					* At Level 1 => 1
				    * At Level 2 => 5
				    * At Level 3 => 50
				    * At Level 4 => 94
				    * At Level 5 => 150
				    * At Level 6 => 250
				    * At Level 7 => 450
				    * Total => 1000
		
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 6), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(6, 56), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(56, 150),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(150, 300),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(300, 550),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));									
		        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(550, 1000),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));

 		 
			 		 * At Level 1 => 1
				     * At Level 2 => 10
				     * At Level 3 => 100
				     * At Level 4 => 1000
				     * At Level 5 => 10000
				     * At Level 6 => 100000
				     * At Level 7 => 1000000
				     * Total => 1111111
				     
				    

				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 11), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(11, 111), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(111, 1111),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(1111, 11111),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(11111, 111111),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));									
		        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(111111, 1111111),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));
*/


        
        									

		        
/*	        Case 6 : (Levels = 8, Manages Limit = 5)
				      At Level 1 => 4
				      At Level 2 => 20
				      At Level 3 => 80 
				      At Level 4 => 250
				      At Level 5 => 700
				      At Level 6 => 1400
				      At Level 7 => 2500
				      At Level 8 => 5046
				      
				      Total => 10000
		        
	    peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 4), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(4, 24), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(24, 104), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(104, 354),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(354, 1054),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(1054, 2454),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));									
        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(2454, 4954),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));	
        peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(4954, 10000),DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT));	
 */       
/*        
        			* At Level 1 => 1
				    * At Level 2 => 3
				    * At Level 3 => 10
				    * At Level 4 => 40
				    * At Level 5 => 90
				    * At Level 6 => 150
				    * At Level 7 => 300
				    * At Level 8 => 406
				    * Total => 1000

        
        peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 4), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(4, 14), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(14, 54),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(54, 144),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(144, 294),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));									
        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(294, 594),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));	
        peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(594, 1000),DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT));	
        
		        * At Level 1 => 1
		        * At Level 2 => 5
		        * At Level 3 => 10
		        * At Level 4 => 100
		        * At Level 5 => 1000
		        * At Level 6 => 10000
		        * At Level 7 => 100000
		        * At Level 8 => 1000000
		        *  Total => 1111116

	                    peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		                peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 6), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		                peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(6, 16), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		                peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(16, 116),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(116, 1116),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(1116, 11116),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));									
		                peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(11116, 111116),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));	
		                peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(111116, 1111116),DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT));	
*/
       
/*
        Case 7 : (Levels = 9, Manages Limit = 10)  
        		* At Level 1 => 1
        		* At Level 2 => 2
		        * At Level 3 => 5
		        * At Level 4 => 10
		        * At Level 5 => 100
		        * At Level 6 => 1000
		        * At Level 7 => 10000
		        * At Level 8 => 100000
		        * At Level 9 => 1000000
		        *  Total => 1111118

	                    peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
	                    peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 3), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		                peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(3, 8), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));
		                peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(8, 18), DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(18, 118),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(118, 1118),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));	
		                peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(1118, 11118),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));									
		                peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(11118, 111118),DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT));	
		                peopleAtLevel.put(LEVEL_NINE, new OrgLevelData(fullNames.subList(111118, 1111118),DIRECTLY_MANAGES_LIMIT_LEVEL_NINE));	
*/	      
        
/*
        Case 8 : (Levels = 10, Manages Limit = 10)  
        		* At Level 1 => 1
        		* At Level 2 => 2
		        * At Level 3 => 3
		        * At Level 4 => 5
		        * At Level 5 => 10
		        * At Level 6 => 100
		        * At Level 7 => 1000
		        * At Level 8 => 10000
		        * At Level 9 => 100000
		        * At Level 10 => 1000000
		        *  Total => 1111121

	                    peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
	                    peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 3), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
	                    peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(3, 6), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));
	                    peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(6, 11), DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));
		                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(11, 21), DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(21, 121),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));	
		                peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(121, 1121),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));	
		                peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(1121, 11121),DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT));									
		                peopleAtLevel.put(LEVEL_NINE, new OrgLevelData(fullNames.subList(11121, 111121),DIRECTLY_MANAGES_LIMIT_LEVEL_NINE));	
		                peopleAtLevel.put(LEVEL_TEN, new OrgLevelData(fullNames.subList(111121, 1111121),DIRECTLY_MANAGES_LIMIT_LEVEL_TEN));	
*/
        
/*

    	(Levels = 12, Manages Limit = 10)  
            		
            		* At Level 1 => 1
            		* At Level 2 => 1
            		* At Level 3 => 1
            		* At Level 4 => 2
    		        * At Level 5 => 3
    		        * At Level 6 => 5
    		        * At Level 7 => 10
    		        * At Level 8 => 100
    		        * At Level 9 => 1000
    		        * At Level 10 => 10000
    		        * At Level 11 => 100000
    		        * At Level 12 => 1000000
    		        *  Total => 1111123
 
    	                    peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
    	                    peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 2), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
    	                    peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(2, 3), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));
    	                    peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(3, 5),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
    		                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(5, 8),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));									
    		                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(8, 13),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));	
    		                peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(13, 23),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));
    		                peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(23, 123),DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT));
    		                peopleAtLevel.put(LEVEL_NINE, new OrgLevelData(fullNames.subList(123, 1123),DIRECTLY_MANAGES_LIMIT_LEVEL_NINE));
    		                peopleAtLevel.put(LEVEL_TEN, new OrgLevelData(fullNames.subList(1123, 11123),DIRECTLY_MANAGES_LIMIT_LEVEL_TEN));
    		                peopleAtLevel.put(LEVEL_ELEVEN, new OrgLevelData(fullNames.subList(11123, 111123),DIRECTLY_MANAGES_LIMIT_LEVEL_ELEVEN));
    		                peopleAtLevel.put(LEVEL_TWELVE, new OrgLevelData(fullNames.subList(111123, 1111123),DIRECTLY_MANAGES_LIMIT_LEVEL_TWELVE));
*/           
        
/*

	(Levels = 15, Manages Limit = 10)  
        		
        		* At Level 1 => 1
        		* At Level 2 => 1
        		* At Level 3 => 1
        		* At Level 4 => 1
        		* At Level 5 => 1
        		* At Level 6 => 1
        		* At Level 7 => 2
		        * At Level 8 => 3
		        * At Level 9 => 5
		        * At Level 10 => 10
		        * At Level 11 => 100
		        * At Level 12 => 1000
		        * At Level 13 => 10000
		        * At Level 14 => 100000
		        * At Level 15 => 1000000
		        *  Total => 1111126

	                    peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
	                    peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 2), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
	                    peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(2, 3), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));
	                    peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(3, 4), DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));
		                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(4, 5), DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(5, 6),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));	
		                peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(6, 8),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));	
		                peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(8, 11),DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT));									
		                peopleAtLevel.put(LEVEL_NINE, new OrgLevelData(fullNames.subList(11, 16),DIRECTLY_MANAGES_LIMIT_LEVEL_NINE));	
		                peopleAtLevel.put(LEVEL_TEN, new OrgLevelData(fullNames.subList(16, 26),DIRECTLY_MANAGES_LIMIT_LEVEL_TEN));
		                peopleAtLevel.put(LEVEL_ELEVEN, new OrgLevelData(fullNames.subList(26, 126),DIRECTLY_MANAGES_LIMIT_LEVEL_ELEVEN));
		                peopleAtLevel.put(LEVEL_TWELVE, new OrgLevelData(fullNames.subList(126, 1126),DIRECTLY_MANAGES_LIMIT_LEVEL_TWELVE));
		                peopleAtLevel.put(LEVEL_THIRTEEN, new OrgLevelData(fullNames.subList(1126, 11126),DIRECTLY_MANAGES_LIMIT_LEVEL_THIRTEEN));
		                peopleAtLevel.put(LEVEL_FOURTEEN, new OrgLevelData(fullNames.subList(11126, 111126),DIRECTLY_MANAGES_LIMIT_LEVEL_FOURTEEN));
		                peopleAtLevel.put(LEVEL_FIFTEEN, new OrgLevelData(fullNames.subList(111126, 1111126),DIRECTLY_MANAGES_LIMIT_LEVEL_FIFTEEN));
*/
        
/*

    	(Levels = 20, Manages Limit = 10)  
            		
            		* At Level 1 => 1
            		* At Level 2 => 1
            		* At Level 3 => 1
            		* At Level 4 => 1
            		* At Level 5 => 1
            		* At Level 6 => 1
            		* At Level 7 => 1
            		* At Level 8 => 1
            		* At Level 9 => 1
            		* At Level 10 => 1
            		* At Level 11 => 1
            		* At Level 12 => 2
    		        * At Level 13 => 3
    		        * At Level 14 => 5
    		        * At Level 15 => 10
    		        * At Level 16 => 100
    		        * At Level 17 => 1000
    		        * At Level 18 => 10000
    		        * At Level 19 => 100000
    		        * At Level 20 => 1000000
    		        *  Total => 1111131

    	                    peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
    	                    peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 2), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
    	                    peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(2, 3), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));
    	                    peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(3, 4), DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));
    		                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(4, 5), DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
    		               	peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(5, 6), DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));
    		               	peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(6, 7), DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));
    		               	peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(7, 8), DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT));
    		               	peopleAtLevel.put(LEVEL_NINE, new OrgLevelData(fullNames.subList(8, 9), DIRECTLY_MANAGES_LIMIT_LEVEL_NINE));
    		               	peopleAtLevel.put(LEVEL_TEN, new OrgLevelData(fullNames.subList(9, 10), DIRECTLY_MANAGES_LIMIT_LEVEL_TEN));
    		                peopleAtLevel.put(LEVEL_ELEVEN, new OrgLevelData(fullNames.subList(10, 11),DIRECTLY_MANAGES_LIMIT_LEVEL_ELEVEN));	
    		                peopleAtLevel.put(LEVEL_TWELVE, new OrgLevelData(fullNames.subList(11, 13),DIRECTLY_MANAGES_LIMIT_LEVEL_TWELVE));	
    		                peopleAtLevel.put(LEVEL_THIRTEEN, new OrgLevelData(fullNames.subList(13, 16),DIRECTLY_MANAGES_LIMIT_LEVEL_THIRTEEN));									
    		                peopleAtLevel.put(LEVEL_FOURTEEN, new OrgLevelData(fullNames.subList(16, 21),DIRECTLY_MANAGES_LIMIT_LEVEL_FOURTEEN));	
    		                peopleAtLevel.put(LEVEL_FIFTEEN, new OrgLevelData(fullNames.subList(21, 31),DIRECTLY_MANAGES_LIMIT_LEVEL_FIFTEEN));
    		                peopleAtLevel.put(LEVEL_SIXTEEN, new OrgLevelData(fullNames.subList(31, 131),DIRECTLY_MANAGES_LIMIT_LEVEL_SIXTEEN));
    		                peopleAtLevel.put(LEVEL_SEVENTEEN, new OrgLevelData(fullNames.subList(131, 1131),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVENTEEN));
    		                peopleAtLevel.put(LEVEL_EIGHTEEN, new OrgLevelData(fullNames.subList(1131, 11131),DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHTEEN));
    		                peopleAtLevel.put(LEVEL_NINETEEN, new OrgLevelData(fullNames.subList(11131, 111131),DIRECTLY_MANAGES_LIMIT_LEVEL_NINETEEN));
    		                peopleAtLevel.put(LEVEL_TWENTY, new OrgLevelData(fullNames.subList(111131, 1111131),DIRECTLY_MANAGES_LIMIT_LEVEL_TWENTY));
*/            
        
        return peopleAtLevel;
    }
}