package com.project.exception;

public class MesaNaoEncontradaException extends BusinessException{

    public MesaNaoEncontradaException() {
        super("Mesa não encontrada");
    }
}
