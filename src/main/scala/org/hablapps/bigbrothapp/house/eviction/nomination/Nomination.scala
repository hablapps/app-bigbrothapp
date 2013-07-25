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

object Nomination {

  trait State { self: speech.Program with BigBrothappProgram =>

    trait Nomination extends Interaction {
      type This = Nomination
      type ContextCol[x] = Option[x]
      type Context = Eviction
      type MemberCol[x] = List[x]
      type Member = Agent
      type Action = SocialAction

      def nominee = alias[Nominee, Nomination](member).head

      def nominators = alias[Nominator, Nomination](member)

      def voters = alias[Voter, Nomination](member)
      
      def eviction = context.head
    }
                  
    implicit val Nomination = builder[Nomination]
  }
}
