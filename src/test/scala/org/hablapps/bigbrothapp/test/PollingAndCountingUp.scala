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
import org.hablapps.bigbrothapp._

class PollingAndCountingUp(sys: speech.System with BigBrothappProgram with react.Debug) extends FunSpec with ShouldMatchers with BeforeAndAfter {

  describe("BigBrothapp") {

    import sys._

    val Output(
      brotha: $[BigBrotha],
      evict: $[Eviction],
      vw1: $[Viewer], 
      vw2: $[Viewer], 
      vw3: $[Viewer],
      redNomination: $[Nomination],
      shrNomination: $[Nomination]) = reset(for {
        sea <- Initiate(BigBrothapp());
        hou <- Initiate(House(), sea);
        aud <- Initiate(Audience(), sea);
        leonardo <- Play(Contestant().name += "leonardo", sea);
        raphael <- Play(Contestant().name += "raphael", sea);
        redRanger <- Play(Contestant().name += "redRanger", sea);
        shredder <- Play(Contestant().name += "shredder", sea);
        brotha <- Play(BigBrotha(), hou);
        _ <- Play(Housemate().name += "leonardo", leonardo, hou);
        _ <- Play(Housemate().name += "raphael", raphael, hou);
        redAtHouse <- Play(Housemate().name += "redRanger", redRanger, hou);
        shrAtHouse <- Play(Housemate().name += "shredder", shredder, hou);
        vw1 <- Play(Viewer(), aud);
        vw2 <- Play(Viewer(), aud);
        vw3 <- Play(Viewer(), aud);
        evict <- Initiate(Eviction().substatus += Polling, hou);
        redNomination <- Initiate(Nomination().name += "redRanger", evict);
        _ <- Play3(Nominee().name += "redRanger", redAtHouse, redNomination);
        shrNomination <- Initiate(Nomination().name += "shredder", evict);
        _ <- Play3(Nominee().name += "shredder", shrAtHouse, shrNomination)
      } yield (brotha, evict, vw1, vw2, vw3, redNomination, shrNomination))

    it("should achieve the polling and countingUp phases") {

      attempt(Say(
        CastVote(__new = Option(Voter())),
    	  vw1, 
    	  redNomination))

      attempt(Say(
        CastVote(__new = Option(Voter())),
        vw2, 
        shrNomination))

      attempt(Say(
        CastVote(__new = Option(Voter())),
        vw3,
        redNomination))

      val NextState(obtained) = attempt(Say(
        DeclareLet(
          _entity = Option(evict), 
          _attribute = "substatus", 
          _value = CountingUp,
          _mode = Option(true)),
        brotha,
        evict))

      //println(obtained)

      reset(for {
        sea <- Initiate(BigBrothapp());
        hou <- Initiate(House(), sea);
        aud <- Initiate(Audience(), sea);
        leonardo <- Play(Contestant().name += "leonardo", sea);
        raphael <- Play(Contestant().name += "raphael", sea);
        redRanger <- Play(Contestant().name += "redRanger", sea);
        shredder <- Play(Contestant().name += "shredder", sea);
        brotha <- Play(BigBrotha(), hou);
        _ <- Play(Housemate().name += "leonardo", leonardo, hou);
        _ <- Play(Housemate().name += "raphael", raphael, hou);
        redAtHouse <- Play(Housemate().name += "redRanger", redRanger, hou);
        shrAtHouse <- Play(Housemate().name += "shredder", shredder, hou);
        vw1 <- Play(Viewer(), aud);
        vw2 <- Play(Viewer(), aud);
        vw3 <- Play(Viewer(), aud);
        _ <- Abandon(redAtHouse)
      } yield ())

	    obtained should be(getState)
    }
  }
}
