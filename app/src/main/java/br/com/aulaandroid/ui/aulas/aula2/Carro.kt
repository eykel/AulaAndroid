package br.com.aulaandroid.ui.aulas.aula2

fun main(){
    val qualquer = Pet("Bernardo", "gato")
    println(qualquer.name)
}

data class Carro(
    val nome: String,
    val idadeInterna: Int,
    val pneu: Pneu,
    val motor: String,
    var motorEmFuncionamento: Boolean
)

data class Pneu(
    val calibragem: Int,
    val marca: String,
    val estado: Estado
)

data class Estado(
    val meiaVida: Boolean,
    val novo: Boolean
)


data class User(
    val firstName: String,
    val lastName: String,
    val loginCount: Int,
    val isWriter: Boolean,
    val workWith: List<String>,
    val pets: List<Pet>
)

data class Pet(
    val name: String,
    val type: String
)




















