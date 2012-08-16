package optional

/**
 *
 */

class ObjectToArgs(val obj: Object) {
  val argParser = new ArgumentParser[FieldArgAssignable](
    ReflectionUtils.getAllDeclaredFields(obj.getClass).map{f => new FieldArgAssignable(f)}
  )

  def parse(args: Array[String]) {
    println(argParser.nameToHolder)
    val parsed = argParser.parse(args)
    parsed.foreach{
      kv =>
        val field = kv._1.field
        field.setAccessible(true)
        field.set(obj, kv._2.value)
    }
  }
}

trait FieldParsing {
  lazy val parser = new ObjectToArgs(this)
  def parse(args: Array[String]) {
    parser.parse(args)
  }
}