import kotlinx.coroutines.*
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun now()=ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.MILLIS)
fun log(msg: String) = println("${now()}:${Thread.currentThread()}: ${msg}")

fun launchInGlobalScope(){
    GlobalScope.launch {
        log("coroutine started")
    }
}

fun main1() {
    log("main started")
    launchInGlobalScope()
    log("launchInGlobalScope() executed")
    Thread.sleep(5000L)
    log("main() terminated")
}

fun main2() = runBlocking<Unit> {
    launch {   // 인자를 안 넘겨주면 상위 코루틴의 Context 를 가짐
        println("main runBlocking       : 나는 ${Thread.currentThread().name} 에서 돌아")
    }
    launch(Dispatchers.Unconfined) {  // 별도로 지정을 안 해줌
        println("Unconfined             : 나는 ${Thread.currentThread().name} 에서 돌아")
    }
    launch(Dispatchers.Default) {  // DefaultDispatchers 등록
        println("Default                : 나는 ${Thread.currentThread().name} 에서 돌아")
    }
    launch(newSingleThreadContext("H43RO_Thread")) {  // 새로운 쓰레드 생성
        println("newSingleThreadContext : 나는 ${Thread.currentThread().name} 에서 돌아")
    }
}


fun sumAll(){
    runBlocking{
        val d1 = async{delay(1000L); 1}
        log("after aync(d1)")
        val d2 = async{delay(1000L); 2}
        log("after aync(d2)")
        val d3 = async{delay(1000L); 3}
        log("after aync(d3)")

        log("1+2+3=${d1.await() + d2.await() + d3.await()}")
        log("after await all & add")
    }
}

//B
fun countdown() = sequence {
    var n = 10
    while(n > 0){
        yield(n)         // ...(ㄱ)
        n--
    }
}

fun example1(){
    for (i in countdown()){ // ...(ㄴ), (ㄴ'), (ㄴ'')...
        println(i)
    }
}

fun main(){
    example1()

}