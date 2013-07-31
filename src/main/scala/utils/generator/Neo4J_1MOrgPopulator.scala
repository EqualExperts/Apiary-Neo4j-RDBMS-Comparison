package utils.generator

import utils.{NeoDBBatchInserter, NeoDB}
import DistributionStrategy._
import org.neo4j.kernel.DefaultFileSystemAbstraction

/**
 * Note: You may need to enable and change the old value 64M of the wrapper.java.maxmemory property
 * in neo4j-wrapper.conf.
 *
 * # Maximum Java Heap Size (in MB)
 * wrapper.java.maxmemory=1024
 *
 * Also enable memory mapped regions in neo4j.properties
 * neostore.nodestore.db.mapped_memory=512M
 * neostore.relationshipstore.db.mapped_memory=512M
 * neostore.propertystore.db.mapped_memory=512M
 *
 * PLEASE NOTE THIS WILL TAKE HOURS TO RUN, SO LEAVE IT OVERNIGHT AND COME BACK NEXT DAY!!!
 *
 * [Thu Jul 18 10:09:50 IST 2013] [INFO]: Total in Org = 1000000 people
 *
 * [Thu Jul 18 10:09:50 IST 2013] [INFO]: Persisting People...
 * [Thu Jul 18 13:04:07 IST 2013] [INFO]: Persisting People...Complete. Execution Time 10456857(ms) =~ 10456.857(secs)
 * [Thu Jul 18 13:04:07 IST 2013] [INFO]: Indexing People...
 * [Thu Jul 18 14:59:28 IST 2013] [INFO]: Indexing People...Complete. Execution Time 6921555(ms) =~ 6921.555(secs)
 * [Thu Jul 18 14:59:28 IST 2013] [INFO]: Creating Relationships using Contiguous Distribution strategy
 * [Thu Jul 18 15:35:06 IST 2013] [INFO]: Persisting Relationships...
 * [Thu Jul 18 16:11:23 IST 2013] [INFO]: Persisting Relationships...Complete. Execution Time 2176988(ms) =~ 2176.988(secs)
 *
 * NOTE: And remember if you want to delete all the data, you DO NOT WANT TO FIRE
 * --> start n = node(*) match n-[r?]->() delete r, n;
 *
 * because the Shell Bombs!!!  (you will have to increase shell memory or do a programmatic delete)
 * neo4j-sh (first290 last14,0)$ start n = node(*) match n-[r?]->() delete r, n;
 * Error occurred in server thread; nested exception is:
 *	java.lang.OutOfMemoryError: Java heap space
 *
 * Best is to zip up the DB or point to a new location on FileSystem.
 *
 * SECOND OPTION: Use BatchInserter to bulk upload data, its fast because
 * 1. No Transactions
 * 2. No need for Neo4j memory configuration params, like above 
 * 
 * Viola here are the surprising results of using BatchInserter
 *
 * [Wed Jul 24 11:29:11 IST 2013] [INFO]: Total in Org = 1000000 people
 *
 *[Wed Jul 24 11:29:11 IST 2013] [INFO]: Persisting People...
 *[Wed Jul 24 11:29:16 IST 2013] [INFO]: Persisting People...Complete. Execution Time 4573(ms) =~ 4.573(secs)
 *[Wed Jul 24 11:29:16 IST 2013] [INFO]: Indexing People...
 *[Wed Jul 24 11:29:31 IST 2013] [INFO]: Indexing People...Complete. Execution Time 14710(ms) =~ 14.710(secs)
 *[Wed Jul 24 11:29:31 IST 2013] [INFO]: Creating Relationships using Contiguous Distribution strategy
 *[Wed Jul 24 11:35:11 IST 2013] [INFO]: Persisting Relationships...
 *[Wed Jul 24 11:35:16 IST 2013] [INFO]: Persisting Relationships...Complete. Execution Time 4443(ms) =~ 4.443(secs)
 *[Wed Jul 24 11:35:19 IST 2013] [INFO]: PLEASE DELETE NODE WITH ID 0 MANUALLY!!!
 */
object Neo4J_1MOrgPopulator extends App with NamesGenerator {
  override def main(args: Array[String]) = {
    val names = syntheticNames(1000000)
//    val names = naturalNames(1000000)
//    val neoDb = NeoDB("http://localhost:7474/db/data")
//    val basePath = "D:/rnd/apiary"
   val basePath = "/Users/dhavald/Documents/workspace/Apiary"

    /**
     * Case 1 : (Levels = 3, Manages Limit = 100)
     * At Level 1 => 100
     * At Level 2 => 10000
     * At Level 3 => 989900
     * Total => 1000000
     **/

//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 100)
//      .withPeopleAtLevel(1, 100)
//      .withPeopleAtLevel(2, 10000)
//      .withPeopleAtLevel(3, 989900)
//      .distribute(Contiguous)

    /**
     * Case 2 : (Levels = 4, Manages Limit = 50)
     * At Level 1 => 8
     * At Level 2 => 400
     * At Level 3 => 20000
     * At Level 4 => 979592
     * Total => 1000000
     **/
//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 50)
//      .withPeopleAtLevel(1, 8)
//      .withPeopleAtLevel(2, 400)
//      .withPeopleAtLevel(3, 20000)
//      .withPeopleAtLevel(4, 979592)
//      .distribute(Contiguous)

    /**
     * Case 3 : (Levels = 5, Manages Limit = 25)
     * At Level 1 => 5
     * At Level 2 => 125
     * At Level 3 => 3125
     * At Level 4 => 78125
     * At Level 5 => 918620
     * Total => 1000000
     **/
//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 25)
//      .withPeopleAtLevel(1, 5)
//      .withPeopleAtLevel(2, 125)
//      .withPeopleAtLevel(3, 3125)
//      .withPeopleAtLevel(4, 78125)
//      .withPeopleAtLevel(5, 918620)
//      .distribute(Contiguous)

    /**
     * Case 4 : (Levels = 6, Manages Limit = 10)
     * At Level 1 => 10
     * At Level 2 => 100
     * At Level 3 => 1000
     * At Level 4 => 10000
     * At Level 5 => 100000
     * At Level 6 => 100000
     * Total => 1000000
     **/
    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 10)
      .withPeopleAtLevel(1, 5)
      .withPeopleAtLevel(2, 50)
      .withPeopleAtLevel(3, 500)
      .withPeopleAtLevel(4, 5000)
      .withPeopleAtLevel(5, 50000)
      .withPeopleAtLevel(6, 100000)
      .distribute(Contiguous)

/*
		 *	Case 5 : (Levels = 7, Manages Limit = 10)
		 *  At Level 1 => 1
         * At Level 2 => 10
         * At Level 3 => 100
         * At Level 4 => 1000
         * At Level 5 => 10000
         * At Level 6 => 100000
         * At Level 7 => 1000000
		 * Total => 1111111

		val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 10)
			.withPeopleAtLevel(1, 1)
			.withPeopleAtLevel(2, 10)
			.withPeopleAtLevel(3, 100)
			.withPeopleAtLevel(4, 1000)
			.withPeopleAtLevel(5, 10000)
			.withPeopleAtLevel(6, 100000)
			.withPeopleAtLevel(7, 1000000)
			.distribute(Contiguous)
*/	
/*	
     * Case 6 : (Levels = 8, Manages Limit = 10)
     * At Level 1 => 1
     * At Level 2 => 5
     * At Level 3 => 10
     * At Level 4 => 100
     * At Level 5 => 1000
     * At Level 6 => 10000
     * At Level 7 => 100000
     * At Level 8 => 1000000
     *  Total => 1111116
     

    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 10)
      .withPeopleAtLevel(1, 1)
      .withPeopleAtLevel(2, 5)
      .withPeopleAtLevel(3, 10)
      .withPeopleAtLevel(4, 100)
      .withPeopleAtLevel(5, 1000)
      .withPeopleAtLevel(6, 10000)
	  .withPeopleAtLevel(7, 100000)
	  .withPeopleAtLevel(8, 1000000)
      .distribute(Contiguous)

	//val neoDb = NeoDBBatchInserter(basePath + "/NEO4J_DATA/apiary_1m_case6")  
*/	  
	  val neoDb = NeoDBBatchInserter(basePath + "/NEO4J_DATA/apiary_1m_l6")
    builder buildWith neoDb
  }
}
