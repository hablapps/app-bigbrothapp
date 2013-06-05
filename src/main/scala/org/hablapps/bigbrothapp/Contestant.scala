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

package org.hablapps.bigbrothapp

import language.reflectiveCalls

import org.hablapps.{updatable,react,speech}
import updatable._
import react._

object Contestant {

  trait State { self: speech.Program with BigBrothappProgram =>

	trait Contestant extends Agent {
	  type This = Contestant
	  type Substatus = Nothing
	  type Context = BigBrothapp
	  type PlayerCol[x] = Traversable[x]
	  type Player = Nothing
	  type RoleCol[x] = Set[x]
	  type Role = Housemate
	  type PerformCol[x] = Traversable[x]
	  type Perform = Nothing
	}

    implicit val Contestant = builder[Contestant]
  }
}
