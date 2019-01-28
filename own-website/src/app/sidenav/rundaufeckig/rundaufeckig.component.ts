import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { Kanal } from '../rundaufeckig/Kanal';

@Component({
  selector: 'app-rundaufeckig',
  templateUrl: './rundaufeckig.component.html',
  styleUrls: ['./rundaufeckig.component.scss']
})
export class RundaufEckigComponent implements OnInit {

  private kanal: Kanal;
  private a: number;
  private b: number;
  private d: number;

  public hint_e: number;
  public hint_f: number;

  public displayedColumns: string[] = ['column1', 'column2', 'column3', 'column4'];

  private DEFAULT_VALIDATOR_ARRAY = [
    Validators.required,
    Validators.minLength(1),
    Validators.maxLength(4)];

  kanalForm = this.formBuilder.group({
    a: ['400', this.DEFAULT_VALIDATOR_ARRAY],
    b: ['200', this.DEFAULT_VALIDATOR_ARRAY],
    d: ['200', this.DEFAULT_VALIDATOR_ARRAY],
    l: ['300', this.DEFAULT_VALIDATOR_ARRAY],
    dicke: ['0', this.DEFAULT_VALIDATOR_ARRAY],
    e: ['0', this.DEFAULT_VALIDATOR_ARRAY],
    f: ['-100', this.DEFAULT_VALIDATOR_ARRAY],
  });

  /*
  e = (d - b) / 2;
  f = (d - a) / 2;
  */

  constructor(private formBuilder: FormBuilder) {

  }

  public resetForm(): void {
    this.kanalForm.reset();
  }

  public ngOnInit(): void {
    this.kanalForm.get('a').valueChanges.subscribe(
      (value) => {
        this.a = parseInt(value, 10);
        if (this.a && this.d) {
          this.calculateHintF();
        }
      });
    this.kanalForm.get('b').valueChanges.subscribe(
      (value) => {
        this.b = parseInt(value, 10);
        if (this.b && this.d) {
          this.calculateHintE();
        }
      });
    this.kanalForm.get('d').valueChanges.subscribe(
      (value) => {
        this.d = parseInt(value, 10);
        if (this.b) {
          this.calculateHintE();
        }
        if (this.a) {
          this.calculateHintF();
        }
      });
  }

  private calculateHintE(): void {
    this.hint_e = (this.d - this.b) / 2;
  }

  private calculateHintF(): void {
    this.hint_f = (this.d - this.a) / 2;
  }

  public onSubmit(): void {
    this.kanal = new Kanal(
      this.kanalForm.controls.a.value,
      this.kanalForm.controls.b.value,
      this.kanalForm.controls.d.value,
      this.kanalForm.controls.l.value,
      this.kanalForm.controls.dicke.value,
      this.kanalForm.controls.e.value,
      this.kanalForm.controls.f.value);
  }
}
