package com.lipao.screenmusic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "artistas")
@Getter @Setter
@NoArgsConstructor
public class Artista {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String nome;

        @Enumerated(EnumType.STRING)
        private TipoArtista tipo;

        @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private List<Musica> musicas = new ArrayList<>();

        public Artista(String nome,  TipoArtista tipo) {
            this.nome = nome;
            this.tipo = tipo;
        }

        public void adicionarMusica(Musica musica) {
            musica.setArtista(this);
            this.musicas.add(musica);
        }
    }
