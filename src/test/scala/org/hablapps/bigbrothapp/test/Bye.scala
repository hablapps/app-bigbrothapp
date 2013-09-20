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

package org.hablapps.bigbrothapp.test

import language.reflectiveCalls

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.ShouldMatchers

import org.hablapps.updatable._
import org.hablapps.react
import org.hablapps.speech
import speech._
import org.hablapps.bigbrothapp._

class Bye(sys: speech.System with BigBrothappProgram with react.Debug) extends FunSpec with ShouldMatchers with BeforeAndAfter {

  describe("BigBrothapp") {
    
    import sys._

    val Output(
      hou: $[House],
      brotha: $[BigBrotha],
      leoAtHouse: $[Housemate],
      rapAtHouse: $[Housemate],
      shrAtHouse: $[Housemate]) = reset(for {
        sea <- Initiate(BigBrothapp())
        hou <- Initiate(House(), sea)
        aud <- Initiate(Audience(), sea)
        leonardo <- Play(Contestant().name += "leonardo", sea)
        raphael <- Play(Contestant().name += "raphael", sea)
        shredder <- Play(Contestant().name += "shredder", sea)
        brotha <- Play(BigBrotha(), hou)
        leoAtHouse <- Play(Housemate().name += "leonardo", leonardo, hou)
        rapAtHouse <- Play(Housemate().name += "raphael", raphael, hou)
        shrAtHouse <- Play(Housemate().name += "shredder", shredder, hou)
      } yield (hou, brotha, leoAtHouse, rapAtHouse, shrAtHouse))

    it("should achieve the polling and countingUp phases") {

      attempt(Say(
        FireHousemate().role += rapAtHouse,
        brotha,
        hou))

      val NextState(obtained) = attempt(Say(
        LeaveHousemate(),
        leoAtHouse,
        hou))

      reset(for {
        sea <- Initiate(BigBrothapp())
        hou <- Initiate(House(), sea)
        aud <- Initiate(Audience(), sea)
        leonardo <- Play(Contestant().name += "leonardo", sea)
        raphael <- Play(Contestant().name += "raphael", sea)
        shredder <- Play(Contestant().name += "shredder", sea)
        brotha <- Play(BigBrotha(), hou)
        _ <- Let(sea.winner += shredder)
      } yield ())

	    obtained should be(getState)
    }
  }
}
