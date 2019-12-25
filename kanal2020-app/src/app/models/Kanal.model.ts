export interface KanalRoute {
    a: string;
    b: string;
    d: string;
    dicke: string;
    e: string;
    f: string;
    l: string;
}

export class Kanal {

    private a: number;
    private b: number;
    private d: number;
    private l: number;
    private f: number;
    private e: number;
    private dicke: number;
    private ab: number;
    private rb: number;
    private arr_a: number[];
    private arr_b: number[];
    private arr_A: number[];
    private arr_B: number[];
    private ra: number;
    private schenkel: number;
    private r: number;
    private step: number;
    private da: number;
    private db: number;
    private ma: number;
    private mb: number;
    private dh: number;
    private wsum: number;
    private w = 0.261799387;

    // added members for data export
    private grX: number;
    private grY: number;
    private grx: number;
    private gry: number;
    private stX: number;
    private stY: number;
    private stx: number;
    private sty: number;

    private dataArray: any[] = [];

    public static roundToTwoDecimals(value: number): string {
        value = value * 10;
        value = Math.round(value);
        value = value / 10;
        return value.toString();
    }

    private constructor(kanal: KanalRoute) {
        this.a = parseInt(kanal.a, 10);
        this.b = parseInt(kanal.b, 10);
        this.d = parseInt(kanal.d, 10);
        this.l = parseInt(kanal.l, 10);
        this.f = parseInt(kanal.f, 10);
        this.e = parseInt(kanal.e, 10);
        this.dicke = parseInt(kanal.dicke, 10);
        this.dh = this.d / 2;
        this.arr_a = [0, 0, 0, 0, 0, 0, 0];
        this.arr_b = [0, 0, 0, 0, 0, 0, 0];
        this.arr_A = [0, 0, 0, 0, 0, 0, 0];
        this.arr_B = [0, 0, 0, 0, 0, 0, 0];
    }

    public static getNewKanal(kanal: KanalRoute) {
        return new Kanal(kanal);
    }



    private checkSymmetrie(): void {
        if ((this.d - this.a) / 2 !== this.f) { // Grundsymmetrie nicht gegeben
            if ((this.d - this.b) / 2 === this.e) { // aber Symmetrie vorhanden
                // Werkstück drehen
                let temp = this.a;
                this.a = this.b;
                this.b = temp;
                temp = this.e;
                this.e = this.f;
                this.f = temp;
            }
        }
    }

    public calculateAll(): string {
        this.checkSymmetrie();
        this.berechneGrunddaten();
        return this.output() + this.output2();
    }

    private berechneGrunddaten(): void {
        this.ab = this.b - this.dicke;
        this.schenkel = this.a / 2;
        this.r = Math.sqrt(this.e * this.e + this.l * this.l);
        this.rb = Math.sqrt(((this.d - this.a) / 2 - this.f) * ((this.d - this.a) / 2 - this.f) + this.r * this.r);
        this.r = Math.sqrt((this.b - this.d + this.e) * (this.b - this.d + this.e) + this.l * this.l); // R2 y-symmetrisch
        this.ra = Math.sqrt(((this.d - this.a) / 2 - this.f) * ((this.d - this.a) / 2 - this.f) + this.r * this.r); // R asymmetrisch

        // ermitteln von b0
        this.mb = this.dh - this.e;
        this.ma = -this.f;
        this.arr_b[0] = Math.sqrt((this.ma * this.ma + this.mb * this.mb) + (this.l * this.l));

        // ermitteln von a0
        this.mb = this.b - (this.dh - this.e);
        this.ma = -this.f;
        this.arr_a[0] = Math.sqrt((this.ma * this.ma + this.mb * this.mb) + (this.l * this.l));

        // ermitteln von A0
        this.mb = this.dh - this.e;
        this.ma = this.a - this.d + this.f;
        this.arr_A[0] = Math.sqrt((this.ma * this.ma + this.mb * this.mb) + (this.l * this.l));

        /* B0 ermitteln */
        this.mb = this.b - (this.dh - this.e);
        this.ma = this.a - this.d + this.f;
        this.arr_B[0] = Math.sqrt((this.ma * this.ma + this.mb * this.mb) + (this.l * this.l));

        /* A's+ B's ermitteln */
        for (let i = 0; i < 7; i++) {
            this.wsum = i;
            this.arr_b[i] = this.bp();
            this.arr_a[i] = this.ap();
            this.arr_B[i] = this.BP();
            this.arr_A[i] = this.AP();
        }

        /*---------------------------- Step errechnen ------------------------------*/
        this.step = Math.sin(this.w) * this.dh;
    }

    private bp(): number {
        this.db = Math.sin(this.w * this.wsum) * this.dh;
        this.da = this.dh - (Math.cos(this.w * this.wsum) * this.dh);
        this.mb = (this.dh - this.e) - this.db;
        this.ma = -this.f + this.da;
        return (Math.sqrt((this.ma * this.ma + this.mb * this.mb) + (this.l * this.l)));
    }
    private ap(): number {
        this.db = Math.sin(this.w * this.wsum) * this.dh;
        this.da = this.dh - (Math.cos(this.w * this.wsum) * this.dh);
        this.mb = (this.b - (this.dh - this.e)) - this.db;
        this.ma = -this.f + this.da;
        return (Math.sqrt((this.ma * this.ma + this.mb * this.mb) + (this.l * this.l)));
    }

    private AP(): number /* berechnen der Kreispunkte links oben */ {
        this.db = Math.sin(this.w * this.wsum) * this.dh;
        this.da = this.dh - (Math.cos(this.w * this.wsum) * this.dh);
        this.mb = (this.dh - this.e) - this.db;
        this.ma = (this.a - this.d + this.f) + this.da; /* modified */
        return (Math.sqrt((this.ma * this.ma + this.mb * this.mb) + (this.l * this.l)));
    }

    private BP(): number /* berechnen der Kreispunkte links unten */ {
        this.db = Math.sin(this.w * this.wsum) * this.dh;
        this.da = this.dh - (Math.cos(this.w * this.wsum) * this.dh);
        this.mb = (this.b - (this.dh - this.e)) - this.db;
        this.ma = (this.a - this.d + this.f) + this.da; /* modified */
        return (Math.sqrt((this.ma * this.ma + this.mb * this.mb) + (this.l * this.l)));
    }

    private output(): string {
        let result = '';
        result += 'Errechnete Werte für das Übergangsstück Rechteck auf Rund!';
        result += '\n       AB: ' + Kanal.roundToTwoDecimals(this.ab);
        result += '  Schenkellänge: ' + Kanal.roundToTwoDecimals(this.schenkel);
        result += '  Step: ' + Kanal.roundToTwoDecimals(this.step);
        result += '\n       RA: ' + Kanal.roundToTwoDecimals(this.rb) + '   RB: ' + Kanal.roundToTwoDecimals(this.ra)
            + '  |   ra: ' + Kanal.roundToTwoDecimals(this.ra) + '   rb: ' + Kanal.roundToTwoDecimals(this.rb) + '\n';
        for (let i = 0; i < 7; i++) {
            result += '\n       A' + i + ': ' + Kanal.roundToTwoDecimals(this.arr_A[i]) + '   B' + i + ': '
                + Kanal.roundToTwoDecimals(this.arr_B[i]) + '  |   a' + i + ': '
                + Kanal.roundToTwoDecimals(this.arr_a[i]) + '   b' + i + ': '
                + Kanal.roundToTwoDecimals(this.arr_b[i]);
        }
        result += this.output2();
        return result;
    }

    public getExtraData(): any[] {
        this.calculateAll();
        return [
            { name: 'AB', value: Kanal.roundToTwoDecimals(this.ab) },
            { name: 'Schenkellänge', value: Kanal.roundToTwoDecimals(this.schenkel) },
            { name: 'Step', value: Kanal.roundToTwoDecimals(this.step) }
        ];
    }

    public getDataSource(): any[] {
        this.calculateAll();
        const array: any[] = [];
        const r = {
            A: 'RA: ' + Kanal.roundToTwoDecimals(this.rb),
            B: 'RB: ' + Kanal.roundToTwoDecimals(this.ra),
            a: 'ra: ' + Kanal.roundToTwoDecimals(this.ra),
            b: 'rb: ' + Kanal.roundToTwoDecimals(this.rb),
        };
        array.push(r);
        for (let i = 0; i < 7; i++) {
            const line = {
                A: 'A' + i + ': ' + Kanal.roundToTwoDecimals(this.arr_A[i]),
                B: 'B' + i + ': ' + Kanal.roundToTwoDecimals(this.arr_B[i]),
                a: 'a' + i + ': ' + Kanal.roundToTwoDecimals(this.arr_a[i]),
                b: 'b' + i + ': ' + Kanal.roundToTwoDecimals(this.arr_b[i])
            };
            array.push(line);
        }
        const l = {
            A: 'LX: ' + Kanal.roundToTwoDecimals(this.grX),
            B: 'LY: ' + Kanal.roundToTwoDecimals(this.grY),
            a: 'lx: ' + Kanal.roundToTwoDecimals(this.grx),
            b: 'ly: ' + Kanal.roundToTwoDecimals(this.gry)
        };
        array.push(l);
        const a = {
            A: 'AX: ' + Kanal.roundToTwoDecimals(this.stX),
            B: 'AY: ' + Kanal.roundToTwoDecimals(this.stY),
            a: 'ax: ' + Kanal.roundToTwoDecimals(this.stx),
            b: 'ay: ' + Kanal.roundToTwoDecimals(this.sty)
        };
        array.push(a);
        return array;
    }

    private output2(): string {
        let result = '';

        /* Interne Variablen (lokal) */
        const x = [0];
        const y = [0];
        /* Seitenansicht+Draufsicht */
        const posx = [0];
        const posy = [0];
        const posX = [0];
        const posY = [0];

        const wa = [0];
        const wb = [0];
        const wA = [0];
        const wB = [0];

        let i: number;
        const maxx = 0;
        const maxy = 0; /* Hilfsvariablen */
        let p1x: number;
        let p2x = 0;
        let py = 0;
        let py2: number; /* Grundkoordinaten */
        let ms: number;
        let ms1 = 0;
        let ms2 = 0; /* Masstabsfaktoren */
        let msstep: number;
        const tx = 0;
        const ty = 0;
        let l1: number;
        let l2: number; /* Hilfsvariablen */
        const xy = 0; /* Verzerrfaktor x,y */

        /*------------------------- Maástab festlegen ------------------------------*/
        if (this.e >= 0) /* Versch. vert. oben */ {
            ms1 = Math.min(ty / (this.b + this.e), ty / (this.d + this.e));
            // py = (int) this.e; /* Y-Start vorinit. */
            py = parseInt(this.e.toString(), 10); /* Y-Start vorinit. */
        }
        if (this.e < 0) /* Versch. vert. unten */ {
            ms1 = Math.min(ty / (this.d - this.e), ty / this.b);
            py = 0; /* Y-Start vorinit. */
        }
        if (this.f >= 0) /* Versch. rechts */ {
            ms2 = Math.min(tx / (this.a + this.f), tx / (this.d + this.f));
            p2x = 0; /* X-Start vorinitialisieren */
            if ((this.d - this.f) > this.a) {
                // p2x = (int)((this.d - this.f) - this.a); /* Bei berdim. d */
                p2x = parseInt(((this.d - this.f) - this.a).toString(), 10); /* Bei berdim. d */
            }
        }
        if (this.f < 0) /* Versch links */ {
            ms2 = Math.min(tx / this.a, tx / (this.d - this.f));
            if ((this.d - this.f) > this.a) {
                // p2x = (int)((this.d - this.f) - this.a); /* X-Start vorinitialisieren */
                p2x = parseInt(((this.d - this.f) - this.a).toString(), 10); /* X-Start vorinitialisieren */
            }
            if ((this.d - this.f) <= this.a) {
                p2x = 0; /* " */
            }
        }
        ms = Math.min(ms1, ms2); /* Kleinster Masstab verw. */
        ms = Math.min(ms, tx / this.l); /* L-nicht vergessen */
        /*------------------- Grundkoordinaten endgltig festlegen ------------------*/
        // py = (int)(10 + py * ms); /* Masstabsanpassung */
        py = parseInt((10 + py * ms).toString(), 10); /* Masstabsanpassung */
        p1x = 10;
        // p2x = (int)(maxx / 2 + 10 + p2x * ms); /* Masstabsanpassung */
        p2x = parseInt((maxx / 2 + 10 + p2x * ms).toString(), 10); /* Masstabsanpassung */

        /*------------------- Seitenansicht berechnen -------------------------------*/
        x[0] = 0;
        y[0] = 0; /* Bezugspunkt ist links oben */
        x[1] = this.l;
        y[1] = -this.e;
        x[2] = this.l;
        y[2] = -this.e + this.d;
        x[3] = 0;
        y[3] = this.b;
        x[4] = 0;
        y[4] = 0;
        x[5] = this.l;
        y[5] = -this.e + this.dh;
        x[6] = 0;
        y[6] = this.b;

        /*------------------- Draufsicht berechnen ----------------------------------*/
        x[10] = 0;
        y[10] = 0; /* Bezugspunkt ist links oben */
        x[11] = this.a;
        y[11] = 0;
        x[12] = this.a;
        y[12] = this.b;
        x[13] = 0;
        y[13] = this.b;
        x[14] = 0;
        y[14] = 0;
        x[15] = this.a - this.d + this.f;
        y[15] = -this.e + this.dh;
        x[16] = 0;
        y[16] = this.b;
        x[17] = this.a - this.dh + this.f;
        y[17] = -this.e + this.d;
        x[18] = this.a;
        y[18] = this.b;
        x[19] = this.a + this.f;
        y[19] = -this.e + this.dh;
        x[20] = this.a;
        y[20] = 0;
        x[21] = this.a - this.dh + this.f;
        y[21] = -this.e;
        x[22] = 0;
        y[22] = 0;

        /*------------------- Kreis von Draufsicht berechnen ------------------------*/
        x[25] = this.a - this.dh + this.f;
        y[25] = -this.e + this.dh;
        x[26] = this.dh;
        /* Kreisradius */


        /*------------------- Verh„ltnisanpassung x-y aller y -----------------------*/
        for (let j = 0; j < 26; j++) {
            y[j] = y[j] * xy;
        }

        /*------------------- Komplette Masstabsanpassung ---------------------------*/
        for (i = 0; i < 27; i++) {
            y[i] = y[i] * ms;
            x[i] = x[i] * ms;
        }


        /*------------------- Ausgabe Seitenansicht ---------------------------------*/

        /*------------------- Aufrissberechnung -------------------------------------*/

        /*------------------- Aufrissberechnung Teil a-b ----------------------------*/
        msstep = Math.sin(this.w * 3) * this.dh; /* gezeichnet wird 8-ter Teilung */

        /* Berechnung s„mtlicher Winkel der Grundseite ab */
        wa[1] = Math.acos(
            (
                (this.arr_a[0] * this.arr_a[0]) +
                (this.ab * this.ab) -
                (this.arr_b[0] * this.arr_b[0])
            )
            /
            (
                2 * this.arr_a[0] * this.ab
            )
        );
        wa[2] = Math.acos(
            (
                (this.arr_a[3] * this.arr_a[3]) +
                (this.arr_a[0] * this.arr_a[0]) -
                (msstep * msstep)
            )
            /
            (
                2 * this.arr_a[3] * this.arr_a[0]
            )
        );
        wa[3] = Math.acos(
            (
                (this.arr_a[6] * this.arr_a[6]) +
                (this.arr_a[3] * this.arr_a[3]) -
                (msstep * msstep)
            )
            /
            (
                2 * this.arr_a[6] * this.arr_a[3]
            )
        );
        wa[4] = Math.acos(
            (
                (this.schenkel * this.schenkel) +
                (this.arr_a[6] * this.arr_a[6]) -
                (this.ra * this.ra)
            )
            /
            (
                2 * this.schenkel * this.arr_a[6]
            )
        );
        wb[1] = Math.acos(
            (
                (this.ab * this.ab) +
                (this.arr_b[0] * this.arr_b[0]) -
                (this.arr_a[0] * this.arr_a[0])
            )
            /
            (
                2 * this.ab * this.arr_b[0]
            )
        );
        wb[2] = Math.acos(
            ((this.arr_b[0] * this.arr_b[0]) + (this.arr_b[3] * this.arr_b[3]) - (msstep * msstep)) / (2 * this.arr_b[0] * this.arr_b[3]));
        wb[3] = Math.acos(
            ((this.arr_b[3] * this.arr_b[3]) + (this.arr_b[6] * this.arr_b[6]) - (msstep * msstep)) / (2 * this.arr_b[3] * this.arr_b[6]));
        wb[4] = Math.acos(
            (
                (this.arr_b[6] * this.arr_b[6]) +
                (this.schenkel * this.schenkel) -
                (this.rb * this.rb)
            )
            /
            (
                2 * this.arr_b[6] * this.schenkel
            )
        );
        /*-- Berechnung der s„mtlicher Punkte des Aufrisses relativ zu Punkt a -----*/
        /*-- Start bei Punkt A mit posx[1],posy[1] dann im Uhrzeigersinn weiter ----*/
        posx[1] = 0;
        posy[1] = 0;

        posx[2] = Math.cos(wa[1] + wa[2] + wa[3] + wa[4]) * this.schenkel;
        posy[2] = Math.sin(wa[1] + wa[2] + wa[3] + wa[4]) * this.schenkel;

        posx[3] = Math.cos(wa[1] + wa[2] + wa[3]) * this.arr_a[6];
        posy[3] = Math.sin(wa[1] + wa[2] + wa[3]) * this.arr_a[6];

        posx[4] = Math.cos(wa[1] + wa[2]) * this.arr_a[3];
        posy[4] = Math.sin(wa[1] + wa[2]) * this.arr_a[3];

        posx[5] = Math.cos(wa[1]) * this.arr_a[0];
        posy[5] = Math.sin(wa[1]) * this.arr_a[0];

        posx[6] = this.ab - (Math.cos(wb[1] + wb[2]) * this.arr_b[3]);
        posy[6] = Math.sin(wb[1] + wb[2]) * this.arr_b[3];

        posx[7] = this.ab - (Math.cos(wb[1] + wb[2] + wb[3]) * this.arr_b[6]);
        posy[7] = Math.sin(wb[1] + wb[2] + wb[3]) * this.arr_b[6];

        posx[8] = this.ab - (Math.cos(wb[1] + wb[2] + wb[3] + wb[4]) * this.schenkel);
        posy[8] = Math.sin(wb[1] + wb[2] + wb[3] + wb[4]) * this.schenkel;

        posx[9] = this.ab;
        posy[9] = 0;

        /*----------------------------- Aufrissberechnung 2-ter Teil ----------------*/
        /* Aufrissberechnung AB relativ zu Punkt A */
        msstep = Math.sin(this.w * 3) * this.dh;

        wA[1] = Math.acos(
            (
                (this.arr_A[0] * this.arr_A[0]) +
                (this.ab * this.ab) -
                (this.arr_B[0] * this.arr_B[0])
            )
            /
            (
                2 * this.arr_A[0] * this.ab
            )
        );
        wA[2] = Math.acos(
            ((this.arr_A[3] * this.arr_A[3]) + (this.arr_A[0] * this.arr_A[0]) - (msstep * msstep)) / (2 * this.arr_A[3] * this.arr_A[0]));
        wA[3] = Math.acos(
            ((this.arr_A[6] * this.arr_A[6]) + (this.arr_A[3] * this.arr_A[3]) - (msstep * msstep)) / (2 * this.arr_A[6] * this.arr_A[3]));
        wA[4] = Math.acos(
            (
                (this.schenkel * this.schenkel) +
                (this.arr_A[6] * this.arr_A[6]) -
                (this.rb * this.rb)
            )
            /
            (
                2 * this.schenkel * this.arr_A[6]
            )
        );
        wB[1] = Math.acos(
            (
                (this.ab * this.ab) +
                (this.arr_B[0] * this.arr_B[0]) -
                (this.arr_A[0] * this.arr_A[0])
            )
            /
            (
                2 * this.ab * this.arr_B[0]
            )
        );
        wB[2] = Math.acos(
            ((this.arr_B[0] * this.arr_B[0]) + (this.arr_B[3] * this.arr_B[3]) - (msstep * msstep)) / (2 * this.arr_B[0] * this.arr_B[3]));
        wB[3] = Math.acos(
            ((this.arr_B[3] * this.arr_B[3]) + (this.arr_B[6] * this.arr_B[6]) - (msstep * msstep)) / (2 * this.arr_B[3] * this.arr_B[6]));
        wB[4] = Math.acos(
            (
                (this.arr_B[6] * this.arr_B[6]) +
                (this.schenkel * this.schenkel) -
                (this.ra * this.ra)
            )
            /
            (
                2 * this.arr_B[6] *
                this.schenkel
            )
        );

        /*-- Berechnung der s„mtlicher Punkte des Aufrisses relativ zu Punkt A ------*/
        /*-- Start bei Punkt A mit posx[1],posy[1] dann im Uhrzeigersinn weiter -----*/
        posX[1] = 0;
        posY[1] = 0;

        posX[2] = Math.cos(wA[1] + wA[2] + wA[3] + wA[4]) * this.schenkel;
        posY[2] = Math.sin(wA[1] + wA[2] + wA[3] + wA[4]) * this.schenkel;

        posX[3] = Math.cos(wA[1] + wA[2] + wA[3]) * this.arr_A[6];
        posY[3] = Math.sin(wA[1] + wA[2] + wA[3]) * this.arr_A[6];

        posX[4] = Math.cos(wA[1] + wA[2]) * this.arr_A[3];
        posY[4] = Math.sin(wA[1] + wA[2]) * this.arr_A[3];

        posX[5] = Math.cos(wA[1]) * this.arr_A[0];
        posY[5] = Math.sin(wA[1]) * this.arr_A[0];

        posX[6] = this.ab - (Math.cos(wB[1] + wB[2]) * this.arr_B[3]);
        posY[6] = Math.sin(wB[1] + wB[2]) * this.arr_B[3];

        posX[7] = this.ab - (Math.cos(wB[1] + wB[2] + wB[3]) * this.arr_B[6]);
        posY[7] = Math.sin(wB[1] + wB[2] + wB[3]) * this.arr_B[6];

        posX[8] = this.ab - (Math.cos(wB[1] + wB[2] + wB[3] + wB[4]) * this.schenkel);
        posY[8] = Math.sin(wB[1] + wB[2] + wB[3] + wB[4]) * this.schenkel;

        posX[9] = this.ab;
        posY[9] = 0;

        /*----------------------- Startpunkt und Plattengr”áe errechnen ------------*/

        /*----------------------- Masstab festlegen ---------------------------------*/
        /* Grundpunkte vorinitialisieren */
        p1x = 0;
        p2x = 0;
        py = 0;
        py2 = 0;

        /* !!!!!!!!!!!! Beachte: Ausgangspunkt für die Punkte ist immer a bzw. A !! */
        /* rechter Aufriss -> p1x,py */
        ms = 0;
        l1 = Math.min(posx[2], posx[3]); /* L. Schenkel unten + oben */
        if (l1 < 0) {
            ms = -l1;
            // p1x = (int) - l1;
            p1x = parseInt((-l1).toString(), 10);
        }
        /* Linker Schenkel */
        l2 = Math.max(posx[8], posx[7]); /* R. Schenkel unten + oben */
        if (l2 - this.ab > 0) { }
        ms = ms + l2 - this.ab; /* Rechter Schenkel */
        ms = ms + this.ab; /* + ab */

        /* linker Aufriss -> p2x,py2 */
        ms1 = 0;
        l1 = Math.min(posX[2], posX[3]); /* L. Schenkel unten + oben */
        if (l1 < 0) {
            ms1 = -l1;
            // p2x = (int) - l1;
            p2x = parseInt((-l1).toString(), 10);
        }
        /* Linker Schenkel */
        l2 = Math.max(posX[8], posX[7]); /* R. Schenkel unten + oben */
        if (l2 - this.ab > 0) {
            ms1 = ms1 + l2 - this.ab; /* Rechter Schenkel */
        }
        ms1 = ms1 + this.ab; /* + ab */

        /* Plattenbreite jetzt ablesbar */
        const grX = ms1; /* l. Plattenbreite */
        this.grX = grX;
        const grx = ms; /* r. Plattenbreite */
        this.grx = ms;

        ms1 = Math.min(tx / ms, tx / ms1); /* X-Masstabsfaktor */

        /* rechter Aufriss */
        ms = Math.max(Math.max(posy[3], posy[4]), Math.max(posy[5], posy[6]));
        ms = Math.max(ms, posy[7]);

        if (Math.min(posy[2], posy[8]) <= 0) {
            // py = (int) Math.min(posy[2], posy[8]);
            py = parseInt(Math.min(posy[2], posy[8]).toString(), 10);
            py = -py;
            ms = ms + py;
        }

        /* linker Aufriss */
        ms2 = Math.max(Math.max(posY[3], posY[4]), Math.max(posY[5], posY[6]));
        ms2 = Math.max(ms2, posY[7]);

        if (Math.min(posY[2], posY[8]) <= 0) {
            // py2 = (int) Math.min(posY[2], posY[8]);
            py2 = parseInt(Math.min(posY[2], posY[8]).toString(), 10);
            py2 = -py2;
            ms2 = ms2 + py2;
        }

        /* Plattenh”he jetzt ablesbar */
        const grY = ms2; /* ms2 enth„lt l. Gesamth”he->Plattengr.Y */
        this.grY = grY;
        const gry = ms; /* ms enth„lt r. Gesamth”he->Plattengr.y */
        this.gry = gry;

        ms2 = Math.min(ty / ms, ty / ms2); /* Y-Masstabsfaktor */

        ms = Math.min(ms1, ms2); /* Masstabsfaktor */

        /*----------------------- Startpunkt ----------------------------------------*/
        /* linker Aufriss */
        const stX: number = p2x;
        this.stX = stX;
        const stY: number = py2;
        this.stY = stY;

        /* rechter Aufriss */
        const sty = py;
        this.sty = sty;
        const stx = p1x;
        this.stx = stx;

        /*----------------- Koordinatengrundpunkte ----------------------------------*/
        // py = (int)((maxy - 10) - py * ms); /* Versch. rechter A. durch Sch. n. unten */
        py = parseInt(((maxy - 10) - py * ms).toString(), 10); /* Versch. rechter A. durch Sch. n. unten */
        // py2 = (int)((maxy - 10) - py2 * ms); /* Versch. linker A. durch Schenkel n. unten */
        py2 = parseInt(((maxy - 10) - py2 * ms).toString(), 10); /* Versch. linker A. durch Schenkel n. unten */
        // p2x = (int)(10 + p2x * ms); /* Versch. linker A. durch l. Schenkel */
        p2x = parseInt((10 + p2x * ms).toString(), 10); /* Versch. linker A. durch l. Schenkel */
        // p1x = (int)((maxx / 2) + 10 + p1x * ms); /* Versch. rechter A. durch l. Schenkel */
        p1x = parseInt(((maxx / 2) + 10 + p1x * ms).toString(), 10); /* Versch. rechter A. durch l. Schenkel */

        /*----------------- Verh„ltnisanpassung x-y aller y -------------------------*/
        for (i = 2; i < 10; i++) {
            posy[i] = posy[i] * xy;
        }
        for (i = 2; i < 10; i++) {
            posY[i] = posY[i] * xy;
        }

        /*----------------- Komplette Masstabsanpassung x-y -------------------------*/
        for (i = 2; i < 10; i++) {
            posy[i] = posy[i] * ms;
            posx[i] = posx[i] * ms;
        }

        for (i = 2; i < 10; i++) {
            posY[i] = posY[i] * ms;
            posX[i] = posX[i] * ms;
        }

        result += '\n\n';
        result += '       LX: ' + Kanal.roundToTwoDecimals(grX) +
            '   LY: ' + Kanal.roundToTwoDecimals(grY) +
            '       lx: ' + Kanal.roundToTwoDecimals(grx) +
            ' ly: ' + Kanal.roundToTwoDecimals(gry);
        result += '\n';
        result += '       AX: ' + Kanal.roundToTwoDecimals(stX) +
            '   AY: ' + Kanal.roundToTwoDecimals(stY) +
            '       ax: ' + Kanal.roundToTwoDecimals(stx) +
            ' ay: ' + Kanal.roundToTwoDecimals(sty);
        return result;
    }
}
