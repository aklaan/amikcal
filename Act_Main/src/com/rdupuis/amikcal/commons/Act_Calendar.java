package com.rdupuis.amikcal.commons;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.rdupuis.amikcal.R;


public class Act_Calendar extends Activity {


    int currentMonth = 0;
    int currentYear = 0;
    final int MAX_DAYS = 43;
    int[] days = new int[MAX_DAYS];
    int[] arrayOfButton = new int[MAX_DAYS];
    Calendar currentCalendar = new GregorianCalendar();
    Intent mIntent;

    /************************************************************
     *
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = getIntent();
        setContentView(R.layout.view_calendar);

        // le Init doit �tre plac� APRES le setContentView sinon
        // les findViewById ne fonctionnent pas.
        init();


    }


    /************************************************************
     *
     */
    private void init() {

        // par d�faut on prend la date du jour.
        currentCalendar = Calendar.getInstance();
        currentCalendar.set(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), 1);
        TextView tv = (TextView) findViewById(R.id.monthLib);
        tv.setText(getMonthLabel(currentCalendar.get(Calendar.MONTH)) + " " + currentCalendar.get(Calendar.YEAR));

        Arrays.fill(days, -1);
        Arrays.fill(arrayOfButton, -1);
        createButtons();
        printDays(currentCalendar);
    }


    /***
     * Déterminier le libellé d'un mois à partir de sont numéro.
     */

    private String getMonthLabel(int monthNumber) {
        String result = "";

        switch (monthNumber) {

            case Calendar.JANUARY:
                result = "Janvier";
                break;
            case Calendar.FEBRUARY:
                result = "Fevrier";
                break;
            case Calendar.MARCH:
                result = "Mars";
                break;
            case Calendar.APRIL:
                result = "Avril";
                break;
            case Calendar.MAY:
                result = "Mai";
                break;
            case Calendar.JUNE:
                result = "Juin";
                break;
            case Calendar.JULY:
                result = "Juillet";
                break;
            case Calendar.AUGUST:
                result = "Aout";
                break;
            case Calendar.SEPTEMBER:
                result = "Septembre";
                break;
            case Calendar.OCTOBER:
                result = "Octobre";
                break;
            case Calendar.NOVEMBER:
                result = "Novembre";
                break;
            case Calendar.DECEMBER:
                result = "Decembre";
                break;

            default:
                result = "";

        }
        return result;

    }

    /**
     * On cree les boutons JOURS
     */
    private void createButtons() {

        TableLayout tl = (TableLayout) findViewById(R.id.calendarLayout);

        // On ajoute 35 boutons
        // 7 sur 5 lignes

        LayoutParams params = new LayoutParams();
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        params.width = 0;

        int idButton = 0;
        for (int i = 1; i < 7; i++) {

            TableRow tr = new TableRow(this);
            tl.addView(tr);

            for (int j = 1; j < 8; j++) {

                //	this.setTheme(R.style.CustomButton);
                Button b = new Button(this);
                b.setLayoutParams(params);
                b.setId(idButton);

                // on d�fini l'action Onclick du bouton
                b.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        Button button = (Button) findViewById(v.getId());
                        /*Context context = getApplicationContext();
						CharSequence text = String.valueOf(v.getId());
						Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
						toast.show();
				       */


                        String result = currentCalendar.get(Calendar.YEAR) + "-" +

                                ((currentCalendar.get(Calendar.MONTH) < 10) ? ("0" + String.valueOf(currentCalendar.get(Calendar.MONTH) + 1)) : currentCalendar.get(Calendar.MONTH) + 1)

                                + "-"
                                + ((Integer.parseInt((String) button.getText()) < 10) ? ("0" + button.getText()) : button.getText());

                        // on alimente le r�sultat dans l'Intent pour que l'Activity m�re puisse
                        //r�cup�rer la valeur.
                        mIntent.putExtra("result", result);

                        //on appelle setResult pour d�clancher le onActivityResult de l'activity m�re.
                        setResult(RESULT_OK, mIntent);

                        //On termine l'Actvity
                        finish();


                    }
                });

                tr.addView(b);
                //On m�morise l'Id du bouton
                arrayOfButton[idButton] = b.getId();
                idButton++;
            }
        }

    }

    /**
     * printDays affiche le n� du jour sur le calendrier
     */

    private void printDays(Calendar calendar) {

        //On clone le calendrier pour travailler sur une copie
        Calendar c = (Calendar) calendar.clone();

        //saugegarde du mois en cours
        int month = c.get(Calendar.MONTH);

        //On fabrique le 1er jour du mois
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);

        //On r�cup�re le n� du 1er jour du mois
        //0= lundi...etc
        int day = c.get(Calendar.DAY_OF_WEEK);

        // le jour 1 est dimanche, mais dans le calendrier affich� � l'�cran
        // c'est le jour 7
        if (day == Calendar.SUNDAY) {
            day = day + 5;
        } else {
            day = day - 2;
        }

        //On affiche le n� du jour sur le bouton tant que l'on ne passe pas
        //au mois suivant.
        int i = 0;
        while (c.get(Calendar.MONTH) == month) {

            Button b = (Button) findViewById(arrayOfButton[i + day]);
            i++;
            b.setText(String.valueOf(i));
            c.add(Calendar.DAY_OF_WEEK, 1);
        }

    }

    /******************************************************************************
     * eraseButtons : efface le texte incrit sur les boutons.
     */
    private void eraseButtons() {

        for (int j = 0; j < MAX_DAYS; j++) {
            if (arrayOfButton[j] != -1) {
                Button b = (Button) findViewById(arrayOfButton[j]);
                b.setText("");
            }

        }
    }


    /*********************************************************************
     * nextMonth : Avance le calendrier d'un mois
     */

    public void nextMonth(View v) {
        eraseButtons();

        currentCalendar.add(Calendar.MONTH, 1);
        TextView tv = (TextView) findViewById(R.id.monthLib);
        tv.setText(getMonthLabel(currentCalendar.get(Calendar.MONTH)) + " " + currentCalendar.get(Calendar.YEAR));
        printDays(currentCalendar);
    }

    /*********************************************************************
     * nextMonth : recule le calendrier d'un mois
     */

    public void previousMonth(View v) {
        eraseButtons();

        currentCalendar.add(Calendar.MONTH, -1);
        TextView tv = (TextView) findViewById(R.id.monthLib);
        tv.setText(getMonthLabel(currentCalendar.get(Calendar.MONTH)) + " " + currentCalendar.get(Calendar.YEAR));
        printDays(currentCalendar);
    }


}