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

object House {

  trait State { self: speech.Program with BigBrothappProgram =>

    trait House extends Interaction {
      type This = House
      type ContextCol[x] = Option[x]
      type Context = BigBrothapp
      type SubinteractionCol[x] = Option[x]
      type Subinteraction = Eviction
      type MemberCol[x] = List[x]
      type Member = Agent@Union[BigBrotha, Housemate]
      type ActionCol[x] = List[x]
      type Action = SocialAction

      def bigBrothapp = context.head

      def eviction = subinteraction.get
      
      def housemates = alias[Housemate, House](member)
    }

    implicit val House = builder[House]
  }
}
