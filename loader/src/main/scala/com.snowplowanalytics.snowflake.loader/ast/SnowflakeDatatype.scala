/*
 * PROPRIETARY AND CONFIDENTIAL
 *
 * Unauthorized copying of this project via any medium is strictly prohibited.
 *
 * Copyright (c) 2017 Snowplow Analytics Ltd. All rights reserved.
 */
package com.snowplowanalytics.snowflake.loader.ast

sealed trait SnowflakeDatatype

object SnowflakeDatatype {
  case class Varchar(size: Int) extends SnowflakeDatatype
  case object Timestamp extends SnowflakeDatatype
  case class Char(size: Int) extends SnowflakeDatatype
  case object SmallInt extends SnowflakeDatatype
  case object DoublePrecision extends SnowflakeDatatype
  case object Integer extends SnowflakeDatatype
  case class Number(precision: Int, scale: Int) extends SnowflakeDatatype
  case object Boolean extends SnowflakeDatatype
  case object Variant extends SnowflakeDatatype
  case object JsonObject extends SnowflakeDatatype
  case object JsonArray extends SnowflakeDatatype
}