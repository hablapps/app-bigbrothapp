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

package org.hablapps.bigbrothapp.house

import language.reflectiveCalls

import org.hablapps.{ updatable, react, speech, bigbrothapp }
import updatable._
import react._
import bigbrothapp._

object Housemate {

  trait State { self: speech.Program with BigBrothappProgram =>

    trait Housemate extends Agent {
      type This = Housemate
      type Context = House
      type PlayerCol[x] = Option[x]
      type Player = Contestant
      type Role = Agent @Union[Nominee, Nominator]
      type Perform = SocialAction

      def contestant = player.get

      def nominating = alias[Nominator, Housemate](role)

      def house = context.get
    }

    implicit val Housemate = builder[Housemate]

    trait FireHousemate extends Fire {
      type This = FireHousemate
      type Context = House
      type Performer = BigBrotha
      type Addressee = Nothing
      type Role = Housemate

      def house = context.get

      override def empowered_aux(implicit state: State) =
        house.subinteraction.isEmpty
    }
    
    implicit val FireHousemate = builder[FireHousemate]

    trait LeaveHousemate extends Leave {
      type This = LeaveHousemate
      type Context = House
      type Performer = Housemate
      type Addressee = Nothing

      def house = context.get

      override def empowered_aux(implicit state: State) =
        house.subinteraction.isEmpty
    }
    
    implicit val LeaveHousemate = builder[LeaveHousemate]
  }

  trait Rules{ self: speech.Program with BigBrothappProgram => 
    when {
      case Deleted(_, mate: Housemate) => implicit state => {
        if (mate.house.housemates.size == 1) {
          val winner = mate.house.housemates(0)
          Sequence(
            Let(mate.house.bigBrothapp.winner += winner.contestant),
            Abandon(winner))
        }
        else
          ActionId()
      }
    }
  }
}
