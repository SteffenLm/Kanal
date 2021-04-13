import { Kanal } from './Kanal';

describe('Kanal Class ', () => {

    it('Should calculate the correct output (oldstyle)', () => {
        const test1 = 'Errechnete Werte für das Übergangsstück Rechteck auf Rund!\n' +
            '       AB: 200  Schenkellänge: 200  Step: 25.9\n' +
            '       RA: 300   RB: 300  |   ra: 300   rb: 300\n' +
            '\n       A0: 331.7   B0: 331.7  |   a0: 331.7   b0: 331.7' +
            '\n       A1: 325.9   B1: 325.9  |   a1: 325.9   b1: 325.9' +
            '\n       A2: 324.6   B2: 324.6  |   a2: 324.6   b2: 324.6' +
            '\n       A3: 328   B3: 328  |   a3: 328   b3: 328' +
            '\n       A4: 335.7   B4: 335.7  |   a4: 335.7   b4: 335.7' +
            '\n       A5: 346.9   B5: 346.9  |   a5: 346.9   b5: 346.9' +
            '\n       A6: 360.6   B6: 360.6  |   a6: 360.6   b6: 360.6' +
            '\n\n' +
            '       LX: 551.6   LY: 359.1       lx: 551.6 ly: 359.1' +
            '\n' +
            '       AX: 175   AY: 0       ax: 175 ay: 0' +
            '\n\n       LX: 551.6   LY: 359.1       lx: 551.6 ly: 359.1' +
            '\n' +
            '       AX: 175   AY: 0       ax: 175 ay: 0';

        const kanal = new Kanal('400', '200', '200', '300', '0', '0', '-100');
        const test2 = kanal.calculateAll();

        for (let i = 0; i < 750; i++) {
            const j = i + 1;
            if (test1.slice(i, j) === test2.slice(i, j)) {

            } else {
                console.log(`Error at ${i}`);
                break;
            }
        }
        // console.log(test1.slice(630, 640));
        // console.log(test2.slice(630, 640));
        // console.log(test1.slice(65, 68));
        // console.log(test2.slice(65, 67));
        // 736 Zeichen
        console.log(test1);
        // console.log(test2);
        expect(test1).toBe(test2);
    });

    // it(`should have as title 'own-website'`, () => {
    // });

    // it('should render title in a h1 tag', () => {
    // });
});
