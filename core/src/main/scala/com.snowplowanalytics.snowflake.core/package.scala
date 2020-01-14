/*
 * Copyright (c) 2017-2020 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.snowflake

import cats.Id
import cats.effect.{ Clock, IO }

import scala.concurrent.duration.TimeUnit

package object core {
  implicit def idClock: Clock[Id] = new Clock[Id] {
    def realTime(unit: TimeUnit): Id[Long] =
      unit.convert(System.currentTimeMillis(), java.util.concurrent.TimeUnit.MILLISECONDS)
    def monotonic(unit: TimeUnit): Id[Long] =
      unit.convert(System.nanoTime(), java.util.concurrent.TimeUnit.NANOSECONDS)
  }

  implicit def ioClock: Clock[IO] =
    Clock.create[IO]
}
