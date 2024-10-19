package br.com.aulaandroid.ui.aulas.aula3

data class Escola(
    val nome: String,
    val numeroAlunos: Int,
    val emFuncionamento: Boolean,
    val endereco: EnderecoEscola,
    val funcionarios : List<Funcionario>,
)

data class EnderecoEscola(
    val nome: String,
    val numero: Int
)

data class Funcionario(
    val nome: String,
    val idade: Int,
    val cargo: Cargo,
    val estadoCivil: String
)

data class Cargo(
    val nomeCargo: String,
    val modeloPresencial: Boolean
)
class Teste(){
    var teste = EnderecoEscola(
        "32434",
         435345
    )
}
