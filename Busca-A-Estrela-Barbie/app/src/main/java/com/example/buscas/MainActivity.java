package com.example.buscas;

import static java.lang.Math.sqrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.service.media.MediaBrowserService;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private Random random;
    private Node lastNode, visitedNode, lastVisitedNode;
    private List<Integer> quemVaiAceitar;
    private int gameRows = 42, gameColumn = 42, currentIndex, index, randomValue, custoAtual, aceitaram, rejeitaram;
    private LinearLayout PLayout, layouts[];
    private DisplayMetrics dm;
    private ImageButton btn[][];
    private List<Node> ordemParaVisitar, caminhoRetorno, friends, justFriends, retornarParaCasa, path;
    private int[][] mapaJogo;
    private Button btnStart, btnstart2, btnRestart;
    private TextView contAceitaram, contRejeitaram, custoTotal, custoMomento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapaJogo = new int[][]{
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, //linha1
                {5, 5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 5, 5, 5, 10, 10, 10, 10, 10, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, //linha2
                {5, 5, 10, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 10, 5, 5, 5, 5, 10, 0, 0, 0, 5, 1, 5, 5, 5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 5}, //linha3
                {5, 5, 10, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 10, 10, 10, 10, 10, 10, 0, 0, 1, 1, 1, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5}, //linha4
                {5, 5, 10, 0, 0, 10, 0, 0, 10, 0, 0, 0, 10, 0, 0, 10, 0, 0, 0, 0, 5, 0, 0, 1, 1, 1, 5, 5, 5, 10, 5, 5, 5, 3, 3, 3, 5, 5, 5, 10, 5, 5}, //linha5
                {5, 5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 0, 0, 0, 5, 0, 0, 0, 5, 1, 5, 5, 5, 10, 5, 5, 5, 3, 3, 3, 5, 5, 5, 10, 5, 5}, //linha6
                {5, 5, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 10, 0, 0, 0, 5, 5, 5, 5, 5, 1, 5, 5, 5, 10, 5, 5, 5, 3, 3, 3, 5, 5, 5, 10, 5, 5}, //linha7
                {5, 5, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 0, 0, 0, 0, 5, 0, 0, 0, 5, 1, 5, 5, 5, 10, 5, 5, 5, 3, 3, 3, 5, 5, 5, 10, 5, 5}, //linha8
                {5, 5, 10, 3, 3, 3, 3, 5, 5, 5, 5, 3, 3, 3, 3, 10, 10, 10, 10, 10, 10, 0, 0, 0, 5, 1, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5}, //linha10
                {5, 5, 10, 3, 3, 3, 3, 5, 5, 5, 5, 3, 3, 3, 3, 10, 5, 5, 5, 5, 10, 0, 0, 1, 1, 1, 5, 5, 5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 5}, //linha10
                {5, 5, 10, 3, 3, 3, 3, 5, 5, 5, 5, 3, 3, 3, 3, 10, 5, 5, 5, 5, 10, 0, 0, 0, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5}, //linha11
                {5, 5, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 5, 5, 5, 5, 10, 0, 0, 0, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5}, //linha12
                {5, 5, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 10, 5, 5, 5, 5, 10, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5}, //linha13
                {5, 5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //linha14
                {5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}, //linha15
                {5, 5, 1, 5, 0, 0, 0, 5, 5, 0, 0, 0, 0, 5, 5, 1, 5, 0, 0, 0, 0, 0, 0, 0, 5, 1, 5, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 5}, //linha16
                {5, 5, 1, 10, 10, 0, 0, 5, 5, 0, 0, 0, 1, 1, 1, 1, 5, 0, 0, 0, 0, 0, 0, 0, 5, 1, 5, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 5}, //linha17
                {5, 5, 1, 5, 0, 0, 0, 5, 5, 0, 0, 0, 0, 5, 5, 1, 5, 0, 1, 0, 0, 0, 1, 0, 5, 1, 5, 0, 0, 0, 0, 1, 0, 0, 5, 0, 0, 1, 0, 0, 5, 5}, //linha18
                {5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 5, 5, 1, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5}, //linha110
                {5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5}, //linha20
                {5, 5, 1, 10, 10, 10, 10, 10, 10, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 10, 5, 5, 5, 1, 5, 5}, //linha21
                {5, 5, 1, 10, 0, 0, 0, 0, 10, 1, 5, 0, 0, 0, 5, 5, 0, 0, 0, 5, 1, 5, 0, 0, 1, 0, 5, 0, 0, 5, 1, 5, 0, 1, 0, 10, 0, 0, 5, 1, 5, 5}, //linha22
                {5, 5, 1, 10, 0, 0, 0, 0, 10, 1, 5, 0, 0, 0, 5, 5, 0, 0, 1, 1, 1, 5, 0, 0, 0, 0, 5, 0, 0, 5, 1, 5, 0, 0, 0, 10, 0, 0, 5, 1, 5, 5}, //linha23
                {5, 5, 1, 10, 0, 0, 0, 10, 10, 1, 1, 1, 0, 0, 5, 5, 0, 0, 0, 5, 1, 5, 5, 5, 5, 5, 5, 0, 1, 1, 1, 5, 5, 5, 5, 10, 0, 1, 1, 1, 5, 5}, //linha24
                {5, 5, 1, 10, 0, 0, 0, 0, 10, 1, 5, 0, 0, 0, 5, 5, 0, 0, 0, 5, 1, 5, 0, 0, 0, 0, 5, 0, 0, 5, 1, 5, 0, 0, 0, 10, 0, 0, 5, 1, 5, 5}, //linha25
                {5, 5, 1, 10, 0, 0, 0, 0, 10, 1, 5, 0, 0, 0, 5, 5, 0, 0, 0, 5, 1, 5, 0, 0, 1, 0, 5, 0, 0, 5, 1, 5, 0, 1, 0, 10, 0, 0, 5, 1, 5, 5}, //linha26
                {5, 5, 1, 10, 10, 10, 10, 10, 10, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 1, 5, 10, 5, 5, 5, 1, 5, 5}, //linha27
                {5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5}, //linha28
                {5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 10, 5, 1, 5, 10, 5, 5, 5, 10, 5, 1, 5, 5, 5, 1, 5, 5, 5, 5, 10, 5, 5, 5, 1, 5, 5}, //linha210
                {5, 5, 1, 5, 0, 0, 0, 0, 0, 0, 5, 1, 5, 0, 0, 0, 10, 0, 1, 0, 10, 0, 0, 0, 10, 0, 1, 0, 5, 5, 1, 5, 0, 0, 0, 10, 0, 0, 5, 1, 5, 5}, //linha30
                {5, 5, 1, 5, 0, 0, 1, 1, 0, 0, 5, 1, 5, 0, 1, 0, 10, 0, 0, 0, 10, 0, 1, 0, 10, 0, 0, 0, 5, 5, 1, 5, 0, 0, 0, 0, 0, 0, 5, 1, 5, 5}, //linha31
                {5, 5, 1, 5, 5, 5, 1, 1, 5, 5, 5, 1, 5, 5, 1, 5, 10, 5, 5, 5, 10, 5, 1, 5, 10, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5}, //linha32
                {5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5}, //linha33
                {5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 1, 10, 10, 10, 10, 1, 5, 5}, //linha34
                {5, 5, 1, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 1, 10, 0, 0, 10, 1, 5, 5}, //linha35
                {5, 5, 1, 5, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 5, 1, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 1, 10, 0, 0, 10, 1, 5, 5}, //linha36
                {5, 5, 1, 5, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 5, 1, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 10, 10, 0, 10, 1, 5, 5}, //linha37
                {5, 5, 1, 5, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 5, 1, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 1, 10, 0, 0, 10, 1, 5, 5}, //linha38
                {5, 5, 1, 5, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 1, 10, 0, 0, 10, 1, 5, 5}, //linha310
                {5, 5, 1, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 1, 10, 10, 10, 10, 1, 5, 5}, //linha40
                {5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 5}, //linha41
                {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}  //linha42
        };

        lastNode = new Node(0,0,0);

        quemVaiAceitar = new ArrayList<>();
        caminhoRetorno = new ArrayList<>();
        retornarParaCasa = new ArrayList<>();

        random = new Random();

        aceitaram = 0;
        rejeitaram = 0;
        custoAtual = 0;

        contAceitaram = findViewById(R.id.contAceitaram);
        contRejeitaram = findViewById(R.id.contRejeitaram);
        custoTotal = findViewById(R.id.custoTotal);
        custoMomento = findViewById(R.id.custoAtual);

        btnStart = findViewById(R.id.btnStart);
        btnRestart = findViewById(R.id.btnRestart);
        btn = new ImageButton[gameRows][gameColumn];

        dm = new DisplayMetrics();
        MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        PLayout = findViewById(R.id.PLayout);
        PLayout.setLayoutParams(new LinearLayout.LayoutParams(dm.widthPixels, dm.heightPixels - 300));
        layouts = new LinearLayout[gameRows];

        for (int i = 0; i < gameRows; i++) {
            layouts[i] = new LinearLayout(this);
            layouts[i].setOrientation(LinearLayout.VERTICAL);
            layouts[i].setLayoutParams(new LinearLayout.LayoutParams(((dm.widthPixels) / gameRows), (dm.heightPixels - 300)));
            layouts[i].setPadding(0, 0, 0, 0);
            PLayout.addView(layouts[i]);
        }

        for (int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gameColumn; j++) {
                // Context ctx = new ContextThemeWrapper(new MaterialButton(this).getContext(), com.google.android.material.R.style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon);
                btn[i][j] = new ImageButton(this);

                if (mapaJogo[i][j] == 0)
                    btn[i][j].setBackgroundColor(Color.rgb(228, 136, 0)); // Laranja
                else if (mapaJogo[i][j] == 1)
                    btn[i][j].setBackgroundColor(Color.rgb(95, 95, 95)); // Cinza escuro
                else if (mapaJogo[i][j] == 3)
                    btn[i][j].setBackgroundColor(Color.rgb(119, 119, 60)); // Marrom
                else if (mapaJogo[i][j] == 5)
                    btn[i][j].setBackgroundColor(Color.rgb(0, 179, 0)); // Verde
                else if (mapaJogo[i][j] == 10)
                    btn[i][j].setBackgroundColor(Color.LTGRAY); // Cinza claro

                ShapeDrawable bordaDrawable = new ShapeDrawable(new RectShape());
                bordaDrawable.getPaint().setColor(Color.rgb(0, 0, 0)); // Cor da borda
                bordaDrawable.getPaint().setStyle(Paint.Style.STROKE);
                bordaDrawable.getPaint().setStrokeWidth(1f);

                Drawable[] camadas = {bordaDrawable, btn[i][j].getBackground()};
                LayerDrawable layerDrawable = new LayerDrawable(camadas);
                layerDrawable.setLayerInset(1, 2, 2, 2, 2);

                btn[i][j].setBackground(layerDrawable);

                btn[i][j].setLayoutParams(new GridLayout.LayoutParams(new ViewGroup.LayoutParams(dm.widthPixels / gameRows, (dm.heightPixels - 300) / gameColumn)));
                btn[i][j].setAdjustViewBounds(true);
                btn[i][j].setScaleType(ImageButton.ScaleType.FIT_CENTER);
                btn[i][j].setPadding(0, 0, 0, 0);
                btn[i][j].setClickable(false);

                layouts[j].addView(btn[i][j]);
            }
        }

        // posições de inicio de cada personagem
        btn[22][18].setImageResource(R.drawable.barbie);
        btn[4][12].setImageResource(R.drawable.carly);
        btn[9][8].setImageResource(R.drawable.brandon);
        btn[36][36].setImageResource(R.drawable.suzy);
        btn[23][37].setImageResource(R.drawable.mary);
        btn[35][14].setImageResource(R.drawable.carly);
        btn[5][34].setImageResource(R.drawable.ken);

        friends = new ArrayList<>();
        justFriends = new ArrayList<>();

        //crio uma lista para todos os amigos, incluindo a própria personagem Barbie
        friends.add(new Node(22, 18, 0));
        friends.add(new Node(4, 12, 0));
        friends.add(new Node(9, 8, 0));
        friends.add(new Node(36, 36, 0));
        friends.add(new Node(23, 37, 0));
        friends.add(new Node(35, 14, 0));
        friends.add(new Node(5, 34, 0));

        //lista que engloba apenas os amigos sem a Barbie
        justFriends.addAll(friends);
        justFriends.remove(0);

        //sequência escolhida para visitar todos os amigos
        ordemParaVisitar =  ordemDeVisitados(friends);

        //método para definir quais dos amigos aceitarão o convite
        setQuemVaiAceitar();

        // Mostra no LogCat a lista de quem vai aceitar, serve apenas para visualizar e não será mostrado ao executar o jogo
        System.out.println(quemVaiAceitar);

    }

    public void setQuemVaiAceitar(){

        // Esse método cria uma lista com 6 valores inteiros, três valores 0 e três valores 1, o valor 1 representa qual amigo
        // aceitará o convite, e o valor 0 indica qual amigo recusará


        // Defino os três primeiros valores como 1
        for (int i = 0; i < 3; i++)
            quemVaiAceitar.add(1);

        // Defino os três últimos valores como 0
        for(int i = 0; i < 3; i++)
            quemVaiAceitar.add(0);

        // Embaralho os valores para que não se possa saber qual a ordem dos amigos que irão aceitar
        Collections.shuffle(quemVaiAceitar);
    }


    public void click(View v) {

        // Ao clicar o botão PLAY desabilita o click do mesmo para não ocorrer erro
        if (btnStart.equals(v)) {
            btnStart.setClickable(false);
            btnStart.setBackgroundColor(Color.LTGRAY);
            btnStart.setTextColor(Color.BLACK);

            // chama o método para construir o caminho que a Barbie irá andar com base na ordem dos amigos que serão visitados
            path = constroeCaminhho(ordemParaVisitar);


            // Mostra no LogCat todos os valores do caminho percorrido, não aparecerá ao executar o jogo
            for(Node node : path)
                System.out.println(node.x + "" + node.y + "  " + mapaJogo[node.x][node.y]);

            // Chama o método para iniciar o percurso
            move(path);

            // Ao clicar no botão RESET inicia-se a classe MainActivity novamente para reiniciar o jogo
        }else if(btnRestart.equals(v)){
            Intent t = new Intent(MainActivity.this, MainActivity.class);
            startActivity(t);
        }

    }

    public List<Node> constroeCaminhho(List<Node> ordemParaVisitar){

        List<Node> caminho = new ArrayList<>();
        List<Node> newList = new ArrayList<>();

        Node start = null;
        Node end = null;

        // cria-se um nó de inicio e destino para gerar o caminho da casa da Barbie até o primeiro amigo,
        // do primeiro amigo ao segundo e assim por diante

        for (int i = 0; i < ordemParaVisitar.size() - 1; i++) {
            start = new Node(ordemParaVisitar.get(i).x, ordemParaVisitar.get(i).y, mapaJogo[ordemParaVisitar.get(i).x][ordemParaVisitar.get(i).y]);
            end = new Node(ordemParaVisitar.get(i+1).x, ordemParaVisitar.get(i+1).y, mapaJogo[ordemParaVisitar.get(i+1).x][ordemParaVisitar.get(i+1).y]);

            // Ao gerar o caminho adiciona-o na lista "caminho" e concatena os demais caminhos gerados
            if(caminho.size() == 0) {
                caminho = Astar.findPath(mapaJogo, start, end);
            }else{
                newList = Astar.findPath(mapaJogo, start, end);
                newList.remove(0);
                caminho.addAll(newList);
            }
        }

        // retorna o caminho completo para visitar todos os amigos
        return caminho;
    }

    public void move(List<Node> path){

        // utiliza um handler para poder atualizar a interface principal parte por parte

        final Handler handler = new Handler(Looper.getMainLooper());

        Runnable runnable = new Runnable() {
            int index = 0;

            @Override
            public void run() {
                if (index < path.size()) {
                    Node node = path.get(index);

                    // Para não deixar a imagem da Barbie nas células já visitados colocamos a função IMageResource com TRANSPARENT
                    for (int i = 0; i < index; i++) {
                        resetFrindsPosition();
                        visitedNode = path.get(i);
                        btn[visitedNode.x][visitedNode.y].setImageResource(android.R.color.transparent);
                    }

                    // Mostra na tela o custo da célula em que a Barbie está em cada momento
                    custoMomento.setText(String.valueOf(mapaJogo[node.x][node.y]));

                    // Soma o custo total do caminho já percorrido e exibe na tela
                    if((index-1) > 0 && node != path.get(index - 1))
                        custoAtual += (mapaJogo[node.x][node.y]);

                    custoTotal.setText(String.valueOf(custoAtual));

                    // Desenha a Barbie no ponto atual
                    btn[node.x][node.y].setImageResource(R.drawable.barbie);

                    // No momento que o amigo aceita ou recusa remove o amigo da lista para sempre comparar a posição 0 da lista
                    if(justFriends.contains(path.get(index))){
                       if(quemVaiAceitar.get(0) == 1) {
                           quemVaiAceitar.remove(0);
                           aceitaram += 1;

                           // Mostra a quantidade de amigos que aceitou na tela
                           contAceitaram.setText(String.valueOf(aceitaram));
                       }else{
                           quemVaiAceitar.remove(0);
                           rejeitaram += 1;

                           // Mostra a quantidade de amigos que rejeitou na tela
                           contRejeitaram.setText(String.valueOf(rejeitaram));
                       }
                    }

                    // Mesmo que o caminho criado seja para visitar todos os 6 amigos a execução deve parar no momento em que 3 deles aceitem o conmvite
                    // Assim o "return" serve para parar a execução desse handler e em seguida chama o método "voltarParaCasa"
                    if(aceitaram == 3) {
                        voltarParaCasa(path.get(index+1));
                        return;
                    }

                    index++;
                    handler.postDelayed(this, 300);
                }
            }
        };

        handler.post(runnable);
    }

    public void voltarParaCasa(Node nodeInicio){

        // Nesse método cria-se o caminho de volta para casa da Barbie (22, 18) a partir da posição do último amigo que aceitou o convite

        final Handler handler = new Handler(Looper.getMainLooper());

        retornarParaCasa = new ArrayList<>();

        retornarParaCasa = Astar.findPath(mapaJogo,nodeInicio, new Node(22,18,0));

        Runnable runnable = new Runnable() {
            int index = 0;

            @Override
            public void run() {
                if (index < retornarParaCasa.size()) {
                    Node node = retornarParaCasa.get(index);

                    for (int i = 0; i < index; i++) {
                        resetFrindsPosition();
                        visitedNode = retornarParaCasa.get(i);
                        btn[visitedNode.x][visitedNode.y].setImageResource(android.R.color.transparent);
                    }

                    custoMomento.setText(String.valueOf(mapaJogo[node.x][node.y]));
                    custoAtual += (mapaJogo[node.x][node.y]);
                    custoTotal.setText(String.valueOf(custoAtual));

                    btn[node.x][node.y].setImageResource(R.drawable.barbie);

                    index++;
                    handler.postDelayed(this, 300); //
                }
            }
        };

        handler.post(runnable);
    }


    public List<Node> ordemDeVisitados(List<Node> friends){

        // O método calcula qual a menor distÂncia de Manhatthan da casa da Barbie até o primeiro amigo, após isso
        // calcula  a menor distância até o segundo amigoe  assim por diante

        List<Node> tempList = new ArrayList<>(friends);
        List<Node> newList = new ArrayList<>();

        newList.add(friends.get(0));

        tempList.remove(0);

        while (!tempList.isEmpty()) {
            int menor = (int) Double.MAX_VALUE;
            Node newNode = null;

            for (Node node : tempList) {
                double distancia = Math.abs(newList.get(newList.size() - 1).x - node.x) + Math.abs(newList.get(newList.size()-1).y - node.y);
                if (distancia < menor) {
                    menor = (int) distancia;
                    newNode = node;
                }
            }

            if (newNode != null) {
                tempList.remove(newNode);
                newList.add(newNode);
            }
        }

        newList.add(new Node(22,18,0));
        return newList;
    }

    public void resetFrindsPosition(){

        // O método coloca as imagens dos amigos em suas devidas posições caso sejam removidas
        btn[4][12].setImageResource(R.drawable.carly);
        btn[9][8].setImageResource(R.drawable.brandon);
        btn[36][36].setImageResource(R.drawable.suzy);
        btn[23][37].setImageResource(R.drawable.mary);
        btn[35][14].setImageResource(R.drawable.carly);
        btn[5][34].setImageResource(R.drawable.ken);
    }
}


