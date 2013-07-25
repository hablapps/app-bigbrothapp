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
import org.hablapps.{ updatable, react, speech }
import updatable._
import react._

object BigBrothapp {

  trait State { self: speech.Program with BigBrothappProgram =>

    trait BigBrothapp extends Interaction {
      type This = BigBrothapp
      type Subinteraction = Interaction
      type Member = Contestant

      val winner: Option[$[Contestant]]

      def house = alias[House, BigBrothapp](subinteraction).head

      def audience = alias[Audience, BigBrothapp](subinteraction)
    }

    implicit val BigBrothapp = builder[BigBrothapp]
  }
}
