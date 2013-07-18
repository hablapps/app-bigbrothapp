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

class Nominating(sys: speech.System with BigBrothappProgram with react.Debug) extends FunSpec with ShouldMatchers with BeforeAndAfter {

  describe("BigBrothapp") {

    import sys._

    val Output(
      leoAtHouse: $[Housemate], 
      rapAtHouse: $[Housemate], 
      redAtHouse: $[Housemate], 
      shrAtHouse: $[Housemate],
      leoNomination: $[Nomination],
      rapNomination: $[Nomination],
      redNomination: $[Nomination],
      shrNomination: $[Nomination]) = reset(for {
        sea <- Initiate(BigBrothapp())
        hou <- Initiate(House(), sea);
        aud <- Initiate(Audience(), sea);
        leonardo <- Play(Contestant().name += "leonardo", sea)
        raphael <- Play(Contestant().name += "raphael", sea)
        redRanger <- Play(Contestant().name += "redRanger", sea)
        shredder <- Play(Contestant().name += "shredder", sea)
        brotha <- Play(BigBrotha(), hou)
        leoAtHouse <- Play(Housemate().name += "leonardo", leonardo, hou)
        rapAtHouse <- Play(Housemate().name += "raphael", raphael, hou)
        redAtHouse <- Play(Housemate().name += "redRanger", redRanger, hou)
        shrAtHouse <- Play(Housemate().name += "shredder", shredder, hou)
        _ <- Play(Viewer(), aud)
        _ <- Play(Viewer(), aud)
        _ <- Play(Viewer(), aud)
        su <- Say(
          EvictionSetUp(__new = Option(Eviction().substatus += Nominating)),
          brotha, 
          hou)
        _ <- Done(su, PERFORMED)
        evict <- Initiate(Eviction().substatus += Nominating, hou)
        leoNomination <- Initiate(Nomination().name += "leonardo", evict)
        _ <- Play3(Nominee().name += "leonardo", leoAtHouse, leoNomination)
        rapNomination <- Initiate(Nomination().name += "raphael", evict)
        _ <- Play3(Nominee().name += "raphael", rapAtHouse, rapNomination)
        redNomination <- Initiate(Nomination().name += "redRanger", evict)
        _ <- Play3(Nominee().name += "redRanger", redAtHouse, redNomination)
        shrNomination <- Initiate(Nomination().name += "shredder", evict)
        _ <- Play3(Nominee().name += "shredder", shrAtHouse, shrNomination)
      } yield (
      	leoAtHouse, 
      	rapAtHouse, 
      	redAtHouse, 
      	shrAtHouse,
      	leoNomination,
      	rapNomination,
      	redNomination,
      	shrNomination))

    it("should allow the housemates to nominate each other") {

      attempt(Say(
        Nominate(
          __new = Option(Nominator().name += "leonardo"),
          _reason = "He is smelly"),
        leoAtHouse, 
        shrNomination))

      attempt(Say(
        Nominate(
          __new = Option(Nominator().name += "raphael"),
          _reason = "Because yes"),
        rapAtHouse, 
        shrNomination))

      attempt(Say(
        Nominate(
          __new = Option(Nominator().name += "redRanger"),
          _reason = "Because of an agreement"),
        redAtHouse,
        shrNomination))

      val NextState(obtained) = attempt(Say(
        Nominate(
          __new = Option(Nominator().name += "shredder"),
          _reason = "I hate red"),
        shrAtHouse, 
        redNomination))

      reset(for {
        sea <- Initiate(BigBrothapp())
        hou <- Initiate(House(), sea)
        aud <- Initiate(Audience(), sea)
        leonardo <- Play(Contestant().name += "leonardo", sea)
        raphael <- Play(Contestant().name += "raphael", sea)
        redRanger <- Play(Contestant().name += "redRanger", sea)
        shredder <- Play(Contestant().name += "shredder", sea)
        brotha <- Play(BigBrotha(), hou)
        leoAtHouse <- Play(Housemate().name += "leonardo", leonardo, hou)
        rapAtHouse <- Play(Housemate().name += "raphael", raphael, hou)
        redAtHouse <- Play(Housemate().name += "redRanger", redRanger, hou)
        shrAtHouse <- Play(Housemate().name += "shredder", shredder, hou)
        _ <- Play(Viewer(), aud)
        _ <- Play(Viewer(), aud)
        _ <- Play(Viewer(), aud)
        su <- Say(
          EvictionSetUp(__new = Option(Eviction().substatus += Nominating)),
          brotha, 
          hou)
        _ <- Done(su, PERFORMED)
        evict <- Initiate(Eviction().substatus += Nominating, hou)
        leoNomination <- Initiate(Nomination().name += "leonardo", evict)
        leoAsNominee <- Play3(Nominee().name += "leonardo", leoAtHouse, leoNomination)
        rapNomination <- Initiate(Nomination().name += "raphael", evict)
        rapAsNominee <- Play3(Nominee().name += "raphael", rapAtHouse, rapNomination)
        redNomination <- Initiate(Nomination().name += "redRanger", evict)
        redAsNominee <- Play3(Nominee().name += "redRanger", redAtHouse, redNomination)
        shrNomination <- Initiate(Nomination().name += "shredder", evict)
        shrAsNominee <- Play3(Nominee().name += "shredder", shrAtHouse, shrNomination)
        _ <- Abandon(leoAsNominee)
        _ <- Abandon(rapAsNominee)
        _ <- Finish(leoNomination)
        _ <- Finish(rapNomination)
        _ <- Let(leoAtHouse.date_updated += default_date)
        _ <- Let(rapAtHouse.date_updated += default_date)
        _ <- Let(redAtHouse.date_updated += default_date)
        _ <- Let(shrAtHouse.date_updated += default_date)
        _ <- Let(evict.date_updated += default_date)
        _ <- Let(shrNomination.date_updated += default_date)
        _ <- Let(redNomination.date_updated += default_date)
        _ <- Let(evict.substatus += Polling)
      } yield (
        leoAtHouse, 
        rapAtHouse, 
        redAtHouse, 
        shrAtHouse,
        leoNomination,
        rapNomination,
        redNomination,
        shrNomination))

	  obtained should be(getState)
    }
  }
}
