import java.lang.Math;

public class berechnungen {

	private double a, b, d, l, f, e, dicke;
	private double ab, rb;
	private double[] arr_b = { 1, 2, 3, 4, 5, 6, 7 };
	private double[] arr_a = { 1, 2, 3, 4, 5, 6, 7 };
	private double ra;
	private double[] arr_A = { 1, 2, 3, 4, 5, 6, 7 };
	private double[] arr_B = { 1, 2, 3, 4, 5, 6, 7 };
	private double schenkel, r, step;
	private double temp, da, db, ma, mb, dh;
	double w = 0.261799387;
	private int wsum;

	public berechnungen(double iv_a, double iv_b, double iv_d, double iv_l, double iv_dicke, double iv_e, double iv_f) {
		a = iv_a;
		b = iv_b;
		d = iv_d;
		l = iv_l;
		f = iv_f;
		e = iv_e;
		dicke = iv_dicke;
		dh = d / 2;

	}

	private void checksymmetrie() {
		if ((d - a) / 2 != f) /* Grundsymetrie nicht gegeben */
		{
			if ((d - b) / 2 == e) /* Aber Symetrie vorhanden */
			{ /* Werkstck drehen */
				temp = a;
				a = b;
				b = temp;
				temp = e;
				e = f;
				f = temp;
			}
			;
		}
		;
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

	private void grundberechnungen() {

		checksymmetrie();
		ab = b - dicke;
		schenkel = a / 2;
		r = Math.sqrt(Math.pow(e, 2) + Math.pow(l, 2));
		rb = Math.sqrt(((d - a) / 2 - f) * ((d - a) / 2 - f) + r * r);
		r = Math.sqrt((b - d + e) * (b - d + e) + (Math.pow(l, 2))); /* R2 y-sym */
		ra = Math.sqrt(((d - a) / 2 - f) * ((d - a) / 2 - f) + r * r); /* R asym. */
		/* b0 ermitteln */
		mb = dh - e;
		ma = -f;
		arr_b[0] = Math.sqrt((ma * ma + mb * mb) + (l * l));

		/* a0 ermitteln */
		mb = b - (dh - e);
		ma = -f;
		arr_a[0] = Math.sqrt((ma * ma + mb * mb) + (l * l));

		/* A0 ermitteln */
		mb = dh - e;
		ma = a - d + f;
		arr_A[0] = Math.sqrt((ma * ma + mb * mb) + (l * l));

		/* B0 ermitteln */
		mb = b - (dh - e);
		ma = a - d + f;
		arr_B[0] = Math.sqrt((ma * ma + mb * mb) + (l * l));

		/* A's+ B's ermitteln */
		for (int i = 0; i < 7; i++) {
			wsum = i;
			arr_b[i] = bp();
			arr_a[i] = ap();
			arr_B[i] = BP();
			arr_A[i] = AP();

			// arr_A[i] = AP();
		}

		/*---------------------------- Step errechnen ------------------------------*/
		step = Math.sin(w) * dh;

	}

	public String output() {
		grundberechnungen();
		format formatter = new format();
		String temp = "";
		temp += "Errechnete Werte für das Übergangsstück Rechteck auf Rund!";
		temp += "\n       AB: " + formatter.roundToTwoDecimals(ab);
		temp += "  Schenkellänge: " + formatter.roundToTwoDecimals(schenkel);
		temp += "  Step: " + formatter.roundToTwoDecimals(step);
		temp += "\n       RA: " + formatter.roundToTwoDecimals(rb) + "   RB: " + formatter.roundToTwoDecimals(ra)
				+ "  |   ra: " + formatter.roundToTwoDecimals(ra) + "   rb: " + formatter.roundToTwoDecimals(rb) + "\n";
		for (int i = 0; i < 7; i++) {
			temp += "\n       A" + i + ": " + formatter.roundToTwoDecimals(arr_A[i]) + "   B" + i + ": "
					+ formatter.roundToTwoDecimals(arr_B[i]) + "  |   a" + i + ": "
					+ formatter.roundToTwoDecimals(arr_a[i]) + "   b" + i + ": "
					+ formatter.roundToTwoDecimals(arr_b[i]);
		}
		temp += output2();
		return temp;
	}

	public String output2() {
		String tanga = "";
		

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
			if (e >= 0) /* Versch. vert. oben */
			{
				ms1 = Math.min(ty / (b + e), ty / (d + e));
				py = (int) e; /* Y-Start vorinit. */
			}
			;
			if (e < 0) /* Versch. vert. unten */
			{
				ms1 = Math.min(ty / (d - e), ty / b);
				py = 0; /* Y-Start vorinit. */
			}
			;
			if (f >= 0) /* Versch. rechts */
			{
				ms2 = Math.min(tx / (a + f), tx / (d + f));
				p2x = 0; /* X-Start vorinitialisieren */
				if ((d - f) > a)
					p2x = (int) ((d - f) - a); /* Bei berdim. d */
			}
			;
			if (f < 0) /* Versch links */
			{
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
			/* Kreisradius */;

			/*------------------- Verh„ltnisanpassung x-y aller y -----------------------*/
			for (i = 0; i < 26; i++)
				y[i] = y[i] * xy;

			/*------------------- Komplette Masstabsanpassung ---------------------------*/
			for (i = 0; i < 27; i++) {
				y[i] = y[i] * ms;
				x[i] = x[i] * ms;
			}
			;

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
			; /* Linker Schenkel */
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
			; /* Linker Schenkel */
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
			;

			/* linker Aufriss */
			ms2 = Math.max(Math.max(posY[3], posY[4]), Math.max(posY[5], posY[6]));
			ms2 = Math.max(ms2, posY[7]);

			if (Math.min(posY[2], posY[8]) <= 0) {
				py2 = (int) Math.min(posY[2], posY[8]);
				py2 = -py2;
				ms2 = ms2 + py2;
			}
			;

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
			;
			for (i = 2; i < 10; i++) {
				posY[i] = posY[i] * ms;
				posX[i] = posX[i] * ms;
			}
			;
		format formatter = new format();
		tanga += "\n\n";
		tanga += "       LX: " + formatter.roundToTwoDecimals(grX) + "   LY: " + formatter.roundToTwoDecimals(grY) + "       lx: " + formatter.roundToTwoDecimals(grx) + " ly: " + formatter.roundToTwoDecimals(gry);
		tanga += "\n";
		tanga += "       AX: " + formatter.roundToTwoDecimals(stX) + "   AY: " + formatter.roundToTwoDecimals(stY) + "       ax: " + formatter.roundToTwoDecimals(stx) + " ay: " + formatter.roundToTwoDecimals(sty);
		return tanga;

	}
}
