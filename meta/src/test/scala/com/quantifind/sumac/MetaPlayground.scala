package com.quantifind.sumac

import java.io.File

import scala.meta._
import org.scalameta.logger // useful for debugging

class MetaPlayground extends org.scalatest.FunSuite {
  import scala.meta._

  test("part 1: tokens") {
    val tokens = "val x = 2".tokenize.get
    logger.elem(tokens.syntax)
    logger.elem(tokens.structure)
  }

  test("part 2: trees") {
    val tree = "val x = 2".parse[Stat].get
    logger.elem(tree.syntax)
    logger.elem(tree.structure)
  }

  test("work with entire source files") {
    val dir = new File("meta/src/test/scala/com/quantifind/sumac/META_INPUT")
    val f = new File(dir, "ExampleClass.scala")
    val parsed = f.parse[Source]
    println(FileRewriter.rewrite(parsed))

    // TODO write result to a file, assert it matches expectations
  }

}
