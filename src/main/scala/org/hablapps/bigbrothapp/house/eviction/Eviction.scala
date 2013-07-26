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
      type Action = SocialAction

      def house = context.get

      def nominations = alias[Nomination, Eviction](subinteraction)

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
      type Context = House
      type Performer = BigBrotha
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

    declarer[BigBrotha].of[Eviction](Eviction._substatus)
      .empowered {
        case _ => true
      }
      .permitted {
        case _ => Some(true)
      }

    when {
      case Eviction._substatus(e: $[Eviction], CountingUp, true) => {
        implicit state => {
          val res = e.nominees.sortBy { n =>
            n.nomination.voters.size
          }.reverse
          val bye = res(0).housemate
          Sequence(
            RecFinish(e),
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
        For(eVal.house.housemates) {
          case mate: $[Housemate] => {
            AndNext(
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
}
