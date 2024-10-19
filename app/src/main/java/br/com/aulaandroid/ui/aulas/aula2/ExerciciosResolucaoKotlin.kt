package br.com.aulaandroid.ui.aulas.aula2

data class ExercíciosResoluçãoKotlin(
    val n1 : Int = 5,
    val n2 : Int = 2,
    val text : String = "5 * 2\n" +
            "O resultado é igual a ",
    val mult : Int = n1 * n2,
    val tex2 : String = "5 * 2 = 10\n",
    val vf : Boolean = true,
    val carinha : String = " (=",
)
fun main() {
    val outraQualquerVariavel = ExercíciosResoluçãoKotlin()

    println(outraQualquerVariavel.text + outraQualquerVariavel.mult)
    println(outraQualquerVariavel.tex2 + outraQualquerVariavel.vf + outraQualquerVariavel.carinha)
}
