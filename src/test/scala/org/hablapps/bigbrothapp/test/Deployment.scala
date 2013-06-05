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

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.ShouldMatchers

import org.hablapps.updatable._
import org.hablapps.react
import org.hablapps.speech
import org.hablapps.bigbrothapp._

class Deployment(sys: BigBrothappProgram with react.Debug) extends FunSpec with ShouldMatchers with BeforeAndAfter {

  describe("BigBrothapp") {

    it("should be deployed with some initial entities") {

      import sys._
    
      reset(for {
        sea <- Initiate(BigBrothapp());
        hou <- Initiate(House(), sea);
        aud <- Initiate(Audience(), sea);
        leonardo <- Play(Contestant(), sea);
        raphael <- Play(Contestant(), sea);
        redRanger <- Play(Contestant(), sea);
        shredder <- Play(Contestant(), sea);
        brotha <- Play(BigBrotha(), hou);
        leoHM <- Play(Housemate(), leonardo, hou);
        rapHM <- Play(Housemate(), raphael, hou);
        redHM <- Play(Housemate(), redRanger, hou);
        shrHM <- Play(Housemate(), shredder, hou);
        vwr1 <- Play(Viewer(), aud);
        vwr2 <- Play(Viewer(), aud);
        vwr3 <- Play(Viewer(), aud)
      } yield ())
    
      val obtained = getState

      reset(for {
        sea <- Initiate(BigBrothapp());
        hou <- Initiate(House(), sea);
        aud <- Initiate(Audience(), sea);
        leonardo <- Play(Contestant(), sea);
        raphael <- Play(Contestant(), sea);
        redRanger <- Play(Contestant(), sea);
        shredder <- Play(Contestant(), sea);
        brotha <- Play(BigBrotha(), hou);
        leoHM <- Play(Housemate(), leonardo, hou);
        rapHM <- Play(Housemate(), raphael, hou);
        redHM <- Play(Housemate(), redRanger, hou);
        shrHM <- Play(Housemate(), shredder, hou);
        vwr1 <- Play(Viewer(), aud);
        vwr2 <- Play(Viewer(), aud);
        vwr3 <- Play(Viewer(), aud)
      } yield ())

      /* No rules, just structural check */
      obtained should be(getState)
    }
  }
}
