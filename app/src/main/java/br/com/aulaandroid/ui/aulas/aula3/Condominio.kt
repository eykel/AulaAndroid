package br.com.aulaandroid.ui.aulas.aula3


data class Condomínio (
    var nome: String,
    val numeroDeblocos: Int,
    val numeroTotalDePessoas: Int,
)
data class Blocos(
    var nome:String = "Bloco1",
    val apartamentos: List<Apartamento>
)
data class Apartamento(
    var proprietario:String,
    var numero:Int,
    var temPet:Boolean,
    var taxaCondominio:Double,
    val inquilino: Inquilino?,
)
data class Inquilino(
    val nome: String = "Maria Luzia",
    val dataDeEntrada:String = "20/12/2020"
)