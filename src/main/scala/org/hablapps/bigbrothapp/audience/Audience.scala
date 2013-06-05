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

package org.hablapps.bigbrothapp.audience

import language.reflectiveCalls

import org.hablapps.{ updatable, react, speech, bigbrothapp }
import updatable._
import react._
import bigbrothapp._

object Audience {

  trait State { self: speech.Program with BigBrothappProgram =>

	trait Audience extends Interaction {
	  type This = Audience
	  type Substatus = Nothing
	  type ContextCol[x] = Option[x]
	  type Context = BigBrothapp
	  type SubinteractionCol[x] = Traversable[x]
	  type Subinteraction = Nothing
	  type MemberCol[x] = List[x]
	  type Member = Viewer
	  type EnvironmentCol[x] = Traversable[x]
	  type Environment = Nothing
	  type ActionCol[x] = Traversable[x]
	  type Action = Nothing
	}
				  
    implicit val Audience = builder[Audience]
  }
}
