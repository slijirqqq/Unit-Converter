package converter

import java.util.*
import kotlin.system.exitProcess

class Data(
    val code: Int,
    val _startNumber: Double = 0.0,
    var _measure: String = "???",
    var _convertMeasure: String = "???",
    var _convertNumber: Double = 0.0,
    var _enumClassStart: String = "",
    var _enumClassConvert: String = ""
) {
    companion object {
        private fun checkOutPut(data: Data, code: Int, output: String){
            when (code) {
                0 -> {
                    if (data._measure == "degrees_fahrenheit") data._measure = "degrees Fahrenheit"
                    if (data._measure == "degrees_celsius") data._measure = "degrees Celsius"
                    if (data._convertMeasure == "degrees_fahrenheit") data._convertMeasure = "degrees Fahrenheit"
                    if (data._convertMeasure == "degrees_celsius") data._convertMeasure = "degrees Celsius"
                    println("${data._startNumber} ${data._measure} is ${data._convertNumber} ${data._convertMeasure}")
                }
                1 -> {
                    when (data._convertMeasure) {
                        "degrees_fahrenheit" -> data._convertMeasure = "degree Fahrenheit"
                        "degrees_celsius" -> data._convertMeasure = "degree Celsius"
                        "inches" -> data._convertMeasure = "inch"
                        "feet" -> data._convertMeasure = "foot"
                        else -> data._convertMeasure = data._convertMeasure.removeSuffix("s")
                    }
                    if (data._measure == "degrees_celsius" || data._measure == "degree_celsius") data._measure = "degrees Celsius"
                    if (data._measure == "degrees_fahrenheit" || data._measure == "degree_fahrenheit") data._measure = "degrees Fahrenheit"
                    println("${data._startNumber} ${data._measure} is ${data._convertNumber} ${data._convertMeasure}")
                }
                2 -> {
                    when (data._measure) {
                        "degrees_fahrenheit" -> data._measure = "degree Fahrenheit"
                        "degrees_celsius" -> data._measure = "degree Celsius"
                        "inches" -> data._measure = "inch"
                        "feet" -> data._measure = "foot"
                        else -> data._measure = data._measure.removeSuffix("s")
                    }
                    if (data._convertMeasure == "degrees_celsius" || data._convertMeasure == "degree_celsius") data._convertMeasure = "degrees Celsius"
                    if (data._convertMeasure == "degrees_fahrenheit" || data._convertMeasure == "degree_fahrenheit") data._convertMeasure = "degrees Fahrenheit"
                    println("${data._startNumber} ${data._measure} is ${data._convertNumber} ${data._convertMeasure}")
                }
                3 -> {
                    if (data._measure == "degrees_fahrenheit") data._measure = "degrees Fahrenheit"
                    else if (data._measure == "degrees_celsius") data._measure = "degrees Celsius"
                    else if (data._measure == "inches") data._measure = "inch"
                    else if (data._measure == "feet") data._measure = "foot"
                    else data._measure = data._measure.removeSuffix("s")

                    if (data._convertMeasure == "degrees_fahrenheit") data._convertMeasure = "degrees Fahrenheit"
                    else if (data._convertMeasure == "degrees_celsius") data._convertMeasure = "degrees Celsius"
                    else if (data._convertMeasure == "inches") data._convertMeasure = "inch"
                    else if (data._convertMeasure == "feet") data._convertMeasure = "foot"
                    else data._convertMeasure = data._convertMeasure.removeSuffix("s")

                    println("${data._startNumber} ${data._measure} is ${data._convertNumber} ${data._convertMeasure}")
                }
                4 -> println(output)
            }
        }

        private fun checkCount(startNumber: Double, convertNumber: Double): Int {
            return if (startNumber != 1.0 && convertNumber != 1.0) 0
            else if (startNumber != 1.0 && convertNumber == 1.0) 1
            else if (startNumber == 1.0 && convertNumber != 1.0) 2
            else 3
        }

        private fun convertTo(data: Data) {
            for (_enum in Measures.values()) {
                if (data._measure == _enum.shortName || data._measure == _enum.fullName || data._measure == _enum.sName || data._measure == _enum.T || data._measure == _enum.Ts) {
                    data._measure = _enum.name.toLowerCase()
                }
                if (data._convertMeasure == _enum.shortName || data._convertMeasure == _enum.fullName || data._convertMeasure == _enum.sName || data._convertMeasure == _enum.T || data._convertMeasure == _enum.Ts) {
                    data._convertMeasure = _enum.name.toLowerCase()
                }
            }
        }

        private fun printResult(data: Data) {
            var code: Int = 0
            var output: String = ""
            for (enum in TemperatureSize.values()) {
                if (data._measure.toUpperCase() == enum.name) {
                    enum.convertTo = data._convertMeasure
                    enum.returnResultOfConversion(enum, data)
                    code = checkCount(data._startNumber, data._convertNumber)
                    convertTo(data)
                }
            }
            for (enum in LengthSize.values()) {
                if (data._measure.toUpperCase() == enum.name) {
                    output = enum.conversionToStartMeasure(data)
                    code = if (output != "") 4
                    else checkCount(data._startNumber, data._convertNumber)
                    convertTo(data)
                }
            }
            for (enum in WidthSize.values()) {
                if (data._measure.toUpperCase() == enum.name) {
                    output = enum.conversionToStartMeasure(data)
                    code = if (output != "") 4
                    else checkCount(data._startNumber, data._convertNumber)
                    convertTo(data)
                }
            }
            checkOutPut(data, code, output)
        }

        fun printExceptions(data: Data) {
            when (data.code) {
                0 -> exitProcess(0)
                1 -> println("Parse error")
                2 -> {
                    convertTo(data)
                    if (data._measure == "degrees_fahrenheit") data._measure = "degrees Fahrenheit"
                    if (data._measure == "degrees_celsius") data._measure = "degrees Celsius"
                    if (data._convertMeasure == "degrees_fahrenheit") data._convertMeasure = "degrees Fahrenheit"
                    if (data._convertMeasure == "degrees_celsius") data._convertMeasure = "degrees Celsius"
                    println("Conversion from ${data._measure} to ${data._convertMeasure} is impossible")
                    }
                3 -> printResult(data)
            }
        }
    }
}

enum class Measures(
    val shortName: String,
    val fullName: String,
    val sName: String,
    val T: String = "",
    val Ts: String = ""
) {
    METERS("m", "meter", "meters"),
    CENTIMETERS("cm", "centimeter", "centimeters"),
    MILLIMETERS("mm", "millimeter", "millimeters"),
    KILOMETERS("km", "kilometer", "kilometers"),
    YARDS("yd", "yard", "yards"),
    MILES("mi", "mile", "miles"),
    FEET("ft", "foot", "feet"),
    INCHES("in", "inch", "inches"),
    GRAMS("g", "gram", "grams"),
    KILOGRAMS("kg", "kilogram", "kilograms"),
    MILLIGRAMS("mg", "milligram", "milligrams"),
    POUNDS("lb", "pound", "pounds"),
    OUNCES("oz", "ounce", "ounces"),
    DEGREES_CELSIUS("c", "dc", "celsius", "degreeCelsius", "degreesCelsius"),
    KELVINS("k", "kelvin", "kelvins"),
    DEGREES_FAHRENHEIT("f", "df", "fahrenheit", "degreeFahrenheit", "degreesFahrenheit")
}

enum class LengthSize(val Name: String, val sequenceLength: Double) {
    METER("meter", 1.0),
    CENTIMETER("centimeter", 0.01),
    MILLIMETER("millimeter", 0.001),
    KILOMETER("kilometer", 1000.0),
    MILE("mile", 1609.35),
    YARD("yard", 0.9144),
    FOOT("foot", 0.3048),
    INCH("inch", 0.0254),
    METERS("meters", 1.0),
    CENTIMETERS("centimeters", 0.01),
    MILLIMETERS("millimeters", 0.001),
    KILOMETERS("kilometers", 1000.0),
    MILES("miles", 1609.35),
    YARDS("yards", 0.9144),
    FEET("feet", 0.3048),
    INCHES("inches", 0.0254),
    M("m", 1.0),
    KM("km", 1000.0),
    CM("cm", 0.01),
    MM("mm", 0.001),
    YD("yd", 0.9144),
    ML("ml", 1609.35),
    FT("ft", 0.3048),
    IN("in", 0.0254);

    fun conversionToStartMeasure(data: Data): String {
        for (enum in LengthSize.values()) {
            if (data._measure.toUpperCase() == enum.name) {
                if (data._startNumber < 0) {
                    return "Length shouldn't be negative"
                }
                data._convertNumber = data._startNumber * enum.sequenceLength
            }
        }
        for (enum in LengthSize.values()) {
            if (data._convertMeasure.toUpperCase() == enum.name) {
                data._convertNumber /= enum.sequenceLength
                return ""
            }
        }
        return ""
    }
}

enum class WidthSize(val Name: String, val sequenceLength: Double) {
    GRAM("gram", 1.0),
    MILLIGRAM("milligram", 0.001),
    KILOGRAM("kilogram", 1000.0),
    POUND("pound", 453.592),
    OUNCE("ounce", 28.3495),
    GRAMS("grams", 1.0),
    MILLIGRAMS("milligrams", 0.001),
    KILOGRAMS("kilograms", 1000.0),
    POUNDS("pounds", 453.592),
    OUNCES("ounces", 28.3495),
    LB("lb", 453.592),
    OZ("oz", 28.3495),
    G("g", 1.0),
    MG("mg", 0.001),
    KG("kg", 1000.0);

    fun conversionToStartMeasure(data: Data): String {
        for (enum in WidthSize.values()) {
            if (data._measure.toUpperCase() == enum.name) {
                if (data._startNumber < 0) {
                    return "Weight shouldn't be negative"
                }
                data._convertNumber = data._startNumber * enum.sequenceLength
            }
        }
        for (enum in WidthSize.values()) {
            if (data._convertMeasure.toUpperCase() == enum.name) {
                data._convertNumber /= enum.sequenceLength
                return ""
            }
        }
        return ""
    }
}

enum class TemperatureSize(val Name: String, var convertTo: String = "") {
    CELSIUS("celsius"),
    C("c"),
    DC("dc"),
    DEGREECELSIUS("degreeCelsius"),
    DEGREESCELSIUS("degreesCelsius"),
    FAHRENHEIT("fahrenheit"),
    F("f"),
    DF("df"),
    DEGREEFAHRENHEIT("degreeFahrenheit"),
    DEGREESFAHRENHEIT("degreesFahrenheit"),
    KELVIN("kelvin"),
    K("k"),
    KELVINS("kelvins");

    fun returnResultOfConversion(measures: TemperatureSize, data: Data) {
        when (measures.convertTo) {
            "c", "dc", "celsius", "degreeCelsius", "degreesCelsius" -> {
                when (measures.Name) {
                    "f", "df", "fahrenheit", "degreeFahrenheit", "degreesFahrenheit" -> data._convertNumber =
                        (data._startNumber - 32.0) * (5.0 / 9.0)
                    "k", "kelvin", "kelvins" -> data._convertNumber = data._startNumber - 273.15
                    else -> data._convertNumber = data._startNumber
                }
            }
            "f", "df", "fahrenheit", "degreeFahrenheit", "degreesFahrenheit" -> {
                when (measures.Name) {
                    "c", "dc", "celsius", "degreeCelsius", "degreesCelsius" -> data._convertNumber = data._startNumber * 1.8 + 32.0
                    "k", "kelvin", "kelvins" -> data._convertNumber = data._startNumber * 9.0 / 5.0 - 459.67
                    else -> data._convertNumber = data._startNumber
                }
            }
            "k", "kelvin", "kelvins" -> {
                when (measures.Name) {
                    "f", "df", "fahrenheit", "degreeFahrenheit", "degreesFahrenheit" -> data._convertNumber =
                    (data._startNumber + 459.67) * (5.0 / 9.0)
                    "c", "dc", "celsius", "degreeCelsius", "degreesCelsius" -> data._convertNumber =
                        data._startNumber + 273.15
                    else -> data._convertNumber = data._startNumber
                }
            }
        }
    }
}

fun isSize(measure: String): List<String> {
    for (enum in TemperatureSize.values()) {
        if (measure.toUpperCase() == enum.name) return listOf(enum.Name, "t")
    }
    for (enum in LengthSize.values()) {
        if (measure.toUpperCase() == enum.name) return listOf(enum.Name, "l")
    }
    for (enum in WidthSize.values()) {
        if (measure.toUpperCase() == enum.name) return listOf(enum.Name, "w")
    }
    return listOf("???", "")
}

fun doSmth(exitCode: String, scanner: Scanner) {
    val startNumber = exitCode.toDouble()
    var interim = scanner.next()
    var measure = ""
    while (interim != "to" && interim != "in" && interim != "convertTo") {
        measure += interim
        interim = scanner.next()
    }
    val list = isSize(measure)
    measure = list[0]
    val enumClassStart = list[1]
    var convertMeasure = scanner.nextLine()
    var temp = ""
    for (word in convertMeasure) {
        if (word != ' ') temp += word
    }
    convertMeasure = temp
    val secList = isSize(convertMeasure)
    convertMeasure = secList[0]
    val enumClassConvert = secList[1]
    if (convertMeasure == "???" || measure == "???" || enumClassConvert != enumClassStart) {
        Data.printExceptions(Data(2, _measure = measure, _convertMeasure = convertMeasure))
    } else {
        Data.printExceptions(Data(3, startNumber, measure, convertMeasure))
    }
}

fun parseInput() {
    val scanner = Scanner(System.`in`)
    print("Enter what you want to convert (or exit): ")
    val exitCode = scanner.next()
    if (exitCode == "exit") Data.printExceptions(Data(0))
    else if (!exitCode.first().isDigit()) {
        if (exitCode.first() != '-' && !(exitCode.removePrefix("-").first().isDigit())) Data.printExceptions(Data(1))
        else {
            doSmth(exitCode, scanner)
        }
    }
    else doSmth(exitCode, scanner)
}


fun main() {
    while (true) {
        parseInput()
    }
}