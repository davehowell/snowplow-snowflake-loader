/*
 * PROPRIETARY AND CONFIDENTIAL
 *
 * Unauthorized copying of this project via any medium is strictly prohibited.
 *
 * Copyright (c) 2017 Snowplow Analytics Ltd. All rights reserved.
 */
package com.snowplowanalytics.snowflake.loader
package connection

import ast._

/** DB-connection adapter */
trait Connection[C] {
  def getConnection(config: LoaderConfig): C
  def execute[S: Statement](connection: C, ast: S): Unit
  def startTransaction(connection: C, name: Option[String]): Unit
  def commitTransaction(connection: C): Unit
  def executeAndOutput[S: Statement](connection: C, ast: S): Unit
  def executeAndCountRows[S: Statement](connection: C, ast: S): Int
}
