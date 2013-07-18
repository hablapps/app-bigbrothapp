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

// package org.hablapps.bigbrothapp.test

// import org.hablapps.{ updatable, react, speech, bigbrothapp }
// import updatable._
// import speech.web

// object WebConsole extends App {

//   object System extends web.PlainSystem with bigbrothapp.BigBrothappProgram {
//     longPollingWaitTime = 15000

//     applicationFilesPath = "static/"

//     val Output(habla) = reset(for {
//       sea <- Initiate(BigBrothapp().name += "bigBrothapp");
//       hou <- Initiate(House().name += "house", sea);
//       aud <- Initiate(Audience().name += "audience", sea);
//       red <- Play(Contestant().name += "red", sea);
//       blue <- Play(Contestant().name += "blue", sea);
//       green <- Play(Contestant().name += "green", sea);
//       yellow <- Play(Contestant().name += "yellow", sea);
//       brotha <- Play(BigBrotha().name += "brotha", hou);
//       redAtHouse <- Play(Housemate().name += "red", red, hou);
//       blueAtHouse <- Play(Housemate().name += "blue", blue, hou);
//       greenAtHouse <- Play(Housemate().name += "green", green, hou);
//       yellowAtHouse <- Play(Housemate().name += "yellow", yellow, hou);
//       _ <- Play(Viewer().name += "viewer1", aud);
//       _ <- Play(Viewer().name += "viewer2", aud);
//       _ <- Play(Viewer().name += "viewer3", aud)
//     } yield ())
   
//     turn_on_log = true
//   }

//   System.launch
// }
