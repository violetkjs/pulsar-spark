/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.spark.sql.pulsar

import org.apache.spark.SparkFunSuite
import org.apache.spark.sql.SparkSession
import org.scalatest.Assertions._

class ConnectorInitializationSuite extends SparkFunSuite {

  // This test will fail if an exception is thrown during initialization of
  // the Pulsar Spark connector.
  test("connector initialization test") {
    val spark = SparkSession.builder
      .appName("connector initialization test")
      .master("local")
      .getOrCreate()

    try {
      val dataframe = spark.readStream
        .format("pulsar")
        .option("service.url", "testurl") // value not important, but must be set
        .option("admin.url", "testurl") // value not important, but must be set
        .option("topic", "testtopic") // value not important, but must be set
        .load
    } catch {
      case e: java.lang.ExceptionInInitializerError =>
        fail(e.getException + " was thrown during connector initialization")
    } finally {
      spark.stop()
    }
  }
}
