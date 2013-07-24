package utils

import org.neo4j.kernel.impl.nioneo.store.FileSystemAbstraction
import org.neo4j.unsafe.batchinsert.{BatchInserter, BatchInserters}

class NeoDBBatchInserter private (val batchInserter: BatchInserter) {
  def shutdown = batchInserter.shutdown
}

import collection.JavaConverters._

object NeoDBBatchInserter {
  def apply(storeDir: String) = new NeoDBBatchInserter(BatchInserters.inserter(storeDir))
  def apply(storeDir: String, fileSystem: FileSystemAbstraction, config: Map[String, String]) = new NeoDBBatchInserter(BatchInserters.inserter(storeDir, fileSystem, config.asJava))
  def apply(storeDir: String, fileSystem: FileSystemAbstraction) = new NeoDBBatchInserter(BatchInserters.inserter(storeDir, fileSystem))
}
