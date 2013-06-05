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

class EvictionStart(sys: speech.System with BigBrothappProgram with react.Debug) extends FunSpec with ShouldMatchers with BeforeAndAfter {

  describe("BigBrothapp") {

    import sys._

    val Output(
      brotha: $[BigBrotha],
      hou: $[House]) = reset(for {
        sea <- Initiate(BigBrothapp());
        hou <- Initiate(House(), sea);
        aud <- Initiate(Audience(), sea);
        leonardo <- Play(Contestant().name += "leonardo", sea);
        raphael <- Play(Contestant().name += "raphael", sea);
        redRanger <- Play(Contestant().name += "redRanger", sea);
        shredder <- Play(Contestant().name += "shredder", sea);
        brotha <- Play(BigBrotha(), hou);
        leoAtHouse <- Play(Housemate().name += "leonardo", leonardo, hou);
        rapAtHouse <- Play(Housemate().name += "raphael", raphael, hou);
        redAtHouse <- Play(Housemate().name += "redRanger", redRanger, hou);
        shrAtHouse <- Play(Housemate().name += "shredder", shredder, hou);
        _ <- Play(Viewer(), aud);
        _ <- Play(Viewer(), aud);
        _ <- Play(Viewer(), aud)
        } yield (brotha, hou))

    it("should empower the Big Brotha to start an eviction process") {

      val NextState(obtained) = attempt(Say(
      	EvictionSetUp(__new = Option(Eviction().substatus += Nominating)),
      	brotha, 
      	hou))

      reset(for {
        sea <- Initiate(BigBrothapp());
        hou <- Initiate(House(), sea);
        aud <- Initiate(Audience(), sea);
        leonardo <- Play(Contestant().name += "leonardo", sea);
        raphael <- Play(Contestant().name += "raphael", sea);
        redRanger <- Play(Contestant().name += "redRanger", sea);
        shredder <- Play(Contestant().name += "shredder", sea);
        brotha <- Play(BigBrotha(), hou);
        leoHM <- Play(Housemate().name += "leonardo", leonardo, hou);
        rapHM <- Play(Housemate().name += "raphael", raphael, hou);
        redHM <- Play(Housemate().name += "redRanger", redRanger, hou);
        shrHM <- Play(Housemate().name += "shredder", shredder, hou);
        _ <- Play(Viewer(), aud);
        _ <- Play(Viewer(), aud);
        _ <- Play(Viewer(), aud);
        su <- Say(
          EvictionSetUp(__new = Option(Eviction().substatus += Nominating)),
          brotha, 
          hou);
        _ <- Done(su, PERFORMED)
        evict <- Initiate(Eviction().substatus += Nominating, hou);
        leoNom <- Initiate(Nomination().name += "leonardo", evict);
        _ <- Play3(Nominee().name += "leonardo", leoHM, leoNom);
        rapNom <- Initiate(Nomination().name += "raphael", evict);
        _ <- Play3(Nominee().name += "raphael", rapHM, rapNom);
        redNom <- Initiate(Nomination().name += "redRanger", evict);
        _ <- Play3(Nominee().name += "redRanger", redHM, redNom);
        shrNom <- Initiate(Nomination().name += "shredder", evict);
        _ <- Play3(Nominee().name += "shredder", shrHM, shrNom)
      } yield ())

      obtained should be(getState)
    }
  }
}
