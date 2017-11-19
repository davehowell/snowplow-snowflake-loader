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

class DryRun {
  private val messages = collection.mutable.ListBuffer.newBuilder[String]

  private var transaction: Option[String] = None
  private var transactionNum = 0

  def log[S: Statement](ast: S) = {
    val message = ast.getStatement.value
    println(message)
    messages += message
  }

  def log(message: String) = {
    val fullMessage = s"INFO: $message"
    println(fullMessage)
    messages += fullMessage
  }

  def startTransaction(name: Option[String]): Unit =
    transaction match {
      case Some(current) =>
        log(s"Invalid state: new transaction started until current [$current] not commited")
      case None =>
        log(s"New transaction ${name.getOrElse(" ")} started")
        transactionNum += 1
        val transactionName = name.getOrElse(transactionNum.toString)
        transaction = Some(name.getOrElse(transactionName))
    }

  def commitTransaction: Unit =
    transaction match {
      case Some(current) =>
        log(s"Transaction [$current] successfully closed")
      case None =>
        log("Invalid state: trying to close non-existent transaction")
    }

  def getResult: List[String] =
    messages.result().toList
}

object DryRun extends Connection[DryRun] {
  def getConnection(config: LoaderConfig): DryRun = {
    val logConnection = new DryRun
    logConnection.log(s"Connected to ${config.snowflakeDb} database")
    logConnection
  }

  def execute[S: Statement](connection: DryRun, ast: S): Unit =
    connection.log(ast)

  def startTransaction(connection: DryRun, name: Option[String]): Unit =
    connection.startTransaction(name)

  def commitTransaction(connection: DryRun): Unit =
    connection.commitTransaction

  def executeAndOutput[S: Statement](connection: DryRun, ast: S): Unit =
    connection.log(ast)

  def executeAndCountRows[S: Statement](connection: DryRun, ast: S): Int = {
    connection.log(ast)
    1 // Used for preliminary checks
  }
}

