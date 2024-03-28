//Código utiliza java 21 para funcionar!!
//Feito por walter e vinicius
package com.etecja.produto;

import java.sql.Date;
import java.util.Scanner;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Persistence;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String codigoBarras;


    @Column
    private String nomeProduto;

    @Column
    private String marcaProduto;

    @Column
    private int quantidadeProduto;

    @Column(name = "data_cadastro")
    private Date dataCadastro;

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getMarca() {
        return marcaProduto;
    }

    public void setMarca(String marca) {
        this.marcaProduto = marca;
    }

    public int getQuantidade() {
        return quantidadeProduto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidadeProduto = quantidade;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public static void cadastrarProduto(EntityManager entityManager, Scanner scanner) {
        entityManager.getTransaction().begin();

        System.out.println("Digite o código de barras do produto:");
        String codigoBarras = scanner.next();

        Produto produtoExistente = entityManager.find(Produto.class, codigoBarras);
        if (produtoExistente != null) {
            System.out.println("O produto com o código de barras informado já existe.");
            entityManager.getTransaction().rollback();
            return;
        }

        System.out.println("Digite o nome do produto:");
        String nomeProduto = scanner.next();

        System.out.println("Digite a marca do produto:");
        String marca = scanner.next();

        System.out.println("Digite a quantidade do produto:");
        int quantidade = scanner.nextInt();

        @SuppressWarnings("deprecation")
        Date dataCadastro = new Date(quantidade, quantidade, quantidade);

        Produto novoProduto = new Produto();
        novoProduto.setCodigoBarras(codigoBarras);
        novoProduto.setNomeProduto(nomeProduto);
        novoProduto.setMarca(marca);
        novoProduto.setQuantidade(quantidade);
        novoProduto.setDataCadastro(dataCadastro);

        entityManager.persist(novoProduto);
        entityManager.getTransaction().commit();

        System.out.println("Produto cadastrado com sucesso!");
    }

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ProdutoPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            cadastrarProduto(entityManager, scanner);
            System.out.println("Deseja cadastrar outro produto? (s/n)");
            String continuar = scanner.next();
            if (!continuar.equalsIgnoreCase("s")) {
                break;
            }
        }

        entityManager.close();
        entityManagerFactory.close();
        scanner.close();
    }
}
