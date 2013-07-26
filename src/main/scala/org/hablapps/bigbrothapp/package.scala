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

import org.hablapps.{ react, speech }
import org.hablapps.serializer. { serializeMe }
import org.hablapps.updatable._

object `package` {

  trait BigBrothappProgram extends speech.web.PlainSystem
      with BigBrothapp.State
      with Contestant.State
      with house.House.State
      with house.BigBrotha.State
      with house.Housemate.State
      with house.Housemate.Rules
      with audience.Audience.State
      with audience.Viewer.State
      with house.eviction.Eviction.State
      with house.eviction.Eviction.Rules
      with house.eviction.nomination.Nomination.State
      with house.eviction.nomination.Nominee.State
      with house.eviction.nomination.Nominator.State
      with house.eviction.nomination.Nominator.Actions
      with house.eviction.nomination.Nominator.Rules
      with house.eviction.nomination.Voter.State {
    serializeMe[this.type]
    println("The BigBrothapp Show Has Just Started!")
  }
}
