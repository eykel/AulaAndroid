package br.com.aulaandroid.ui.aulas.aula2

fun main(){

    println("CHEGA AQUI")

    println(pedroPadraoEmKotlin("A funcao default mais o valor dentro dela --"))
    println(pedroNaoRetornaNada("A funcao que nao retorna nada mais o valor dentro dela --"))
    println(pedroDireta("A funcao direta mais o valor dentro dela --"))
}


private fun pedroPadraoEmKotlin(parametro: String) : String {
    val novoNome = parametro + "Oi, eu sou a função padrão em kotlin."
    return novoNome
}

fun pedroNaoRetornaNada(parametro: String) {
    val novoNome =  parametro + "Oi, eu sou a função padrão porém que não retorna nada."
}

fun pedroDireta(parametro: String): String = parametro + "Oi, eu sou a função direta em kotlin."

