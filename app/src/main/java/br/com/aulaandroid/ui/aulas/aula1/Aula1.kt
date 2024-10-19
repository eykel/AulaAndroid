package br.com.aulaandroid.ui.aulas.aula1


class Aula1 {
}

fun main(){
    
    //Padrão.
    val variavelPadraoInteira: Int = 0
    val variavelPadraoString: String = ""
    val variavelPadraoBooleana: Boolean = false
    val variavelPadraoChar: Char = 'a'
    val variavelPadraoDouble: Double = 0.0
    
    //Com tipos implícitos.
    val tipoImplicitoInteira = 0
    val tipoImplicitoString = ""
    val tipoImplicitoBooleana = false
    val tipoImplicitoChar = 'a'
    val tipoImplicitoDouble = 0.0


    //Com valores não declarados
    val semDeclararValorInteira: Int
    val semDeclararValorString: String
    val semDecalraraValorBooleana: Boolean
    val semDecalraraValorChar: Char
    val semDecalraraValorDouble: Double


    //Com valores nullos
    val comValorNulloInteiro: Int? = null
    val comValorNulloString: String? = null
    val comValorNulloBooleana: Boolean? = null
    val comValorNulloChar: Char? = null
    val comValorNulloDouble: Double? = null

    //inicialização tardia (Não pode ser utilizado para tipos primitivos)
    //String não é tipo primitivo, é derivado de Char
    lateinit var lateInitVariavelString: String

}





