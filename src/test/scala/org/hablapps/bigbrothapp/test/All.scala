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

import org.scalatest.BeforeAndAfter
import org.scalatest.Suite
import org.scalatest.Suites
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import scala.collection.immutable.HashSet
import scala.collection.immutable.HashMap

class All extends Suites(
  new Bye(new BigBrothappSystem),
  new Deployment(new BigBrothappSystem),
  new EvictionStart(new BigBrothappSystem),
  new Nominating(new BigBrothappSystem),
  new PollingAndCountingUp(new BigBrothappSystem),
  new Winner(new BigBrothappSystem))
