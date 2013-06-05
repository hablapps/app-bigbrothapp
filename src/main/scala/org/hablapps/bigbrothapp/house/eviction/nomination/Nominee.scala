/*
 * Copyright (c) 2013 Habla Computing
 *
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

package org.hablapps.bigbrothapp.house.eviction.nomination

import language.reflectiveCalls

import org.hablapps.{ updatable, react, speech, bigbrothapp }
import updatable._
import react._
import bigbrothapp._

object Nominee {

  trait State { self: speech.Program with BigBrothappProgram =>

	trait Nominee extends Agent {
	  type This = Nominee
	  type Substatus = Nothing
	  type Context = Nomination
	  type PlayerCol[x] = Option[x]
	  type Player = Housemate
	  type RoleCol[x] = Traversable[x]
	  type Role = Nothing
	  type PerformCol[x] = Traversable[x]
	  type Perform = Nothing

	  def housemate = player.get
	  def nomination = context.get
	  def voters = nomination.voters
	}
				  
    implicit val Nominee = builder[Nominee]
  }
}
