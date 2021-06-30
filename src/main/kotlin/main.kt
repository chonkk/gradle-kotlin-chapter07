
//[코틀린 프로그래밍] Chapter.06 오류를 예방하는 타입 안정성
fun main(){
//    sample1()
//    sample2()
//    sample3()
//    sample4()
//    sample5()
//    sample6()
//    sample7()
//    sample8()
//    sample9()
//    sample10()
//    sample11()
//    sample12()
//    sample13()
//    sample14()
    sample15()
}
// 선언 방법
data class Task(
    val id: Int,
    val name: String,
    val completed: Boolean,
    val assigned: Boolean
)
fun sample15() {
    // 사용
    val task = Task(
        1,
        "Create Project",
        assigned = true,
        completed = false
    )

// 구조분해를 하지 않는 경우
    println(task.assigned)
    println(task.id)
    println(task.name)
    println(task.completed)

// 구조분해를 하는 경우
    val (id, _, _, isAssigned) = task
    println("$id, $isAssigned")
}

// 제네릭에 조건이 하나만 있으므로 where절을 사용하지 않고 :만 사용하자!
class PriorityPair<T : Comparable<T>>(member1: T, member2: T) {
    val first: T
    val second: T

    init {
        if (member1 >= member2) {
            first = member1
            second = member2
        } else {
            first = member2
            second = member1
        }
    }

    override fun toString(): String {
        return "$first $second"
    }
}
fun sample14() {
    // check!!
    println(PriorityPair(2, 1))  // 2, 1
    println(PriorityPair("A", "B"))  // B, A
}

class MachineOperator2 private constructor(val name: String) {
    fun checkin() = checkedIn++
    fun checkout() = checkedIn--

    companion object MachineOperatorFactory {
        var checkedIn = 0
        fun create(name: String): MachineOperator2 {
            val instance = MachineOperator2(name)
            instance.checkin()
            return instance
        }
    }
}
fun sample13() {
    val operator = MachineOperator2.create("Meter")
    println(MachineOperator.checkedIn)
//    val operator2 = MachineOperator2("test")
}

// 컴패니언 명 지정
class MachineOperator(val name: String) {
    fun checkin() = checkedIn++
    fun checkout() = checkedIn--

    companion object MachineOperatorFactory {   // 선언
        var checkedIn = 0
        fun minimumBreak() = "15 minutes every 2 hours"
    }
}
fun sample12() {
    // 컴패니언 사용
    MachineOperator("Mater").checkin()
    println(MachineOperator.minimumBreak())
    println(MachineOperator.checkedIn)
    MachineOperator("Mater").checkin()
    MachineOperator("Mater").checkout()
    MachineOperator("Mater").checkin()
    println(MachineOperator.checkedIn)
// 사용
    val ref = MachineOperator.MachineOperatorFactory
    println(ref.checkedIn)
    println(ref.minimumBreak())
}

inline class SSN(val id: String)
fun sample11() {

    fun receiveSSN(ssn: SSN) {
        println("Received $ssn")
        println("Received ${ssn.id}")
    }

    receiveSSN(SSN("111-111-1111111"))
}

// Method 접근자 설정
class Person2(val first: String, val last: String) {
    internal fun fullName() = "$last $first"
    private fun yearsOfService(): Int = throw RuntimeException("Not implemented yet")
}
fun sample10() {
    val jane = Person2("Jane", "Doe")
    println(jane.fullName())
//jane.yearsOfService()  // 접근이 불가능하다.
}

class Person(val first: String, val last: String) {
    var fulltime = true
    var location: String = "-"

    constructor(first: String, last: String, fte: Boolean) : this(first, last) {
        fulltime = fte
    }

    constructor(first: String, last: String, loc: String) : this(first, last) {
        fulltime = false
        location = loc
    }

    override fun toString(): String {
        return "person(first='$first', last='$last', fulltime=$fulltime, location='$location')"
    }
}
fun sample9() {
    println(Person("Jame", "Doe"))
    println(Person("Jame", "Doe", false))
    println(Person("Jame", "Doe", "home"))
}


class Car4( val yearOfMake: Int, theColor: String) {
    var fuelLevel = 100
        private set
    var color = theColor
        set(value) {
            if (value.isBlank()) {
                throw RuntimeException("no empty, please")
            }
            field = value
        }

    init {  // 초기화 블록
        if (yearOfMake < 2020) {
            fuelLevel = 90
        }
    }
}
fun sample8() {
    val car = Car4(2010, "123")
    println(car.color)
    println(car.fuelLevel)
}

// car3 클래스의 theColor field의 값을 바꾸기 위해 set, field를 사용
class Car3(val yearOfMake: Int, theColor: String) {
    var fuelLevel = 100
    var color = theColor
        // private set(value) {  // private set으로 만들 경우
        set(value) {         // 속성 제어 변경
            if (value.isBlank()) {
                throw RuntimeException("no empty, please")
            }
            field = value
        }
}
fun sample7() {
    val car = Car3(2019, "123")
    println(car.color)
}
// read && write class
class Car(val yearOfMake: Int, var color: String)

fun sample6() {

    val car = Car(2019, "Red")
    println(car.color)
    car.color = "Green"
    println(car.yearOfMake)
    println(car.color)
}

// 싱글톤으로 val, var, mothod 구현
object Sun : Runnable {
    val radiusInKM = 615453
    var coreTemperatureInC = 1908527
    override fun run() {
        println("spin....")
    }
}
fun sample5() {

    fun moveIt(runnable: Runnable) {
        runnable.run()
    }

    println(Sun.radiusInKM)
    moveIt(Sun)
}

fun sample4() {
    fun createRunnable2(): AutoCloseable = object : Runnable, AutoCloseable {
        override fun run() {
            println("run..")
        }

        override fun close() {
            println("close..")
        }
    }
//    createRunnable2().run()
    createRunnable2().close()
}

fun sample3() {
    fun createRunnable(): Runnable = Runnable { println("runnable run..") }
    createRunnable().run()
}

fun sample2() {
    fun createRunnable(): Runnable {
        var runnable = object : Runnable {
            override fun run() {
                println("runnable run..")
            }
        }
        return runnable
    }

    val runnable = createRunnable()
    runnable.run()
}

fun sample1() {
    fun drawCircle() {
        val circle = object {
            val x = 10
            val y = 20
            val radius = 30
        }
        println("Circle x : ${circle.x} y : ${circle.y} radius : ${circle.radius}")
    }

    drawCircle()
}
