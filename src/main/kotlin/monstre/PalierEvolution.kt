package org.example.monstre

class PalierEvolution (
    val id: Int,
    val niveauRequis : Int,
    val evolution : EspeceMonstre
){
    fun peutEvoluer(individu: IndividuMonstre): Boolean{
        if(individu.niveau>=niveauRequis){
            return true
        }
        else{
            return false
        }
    }
}