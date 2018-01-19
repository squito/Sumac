/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.quantifind.sumac

import scala.meta._
import scala.meta.parsers.Parsed

object FileRewriter {
  val FROM = "META_INPUT"
  val TO = "META_OUTPUT"

  def rewrite(orig: Parsed[Source]): Parsed[Source] = {
    orig match {
      case Parsed.Success(Source((pkg @ Pkg(_, _)) :: Nil)) =>
        Parsed.Success(Source(rewriteBody(pkg)))
      case other =>
        println(s"$orig isn't a Success(Source(...)), its a ${other.getClass()}")
        other
    }
  }

  def rewriteBody(pkg: Pkg): List[Stat] = {
    // change the package name
    println(pkg.tokens)
    println("*********")
    List(pkg.ref match {
      case select @ Term.Select(qual, name @ Term.Name(FROM)) =>
        val newRef = select.copy(name = name.copy(value = TO))
        // TODO :( this loses the comments on the original, no longer in the tokens :( :(
        val r = pkg.copy(ref = newRef)
        println(r.tokens)
        r
      case other =>
        pkg
    })
    // TODO change the rest :)
  }

}
