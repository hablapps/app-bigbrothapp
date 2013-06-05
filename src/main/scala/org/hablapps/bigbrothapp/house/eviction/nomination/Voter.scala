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

object Voter {

  trait State { self: speech.Program with BigBrothappProgram =>

    trait Voter extends Agent {
      type This = Voter
      type Substatus = Nothing
      type Context = Nomination
      type PlayerCol[x] = Option[x]
      type Player = Viewer
      type RoleCol[x] = Traversable[x]
      type Role = Nothing
      type PerformCol[x] = Traversable[x]
      type Perform = Nothing
	  }
				  
    implicit val Voter = builder[Voter]

    trait CastVote extends Join {
      type This = CastVote
      type Substatus = Nothing
      type Context = Nomination
      type Performer = Viewer
      type Addressee = Nothing
      type New = Voter

      def viewer = performer.get
      def nomination = context.get

      def NewE = implicitly[Evidence[Voter]]
      def BuilderE: Builder[Voter] = Voter

      override def empowered(implicit state: State) =
        (nomination.eviction.substatus.get == Polling) && 
          (viewer.votating.size == 0)
    }

    implicit val CastVote = builder[CastVote]
  }
}
