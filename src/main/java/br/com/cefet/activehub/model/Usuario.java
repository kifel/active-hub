package br.com.cefet.activehub.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Usuario {
    private int id;
    private String nome;
    private String username;
    private String password;
}
