package com.example.gk.potterwatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class SortingHat extends AppCompatActivity {

    final String Questions[] = new String[16];                      // Question number with order
    final String Options[][] = new String[16][9];                   // Options of the resp. question
    final String Houses[] = new String[5];
    int array[] = new int[10];                                      // Question Number in Order

    ImageButton UpArrow, DownArrow;
    TextView Question;
    Button option[] = new Button[9];
    int index = 0;                                                  // The index of the question that is being asked

    int scoreArray[] = new int[5];                                  // Score per house of the quiz
    String text;                                                    // the text that the user selected for the question

    String table[][][] = new String[16][9][5];

    private void fillHouses() {
        Houses[0] = " ";
        Houses[1] = "Gryffindor";
        Houses[2] = "Hufflepuff";
        Houses[3] = "Ravenclaw";
        Houses[4] = "Slytherin";
    }

    public void generatenumber() {
        for (int j = 0; j < 10; j++) {
            Random rand = new Random();
            boolean flag = false;
            int n = rand.nextInt(15) + 1;
            for (int i = 0; i < array.length; i++) {
                if (array[i] == n)
                    flag = true;
            }
            if (flag == false)
                array[j] = n;
            else
                j--;
        }
    }
    public void simpledelay() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                updateUI();
            }
        }, 250);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting_hat);
        fillHouses();
        OptionsPopulator();
        QuestionPopulator();
        scoretable();
        init();
        DownArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                godown();
            }
        });

        UpArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goup();
            }
        });

        option[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = (String) option[1].getText();
                simpledelay();

            }
        });

        option[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = (String) option[2].getText();
                simpledelay();
            }
        });
        option[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = (String) option[3].getText();
                simpledelay();
            }
        });
        option[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = (String) option[4].getText();
                simpledelay();
            }
        });
        option[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = (String) option[5].getText();
                simpledelay();
            }
        });
        option[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = (String) option[6].getText();
                simpledelay();
            }
        });
        option[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = (String) option[7].getText();
                simpledelay();
            }
        });
        option[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = (String) option[8].getText();
                simpledelay();
            }
        });
    }

    public void visgonebuttons() {
        for (int i = 1; i < 9; i++)
            option[i].setVisibility(View.GONE);

    }

    public void godown() {
        Question.setVisibility(View.GONE);
        DownArrow.setVisibility(View.GONE);
        UpArrow.setVisibility(View.VISIBLE);

        int buttonnumber = Integer.parseInt(Options[array[index]][0]);

        for (int i = 1; i <= buttonnumber; i++) {
            String value = Options[array[index]][i];
            option[i].setVisibility(View.VISIBLE);
            option[i].setText(value);
        }
    }

    public void goup() {
        Question.setVisibility(View.VISIBLE);
        DownArrow.setVisibility(View.VISIBLE);
        UpArrow.setVisibility(View.GONE);
        visgonebuttons();
    }

    public void init() {
        UpArrow = (ImageButton) findViewById(R.id.UpArrow);
        DownArrow = (ImageButton) findViewById(R.id.DownArrow);
        Question = (TextView) findViewById(R.id.sortquestion);
        UpArrow.setVisibility(View.GONE);

        option[1] = (Button) findViewById(R.id.b1);
        option[2] = (Button) findViewById(R.id.b2);
        option[3] = (Button) findViewById(R.id.b3);
        option[4] = (Button) findViewById(R.id.b4);
        option[5] = (Button) findViewById(R.id.b5);
        option[6] = (Button) findViewById(R.id.b6);
        option[7] = (Button) findViewById(R.id.b7);
        option[8] = (Button) findViewById(R.id.b8);

        visgonebuttons();

        generatenumber();
        Question.setText(Questions[array[index]]);
    }

    public void updateUI() {
        Calculator();
        index++;
        if (index < 10) {
            Question.setText(Questions[array[index]]);
            goup();
        } else {
            showhouse();
        }
    }

    public void showhouse() {
        int max = 0;
        int j = 0;
        for (int i = 1; i < 5; i++) {
            if (scoreArray[i] > max) {
                max = scoreArray[i];
                j = i;
            }
        }
        finaldisplay(j);
    }

    public void finaldisplay(int ans) {
        Intent test = new Intent(SortingHat.this, TestActivity.class);
        test.putExtra("KEY", Houses[ans]);
        startActivity(test);
    }

    private void Calculator() {
        int qno = array[index];
        int buttonnumber = Integer.parseInt(Options[qno][0]);

        for (int i = 1; i <= buttonnumber; i++) {
            if (text.compareToIgnoreCase(table[qno][i][0]) == 0) {
                int a;

                for (int j = 1; j < 5; j++) {
                    a = Integer.parseInt(table[qno][i][j]);
                    scoreArray[j] += a;
                }
                break;
            }
        }
    }

    private void OptionsPopulator() {

        Options[1][0] = "2";
        Options[1][1] = "Fire";
        Options[1][2] = "Water";

        Options[2][0] = "2";
        Options[2][1] = "Wood";
        Options[2][2] = "Metal";

        Options[3][0] = "2";
        Options[3][1] = "Day";
        Options[3][2] = "Night";

        Options[4][0] = "2";
        Options[4][1] = "Summer";
        Options[4][2] = "Winter";

        Options[5][0] = "2";
        Options[5][1] = "Rain";
        Options[5][2] = "Snow";

        Options[6][0] = "5";
        Options[6][1] = "Curl up indoors and read a book";
        Options[6][2] = "Hang out with bunch of friends";
        Options[6][3] = "Paint the town";
        Options[6][4] = "Learn something new";
        Options[6][5] = "Go shopping to the mall";

        Options[7][0] = "5";
        Options[7][1] = "Cat";
        Options[7][2] = "Dog";
        Options[7][3] = "Parrot";
        Options[7][4] = "Fish";
        Options[7][5] = "Owl";

        Options[8][0] = "5";
        Options[8][1] = "Befriend him and make him feel included";
        Options[8][2] = "Pick on him along with your friends";
        Options[8][3] = "Ignore him completely. He needs to get through it like everybody else";
        Options[8][4] = "Try and convince your friends to stop picking on him";
        Options[8][5] = "Tell on your friends to your boss";


        Options[9][0] = "4";
        Options[9][1] = "A meadow with a lone tree in the middle";
        Options[9][2] = "A library brimming with racks of books";
        Options[9][3] = "An abandoned seashore";
        Options[9][4] = "The heart of a big city";

        Options[10][0] = "5";
        Options[10][1] = "World Peace";
        Options[10][2] = "Ten times the money that you desire";
        Options[10][3] = "Happiness";
        Options[10][4] = "Immortality";
        Options[10][5] = "Omniscience";


        Options[11][0] = "4";
        Options[11][1] = "Rock";
        Options[11][2] = "Pop";
        Options[11][3] = "Classical";
        Options[11][4] = "EDM";


        Options[12][0] = "2";
        Options[12][1] = "Light";
        Options[12][2] = "Dark";

        Options[13][0] = "5";
        Options[13][1] = "Owlery";
        Options[13][2] = "Library";
        Options[13][3] = "The Lake";
        Options[13][4] = "Quidditch Pitch";
        Options[13][5] = "Common Room";

        Options[14][0] = "8";
        Options[14][1] = "Gringotts";
        Options[14][2] = "Madam Malkin's Robes for special occassions";
        Options[14][3] = "Ollivander's";
        Options[14][4] = "Eyelops Owl Emporium";
        Options[14][5] = "Weasleys Wizarding Wheezes";
        Options[14][6] = "Flourish and Blotts";
        Options[14][7] = "Apothecary";
        Options[14][8] = "Quality Quidditch Supplies";

        Options[15][0] = "3";
        Options[15][1] = "I'm a natural";
        Options[15][2] = "I can do it if I have to";
        Options[15][3] = "I hate it!";

    }

    private void scoretable() {
        table[1][1][0] = "Fire";
        table[1][1][1] = "7";
        table[1][1][2] = "6";
        table[1][1][3] = "3";
        table[1][1][4] = "5";


        table[1][2][0] = "Water";
        table[1][2][1] = "2";
        table[1][2][2] = "5";
        table[1][2][3] = "7";
        table[1][2][4] = "8";

        table[2][1][0] = "Wood";
        table[2][1][1] = "6";
        table[2][1][2] = "8";
        table[2][1][3] = "3";
        table[2][1][4] = "3";


        table[2][2][0] = "Metal";
        table[2][2][1] = "5";
        table[2][2][2] = "3";
        table[2][2][3] = "8";
        table[2][2][4] = "8";


        table[3][1][0] = "Day";
        table[3][1][1] = "7";
        table[3][1][2] = "7";
        table[3][1][3] = "4";
        table[3][1][4] = "3";


        table[3][2][0] = "Night";
        table[3][2][1] = "5";
        table[3][2][2] = "4";
        table[3][2][3] = "7";
        table[3][2][4] = "7";


        table[4][1][0] = "Summer";
        table[4][1][1] = "7";
        table[4][1][2] = "8";
        table[4][1][3] = "3";
        table[4][1][4] = "3";


        table[4][2][0] = "Winter";
        table[4][2][1] = "5";
        table[4][2][2] = "3";
        table[4][2][3] = "8";
        table[4][2][4] = "8";

        // 5th Questions
        table[5][1][0] = "Rain";
        table[5][1][1] = "3";
        table[5][1][2] = "3";
        table[5][1][3] = "6";
        table[5][1][4] = "7";


        table[5][2][0] = "Snow";
        table[5][2][1] = "8";
        table[5][2][2] = "6";
        table[5][2][3] = "4";
        table[5][2][4] = "5";


        // 6th Questions
        table[6][1][0] = "Curl up indoors and read a book";
        table[6][1][1] = "3";
        table[6][1][2] = "8";
        table[6][1][3] = "6";
        table[6][1][4] = "3";

        table[6][2][0] = "Hang out with a bunch of friends";
        table[6][2][1] = "8";
        table[6][2][2] = "5";
        table[6][2][3] = "4";
        table[6][2][4] = "8";

        table[6][3][0] = "Paint the town";
        table[6][3][1] = "3";
        table[6][3][2] = "3";
        table[6][3][3] = "8";
        table[6][3][4] = "6";

        table[6][4][0] = "Learn something new";
        table[6][4][1] = "5";
        table[6][4][2] = "5";
        table[6][4][3] = "8";
        table[6][4][4] = "4";

        table[6][5][0] = "Go shopping to the mall";
        table[6][5][1] = "6";
        table[6][5][2] = "4";
        table[6][5][3] = "3";
        table[6][5][4] = "8";


        // 7th Questions
        table[7][1][0] = "Cat";
        table[7][1][1] = "6";
        table[7][1][2] = "3";
        table[7][1][3] = "3";
        table[7][1][4] = "7";

        table[7][2][0] = "Dog";
        table[7][2][1] = "4";
        table[7][2][2] = "8";
        table[7][2][3] = "5";
        table[7][2][4] = "2";

        table[7][3][0] = "Parrot";
        table[7][3][1] = "4";
        table[7][3][2] = "3";
        table[7][3][3] = "8";
        table[7][3][4] = "3";

        table[7][4][0] = "Fish";
        table[7][4][1] = "5";
        table[7][4][2] = "4";
        table[7][4][3] = "4";
        table[7][4][4] = "5";

        table[7][5][0] = "Owl";
        table[7][5][1] = "7";
        table[7][5][2] = "6";
        table[7][5][3] = "5";
        table[7][5][4] = "5";


        // 8th Questions
        table[8][1][0] = "Befriend him and make him feel included";
        table[8][1][1] = "4";
        table[8][1][2] = "8";
        table[8][1][3] = "3";
        table[8][1][4] = "2";

        table[8][2][0] = "Pick on him along with your friends";
        table[8][2][1] = "6";
        table[8][2][2] = "2";
        table[8][2][3] = "4";
        table[8][2][4] = "8";

        table[8][3][0] = "Ignore him completely. He needs to get through it like everybody else";
        table[8][3][1] = "3";
        table[8][3][2] = "3";
        table[8][3][3] = "7";
        table[8][3][4] = "6";

        table[8][4][0] = "Try and convince your friends to stop picking on him";
        table[8][4][1] = "5";
        table[8][4][2] = "5";
        table[8][4][3] = "4";
        table[8][4][4] = "3";

        table[8][5][0] = "Tell on your friends to your boss";
        table[8][5][1] = "3";
        table[8][5][2] = "2";
        table[8][5][3] = "5";
        table[8][5][4] = "5";

        //9th Question

        table[9][1][0] = "A meadow with a lone tree in the middle";
        table[9][1][1] = "4";
        table[9][1][2] = "7";
        table[9][1][3] = "4";
        table[9][1][4] = "3";

        table[9][2][0] = "A library brimming with racks of books";
        table[9][2][1] = "4";
        table[9][2][2] = "5";
        table[9][2][3] = "8";
        table[9][2][4] = "3";

        table[9][3][0] = "An abandoned seashore";
        table[9][3][1] = "3";
        table[9][3][2] = "6";
        table[9][3][3] = "5";
        table[9][3][4] = "7";

        table[9][4][0] = "The heart of a big city";
        table[9][4][1] = "8";
        table[9][4][2] = "4";
        table[9][4][3] = "3";
        table[9][4][4] = "8";

        //10th Question
        table[10][1][0] = "World peace";
        table[10][1][1] = "4";
        table[10][1][2] = "7";
        table[10][1][3] = "3";
        table[10][1][4] = "3";

        table[10][2][0] = "Ten times the money that you desire";
        table[10][2][1] = "5";
        table[10][2][2] = "3";
        table[10][2][3] = "4";
        table[10][2][4] = "8";

        table[10][3][0] = "Happiness";
        table[10][3][1] = "7";
        table[10][3][2] = "6";
        table[10][3][3] = "3";
        table[10][3][4] = "3";

        table[10][4][0] = "Immortality";
        table[10][4][1] = "5";
        table[10][4][2] = "5";
        table[10][4][3] = "5";
        table[10][4][4] = "8";

        table[10][5][0] = "Omniscience";
        table[10][5][1] = "4";
        table[10][5][2] = "4";
        table[10][5][3] = "8";
        table[10][5][4] = "5";


        //11th Question
        table[11][1][0] = "Rock";
        table[11][1][1] = "6";
        table[11][1][2] = "4";
        table[11][1][3] = "3";
        table[11][1][4] = "6";

        table[11][2][0] = "Pop";
        table[11][2][1] = "5";
        table[11][2][2] = "7";
        table[11][2][3] = "5";
        table[11][2][4] = "4";

        table[11][3][0] = "Classical";
        table[11][3][1] = "4";
        table[11][3][2] = "5";
        table[11][3][3] = "7";
        table[11][3][4] = "3";

        table[11][4][0] = "EDM";
        table[11][4][1] = "7";
        table[11][4][2] = "3";
        table[11][4][3] = "2";
        table[11][4][4] = "8";

        //12th Question
        table[12][1][0] = "Light";
        table[12][1][1] = "6";
        table[12][1][2] = "8";
        table[12][1][3] = "3";
        table[12][1][4] = "3";

        table[12][2][0] = "Dark";
        table[12][2][1] = "4";
        table[12][2][2] = "3";
        table[12][2][3] = "8";
        table[12][2][4] = "8";


        //13th Question
        table[13][1][0] = "Owlery";
        table[13][1][1] = "6";
        table[13][1][2] = "5";
        table[13][1][3] = "2";
        table[13][1][4] = "4";

        table[13][2][0] = "Library";
        table[13][2][1] = "4";
        table[13][2][2] = "5";
        table[13][2][3] = "8";
        table[13][2][4] = "3";

        table[13][3][0] = "The Lake";
        table[13][3][1] = "6";
        table[13][3][2] = "5";
        table[13][3][3] = "3";
        table[13][3][4] = "6";

        table[13][4][0] = "Quidditch Pitch";
        table[13][4][1] = "8";
        table[13][4][2] = "3";
        table[13][4][3] = "3";
        table[13][4][4] = "7";

        table[13][5][0] = "Common Room";
        table[13][5][1] = "4";
        table[13][5][2] = "8";
        table[13][5][3] = "5";
        table[13][5][4] = "3";


        //14th Question
        table[14][1][0] = "Gringotts";
        table[14][1][1] = "6";
        table[14][1][2] = "4";
        table[14][1][3] = "5";
        table[14][1][4] = "7";

        table[14][2][0] = "Madam Malkin's Robes for special occassions";
        table[14][2][1] = "5";
        table[14][2][2] = "4";
        table[14][2][3] = "5";
        table[14][2][4] = "7";

        table[14][3][0] = "Ollivander's";
        table[14][3][1] = "8";
        table[14][3][2] = "4";
        table[14][3][3] = "5";
        table[14][3][4] = "6";

        table[14][4][0] = "Eyelops Owl Emporium";
        table[14][4][1] = "5";
        table[14][4][2] = "8";
        table[14][4][3] = "4";
        table[14][4][4] = "4";

        table[14][5][0] = "Weasleys Wizarding Wheezes";
        table[14][5][1] = "9";
        table[14][5][2] = "4";
        table[14][5][3] = "3";
        table[14][5][4] = "5";

        table[14][6][0] = "Flourish and Blotts";
        table[14][6][1] = "4";
        table[14][6][2] = "4";
        table[14][6][3] = "8";
        table[14][6][4] = "4";

        table[14][7][0] = "Apothecary";
        table[14][7][1] = "4";
        table[14][7][2] = "4";
        table[14][7][3] = "7";
        table[14][7][4] = "7";

        table[14][8][0] = "Quality Quidditch Supplies";
        table[14][8][1] = "8";
        table[14][8][2] = "5";
        table[14][8][3] = "5";
        table[14][8][4] = "8";


        //15th Question
        table[15][1][0] = "I'm a natural";
        table[15][1][1] = "6";
        table[15][1][2] = "7";
        table[15][1][3] = "3";
        table[15][1][4] = "4";

        table[15][2][0] = "I can do it if I have to";
        table[15][2][1] = "3";
        table[15][2][2] = "4";
        table[15][2][3] = "8";
        table[15][2][4] = "8";

        table[15][3][0] = "I hate it!";
        table[15][3][1] = "3";
        table[15][3][2] = "4";
        table[15][3][3] = "7";
        table[15][3][4] = "6";

    }

    private void QuestionPopulator() {
        Questions[0] = " ";
        Questions[1] = "Fire or Water?";
        Questions[2] = "Wood or Metal?";
        Questions[3] = "Day or Night?";
        Questions[4] = "Summer or Winter?";
        Questions[5] = "Rain or Snow?";
        Questions[6] = "What is your ideal way to spend a holiday?";
        Questions[7] = "What animal would you rather have as a pet?";
        Questions[8] = "The newbie in your workplace is getting a lot of hate from your fellow coworkers. Part of the part is his attitude which he has no intention to change. What would you do?";
        Questions[9] = "What do you feel the most drawn towards?";
        Questions[10] = "If you could have one wish from the following, what would you choose?";
        Questions[11] = "What type of music do you prefer?";
        Questions[12] = "Light or Dark?";
        Questions[13] = "What would be your favourite hangout spot at Hogwarts?";
        Questions[14] = "If you were at Diagon Alley, where would you go first?";
        Questions[15] = "How easy do you find it to start conversations with people?";

    }

}
