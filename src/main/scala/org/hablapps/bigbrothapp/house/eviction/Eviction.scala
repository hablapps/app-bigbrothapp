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

package org.hablapps.bigbrothapp.house.eviction

import language.reflectiveCalls

import org.hablapps.{ updatable, react, speech, bigbrothapp }
import updatable._
import react._
import bigbrothapp._

object Eviction {

  trait State { self: speech.Program with BigBrothappProgram =>

	  trait Eviction extends Interaction {
	    type This = Eviction
	    type Substatus = EvictionSubstatus
	    type ContextCol[x] = Option[x]
	    type Context = House
	    type SubinteractionCol[x] = List[x]
	    type Subinteraction = Nomination
	    type MemberCol[x] = Traversable[x]
	    type Member = Nothing
	    type EnvironmentCol[x] = Traversable[x]
	    type Environment = Nothing
	    type ActionCol[x] = Traversable[x]
	    type Action = SocialAction

	    def house = context.get
      def nominations = subinteraction.alias[Nomination]
      def nominees(implicit state: State) = 
        for (n <- nominations) yield n.nominee
      def nominators(implicit state: State) = 
        nominations flatMap { n => n.nominators }
      def voters(implicit state: State) =
        nominations flatMap { n => n.voters }
	  }
				  
    implicit val Eviction = builder[Eviction]

    trait EvictionSetUp extends SetUp {
      type This = EvictionSetUp
      type Substatus = Nothing
      type Context = House
      type Performer = BigBrotha
      type Addressee = Nothing
      type New = Eviction
    
      def house = context.head

      override def empowered(implicit state: State) =
        ! house.subinteraction.isDefined
    }
    
    implicit val EvictionSetUp = builder[EvictionSetUp]

    trait EvictionSubstatus extends EntityStatus

    case object Nominating extends EvictionSubstatus
    case object Polling extends EvictionSubstatus
    case object CountingUp extends EvictionSubstatus
  }

  trait Rules { self: speech.Program with BigBrothappProgram =>

    // This is not working at all!
    //
    declarer[BigBrotha].of[Eviction](Eviction._substatus)
      .empowered {
        case _ => implicit state => true
      }
      .permitted {
        case _ => implicit state => Some(true)
      }

    when {
      case _Set(e: $[Eviction], Eviction._substatus, CountingUp, true) => {
        implicit state => {
          val res = e.nominees.sortBy { n =>
            n.nomination.voters.size
          }.reverse
          val bye = res(0).housemate
          Sequence(
            FreeEviction(e),
            Abandon(bye))
        }
      }
    }

    // when {
    //   case ev @ New(e: $[Eviction], _: Eviction) =>
    //     For(e.house.housemates) { n =>
    //       Notify(n, ev)
    //     }
    // }

    when {
      case New(evict: $[Eviction] @unchecked, eVal: Eviction) => {
        implicit state => For(eVal.house.housemates) {
          case mate: $[Housemate] => {
            implicit state => AndNext(
              Initiate2(Nomination(_name = mate.name), evict),
              {
                case New(nom: $[Nomination], _) =>
                  Play3(Nominee(_name = mate.name), mate, nom)
              })
          }
        }
      }
    }
  }

  trait Actions { self: speech.Program with BigBrothappProgram => 
  
    case class FreePollingNominees(evict: $[Eviction]) extends DefinedAction(
      For(evict.nominees) {
        case n => Abandon(n)
      })

    case class FreeVoters(evict: $[Eviction]) extends DefinedAction(
      For(evict.voters) {
        case v => Abandon(v)
      })

    case class FreePollingNominations(evict: $[Eviction]) extends DefinedAction(
      For(evict.nominations) {
        case n => Finish(n)
      })

    case class FreeEviction(evict: $[Eviction]) extends DefinedAction(
      Sequence(
        FreePollingNominees(evict),
        FreeVoters(evict),
        FreePollingNominations(evict),
        Finish(evict)))
  }
}
