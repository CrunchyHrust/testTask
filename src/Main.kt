import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.Period


val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

fun readData(filePath: String): List<User> {
    val users = mutableListOf<User>()

    File(filePath).forEachLine { line: String ->
        val parts = line.split(';')
        val (name, gender, birthdayString, phoneNumber, payementString) = parts
        val birthday = LocalDate.parse(birthdayString, formatter)
        val payement = if (payementString.isNotBlank()){
            payementString.toDouble()
        } else {
            0.0
        }
        val user = User(name, gender, birthday, phoneNumber, payement)
        users.add(user)
    }
    return users
}

data class User (
    val name: String,
    val gender: String,
    val birthday: LocalDate,
    val phoneNumber: String,
    val payement: Double
)

fun main(){
    val filePath = "data"
    val users = readData(filePath)

    users.forEach { println(it) }

    val females = users.count {it.gender == "жен"}
    println("Количество женщин $females")
    val males = users.count {it.gender == "муж"}
    println("Количество мужчин $males")

    val today = LocalDate.now()
    var boomers = 0
    for (user in users) {
        val period = Period.between(user.birthday, today)
        if (period.years > 25) {
            boomers += 1
        }
    }
    println("Число людей старше 25 лет $boomers")

    val delitel = users.count()
    var allPay: Double = 0.0
    for (user in users) {
        var sum: Double = allPay + user.payement
        allPay = sum
    }
    val midPay: Double = allPay / delitel
    println("средняя заработная плата - $midPay")

    val phoneWomen = users.count { it.gender == "жен" && it.phoneNumber.isNotBlank() }
    println("Число женщин с номером телефона $phoneWomen" )
}