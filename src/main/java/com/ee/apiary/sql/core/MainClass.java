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
    
    private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_ONE = 5;
    private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_TWO = 5;
    private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_THREE = 5;
    private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR = 5;
    private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE = 5;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_SIX = 5;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN = 5;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT = 5;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_NINE = 5;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_TEN = 5;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_ELEVEN = 5;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_TWELVE = 5;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_THIRTEEN = 5;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_FOURTEEN = 5;
	private static final int DIRECTLY_MANAGES_LIMIT_LEVEL_FIFTEEN = 5;
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
        System.out.println("Started creating person objects....");
        long tStartPerson = System.currentTimeMillis();
        SortedMap<Integer, OrgLevelData> levelWithPeople = sqlOrgHierarchy.toLevelWithPersonObjs(orgDataAtLevel);
        long tEndPerson = System.currentTimeMillis();
        System.out.println("Created person objects. Time taken : " + (tEndPerson - tStartPerson)/1000 + " sec/s");

        System.out.println("Started creating direct managers....");
        long tStartDirectManager = System.currentTimeMillis();
        List<DirectManager> directManagers = sqlOrgHierarchy.createDirectManagerRelationships(levelWithPeople);
        long tEndDirectManager = System.currentTimeMillis();
        System.out.println("Created direct managers. Time taken : " + (tEndDirectManager - tStartDirectManager)/1000 + " sec/s");

        System.out.println("Started saving data....");
        long tStartSave = System.currentTimeMillis();
        SqlOrgHierarchyDAO.saveOrgData(levelWithPeople, directManagers);
        long tEndSave = System.currentTimeMillis();
        System.out.println("Organisation data saved in DB. Time taken : " + (tEndSave - tStartSave)/1000 + " sec/s");
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


/*        Case 1 :  /*
					* Case 1 : (Levels = 3, Manages Limit = 100)
					* At Level 1 => 100
					* At Level 2 => 10000
					* At Level 3 => 989900
					* Total => 1000000
					*
 
                peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 100), 100));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(100, 10100), 100));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(10100, 1000000), 100));	
 */ 		
		
		
/*        Case 1 : (Levels = 3, Manages Limit = 5)
			        At Level 1 => 4000
			        At Level 2 => 16000
			        At Level 3 => 80000
			        
			        Total => 100000
  
                peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 4000), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(4000, 20000), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(20000, 100000), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
*/ 
 /*        Case 1 : (Levels = 3, Manages Limit = 5)
			        At Level 1 => 400
			        At Level 2 => 1600
			        At Level 3 => 8000
			        
			        Total => 10000

                peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 400), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(400, 2000), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(2000, 10000), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
  */     
         
/*        Case 1 : (Levels = 3, Manages Limit = 5)
			        At Level 1 => 40
			        At Level 2 => 160
			        At Level 3 => 800
			        
			        Total => 1000

                peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 40), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(40, 200), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(200, 1000), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
*/

        /**
         * case 1:
         * total people in organisation = 2000000, with Levels = 3, withPersonManagingMaxOf = 1000, directlyReportingToMax = 1
         *  At Level 1 => 10
         *  At Level 2 => 10000
         *  At Level 3 => 1989990
         *  Total => 2000000


        peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 10), 1000));
        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(10, 10010), 1000));
        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(10010, 2000000), 1000));
*/


/*        Case 2 : (Levels = 4, Manages Limit = 5)
         val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 5)
                          .withPeopleAtLevel(1, 10)
                          .withPeopleAtLevel(2, 43)
                          .withPeopleAtLevel(3, 200)
                          .withPeopleAtLevel(4, 747)
                          .distribute(Contiguous)
						  
						  
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 10), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(10, 53), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(53, 253), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(253, 1000),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));		  
*/			  
						  
        
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
        

/*
				* case 2:
				* total people in organisation = 10000 with levels = 4, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
				* At Level 1 => 100
				* At Level 2 => 500
				* At Level 3 => 2000
				* At Level 4 => 7400
				* Total => 10000
 
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 100), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(100, 600), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(600, 2600), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(2600, 10000),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
*/
   
/*
				* Case 2 : (Levels = 4, Manages Limit = 50)
				* At Level 1 => 8
				* At Level 2 => 400
				* At Level 3 => 20000
				* At Level 4 => 979592
				* Total => 1000000
				*
				
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 8), 50));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(8, 408), 50));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(408, 20408), 50));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(20408, 1000000),50));	
*/

/**
                 * case 2:
                 * total people in organisation = 2000000 with levels = 4, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
                 * At Level 1 => 5
                 * At Level 2 => 25
                 * At Level 3 => 4000
                 * At Level 4 => 1995970
                 * Total => 2000000


                 peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 5), 500));
                 peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(5, 30), 500));
                 peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(30, 4030), 500));
                 peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(4030, 2000000),500));
 */




/*		Case 3 : (Levels = 5, Manages Limit = 5)
			      At Level 1 => 3      
			      At Level 2 => 15   
			      At Level 3 => 75     
			      At Level 4 => 300 
			      At Level 5 => 607
			      
			      Total => 1000

                peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 3), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(3, 18), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(18, 93), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(93, 393),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(393, 1000),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));
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

/*
				* case 3:
				* total people in organisation = 10000 with levels = 5, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
				*
				* At Level 1 => 30
				* At Level 2 => 150
				* At Level 3 => 750
				* At Level 4 => 3000
				* At Level 5 => 6070
				* Total => 10000
    
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 30), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(30, 180), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(180, 930), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(930, 3930),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(3930, 10000),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));
 
 */

/*
				* Case 3 : (Levels = 5, Manages Limit = 25)
				* At Level 1 => 5
				* At Level 2 => 125
				* At Level 3 => 3125
				* At Level 4 => 78125
				* At Level 5 => 918620
				* Total => 1000000
				*
	 
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 5), 25));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(5, 130), 25));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(130, 3255), 25));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(3255, 81380),25));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(81380, 1000000),25));
*/
/**
         * case 3:
         * total people in organisation = 2000000 with levels = 5, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
         *
         * At Level 1 => 3
         * At Level 2 => 15
         * At Level 3 => 400
         * At Level 4 => 4000
         * At Level 5 => 1995582
         * Total => 2000000

         peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 3), 500));
         peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(3, 18), 500));
         peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(18, 418), 500));
         peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(418, 4418),500));
         peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(4418, 2000000),500));
 */

/*
				* case 4:
				* total people in organisation = 100000 with levels = 6, withPersonManagingMaxOf = 10, directlyReportingToMax = 1
				*
				* At Level 1 => 2
				* At Level 2 => 20
				* At Level 3 => 100
				* At Level 4 => 1000
				* At Level 5 => 10000
				* At Level 6 => 88878
				* Total => 100000

				
				
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 2), 10));	
                peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(2, 22), 10));
                peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(22, 122), 10));	
                peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(122, 1122),10));	
                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(1122, 11122),10));	
                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(11122, 100000),10));
*/				
/*
     * case 4:
     * total people in organisation = 1000 with levels = 6, withPersonManagingMaxOf = 19, directlyReportingToMax = 1
     *
     * At Level 1 => 1
     * At Level 2 => 5
     * At Level 3 => 94
     * At Level 4 => 200
     * At Level 5 => 300
     * At Level 6 => 400
     * Total => 1000
     
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), 19));	
                peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 6), 19));
                peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(6, 100), 19));	
                peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(100, 300),19));	
                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(300, 600),19));	
                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(600, 1000),19));
*/
  

/*  			* case 4:
				* total people in organisation = 10000 with levels = 6, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
				*
				* At Level 1 => 30
				* At Level 2 => 100
				* At Level 3 => 500
				* At Level 4 => 1000
				* At Level 5 => 3000
				* At Level 6 => 5370
				* Total => 10000
				
					
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 30), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
                peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(30, 130), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
                peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(130, 630), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
                peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(630, 1630),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(1630, 4630),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(4630, 10000),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));
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

/*
     * Case 4 : (Levels = 6, Manages Limit = 10)
     * At Level 1 => 10
     * At Level 2 => 95
     * At Level 3 => 950
     * At Level 4 => 9500
     * At Level 5 => 95000
     * At Level 6 => 894445
     * Total => 1000000
     *
				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 10), 10));	
                peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(10, 105), 10));
                peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(105, 1055), 10));	
                peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(1055, 10555),10));	
                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(10555, 105555),10));	
                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(105555, 1000000),10));
*/

/**
             * case 4:
             * total people in organisation = 2000000 with levels = 6, withPersonManagingMaxOf = 500, directlyReportingToMax = 1
             *
             * At Level 1 => 3
             * At Level 2 => 10
             * At Level 3 => 50
             * At Level 4 => 400
             * At Level 5 => 20000
             * At Level 6 => 1979537
             * Total => 2000000

            peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 3), 500));
            peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(3, 13), 500));
            peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(13, 63), 500));
            peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(63, 463),500));
            peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(463, 20463),500));
            peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(20463, 2000000),500));
 */
 /*
				* case 5:
				* total people in organisation = 100000 with levels = 7, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
				*
				* At Level 1 => 6
				* At Level 2 => 29
				* At Level 3 => 143
				* At Level 4 => 711
				* At Level 5 => 3555
				* At Level 6 => 17775
				* At Level 7 => 77781
				* Total => 100000

				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 6), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(6, 35), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(35, 178), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(178, 889),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(889, 4444),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(4444, 22219),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));									
		        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(22219, 100000),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));
*/

     

              
/*
		* case 5:
		* total people in organisation = 10000 with levels = 7, withPersonManagingMaxOf = 5, directlyReportingToMax = 1
		*
		* At Level 1 => 20
		* At Level 2 => 50
		* At Level 3 => 200
		* At Level 4 => 700
		* At Level 5 => 1400
		* At Level 6 => 2800
		* At Level 7 => 4830
		* Total => 10000

				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 20), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(20, 70), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(70, 270), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(270, 970),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(970, 2370),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(2370, 5170),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));									
		        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(5170, 10000),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));	
*/
/*
			* Case 5 : (Levels = 7, Manages Limit = 5)
			* At Level 1 => 2
			* At Level 2 => 5
			* At Level 3 => 20
			* At Level 4 => 70
			* At Level 5 => 140
			* At Level 6 => 280
			* At Level 7 => 483
			* 

				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 2), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(2, 7), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(7, 27), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(27, 97),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(97, 237),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(237, 517),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));									
		        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(517, 1000),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));	
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

/*		val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 8)
			.withPeopleAtLevel(1, 5)
			.withPeopleAtLevel(2, 40)
			.withPeopleAtLevel(3, 320)
			.withPeopleAtLevel(4, 2560)
			.withPeopleAtLevel(5, 20480)
			.withPeopleAtLevel(6, 163840)
			.withPeopleAtLevel(7, 812755)
			.distribute(Contiguous)

				peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 5), 8));	
		        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(5, 45), 8));
		        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(45, 365), 8));	
		        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(365, 2925),8));	
		        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(2925, 23405),8));	
		        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(23405, 187245),8));									
		        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(187245, 1000000),8));
*/

/**
             * case 5:
             * total people in organisation = 2000000 with levels = 7, withPersonManagingMaxOf = 100, directlyReportingToMax = 1
             *
             * At Level 1 => 2
             * At Level 2 => 50
             * At Level 3 => 200
             * At Level 4 => 1000
             * At Level 5 => 10000
             * At Level 6 => 100000
             * At Level 7 => 1888748
             * Total => 2000000


             peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 2), 100));
             peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(2, 52), 100));
             peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(52, 252), 100));
             peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(252, 1252),100));
             peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(1252, 11252),100));
             peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(11252, 111252),100));
             peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(111252, 2000000),100));
 */


/*val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 4)
      .withPeopleAtLevel(1, 5)
      .withPeopleAtLevel(2, 19)
      .withPeopleAtLevel(3, 76)
      .withPeopleAtLevel(4, 303)
      .withPeopleAtLevel(5, 1212)
      .withPeopleAtLevel(6, 4848)
      .withPeopleAtLevel(7, 19392)
      .withPeopleAtLevel(8, 74145)

		peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 5), 4));	
        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(5, 24), 4));
        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(24, 100), 4));	
        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(100, 403),4));	
        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(403, 1615),4));	
        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(1615, 6463),4));									
        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(6463, 25855),4));	
        peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(25855, 100000),4));
*/
/*
			* Case 6 : (Levels = 8, Manages Limit = 4)
			* At Level 1 => 1
			* At Level 2 => 3
			* At Level 3 => 7
			* At Level 4 => 12
			* At Level 5 => 25
			* At Level 6 => 52
			* At Level 7 => 200
			* At Level 8 => 700
			* 
    
        peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 1), 4));	
        peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(1, 4), 4));
        peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(4, 11), 4));	
        peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(11, 23),4));	
        peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(23, 48),4));	
        peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(48, 100),4));									
        peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(100, 300),4));	
        peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(300, 1000),4));										
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
						* Case 6 : (Levels = 8, Manages Limit = 5)
						* At Level 1 => 11
						* At Level 2 => 55
						* At Level 3 => 275
						* At Level 4 => 1375
						* At Level 5 => 6875
						* At Level 6 => 34375
						* At Level 7 => 171875
						* At Level 8 => 785159
						*  Total => 1000000

						peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 11), DIRECTLY_MANAGES_LIMIT_LEVEL_ONE));	
		                peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(11, 66), DIRECTLY_MANAGES_LIMIT_LEVEL_TWO));
		                peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(66, 341), DIRECTLY_MANAGES_LIMIT_LEVEL_THREE));	
		                peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(341, 1716),DIRECTLY_MANAGES_LIMIT_LEVEL_FOUR));	
		                peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(1716, 8591),DIRECTLY_MANAGES_LIMIT_LEVEL_FIVE));	
		                peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(8591, 42966),DIRECTLY_MANAGES_LIMIT_LEVEL_SIX));									
		                peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(42966, 214841),DIRECTLY_MANAGES_LIMIT_LEVEL_SEVEN));	
		                peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(214841, 1000000),DIRECTLY_MANAGES_LIMIT_LEVEL_EIGHT));
*/
/**
         * case 6:
         * total people in organisation = 2000000 with levels = 8, withPersonManagingMaxOf = 50, directlyReportingToMax = 1
         *
         * At Level 1 => 2
         * At Level 2 => 20
         * At Level 3 => 100
         * At Level 4 => 500
         * At Level 5 => 5000
         * At Level 6 => 10000
         * At Level 7 => 50000
         * At Level 8 => 1934378
         * Total => 2000000
 */
         peopleAtLevel.put(LEVEL_ONE, new OrgLevelData(fullNames.subList(0, 2), 50));
         peopleAtLevel.put(LEVEL_TWO, new OrgLevelData(fullNames.subList(2, 22), 50));
         peopleAtLevel.put(LEVEL_THREE, new OrgLevelData(fullNames.subList(22, 122), 50));
         peopleAtLevel.put(LEVEL_FOUR, new OrgLevelData(fullNames.subList(122, 622),50));
         peopleAtLevel.put(LEVEL_FIVE, new OrgLevelData(fullNames.subList(622, 5622),50));
         peopleAtLevel.put(LEVEL_SIX, new OrgLevelData(fullNames.subList(5622, 15622),50));
         peopleAtLevel.put(LEVEL_SEVEN, new OrgLevelData(fullNames.subList(15622, 65622),50));
         peopleAtLevel.put(LEVEL_EIGHT, new OrgLevelData(fullNames.subList(65622, 2000000),50));


	
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
