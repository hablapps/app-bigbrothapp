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

class Winner(sys: speech.System with BigBrothappProgram with react.Debug) extends FunSpec with ShouldMatchers with BeforeAndAfter {

  describe("BigBrothapp") {
    
    import sys._

    val Output(
      brotha: $[BigBrotha],
      evict: $[Eviction],
      vw1: $[Viewer], 
      vw2: $[Viewer], 
      vw3: $[Viewer],
      leoNomination: $[Nomination],
      shrNomination: $[Nomination]) = reset(for {
        sea <- Initiate(BigBrothapp());
        hou <- Initiate(House(), sea);
        aud <- Initiate(Audience(), sea);
        leonardo <- Play(Contestant().name += "leonardo", sea);
        shredder <- Play(Contestant().name += "shredder", sea);
        brotha <- Play(BigBrotha(), hou);
        leoAtHouse <- Play(Housemate().name += "leonardo", leonardo, hou);
        shrAtHouse <- Play(Housemate().name += "shredder", shredder, hou);
        vw1 <- Play(Viewer(), aud);
        vw2 <- Play(Viewer(), aud);
        vw3 <- Play(Viewer(), aud);
        evict <- Initiate(Eviction().substatus += Polling, hou);
        leoNomination <- Initiate(Nomination().name += "leonardo", evict);
        _ <- Play3(Nominee().name += "leonardo", leoAtHouse, leoNomination);
        shrNomination <- Initiate(Nomination().name += "shredder", evict);
        _ <- Play3(Nominee().name += "shredder", shrAtHouse, shrNomination)
      } yield (brotha, evict, vw1, vw2, vw3, leoNomination, shrNomination))

    it("should achieve the polling and countingUp phases") {

      attempt(Say(
        CastVote(__new = Option(Voter())),
    	  vw1, 
    	  leoNomination))

      attempt(Say(
        CastVote(__new = Option(Voter())),
        vw2, 
        leoNomination))

      attempt(Say(
        CastVote(__new = Option(Voter())),
        vw3,
        shrNomination))

      val NextState(obtained) = attempt(Say(
        DeclareLet(
          _entity = Option(evict), 
          _attribute = "substatus", 
          _value = CountingUp,
          _mode = Option(true)),
        brotha,
        evict))

      reset(for {
        sea <- Initiate(BigBrothapp());
        hou <- Initiate(House(), sea);
        aud <- Initiate(Audience(), sea);
        leonardo <- Play(Contestant().name += "leonardo", sea);
        shredder <- Play(Contestant().name += "shredder", sea);
        brotha <- Play(BigBrotha(), hou);
        leoAtHouse <- Play(Housemate().name += "leonardo", leonardo, hou);
        shrAtHouse <- Play(Housemate().name += "shredder", shredder, hou);
        vw1 <- Play(Viewer(), aud);
        vw2 <- Play(Viewer(), aud);
        vw3 <- Play(Viewer(), aud);
        _ <- Abandon(leoAtHouse);
        _ <- Abandon(shrAtHouse);
        _ <- sea.winner /+ shredder
      } yield ())

	    obtained should be(getState)
    }
  }
}
