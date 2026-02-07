package com.lipao.screenmusic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "musicas")
@Getter
@Setter
@NoArgsConstructor

public class Musica {

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

}
