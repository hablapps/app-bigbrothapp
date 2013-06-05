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

import org.hablapps.{ updatable, react, speech, bigbrothapp }
import updatable._
import speech.web

object WebConsole extends App {

  object System extends web.PlainSystem with bigbrothapp.BigBrothappProgram {
    applicationFilesPath = "/home/jlopez/indigo/workspace-trunk/SPEECH-WEB/src/org/hablapps/speech/web/js/console/"

    val Output(habla) = reset(for {
      sea <- Initiate(BigBrothapp().name += "bigBrothapp");
      hou <- Initiate(House().name += "house", sea);
      aud <- Initiate(Audience().name += "audience", sea);
      leonardo <- Play(Contestant().name += "leonardo", sea);
      raphael <- Play(Contestant().name += "raphael", sea);
      redRanger <- Play(Contestant().name += "redRanger", sea);
      shredder <- Play(Contestant().name += "shredder", sea);
      brotha <- Play(BigBrotha().name += "bigBrotha", hou);
      leoAtHouse <- Play(Housemate().name += "leonardo", leonardo, hou);
      rapAtHouse <- Play(Housemate().name += "raphael", raphael, hou);
      redAtHouse <- Play(Housemate().name += "redRanger", redRanger, hou);
      shrAtHouse <- Play(Housemate().name += "shredder", shredder, hou);
      _ <- Play(Viewer().name += "viewer1", aud);
      _ <- Play(Viewer().name += "viewer2", aud);
      _ <- Play(Viewer().name += "viewer3", aud)
    } yield ())
   
    turn_on_log = true
  }

  System.launch
}
