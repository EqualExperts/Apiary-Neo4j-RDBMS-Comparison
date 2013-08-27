package utils.generator

import DistributionStrategy._
import org.neo4j.unsafe.batchinsert.BatchInserters
import org.hibernate.cfg.AnnotationConfiguration

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
object DB_1M_OrgPopulator extends App {
  override def main(args: Array[String]) = {
   val basePath = "/Users/dhavald/Documents/workspace/Apiary/NEO4J_DATA/apiary_1m_l8"
   val orgSize = 1000000

    /**
     * Case 1 : (Levels = 3, Manages Limit = 1000)
     * At Level 1 => 10
     * At Level 2 => 10000
     * At Level 3 => 989990
     * Total => 1000000
     **/

//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 1000)
//      .withPeopleAtLevel(1, 10)
//      .withPeopleAtLevel(2, 10000)
//      .withPeopleAtLevel(3, 989990)
//      .distribute(Contiguous)

    /**
     * Case 2 : (Levels = 4, Manages Limit = 500)
     * At Level 1 => 5
     * At Level 2 => 25
     * At Level 3 => 4000
     * At Level 4 => 995970
     * Total => 1000000
     **/
//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 500)
//      .withPeopleAtLevel(1, 5)
//      .withPeopleAtLevel(2, 25)
//      .withPeopleAtLevel(3, 4000)
//      .withPeopleAtLevel(4, 995970)
//      .distribute(Contiguous)

    /**
     * Case 3 : (Levels = 5, Manages Limit = 500)
     * At Level 1 => 5
     * At Level 2 => 125
     * At Level 3 => 3125
     * At Level 4 => 78125
     * At Level 5 => 918620
     * Total => 1000000
     **/
//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 500)
//      .withPeopleAtLevel(1, 5)
//      .withPeopleAtLevel(2, 125)
//      .withPeopleAtLevel(3, 3125)
//      .withPeopleAtLevel(4, 78125)
//      .withPeopleAtLevel(5, 918620)
//      .distribute(Contiguous)

    /**
     * Case 4 : (Levels = 6, Manages Limit = 10)
     * At Level 1 => 3
     * At Level 2 => 10
     * At Level 3 => 50
     * At Level 4 => 400
     * At Level 5 => 20000
     * At Level 6 => 979537
     * Total => 1000000
     **/
//    val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 500)
//      .withPeopleAtLevel(1, 3)
//      .withPeopleAtLevel(2, 10)
//      .withPeopleAtLevel(3, 50)
//      .withPeopleAtLevel(4, 400)
//      .withPeopleAtLevel(5, 20000)
//      .withPeopleAtLevel(6, 979537)
//      .distribute(Contiguous)

    /**
		 * Case 5 : (Levels = 7, Manages Limit = 8)
		 * At Level 1 => 1
     * At Level 2 => 10
     * At Level 3 => 100
     * At Level 4 => 1000
     * At Level 5 => 20480
     * At Level 6 => 163840
     * At Level 7 => 812755
		 * Total => 1000000
     **/
//		val builder = OrganizationBuilder(names, withPersonManagingMaxOf = 100)
//      .withPeopleAtLevel(1, 3)
//      .withPeopleAtLevel(2, 23)
//      .withPeopleAtLevel(3, 100)
//      .withPeopleAtLevel(4, 1800)
//      .withPeopleAtLevel(5, 20480)
//      .withPeopleAtLevel(6, 164840)
//      .withPeopleAtLevel(7, 812754)
//			.distribute(Contiguous)

    /*
     * Case 6 : (Levels = 8, Manages Limit = 50)
     * At Level 1 => 2
     * At Level 2 => 20
     * At Level 3 => 100
     * At Level 4 => 500
     * At Level 5 => 5000
     * At Level 6 => 10000
     * At Level 7 => 50000
     * At Level 8 => 934378
     *  Total => 1000000
     */

    new OrgLevelBuilder(orgSize, 8, Contiguous) {
      val neo4jStoreDir = basePath + level
      val orgDef = OrganizationDef(names, withPersonManagingMaxOf = 50)
      .withPeopleAtLevel(1, 10)
      .withPeopleAtLevel(2, 500)
      .withPeopleAtLevel(3, 25000)
      .withPeopleAtLevel(4, 100000)
      .withPeopleAtLevel(5, 200000)
      .withPeopleAtLevel(6, 400000)
	    .withPeopleAtLevel(7, 200000)
	    .withPeopleAtLevel(8, 74490)
    }
  }
}
