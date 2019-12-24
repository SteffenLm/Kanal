public class Kanal {

    private double a, b, d, l, f, e, dicke;
    private double ab, rb;
    private double[] arr_a, arr_b, arr_A, arr_B;
    private double ra, schenkel, r, step;
    private double da, db, ma, mb, dh;
    private final double w = 0.261799387;
    private int wsum;

    public Kanal(double a, double b, double d, double l,
                 double dicke, double e, double f) {
        this.a = a;
        this.b = b;
        this.d = d;
        this.l = l;
        this.f = f;
        this.e = e;
        this.dicke = dicke;
        this.arr_a = new double[7];
        this.arr_b = new double[7];
        this.arr_A = new double[7];
        this.arr_B = new double[7];
        this.dh = d / 2;
    }

    private void checkSymmetrie() {
        if ((d - a) / 2 != f) { // Grundsymmetrie nicht gegeben
            if ((d - b) / 2 == e) { // aber Symmetrie vorhanden
                // Werkstück drehen
                double temp = a;
                a = b;
                b = temp;
                temp = e;
                e = f;
                f = temp;
            }
        }
    }

    public String calculateAll() {
        this.checkSymmetrie();
        this.berechneGrunddaten();
        return this.output() + this.output2();
    }

    private void berechneGrunddaten() {
        this.ab = this.b - this.dicke;
        this.schenkel = this.a / 2;
        this.r = Math.sqrt(this.e * this.e + this.l * this.l);
        this.rb = Math.sqrt(((this.d - this.a) / 2 - this.f) * ((this.d - this.a) / 2 - this.f) + this.r * this.r);
        this.r = Math.sqrt((b - d + e) * (b - d + e) + this.l * this.l); // R2 y-symmetrisch
        this.ra = Math.sqrt(((this.d - this.a) / 2 - this.f) * ((this.d - this.a) / 2 - this.f) + this.r * this.r); // R asymmetrisch

        // ermitteln von b0
        this.mb = this.dh - this.e;
        this.ma = -f;
        this.arr_b[0] = Math.sqrt((ma * ma + mb * mb) + (l * l));

        // ermitteln von a0
        this.mb = this.b - (this.dh - this.e);
        this.ma = -this.f;
        this.arr_a[0] = Math.sqrt((ma * ma + mb * mb) + (l * l));

        // ermitteln von A0
        this.mb = this.dh - this.e;
        this.ma = this.a - this.d + this.f;
        this.arr_A[0] = Math.sqrt((ma * ma + mb * mb) + (l * l));

        /* B0 ermitteln */
        this.mb = this.b - (this.dh - this.e);
        this.ma = this.a - this.d + this.f;
        this.arr_B[0] = Math.sqrt((ma * ma + mb * mb) + (l * l));

        /* A's+ B's ermitteln */
        for (int i = 0; i < 7; i++) {
            this.wsum = i;
            arr_b[i] = this.bp();
            arr_a[i] = this.ap();
            arr_B[i] = this.BP();
            arr_A[i] = this.AP();
            // arr_A[i] = AP();
        }

        /*---------------------------- Step errechnen ------------------------------*/
        this.step = Math.sin(this.w) * this.dh;

    }

    private double bp() {
        db = Math.sin(w * wsum) * dh;
        da = dh - (Math.cos(w * wsum) * dh);
        mb = (dh - e) - db;
        ma = -f + da;
        return (Math.sqrt((ma * ma + mb * mb) + (l * l)));
    }
    private double ap() {
        db = Math.sin(w * wsum) * dh;
        da = dh - (Math.cos(w * wsum) * dh);
        mb = (b - (dh - e)) - db;
        ma = -f + da;
        return (Math.sqrt((ma * ma + mb * mb) + (l * l)));
    }

    private double AP() /* berechnen der Kreispunkte links oben */
    {
        db = Math.sin(w * wsum) * dh;
        da = dh - (Math.cos(w * wsum) * dh);
        mb = (dh - e) - db;
        ma = (a - d + f) + da; /* modified */
        return (Math.sqrt((ma * ma + mb * mb) + (l * l)));
    }

    private double BP() /* berechnen der Kreispunkte links unten */
    {
        db = Math.sin(w * wsum) * dh;
        da = dh - (Math.cos(w * wsum) * dh);
        mb = (b - (dh - e)) - db;
        ma = (a - d + f) + da; /* modified */
        return (Math.sqrt((ma * ma + mb * mb) + (l * l)));
    }

    private String output() {
        String result = "";
        result += "Errechnete Werte für das Übergangsstück Rechteck auf Rund!";
        result += "\n       AB: " + Calculations.roundToTwoDecimals(ab);
        result += "  Schenkellänge: " + Calculations.roundToTwoDecimals(schenkel);
        result += "  Step: " + Calculations.roundToTwoDecimals(step);
        result += "\n       RA: " + Calculations.roundToTwoDecimals(rb) + "   RB: " + Calculations.roundToTwoDecimals(ra)
                + "  |   ra: " + Calculations.roundToTwoDecimals(ra) + "   rb: " + Calculations.roundToTwoDecimals(rb) + "\n";
        for (int i = 0; i < 7; i++) {
            result += "\n       A" + i + ": " + Calculations.roundToTwoDecimals(arr_A[i]) + "   B" + i + ": "
                    + Calculations.roundToTwoDecimals(arr_B[i]) + "  |   a" + i + ": "
                    + Calculations.roundToTwoDecimals(arr_a[i]) + "   b" + i + ": "
                    + Calculations.roundToTwoDecimals(arr_b[i]);
        }
        result += output2();
        return result;
    }

    private String output2() {
        String result = "";


        /* Interne Variablen (lokal) */
        double[] x = new double[27];
        double[] y = new double[27];
        /* Seitenansicht+Draufsicht */
        double[] posx = new double[10];
        double[] posy = new double[10];
        double[] posX = new double[10];
        double[] posY = new double[10];

        double[] wa = new double[5];
        double[] wb = new double[5];
        double[] wA = new double[5];
        double[] wB = new double[5];

        int i, maxx = 0, maxy = 0; /* Hilfsvariablen */
        int p1x, p2x = 0, py = 0, py2; /* Grundkoordinaten */
        double ms, ms1 = 0, ms2 = 0; /* Masstabsfaktoren */
        double msstep, tx = 0, ty = 0, l1, l2; /* Hilfsvariablen */
        double xy = 0; /* Verzerrfaktor x,y */

        /*------------------------- Maástab festlegen ------------------------------*/
        if (e >= 0) /* Versch. vert. oben */ {
            ms1 = Math.min(ty / (b + e), ty / (d + e));
            py = (int) e; /* Y-Start vorinit. */
        }
        ;
        if (e < 0) /* Versch. vert. unten */ {
            ms1 = Math.min(ty / (d - e), ty / b);
            py = 0; /* Y-Start vorinit. */
        }
        ;
        if (f >= 0) /* Versch. rechts */ {
            ms2 = Math.min(tx / (a + f), tx / (d + f));
            p2x = 0; /* X-Start vorinitialisieren */
            if ((d - f) > a)
                p2x = (int) ((d - f) - a); /* Bei berdim. d */
        }
        ;
        if (f < 0) /* Versch links */ {
            ms2 = Math.min(tx / a, tx / (d - f));
            if ((d - f) > a)
                p2x = (int) ((d - f) - a); /* X-Start vorinitialisieren */
            if ((d - f) <= a)
                p2x = 0; /* " */
        }
        ;
        ms = Math.min(ms1, ms2); /* Kleinster Masstab verw. */
        ms = Math.min(ms, tx / l); /* L-nicht vergessen */
        /*------------------- Grundkoordinaten endgltig festlegen ------------------*/
        py = (int) (10 + py * ms); /* Masstabsanpassung */
        p1x = 10;
        p2x = (int) (maxx / 2 + 10 + p2x * ms); /* Masstabsanpassung */

        /*------------------- Seitenansicht berechnen -------------------------------*/
        x[0] = 0;
        y[0] = 0; /* Bezugspunkt ist links oben */
        x[1] = l;
        y[1] = -e;
        x[2] = l;
        y[2] = -e + d;
        x[3] = 0;
        y[3] = b;
        x[4] = 0;
        y[4] = 0;
        x[5] = l;
        y[5] = -e + dh;
        x[6] = 0;
        y[6] = b;

        /*------------------- Draufsicht berechnen ----------------------------------*/
        x[10] = 0;
        y[10] = 0; /* Bezugspunkt ist links oben */
        x[11] = a;
        y[11] = 0;
        x[12] = a;
        y[12] = b;
        x[13] = 0;
        y[13] = b;
        x[14] = 0;
        y[14] = 0;
        x[15] = a - d + f;
        y[15] = -e + dh;
        x[16] = 0;
        y[16] = b;
        x[17] = a - dh + f;
        y[17] = -e + d;
        x[18] = a;
        y[18] = b;
        x[19] = a + f;
        y[19] = -e + dh;
        x[20] = a;
        y[20] = 0;
        x[21] = a - dh + f;
        y[21] = -e;
        x[22] = 0;
        y[22] = 0;

        /*------------------- Kreis von Draufsicht berechnen ------------------------*/
        x[25] = a - dh + f;
        y[25] = -e + dh;
        x[26] = dh;
        /* Kreisradius */


        /*------------------- Verh„ltnisanpassung x-y aller y -----------------------*/
        for (i = 0; i < 26; i++)
            y[i] = y[i] * xy;

        /*------------------- Komplette Masstabsanpassung ---------------------------*/
        for (i = 0; i < 27; i++) {
            y[i] = y[i] * ms;
            x[i] = x[i] * ms;
        }


        /*------------------- Ausgabe Seitenansicht ---------------------------------*/

        /*------------------- Aufrissberechnung -------------------------------------*/

        /*------------------- Aufrissberechnung Teil a-b ----------------------------*/
        msstep = Math.sin(w * 3) * dh; /* gezeichnet wird 8-ter Teilung */

        /* Berechnung s„mtlicher Winkel der Grundseite ab */
        wa[1] = Math.acos(((arr_a[0] * arr_a[0]) + (ab * ab) - (arr_b[0] * arr_b[0])) / (2 * arr_a[0] * ab));
        wa[2] = Math.acos(
                ((arr_a[3] * arr_a[3]) + (arr_a[0] * arr_a[0]) - (msstep * msstep)) / (2 * arr_a[3] * arr_a[0]));
        wa[3] = Math.acos(
                ((arr_a[6] * arr_a[6]) + (arr_a[3] * arr_a[3]) - (msstep * msstep)) / (2 * arr_a[6] * arr_a[3]));
        wa[4] = Math.acos(((schenkel * schenkel) + (arr_a[6] * arr_a[6]) - (ra * ra)) / (2 * schenkel * arr_a[6]));
        wb[1] = Math.acos(((ab * ab) + (arr_b[0] * arr_b[0]) - (arr_a[0] * arr_a[0])) / (2 * ab * arr_b[0]));
        wb[2] = Math.acos(
                ((arr_b[0] * arr_b[0]) + (arr_b[3] * arr_b[3]) - (msstep * msstep)) / (2 * arr_b[0] * arr_b[3]));
        wb[3] = Math.acos(
                ((arr_b[3] * arr_b[3]) + (arr_b[6] * arr_b[6]) - (msstep * msstep)) / (2 * arr_b[3] * arr_b[6]));
        wb[4] = Math.acos(((arr_b[6] * arr_b[6]) + (schenkel * schenkel) - (rb * rb)) / (2 * arr_b[6] * schenkel));

        /*-- Berechnung der s„mtlicher Punkte des Aufrisses relativ zu Punkt a -----*/
        /*-- Start bei Punkt A mit posx[1],posy[1] dann im Uhrzeigersinn weiter ----*/
        posx[1] = 0;
        posy[1] = 0;

        posx[2] = Math.cos(wa[1] + wa[2] + wa[3] + wa[4]) * schenkel;
        posy[2] = Math.sin(wa[1] + wa[2] + wa[3] + wa[4]) * schenkel;

        posx[3] = Math.cos(wa[1] + wa[2] + wa[3]) * arr_a[6];
        posy[3] = Math.sin(wa[1] + wa[2] + wa[3]) * arr_a[6];

        posx[4] = Math.cos(wa[1] + wa[2]) * arr_a[3];
        posy[4] = Math.sin(wa[1] + wa[2]) * arr_a[3];

        posx[5] = Math.cos(wa[1]) * arr_a[0];
        posy[5] = Math.sin(wa[1]) * arr_a[0];

        posx[6] = ab - (Math.cos(wb[1] + wb[2]) * arr_b[3]);
        posy[6] = Math.sin(wb[1] + wb[2]) * arr_b[3];

        posx[7] = ab - (Math.cos(wb[1] + wb[2] + wb[3]) * arr_b[6]);
        posy[7] = Math.sin(wb[1] + wb[2] + wb[3]) * arr_b[6];

        posx[8] = ab - (Math.cos(wb[1] + wb[2] + wb[3] + wb[4]) * schenkel);
        posy[8] = Math.sin(wb[1] + wb[2] + wb[3] + wb[4]) * schenkel;

        posx[9] = ab;
        posy[9] = 0;

        /*----------------------------- Aufrissberechnung 2-ter Teil ----------------*/
        /* Aufrissberechnung AB relativ zu Punkt A */
        msstep = Math.sin(w * 3) * dh;

        wA[1] = Math.acos(((arr_A[0] * arr_A[0]) + (ab * ab) - (arr_B[0] * arr_B[0])) / (2 * arr_A[0] * ab));
        wA[2] = Math.acos(
                ((arr_A[3] * arr_A[3]) + (arr_A[0] * arr_A[0]) - (msstep * msstep)) / (2 * arr_A[3] * arr_A[0]));
        wA[3] = Math.acos(
                ((arr_A[6] * arr_A[6]) + (arr_A[3] * arr_A[3]) - (msstep * msstep)) / (2 * arr_A[6] * arr_A[3]));
        wA[4] = Math.acos(((schenkel * schenkel) + (arr_A[6] * arr_A[6]) - (rb * rb)) / (2 * schenkel * arr_A[6]));
        wB[1] = Math.acos(((ab * ab) + (arr_B[0] * arr_B[0]) - (arr_A[0] * arr_A[0])) / (2 * ab * arr_B[0]));
        wB[2] = Math.acos(
                ((arr_B[0] * arr_B[0]) + (arr_B[3] * arr_B[3]) - (msstep * msstep)) / (2 * arr_B[0] * arr_B[3]));
        wB[3] = Math.acos(
                ((arr_B[3] * arr_B[3]) + (arr_B[6] * arr_B[6]) - (msstep * msstep)) / (2 * arr_B[3] * arr_B[6]));
        wB[4] = Math.acos(((arr_B[6] * arr_B[6]) + (schenkel * schenkel) - (ra * ra)) / (2 * arr_B[6] * schenkel));

        /*-- Berechnung der s„mtlicher Punkte des Aufrisses relativ zu Punkt A ------*/
        /*-- Start bei Punkt A mit posx[1],posy[1] dann im Uhrzeigersinn weiter -----*/
        posX[1] = 0;
        posY[1] = 0;

        posX[2] = Math.cos(wA[1] + wA[2] + wA[3] + wA[4]) * schenkel;
        posY[2] = Math.sin(wA[1] + wA[2] + wA[3] + wA[4]) * schenkel;

        posX[3] = Math.cos(wA[1] + wA[2] + wA[3]) * arr_A[6];
        posY[3] = Math.sin(wA[1] + wA[2] + wA[3]) * arr_A[6];

        posX[4] = Math.cos(wA[1] + wA[2]) * arr_A[3];
        posY[4] = Math.sin(wA[1] + wA[2]) * arr_A[3];

        posX[5] = Math.cos(wA[1]) * arr_A[0];
        posY[5] = Math.sin(wA[1]) * arr_A[0];

        posX[6] = ab - (Math.cos(wB[1] + wB[2]) * arr_B[3]);
        posY[6] = Math.sin(wB[1] + wB[2]) * arr_B[3];

        posX[7] = ab - (Math.cos(wB[1] + wB[2] + wB[3]) * arr_B[6]);
        posY[7] = Math.sin(wB[1] + wB[2] + wB[3]) * arr_B[6];

        posX[8] = ab - (Math.cos(wB[1] + wB[2] + wB[3] + wB[4]) * schenkel);
        posY[8] = Math.sin(wB[1] + wB[2] + wB[3] + wB[4]) * schenkel;

        posX[9] = ab;
        posY[9] = 0;

        /*----------------------- Startpunkt und Plattengr”áe errechnen ------------*/

        /*----------------------- Masstab festlegen ---------------------------------*/
        /* Grundpunkte vorinitialisieren */
        p1x = 0;
        p2x = 0;
        py = 0;
        py2 = 0;

        /* !!!!!!!!!!!! Beachte: Ausgangspunkt fr die Punkte ist immer a bzw. A !! */
        /* rechter Aufriss -> p1x,py */
        ms = 0;
        l1 = Math.min(posx[2], posx[3]); /* L. Schenkel unten + oben */
        if (l1 < 0) {
            ms = -l1;
            p1x = (int) -l1;
        }
         /* Linker Schenkel */
        l2 = Math.max(posx[8], posx[7]); /* R. Schenkel unten + oben */
        if (l2 - ab > 0)
            ms = ms + l2 - ab; /* Rechter Schenkel */
        ms = ms + ab; /* + ab */

        /* linker Aufriss -> p2x,py2 */
        ms1 = 0;
        l1 = Math.min(posX[2], posX[3]); /* L. Schenkel unten + oben */
        if (l1 < 0) {
            ms1 = -l1;
            p2x = (int) -l1;
        }
         /* Linker Schenkel */
        l2 = Math.max(posX[8], posX[7]); /* R. Schenkel unten + oben */
        if (l2 - ab > 0)
            ms1 = ms1 + l2 - ab; /* Rechter Schenkel */
        ms1 = ms1 + ab; /* + ab */

        /* Plattenbreite jetzt ablesbar */
        double grX = ms1; /* l. Plattenbreite */
        double grx = ms; /* r. Plattenbreite */

        ms1 = Math.min(tx / ms, tx / ms1); /* X-Masstabsfaktor */

        /* rechter Aufriss */
        ms = Math.max(Math.max(posy[3], posy[4]), Math.max(posy[5], posy[6]));
        ms = Math.max(ms, posy[7]);

        if (Math.min(posy[2], posy[8]) <= 0) {
            py = (int) Math.min(posy[2], posy[8]);
            py = -py;
            ms = ms + py;
        }

        /* linker Aufriss */
        ms2 = Math.max(Math.max(posY[3], posY[4]), Math.max(posY[5], posY[6]));
        ms2 = Math.max(ms2, posY[7]);

        if (Math.min(posY[2], posY[8]) <= 0) {
            py2 = (int) Math.min(posY[2], posY[8]);
            py2 = -py2;
            ms2 = ms2 + py2;
        }

        /* Plattenh”he jetzt ablesbar */
        double grY = ms2; /* ms2 enth„lt l. Gesamth”he->Plattengr.Y */
        double gry = ms; /* ms enth„lt r. Gesamth”he->Plattengr.y */

        ms2 = Math.min(ty / ms, ty / ms2); /* Y-Masstabsfaktor */

        ms = Math.min(ms1, ms2); /* Masstabsfaktor */

        /*----------------------- Startpunkt ----------------------------------------*/
        /* linker Aufriss */
        double stX = p2x;
        double stY = py2;

        /* rechter Aufriss */
        double sty = py;
        double stx = p1x;

        /*----------------- Koordinatengrundpunkte ----------------------------------*/
        py = (int) ((maxy - 10) - py * ms); /* Versch. rechter A. durch Sch. n. unten */
        py2 = (int) ((maxy - 10) - py2 * ms); /* Versch. linker A. durch Schenkel n. unten */
        p2x = (int) (10 + p2x * ms); /* Versch. linker A. durch l. Schenkel */
        p1x = (int) ((maxx / 2) + 10 + p1x * ms); /* Versch. rechter A. durch l. Schenkel */

        /*----------------- Verh„ltnisanpassung x-y aller y -------------------------*/
        for (i = 2; i < 10; i++)
            posy[i] = posy[i] * xy;
        for (i = 2; i < 10; i++)
            posY[i] = posY[i] * xy;

        /*----------------- Komplette Masstabsanpassung x-y -------------------------*/
        for (i = 2; i < 10; i++) {
            posy[i] = posy[i] * ms;
            posx[i] = posx[i] * ms;
        }

        for (i = 2; i < 10; i++) {
            posY[i] = posY[i] * ms;
            posX[i] = posX[i] * ms;
        }

        result += "\n\n";
        result += "       LX: " + Calculations.roundToTwoDecimals(grX) + "   LY: " + Calculations.roundToTwoDecimals(grY) + "       lx: " + Calculations.roundToTwoDecimals(grx) + " ly: " + Calculations.roundToTwoDecimals(gry);
        result += "\n";
        result += "       AX: " + Calculations.roundToTwoDecimals(stX) + "   AY: " + Calculations.roundToTwoDecimals(stY) + "       ax: " + Calculations.roundToTwoDecimals(stx) + " ay: " + Calculations.roundToTwoDecimals(sty);
        return result;
    }
}
