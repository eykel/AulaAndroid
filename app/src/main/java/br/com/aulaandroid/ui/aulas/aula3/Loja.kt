package br.com.aulaandroid.ui.aulas.aula3

data class Loja (
    val endereco: Endereco,
    val clientes: List<Cliente>,
    val nome: String,
    val cnpj: String
)
data class Endereco (
    val rua: String,
    val numero: Int,
    val bairro: Bairro
)
data class Bairro (
    val nome: String,
    val numeroDeHabitantes: Int
)
data class Cliente (
    val nome: String,
    val possuiDependentes: Boolean,
    val dependentes: List<Dependente>?,
    val formaDePagamentoPreferida: List<String>,
    val endereco: Endereco
)
data class Dependente (
    val nome: String,
    val idade: Int,
    val sexo: String?
)