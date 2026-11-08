package ao.sos.hub.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO (Data Transfer Object) para transporte de dados de utilizador
 * entre as camadas web (Servlets/JSP) e a camada de negocio.
 * 
 * Implementa Serializable para suportar armazenamento em sessao HTTP
 * e transmissao entre componentes.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String numeroEstudante;
    private String email;
    private String senha;
    private String papel;      // "ADMIN" ou "AUTOR"
    private boolean activo;

    public UserDTO() {
    }

    public UserDTO(Long id, String nome, String numeroEstudante, String email, String papel, boolean activo) {
        this.id = id;
        this.nome = nome;
        this.numeroEstudante = numeroEstudante;
        this.email = email;
        this.papel = papel;
        this.activo = activo;
    }

    public UserDTO(Long id, String nome, String numeroEstudante, String email, String senha, String papel, boolean activo) {
        this(id, nome, numeroEstudante, email, papel, activo);
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroEstudante() {
        return numeroEstudante;
    }

    public void setNumeroEstudante(String numeroEstudante) {
        this.numeroEstudante = numeroEstudante;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) || Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", numeroEstudante='" + numeroEstudante + '\'' +
                ", email='" + email + '\'' +
                ", papel='" + papel + '\'' +
                ", activo=" + activo +
                '}';
    }
}

