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

object Nominator {

  trait State { self: speech.Program with BigBrothappProgram =>

    trait Nominator extends Agent {
      type This = Nominator
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
    }
				  
    implicit val Nominator = builder[Nominator]

    trait Nominate extends Join {
      type This = Nominate
      type Substatus = Nothing
      type Context = Nomination
      type Performer = Housemate
      type Addressee = Nothing
      type New = Nominator

      val reason: String

      def nomination = context.head
      def eviction = nomination.context.head
      def housemate = performer.get
    
      def NewE = implicitly[Evidence[Nominator]]
      def BuilderE: Builder[Nominator] = Nominator

      override def empowered(implicit state: State) =
        (eviction.substatus == Option(Nominating)) &&
          (! reason.isEmpty) &&
          (housemate.nominating.size == 0) &&
          (nomination.nominee.housemate != housemate)
    }
    
    implicit val Nominate = builder[Nominate]
  }

  trait Rules { self: speech.Program with BigBrothappProgram =>
    when {
      case New(nominator: $[Nominator], nVal: Nominator) => implicit state => {
        val mates = nominator.housemate.house.housemates
        val cnt = mates.foldLeft(0) { (c, m) =>
          c + m.nominating.size
        }
        if (mates.size == cnt) {
          Sequence(
            GetReadyForVotation(nominator.nomination.eviction),
            Let(nVal.nomination.eviction.substatus += Polling))
        }
        else
          ActionId() // not everyone has posted a nomination yet
      }
    }
  }

  trait Actions { self: speech.Program with BigBrothappProgram => 

  case class FreeNominees(eviction: $[Eviction]) extends DefinedAction(
    implicit state => {
      
      val bound = (eviction.nominees map { n => 
        n.nomination.nominators.size
      }).sorted.reverse(1)

      For(eviction.nominees.filter { n => 
        n.nomination.nominators.size < bound
      }) {
        case n => Abandon(n)
      }
    })

  case class FreeNominators(eviction: $[Eviction]) extends DefinedAction(
    For(eviction.nominators) {
      case n => Abandon(n)
    })

  case class FreeNominations(eviction: $[Eviction]) extends DefinedAction(
    For(eviction.nominations filter { _.member.isEmpty }) {
      case n => Finish(n)
    })

  case class GetReadyForVotation(eviction: $[Eviction]) extends DefinedAction(
    Sequence(
      FreeNominees(eviction), 
      FreeNominators(eviction),
      FreeNominations(eviction)))
  }

}
